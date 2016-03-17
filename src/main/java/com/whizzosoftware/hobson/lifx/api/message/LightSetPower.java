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
 * Sent by a client to change the light power level.
 *
 * @author Dan Noguerol
 */
public class LightSetPower extends Message {
    public static final int TYPE = 117;

    private int level;
    private int duration;

    /**
     * Constructor.
     *
     * @param level the power level (must be either 0 or 65535)
     * @param duration the power level transition time in milliseconds
     */
    public LightSetPower(InetSocketAddress recipient, int level, int duration) {
        super();
        setRecipient(recipient);
        this.level = level;
        this.duration = duration;
    }

    public int getLevel() {
        return level;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int getType() {
        return TYPE;
    }

    @Override
    public int getPayloadLength() {
        return 6;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
            append("level", level).
            append("duration", duration).
            toString();
    }
}
