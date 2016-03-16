package com.whizzosoftware.hobson.lifx.api.message;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.net.InetSocketAddress;

public class LightState extends Message {
    public static final int TYPE = 107;

    private HSBK color;
    private int power;
    private String label;

    public LightState(InetSocketAddress sender, MessageHeader header, HSBK color, int power, String label) {
        super(header);
        setSender(sender);
        this.color = color;
        this.power = power;
        this.label = label;
    }

    public HSBK getColor() {
        return color;
    }

    public int getPower() {
        return power;
    }

    public String getLabel() {
        return label;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
            append("color", color).
            append("power", power).
            append("label", label).
            toString();
    }
}
