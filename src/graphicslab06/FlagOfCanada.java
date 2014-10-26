package graphicslab06;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class FlagOfCanada extends UniqueFlag {

    private static final Area flagBase;
    private static final Area redZone;

    private static final AffineTransform centering
            = AffineTransform.getTranslateInstance(1, 0.5);
    private static final double flagRatioCA;

    static {
        flagRatioCA = 2;
        
        flagBase = getFlagBase(flagRatioCA);
        redZone = new Area();
        redZone.add(new Area(new Rectangle2D.Double(0, 0, 0.5, 1)));
        redZone.add(new Area(new Rectangle2D.Double(1.5, 0, 0.5, 1)));
        redZone.add(new Area(buildMapleLeaf())
                .createTransformedArea(centering));
    }

    static Path2D.Double buildMapleLeaf() {
        return new Path2D.Double(new Rectangle2D.Double(-25.0 / 64, -13 / 32.0,
                25 / 32.0, 27 / 32.0));
    }

    public FlagOfCanada() {
        super("Canada");
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase,
                Color.white));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(redZone, Color.red));
    }

    @Override
    public double getFlagRatio() {
        return flagRatioCA;
    }
}
