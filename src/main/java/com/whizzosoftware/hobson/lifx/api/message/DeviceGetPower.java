/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.lifx.api.message;

import java.net.InetSocketAddress;

/**
 * Get device power level. Causes the device to transmit a DeviceStatePower message.
 *
 * @author Dan Noguerol
 */
public class DeviceGetPower extends Message {
    public static final int TYPE = 20;

    public DeviceGetPower(InetSocketAddress recipient) {
        super();
        setRecipient(recipient);
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
