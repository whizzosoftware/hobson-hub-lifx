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

public class LightSetColor extends Message {
    public static final int TYPE = 102;

    private HSBK color;
    private int duration;

    public LightSetColor(InetSocketAddress recipient, HSBK color, int duration) {
        super(true);
        setRecipient(recipient);
        this.color = color;
        this.duration = duration;
    }

    public HSBK getColor() {
        return color;
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
        return 13;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("color", color).append("duration", duration).toString();
    }
}
