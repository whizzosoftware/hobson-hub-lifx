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
import io.netty.channel.*;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * A Netty decoder for LIFX messages.
 *
 * @author Dan Noguerol
 */
public class LIFXFrameDecoder extends MessageToMessageDecoder {
    static final Logger logger = LoggerFactory.getLogger(LIFXFrameDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext context, Object o, List list) throws Exception {
        if (o instanceof DatagramPacket) {
            DatagramPacket p = (DatagramPacket)o;
            ByteBuf buf = p.content();
            if (buf.readableBytes() >= 32) {
                int length = buf.getByte(0) + (buf.getByte(1) << 8);
                int type = buf.getByte(32) + (buf.getByte(33) << 8);

                if (buf.readableBytes() >= length) {
                    switch (type) {
                        case DeviceStateService.TYPE:
                            list.add(readDeviceStateService(p.sender(), buf));
                            break;
                        case LightState.TYPE:
                            list.add(readLightState(p.sender(), buf));
                            break;
                        case LightStatePower.TYPE:
                            list.add(readLightStatePower(p.sender(), buf));
                            break;
                        default:
                            logger.debug("Unknown message type received: {}", type);
                    }
                } else {
                    logger.trace("Ignoring datagram with invalid length: {}", length);
                }
            } else {
                logger.trace("Ignoring datagram with invalid length: {}", buf.readableBytes());
            }
        }
    }

    protected Object readDeviceStateService(InetSocketAddress sender, ByteBuf buf) {
        return new DeviceStateService(sender, readHeader(buf), buf.readByte(), read32BitLE(buf));
    }

    protected Object readLightState(InetSocketAddress sender, ByteBuf buf) {
        MessageHeader header = readHeader(buf);
        HSBK color = readColor(buf);
        read16BitLE(buf); // reserved
        int power = read16BitLE(buf);
        String label = readString(buf, 32);
        read64BitLE(buf); // reserved
        return new LightState(sender, header, color, power, label).setSender(sender);
    }

    protected Object readLightStatePower(InetSocketAddress sender, ByteBuf buf) {
        return new LightStatePower(sender, readHeader(buf), read16BitLE(buf)).setSender(sender);
    }

    protected MessageHeader readHeader(ByteBuf buf) {
        // read message length
        int length = read16BitLE(buf);
        // read origin/tagged/addressable/protocol
        buf.readByte();
        buf.readByte();
        // read source
        int source = read32BitLE(buf);
        // read target
        long target = read64BitLE(buf);
        // read reserved
        buf.readByte();
        buf.readByte();
        buf.readByte();
        buf.readByte();
        buf.readByte();
        buf.readByte();
        // read ack_required/res_required
        buf.readByte();
        // read sequence
        byte sequence = buf.readByte();
        // read reserved
        buf.readByte();
        buf.readByte();
        buf.readByte();
        buf.readByte();
        buf.readByte();
        buf.readByte();
        buf.readByte();
        buf.readByte();
        // read type
        int type = read16BitLE(buf);
        // read reserved
        buf.readByte();
        buf.readByte();

        // TODO: set correct booleans
        return new MessageHeader(length, false, false, source, target, true, true, sequence, type);
    }

    protected int read16BitLE(ByteBuf buf) {
        return ((buf.readByte() & 0xFF) + ((buf.readByte() << 8)) & 0xFFFF);
    }

    protected int read32BitLE(ByteBuf buf) {
        return (buf.readByte() + (buf.readByte() >> 8) + (buf.readByte() >> 16) + (buf.readByte() >> 24));
    }

    protected long read64BitLE(ByteBuf buf) {
        return (buf.readByte() + (buf.readByte() >> 8) + (buf.readByte() >> 16) + (buf.readByte() >> 24) + (buf.readByte() >> 32) + (buf.readByte() >> 40) + (buf.readByte() >> 48) + (buf.readByte() >> 56));
    }

    protected String readString(ByteBuf buf, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < len; i++) {
            byte b = buf.readByte();
            if (b > 0) {
                sb.append((char)b);
            }
        }
        return sb.toString();
    }

    protected HSBK readColor(ByteBuf buf) {
        return new HSBK(read16BitLE(buf), read16BitLE(buf), read16BitLE(buf), read16BitLE(buf));
    }
}
