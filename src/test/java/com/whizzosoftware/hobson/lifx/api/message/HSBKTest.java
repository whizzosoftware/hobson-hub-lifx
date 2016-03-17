/*******************************************************************************
 * Copyright (c) 2016 Whizzo Software, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package com.whizzosoftware.hobson.lifx.api.message;

import org.junit.Test;
import static org.junit.Assert.*;

public class HSBKTest {
    @Test
    public void testHSBKConversion() {
        HSBK color = new HSBK(255, 0, 0);
        assertEquals(0, color.getHue());
        assertEquals(65535, color.getSaturation());
        assertEquals(65535, color.getBrightness());
        assertEquals("rgb(255,0,0)", color.toRGBString());

        color = new HSBK(0, 0, 255);
        assertEquals(43690, color.getHue());
        assertEquals(65535, color.getSaturation());
        assertEquals(65535, color.getBrightness());
        assertEquals("rgb(0,0,255)", color.toRGBString());

        color = new HSBK(21845, 65535, 65535, 3500);
        assertEquals("rgb(0,255,0)", color.toRGBString());
    }
}
