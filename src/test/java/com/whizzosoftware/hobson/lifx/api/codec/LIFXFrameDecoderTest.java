/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.lifx.api.codec;

import com.whizzosoftware.hobson.lifx.api.message.LightState;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import org.junit.Test;
import static org.junit.Assert.*;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class LIFXFrameDecoderTest {
    @Test
    public void testLightState() throws Exception {
        byte[] expected = new byte[] {0x58, 0x00, 0x00, 0x54, 0x42, 0x52, 0x4B, 0x52, (byte)0xD0, 0x73, (byte)0xD5, 0x12, 0x09, 0x34, 0x00, 0x00, 0x4C, 0x49, 0x46, 0x58, 0x56, 0x32, 0x00, 0x00, 0x58, (byte)0x92, 0x17, (byte)0xD9, 0x2E, 0x0F, 0x3C, 0x14, 0x6B, 0x00, 0x00, 0x00, (byte)0xD3, (byte)0xFD, 0x00, 0x00, (byte)0xFF, (byte)0xFF, (byte)0xAC, 0x0D, 0x00, 0x00, (byte)0xFF, (byte)0xFF, 0x44, 0x61, 0x6E, 0x27, 0x73, 0x20, 0x4F, 0x66, 0x66, 0x69, 0x63, 0x65, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        LIFXFrameDecoder d = new LIFXFrameDecoder();
        List<Object> list = new ArrayList<>();
        d.decode(null, new DatagramPacket(Unpooled.wrappedBuffer(expected), new InetSocketAddress("255.255.255.255", 56700)), list);
        assertEquals(1, list.size());
        assertTrue(list.get(0) instanceof LightState);
        assertEquals("Dan's Office", ((LightState)list.get(0)).getLabel());
    }

    @Test
    public void testReadShortLE() {
        LIFXFrameDecoder d = new LIFXFrameDecoder();
        assertEquals(65535, d.read16BitLE(Unpooled.wrappedBuffer(new byte[] { -1, -1 })));
        assertEquals(32767, d.read16BitLE(Unpooled.wrappedBuffer(new byte[] { -1, 127 })));
        assertEquals(0, d.read16BitLE(Unpooled.wrappedBuffer(new byte[] { 0, 0 })));
    }
}
