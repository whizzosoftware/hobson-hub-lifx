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

abstract public class Message {
    private MessageHeader header;
    private InetSocketAddress sender;
    private InetSocketAddress recipient;

    public Message() {
        this(false);
    }

    public Message(boolean tagged) {
        this.header = new MessageHeader(getLength(), tagged, true, 0, 0, false, false, (byte)0, getType());
    }

    public Message(MessageHeader header) {
        this.header = header;
    }

    public MessageHeader getHeader() {
        return header;
    }

    public int getLength() {
        return MessageHeader.LENGTH + getPayloadLength();
    }

    public InetSocketAddress getSender() {
        return sender;
    }

    public Message setSender(InetSocketAddress sender) {
        this.sender = sender;
        return this;
    }

    public InetSocketAddress getRecipient() {
        return recipient;
    }

    public Message setRecipient(InetSocketAddress recipient) {
        this.recipient = recipient;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).toString();
    }

    abstract public int getType();
    abstract public int getPayloadLength();
}
