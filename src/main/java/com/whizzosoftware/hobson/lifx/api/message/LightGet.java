package com.whizzosoftware.hobson.lifx.api.message;

import java.net.InetSocketAddress;

/**
 * Sent by a client to obtain the light state. Causes the device to transmit a LightState message.
 *
 * @author Dan Noguerol
 */
public class LightGet extends Message {
    public static final int TYPE = 101;

    public LightGet(InetSocketAddress recipient) {
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
