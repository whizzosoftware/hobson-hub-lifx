package com.whizzosoftware.hobson.lifx.api.message;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.awt.*;

public class HSBK {
    private static final int DEFAULT_KELVIN = 3500;

    private int hue;
    private int saturation;
    private int brightness;
    private int kelvin;

    public HSBK(int r, int g, int b) {
        float[] hsb = Color.RGBtoHSB(r, g, b, null);
        this.hue = (int)((hsb[0]) * 65535);
        this.saturation = (int)((hsb[1]) * 65535);
        this.brightness = (int)((hsb[2]) * 65535);
        this.kelvin = DEFAULT_KELVIN;
    }

    public HSBK(int hue, int saturation, int brightness, int kelvin) {
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
        this.kelvin = kelvin;
    }

    public int getHue() {
        return hue;
    }

    public int getSaturation() {
        return saturation;
    }

    public int getBrightness() {
        return brightness;
    }

    public int getKelvin() {
        return kelvin;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
            append("hue", hue).
            append("saturation", saturation).
            append("brightness", brightness).
            append("kelvin", kelvin).
            toString();
    }
}
