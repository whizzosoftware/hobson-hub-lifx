package com.whizzosoftware.hobson.lifx.api.message;

import java.net.InetSocketAddress;

/**
 * Sent by a client to obtain the power level. Causes the device to transmit a LightStatePower message.
 *
 * @author Dan Noguerol
 */
public class LightGetPower extends Message {
    public static final int TYPE = 116;

    public LightGetPower(InetSocketAddress recipient) {
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
