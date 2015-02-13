/*
 * Copyright (C) 2015 scott.walker
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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.stream.IntStream;

public class FlagOfStates extends Flag {

    private static final Color blue = new Color(0x3c3b6e);
    private static final Color red = new Color(0xb22234);
    private static final Color white = Color.white;
    private static final Area flagBase = getFlagBase(1.5);
    private static final Area stripes = new Area();
    private static final Area canton
            = new Area(new Rectangle2D.Double(0, 0, 0.6, 7.0 / 13));
    private static final Area globe = new Area();

    static {
        IntStream.range(0, 13).filter((int i) -> i % 2 == 1)
                .forEach((int i) -> {
                    Area stripe = new Area(
                            new Rectangle2D.Double(0, i / 13.0,
                                    flagRatio, 1 / 13.0));
                    stripes.add(stripe);
                });

        Ellipse2D.Double outer = new Ellipse2D.Double(-3.25 / 13, -3.25 / 13, 6.5 / 13, 6.5 / 13);
        Ellipse2D.Double inner = new Ellipse2D.Double(-2.75 / 13, -2.75 / 13, 5.5 / 13, 5.5 / 13);
        Ellipse2D.Double secondOuter = new Ellipse2D.Double(-1.583 / 13, -3.25 / 13, 3.167 / 13, 6.5 / 13);
        Ellipse2D.Double secondInner = new Ellipse2D.Double(-1.083 / 13, -2.75 / 13, 2.167 / 13, 5.5 / 13);

        globe.add(new Area(outer));
        globe.subtract(new Area(inner));
        globe.add(new Area(secondOuter));
        globe.subtract(new Area(secondInner));

        Rectangle2D.Double bar = new Rectangle2D.Double(-0.25 / 13, outer.y, 0.5 / 13, outer.height);

//        globe.add(new Area(bar));
        globe.add(new Area(AffineTransform.getQuadrantRotateInstance(1).createTransformedShape(bar)));
        globe.intersect(new Area(outer));

        globe.transform(AffineTransform.getTranslateInstance(0.3, 3.5 / 13));
    }

    public FlagOfStates() {
        super("United States");
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase, red));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(stripes, white));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(canton, blue));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(globe, white));
    }

}
