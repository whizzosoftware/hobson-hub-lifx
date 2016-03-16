/*******************************************************************************
 * Copyright (c) 2013 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.lifx;

import com.whizzosoftware.hobson.api.plugin.PluginStatus;
import com.whizzosoftware.hobson.api.plugin.channel.AbstractChannelObjectPlugin;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.lifx.api.codec.LIFXFrameDecoder;
import com.whizzosoftware.hobson.lifx.api.codec.LIFXFrameEncoder;
import com.whizzosoftware.hobson.lifx.api.message.*;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * A Hobson plugin that can control LIFX bulbs.
 *
 * @author Dan Noguerol
 */
public class LIFXPlugin extends AbstractChannelObjectPlugin {
    private static final Logger logger = LoggerFactory.getLogger(LIFXPlugin.class);

    private static final int SERVICE_UDP = 1;

    private Map<String,LIFXBulb> devices = new HashMap<>();

    public LIFXPlugin(String pluginId) {
        super(pluginId);
    }

    @Override
    public String getName() {
        return "LIFX Plugin";
    }

    @Override
    protected ChannelInboundHandlerAdapter getDecoder() {
        return new LIFXFrameDecoder();
    }

    @Override
    protected ChannelOutboundHandlerAdapter getEncoder() {
        return new LIFXFrameEncoder();
    }

    @Override
    protected int getDefaultPort() {
        return 56700;
    }

    @Override
    protected void configureChannel(ChannelConfig cfg) {
    }

    @Override
    protected boolean isConnectionless() {
        return true;
    }

    @Override
    protected void onChannelConnected() {
        logger.trace("Channel connected");
        setStatus(PluginStatus.running());
        getDeviceService();
    }

    @Override
    protected void onChannelData(Object o) {
        Message m = (Message)o;
        logger.trace("Channel data received from {}: {}", m.getSender(), m);
        String id = Long.toHexString(m.getHeader().getTarget());
        switch (m.getType()) {
            case DeviceStateService.TYPE:
                DeviceStateService dss = (DeviceStateService)m;
                // only pay attention to UDP services
                if (dss.getService() == SERVICE_UDP && !devices.containsKey(id)) {
                    logger.debug("Discovered new bulb {} at {}; interrogating it", id, m.getSender());
                    send(new LightGet(m.getSender()));
                }
                break;
            case LightState.TYPE:
                LightState ls = (LightState)m;
                LIFXBulb bulb = devices.get(id);
                logger.trace("Received bulb status from {}: {}", id, ls);
                if (bulb != null) {
                    bulb.update(ls);
                } else {
                    logger.debug("Publishing new bulb: {}", id);
                    bulb = new LIFXBulb(this, m.getHeader().getTarget(), m.getSender(), ls.getLabel(), ls);
                    publishDevice(bulb);
                    devices.put(id, bulb);
                }
                break;
        }
    }

    @Override
    protected void onChannelDisconnected() {
        logger.trace("Channel disconnected");
    }

    @Override
    public long getRefreshInterval() {
        return 5;
    }

    @Override
    public void onRefresh() {
        send(new LightGet(new InetSocketAddress("255.255.255.255", getDefaultPort())));
    }

    public void getDeviceService() {
        send(new DeviceGetService());
    }

    public void sendLightSetPower(InetSocketAddress recipient, boolean on) {
        send(new LightSetPower(recipient, on ? 65535 : 0, 0));
    }

    public void sendLightSetColor(InetSocketAddress recipient, HSBK color) {
        send(new LightSetColor(recipient, color, 0));
    }

    @Override
    protected TypedProperty[] createSupportedProperties() {
        return null;
    }
}
