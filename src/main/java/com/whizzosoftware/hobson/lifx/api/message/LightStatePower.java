/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.lifx.api.message;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.net.InetSocketAddress;

/**
 * Sent by a device to provide the current power level.
 *
 * @author Dan Noguerol
 */
public class LightStatePower extends Message {
    public static final int TYPE = 118;

    private int level;

    public LightStatePower(InetSocketAddress sender, MessageHeader header, int level) {
        super(header);
        setSender(sender);
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public int getType() {
        return TYPE;
    }

    @Override
    public int getPayloadLength() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("level", level).toString();
    }
}
