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
 * Sent by a client to obtain the power level. Causes the device to transmit a LightStatePower message.
 *
 * @author Dan Noguerol
 */
public class LightGetPower extends Message {
    public static final int TYPE = 116;

    public LightGetPower(InetSocketAddress recipient) {
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
