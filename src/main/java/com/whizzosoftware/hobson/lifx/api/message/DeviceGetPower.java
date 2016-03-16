package com.whizzosoftware.hobson.lifx.api.message;

import java.net.InetSocketAddress;

/**
 * Get device power level. Causes the device to transmit a DeviceStatePower message.
 *
 * @author Dan Noguerol
 */
public class DeviceGetPower extends Message {
    public static final int TYPE = 20;

    public DeviceGetPower(InetSocketAddress recipient) {
        super();
        setRecipient(recipient);
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
