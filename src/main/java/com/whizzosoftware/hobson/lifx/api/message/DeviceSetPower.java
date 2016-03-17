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
 * Sets the device power level.
 *
 * @author Dan Noguerol
 */
public class DeviceSetPower extends Message {
    public static final int TYPE = 21;

    private int level;

    /**
     * Constructor.
     *
     * @param level zero implies standby and non-zero sets a corresponding power draw level. Currently only 0 and 65535
     *              are supported.
     */
    public DeviceSetPower(InetSocketAddress recipient, int level) {
        super();
        setRecipient(recipient);
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
        return 2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("level", level).toString();
    }
}
