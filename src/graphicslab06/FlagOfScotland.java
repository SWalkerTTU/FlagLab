package graphicslab06;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class FlagOfScotland extends UniqueFlagA {

    private static final AffineTransform nwse;
    private static final AffineTransform nesw;
    private static final Color blue = new Color(0x6EB6);
    private static final Area rect;
    private static final Area saltire;
    private static final Area flagBase;

    static {
        setFlagRatio(1.25);
        double baseAngle = Math.atan2(1, 1.3);
        setAngle(baseAngle);
        nesw = AffineTransform.getRotateInstance(-baseAngle, 0.625, 0.5);
        nwse = AffineTransform.getRotateInstance(baseAngle, 0.625, 0.5);
        flagBase = GL6Util.getFlagBase(getFlagRatio());
        double barWidth
                = 1 / 6.0 * (Math.sin(baseAngle) + Math.cos(baseAngle));

        rect = new Area(new Rectangle2D.Double(-0.25, 0.5 - barWidth / 2,
                1.75, barWidth));
        saltire = rect.createTransformedArea(nwse);
        saltire.add(rect.createTransformedArea(nesw));

        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase, blue));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(saltire,
                Color.white));
    }

    public FlagOfScotland() {
        super("Scotland");
    }
}
