/*
 *******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************
*/
package com.whizzosoftware.hobson.lifx.api.message;

import java.net.InetSocketAddress;

/**
 * Sent by a client to acquire responses from all devices on the local network. Causes the devices to transmit
 * one or more DeviceStateService messages.
 *
 * @author Dan Noguerol
 */
public class DeviceGetService extends Message {
    public static final int TYPE = 0x02;

    public DeviceGetService() {
        super(true);
        setRecipient(new InetSocketAddress("255.255.255.255", 56700));
    }

    @Override
    public int getType() {
        return TYPE;
    }

    @Override
    public int getPayloadLength() {
        return 0;
    }
}
