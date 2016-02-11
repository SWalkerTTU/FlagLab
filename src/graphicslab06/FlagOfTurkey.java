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
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

/**
 *
 * @author scott.walker
 */
public class FlagOfTurkey extends UniqueFlag {

    private static final Color red = new Color(0.89f, 0.039f, 0.09f);
    private static final Color white = Color.white;

    private static final double hemWidth = 1 / 30.0;
    private static final double flagRatioTR = 1.5 + hemWidth;

    private static final Area whiteZone = new Area();
    private static final Area flagBase = getFlagBase(flagRatioTR);

    static {
        Area hem = new Area(new Rectangle2D.Double(0, 0, hemWidth, 1));

        Point2D.Double a = new Point2D.Double(0.5 + hemWidth, 0.5);
        double b = 0.25;
        Point2D.Double c = new Point2D.Double(a.x + 0.0625, 0.5);
        double d = 0.2;

        Area crescent = new Area(new Ellipse2D.Double(a.x - b, a.y - b, 2 * b, 2 * b));
        crescent.subtract(new Area(new Ellipse2D.Double(c.x - d, c.y - d, 2 * d, 2 * d)));

        double e = c.x - d + 1 / 3.0;
        double f = 0.125;

        Area star = new Area(GL6Util.star);
        star.transform(AffineTransform.getQuadrantRotateInstance(3));
        star.transform(AffineTransform.getScaleInstance(f, f));
        star.transform(AffineTransform.getTranslateInstance(e + f, 0.5));

        whiteZone.add(hem);
        whiteZone.add(crescent);
        whiteZone.add(star);

    }

    public FlagOfTurkey() {
        super("Turkey");
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase, red));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(whiteZone, white));
    }

    @Override
    public double getFlagRatio() {
        return flagRatioTR;
    }

}
