/*
 * Copyright (C) 2014 scott.walker
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package graphicslab06;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.HashMap;

/**
 *
 * @author scott.walker
 */
public class FlagOfMexico extends UniqueFlag {
    private static final Color white = Color.white;
    private static final Color green = Color.decode("0x006847");
    private static final Color red = Color.decode("0xCE1126");
    
    private static final double flagRatioMX = 1.75;

    private static final Area flagBase;
    private static final Area greenField;
    private static final Area redField;
    
    static {
        flagBase = getFlagBase(flagRatioMX);
        AffineTransform scrunch = AffineTransform.getScaleInstance(1.0 / 3, 1);
        greenField = flagBase.createTransformedArea(scrunch);
        scrunch.translate(3.5, 0);
        redField = flagBase.createTransformedArea(scrunch);
        
        
    }

    public FlagOfMexico() {
        super("Mexico");
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase, white));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(greenField, green));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(redField,red));
    }

    @Override
    public double getFlagRatio() {
        return flagRatioMX;
    }
    
    
}
