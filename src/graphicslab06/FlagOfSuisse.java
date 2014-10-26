package graphicslab06;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class FlagOfSuisse extends UniqueFlag {

    private static final Area cross;
    private static final Area flagBase;
    private static final double flagRatioCH = 1;

    static {
        flagBase = getFlagBase(flagRatioCH);
        cross = new Area(new Rectangle2D.Double(1 / 6.0, 0.4,
                2.0 / 3, 0.2));
        AffineTransform ns = AffineTransform
                .getQuadrantRotateInstance(1, 0.5, 0.5);
        cross.add(cross.createTransformedArea(ns));
    }

    public FlagOfSuisse() {
        super("Switzerland");
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase, Color.red));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(cross, Color.white));
    }

    @Override
    public double getFlagRatio() {
        return flagRatioCH;
    }
}
