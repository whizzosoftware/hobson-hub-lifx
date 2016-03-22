/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.lifx;

import com.whizzosoftware.hobson.api.device.AbstractHobsonDevice;
import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.property.PropertyContainer;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.HobsonVariable;
import com.whizzosoftware.hobson.api.variable.VariableConstants;
import com.whizzosoftware.hobson.api.variable.VariableContext;
import com.whizzosoftware.hobson.api.variable.VariableUpdate;
import com.whizzosoftware.hobson.lifx.api.message.HSBK;
import com.whizzosoftware.hobson.lifx.api.message.LightState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class LIFXBulb extends AbstractHobsonDevice {
    private static final Logger logger = LoggerFactory.getLogger(LIFXBulb.class);

    private InetSocketAddress address;
    private LightState initialState;

    /**
     * Constructor.
     *
     * @param plugin the HobsonPlugin that created this device
     * @param id     the device ID
     */
    LIFXBulb(HobsonPlugin plugin, long id, InetSocketAddress address, String name, LightState state) {
        super(plugin, Long.toHexString(id));
        this.address = address;
        setDefaultName(name);
        this.initialState = state;
    }

    @Override
    protected TypedProperty[] createSupportedProperties() {
        return null;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.LIGHTBULB;
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.ON;
    }

    @Override
    public void onStartup(PropertyContainer config) {
        long now = System.currentTimeMillis();
        publishVariable(VariableConstants.ON, initialState != null && initialState.getPower() > 0, HobsonVariable.Mask.READ_WRITE, initialState != null ? now : null);
        publishVariable(VariableConstants.COLOR, initialState != null ? initialState.getColor().toRGBString() : null, HobsonVariable.Mask.READ_WRITE, initialState != null ? now : null);
        initialState = null;
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public void onSetVariable(String variableName, Object value) {
        logger.info("LIFX bulb set variable {} to {}", variableName, value);
        switch (variableName) {
            case VariableConstants.ON:
                ((LIFXPlugin)getPlugin()).sendLightSetPower(address, (boolean)value);
                break;
            case VariableConstants.COLOR:
                ((LIFXPlugin)getPlugin()).sendLightSetColor(address, new HSBK((String)value));
                break;
        }
    }

    void update(LightState state) {
        long now = System.currentTimeMillis();
        logger.trace("Device {} update: {}", getContext(), state);
        setDeviceAvailability(true, now);
        List<VariableUpdate> notes = new ArrayList<>();
        notes.add(new VariableUpdate(VariableContext.create(getContext(), VariableConstants.ON), state.getPower() > 0, now));
        notes.add(new VariableUpdate(VariableContext.create(getContext(), VariableConstants.COLOR), state.getColor().toColorString(), now));
        fireVariableUpdateNotifications(notes);
    }
}
