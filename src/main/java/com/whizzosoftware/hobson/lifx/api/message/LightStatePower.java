package com.whizzosoftware.hobson.lifx.api.message;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.net.InetSocketAddress;

/**
 * Sent by a device to provide the current power level.
 *
 * @author Dan Noguerol
 */
public class LightStatePower extends Message {
    public static final int TYPE = 118;

    private short level;

    public LightStatePower(InetSocketAddress sender, MessageHeader header, short level) {
        super(header);
        setSender(sender);
        this.level = level;
    }

    public short getLevel() {
        return level;
    }

    @Override
    public int getType() {
        return TYPE;
    }

    @Override
    public int getPayloadLength() {
        return 0;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("level", level).toString();
    }
}
