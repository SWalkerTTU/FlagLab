package graphicslab06;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class FlagOfScotland extends UniqueFlag {

    private static final AffineTransform nwse;
    private static final AffineTransform nesw;
    private static final Color blue = new Color(0x6EB6);
    private static final Area rect;
    private static final Area saltire;
    private static final Area flagBase;
    private static final double flagRatioSCO = 1.25;

    static {
        double angle = Math.atan2(1, 1.3);
        nesw = AffineTransform.getRotateInstance(-angle, 0.625, 0.5);
        nwse = AffineTransform.getRotateInstance(angle, 0.625, 0.5);
        flagBase = getFlagBase(flagRatioSCO);
        double barWidth
                = 1 / 6.0 * (Math.sin(angle) + Math.cos(angle));

        rect = new Area(new Rectangle2D.Double(-0.25, 0.5 - barWidth / 2,
                1.75, barWidth));
        saltire = rect.createTransformedArea(nwse);
        saltire.add(rect.createTransformedArea(nesw));
    }

    public FlagOfScotland() {
        super("Scotland");
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase, blue));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(saltire,
                Color.white));
    }
    
    @Override
    public double getFlagRatio(){
        return flagRatioSCO;
    }
}
