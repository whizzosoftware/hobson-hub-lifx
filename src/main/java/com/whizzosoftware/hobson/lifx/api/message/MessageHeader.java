/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.lifx.api.message;

/**
 * A LIFX message header.
 *
 * @author Dan Noguerol
 */
public class MessageHeader {
    public static final int LENGTH = 36;

    // Frame section
    private int length;
    private boolean tagged;
    private boolean addressable = true;
    private int source = 0;
    // Frame address section
    private long target = 0;
    private boolean ackRequired = false;
    private boolean resRequired = false;
    private byte sequence = 0;
    // Protocol header
    private int type;

    public MessageHeader(int length, boolean tagged, boolean addressable, int source, long target, boolean ackRequired, boolean resRequired, byte sequence, int type) {
        this.length = length;
        this.tagged = tagged;
        this.addressable = addressable;
        this.source = source;
        this.target = target;
        this.ackRequired = ackRequired;
        this.resRequired = resRequired;
        this.sequence = sequence;
        this.type = type;
    }

    public int getMessageLength() {
        return length;
    }

    public boolean isTagged() {
        return tagged;
    }

    public boolean isAddressable() {
        return addressable;
    }

    public int getSource() {
        return source;
    }

    public long getTarget() {
        return target;
    }

    public boolean isACKRequired() {
        return ackRequired;
    }

    public boolean isResponseRequired() {
        return resRequired;
    }

    public byte getSequence() {
        return sequence;
    }

    public int getType() {
        return type;
    }

    public int getProtocol() {
        return 1024;
    }
}
