package graphicslab06;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class NordicFlag extends Flag {

    private static final Area flagBase = new Area();
    private static final Area cross = new Area();

    static {
        setFlagRatio(1.5);
        flagBase.add(new Area(GL6Util.makeFlagBox(getFlagRatio())));
        cross.add(new Area(new Rectangle2D.Double(-1.5, 0.4, 3, 0.2)));

        AffineTransform ns = AffineTransform
                .getQuadrantRotateInstance(1, 0.5, 0.5);

        
        cross.add(cross.createTransformedArea(ns));
    }

    public NordicFlag(String n, Color[] c) {
        super(n);
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase, c[0]));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(cross, c[1]));
    }
}
