package com.whizzosoftware.hobson.lifx.api.message;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.net.InetSocketAddress;

/**
 * Response to a GetDeviceServiceRequest message.
 *
 * @author Dan Noguerol
 */
public class DeviceStateService extends Message {
    public static final int TYPE = 0x03;

    private byte service;
    private int port;

    public DeviceStateService(InetSocketAddress sender, MessageHeader header, byte service, int port) {
        super(header);
        setSender(sender);
        this.service = service;
        this.port = port;
    }

    public byte getService() {
        return service;
    }

    public int getPort() {
        return port;
    }

    @Override
    public int getType() {
        return TYPE;
    }

    @Override
    public int getPayloadLength() {
        return 5;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("service", service).append("port", port).toString();
    }
}
