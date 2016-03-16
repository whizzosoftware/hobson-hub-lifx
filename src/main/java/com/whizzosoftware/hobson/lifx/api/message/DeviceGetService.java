package com.whizzosoftware.hobson.lifx.api.message;

import java.net.InetSocketAddress;

/**
 * Sent by a client to acquire responses from all devices on the local network. Causes the devices to transmit
 * one or more DeviceStateService messages.
 *
 * @author Dan Noguerol
 */
public class DeviceGetService extends Message {
    public static final int TYPE = 2;

    public DeviceGetService() {
        super(true);
        setRecipient(new InetSocketAddress("255.255.255.255", 56700));
    }

    @Override
    public int getType() {
        return TYPE;
    }

    @Override
    public int getPayloadLength() {
        return 0;
    }
}
