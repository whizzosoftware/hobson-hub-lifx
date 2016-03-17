/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.lifx.api.codec;

import com.whizzosoftware.hobson.lifx.api.message.*;
import com.whizzosoftware.hobson.lifx.api.message.HSBK;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * A Netty encoder for LIFX messages.
 *
 * @author Dan Noguerol
 */
public class LIFXFrameEncoder extends MessageToMessageEncoder<Message> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message m, List<Object> list) throws Exception {
        ByteBuf buf = null;

        switch(m.getType()) {
            case DeviceGetPower.TYPE:
            case DeviceGetService.TYPE:
            case LightGet.TYPE:
                buf = Unpooled.buffer(m.getLength());
                writeMessageHeader(buf, m.getHeader());
                break;
            case DeviceSetPower.TYPE:
                buf = Unpooled.buffer(m.getLength());
                writeDeviceSetPower(buf, (DeviceSetPower)m);
                break;
            case LightSetColor.TYPE:
                buf = Unpooled.buffer(m.getLength());
                writeLightSetColor(buf, (LightSetColor)m);
                break;
            case LightSetPower.TYPE:
                buf = Unpooled.buffer(m.getLength());
                writeLightSetPower(buf, (LightSetPower)m);
                break;
            default:
                logger.error("Unsupported message passed to encoder: {}", m);
        }

        if (buf != null) {
            list.add(new DatagramPacket(buf, m.getRecipient()));
        } else {
            logger.error("Unknown message passed to encoder: {}", m);
        }
    }

    protected void writeDeviceSetPower(ByteBuf buf, DeviceSetPower m) {
        writeMessageHeader(buf, m.getHeader());
        writeShortLE(buf, m.getLevel());
    }

    protected void writeLightSetColor(ByteBuf buf, LightSetColor m) {
        writeMessageHeader(buf, m.getHeader());
        buf.writeByte(0); // reserved
        writeColor(buf, m.getColor());
        writeIntLE(buf, m.getDuration());
    }

    protected void writeLightSetPower(ByteBuf buf, LightSetPower m) {
        writeMessageHeader(buf, m.getHeader());
        writeShortLE(buf, m.getLevel());
        writeIntLE(buf, m.getDuration());
    }

    protected void writeMessageHeader(ByteBuf buf, MessageHeader m) {
        // write message length
        writeShortLE(buf, m.getMessageLength());
        // write origin/tagged/addressable/protocol
        buf.writeByte((byte)(m.getProtocol() & 255));
        buf.writeByte((byte)((m.isTagged() ? 32 : 0) + (m.isAddressable() ? 16 : 0) + (m.getProtocol() >> 8)));
        // write source
        writeIntLE(buf, m.getSource());
        // write target
        writeLongLE(buf, m.getTarget());
        // write reserved
        buf.writeByte(0);
        buf.writeByte(0);
        buf.writeByte(0);
        buf.writeByte(0);
        buf.writeByte(0);
        buf.writeByte(0);
        // write ack_required/res_required
        buf.writeByte((byte)((m.isACKRequired() ? 2 : 0) + (m.isResponseRequired() ? 1 : 0)));
        // write sequence
        buf.writeByte(m.getSequence());
        // write reserved
        buf.writeByte(0);
        buf.writeByte(0);
        buf.writeByte(0);
        buf.writeByte(0);
        buf.writeByte(0);
        buf.writeByte(0);
        buf.writeByte(0);
        buf.writeByte(0);
        // write type
        writeShortLE(buf, m.getType());
        // write reserved
        buf.writeByte(0);
        buf.writeByte(0);
    }

    protected void writeShortLE(ByteBuf buf, int i) {
        writeShortLE(buf, (short)i);
    }

    protected void writeShortLE(ByteBuf buf, short s) {
        buf.writeByte(s);
        buf.writeByte(s >> 8);
    }

    protected void writeIntLE(ByteBuf buf, int i) {
        buf.writeByte(i);
        buf.writeByte(i >> 8);
        buf.writeByte(i >> 16);
        buf.writeByte(i >> 24);
    }

    protected void writeLongLE(ByteBuf buf, long i) {
        buf.writeByte((byte)i);
        buf.writeByte((byte)(i >> 8));
        buf.writeByte((byte)(i >> 16));
        buf.writeByte((byte)(i >> 24));
        buf.writeByte((byte)(i >> 32));
        buf.writeByte((byte)(i >> 40));
        buf.writeByte((byte)(i >> 48));
        buf.writeByte((byte)(i >> 56));
    }

    protected void writeColor(ByteBuf buf, HSBK color) {
        writeShortLE(buf, (short)color.getHue());
        writeShortLE(buf, (short)color.getSaturation());
        writeShortLE(buf, (short)color.getBrightness());
        writeShortLE(buf, (short)color.getKelvin());
    }
}
