/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
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
        this.hue = (int)((Math.min(hsb[0], 1.0)) * 65535);
        this.saturation = (int)((Math.min(hsb[1], 1.0)) * 65535);
        this.brightness = (int)((Math.min(hsb[2], 1.0)) * 65535);
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

    public String toRGBString() {
        int rgb = Color.HSBtoRGB(this.hue / 65535.0f, this.saturation / 65535.0f, this.brightness / 65535.0f);
        return "rgb(" + ((rgb >> 16) & 0xFF) + "," + ((rgb >> 8) & 0xFF) + "," + (rgb & 0xFF) + ")";
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
