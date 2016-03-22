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
import java.util.StringTokenizer;

public class HSBK {
    public static final int DEFAULT_KELVIN = 3500;

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

    public HSBK(String s) {
        if (s.startsWith("hsb(") && s.endsWith(")")) {
            StringTokenizer tok = new StringTokenizer(s.substring(4, s.length() - 1), ",");
            hue = (int)(Integer.parseInt(tok.nextToken().trim()) * 182.0416666667);
            saturation = (int)(Integer.parseInt(tok.nextToken().trim()) * 655.35);
            brightness = (int)(Integer.parseInt(tok.nextToken().trim()) * 655.35);
            kelvin = DEFAULT_KELVIN;
        } else if (s.startsWith("kb(") && s.endsWith(")")) {
            StringTokenizer tok = new StringTokenizer(s.substring(3, s.length() - 1), ",");
            hue = 0;
            saturation = 0;
            kelvin = Integer.parseInt(tok.nextToken().trim());
            brightness = (int)(Integer.parseInt(tok.nextToken().trim()) * 655.35);
        } else {
            throw new IllegalArgumentException("Not a valid color string");
        }
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

    public String toColorString() {
        if (saturation > 0) {
            return "hsb(" + (int)(hue / 182.0416666667) + "," + (int)(saturation / 655.35) + "," + (int)(brightness / 655.35) + ")";
        } else {
            return "kb(" + kelvin + "," + (int)(brightness / 655.35) + ")";
        }
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
