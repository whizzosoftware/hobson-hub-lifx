/*
 *******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.lifx;

import com.whizzosoftware.hobson.api.device.DeviceType;
import com.whizzosoftware.hobson.api.device.proxy.AbstractHobsonDeviceProxy;
import com.whizzosoftware.hobson.api.plugin.HobsonPlugin;
import com.whizzosoftware.hobson.api.property.TypedProperty;
import com.whizzosoftware.hobson.api.variable.VariableConstants;
import com.whizzosoftware.hobson.api.variable.VariableMask;
import com.whizzosoftware.hobson.lifx.api.message.HSBK;
import com.whizzosoftware.hobson.lifx.api.message.LightState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class LIFXBulb extends AbstractHobsonDeviceProxy {
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
        super(plugin, Long.toHexString(id), name, DeviceType.LIGHTBULB);
        this.address = address;
        this.initialState = state;
    }

    @Override
    protected TypedProperty[] getConfigurationPropertyTypes() {
        return null;
    }

    @Override
    public String getManufacturerName() {
        return "LIFX";
    }

    @Override
    public String getManufacturerVersion() {
        return null;
    }

    @Override
    public String getModelName() {
        return null;
    }

    @Override
    public String getPreferredVariableName() {
        return VariableConstants.ON;
    }

    @Override
    public void onDeviceConfigurationUpdate(Map<String,Object> config) {

    }

    @Override
    public void onStartup(String name, Map<String,Object> config) {
        long now = System.currentTimeMillis();
        publishVariables(
            createDeviceVariable(VariableConstants.ON, VariableMask.READ_WRITE, initialState != null && initialState.getPower() > 0, initialState != null ? now : null),
            createDeviceVariable(VariableConstants.COLOR, VariableMask.READ_WRITE, initialState != null ? initialState.getColor().toRGBString() : null, initialState != null ? now : null)
        );
        initialState = null;
    }

    @Override
    public void onShutdown() {
    }

    @Override
    public void onSetVariables(Map<String,Object> values) {
        logger.info("LIFX bulb {} set variables: {}", getContext(), values);
        for (String name : values.keySet()) {
            Object value = values.get(name);
            switch (name) {
                case VariableConstants.ON:
                    ((LIFXPlugin) getPlugin()).sendLightSetPower(address, (boolean) value);
                    break;
                case VariableConstants.COLOR:
                    ((LIFXPlugin) getPlugin()).sendLightSetColor(address, new HSBK((String) value));
                    break;
            }
        }
    }

    void update(LightState state) {
        long now = System.currentTimeMillis();
        logger.trace("Device {} update: {}", getContext(), state);

        setLastCheckin(now);

        Map<String,Object> values = new HashMap<>();
        values.put(VariableConstants.ON, state.getPower() > 0);
        values.put(VariableConstants.COLOR, state.getColor().toColorString());
        setVariableValues(values);
    }
}
