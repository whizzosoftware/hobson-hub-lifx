/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.lifx.api.codec;

import com.whizzosoftware.hobson.lifx.api.message.DeviceGetService;
import com.whizzosoftware.hobson.lifx.api.message.LightSetColor;
import com.whizzosoftware.hobson.lifx.api.message.HSBK;
import io.netty.channel.socket.DatagramPacket;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class LIFXFrameEncoderTest {
    @Test
    public void testDeviceGetService() throws Exception {
        byte[] expected = {
            0x24, 0x00, // message size
            0x00, 0x34, // origin/tagged/addressable/protocol
            0x00, 0x00, 0x00, 0x00, // source identifier
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // MAC address
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // reserved
            0x00, 0x00, // reserved/ack_required/res_required/sequence
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, // reserved
            0x02, 0x00, // type
            0x00, 0x00 // reserved
        };
        LIFXFrameEncoder d = new LIFXFrameEncoder();
        List<Object> list = new ArrayList<>();
        d.encode(null, new DeviceGetService(), list);
        assertEquals(1, list.size());
        assertTrue(list.get(0) instanceof DatagramPacket);
        DatagramPacket dp = (DatagramPacket)list.get(0);
        byte[] b = dp.content().array();
        assertEquals(expected.length, b.length);
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], b[i]);
        }
    }

    @Test
    public void testSetColorTest() throws Exception {
        byte[] expected = { 0x31, 0x00, 0x00, 0x34, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x66, 0x00, 0x00, 0x00, 0x00, 0x55, 0x55, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xAC, 0x0D, 0x00, 0x04, 0x00, 0x00 };
        LIFXFrameEncoder d = new LIFXFrameEncoder();
        List<Object> list = new ArrayList<>();
        d.encode(null, new LightSetColor(null, new HSBK(21845, 65535, 65535, 3500), 1024), list);
        assertEquals(1, list.size());
        assertTrue(list.get(0) instanceof DatagramPacket);
        DatagramPacket dp = (DatagramPacket)list.get(0);
        byte[] b = dp.content().array();
        assertEquals(expected.length, b.length);
        for (int i=0; i < expected.length; i++) {
            assertEquals(expected[i], b[i]);
        }
    }
}
