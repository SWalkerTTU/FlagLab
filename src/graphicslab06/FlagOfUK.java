package graphicslab06;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class FlagOfUK extends UniqueFlag {

    private static final Color blue = new Color(0x2b7f);

    private static final AffineTransform ew;
    private static final Area flagBase;
    private static final AffineTransform nesw;
    private static final AffineTransform ns;
    private static final AffineTransform nwse;
    private static final Color red = new Color(0xce1126);

    private static final Area redCross = new Area();
    private static final Area redSaltire = new Area();
    private static final Area redStripe = new Area(
            new Rectangle2D.Double(1, 13 / 30.0, 1.2, 1 / 15.0));
    private static final Area saltire = new Area();
    private static final Area stripe = new Area(
            new Rectangle2D.Double(-0.2, 0.4, 2.4, 0.2));
    private static final Color white = Color.white;
    private static final Area whiteCross = new Area(
            new Rectangle2D.Double(-0.2, 1 / 3.0, 2.4, 1 / 3.0));
    private static final double flagRatioUK = 2;

    static {
        double angle = Math.atan2(1, flagRatioUK);
        flagBase = getFlagBase(flagRatioUK);
        ns = AffineTransform.getQuadrantRotateInstance(1, 1, 0.5);
        ew = AffineTransform.getQuadrantRotateInstance(2, 1, 0.5);
        nesw = AffineTransform.getRotateInstance(-angle, 1, 0.5);
        nwse = AffineTransform.getRotateInstance(angle, 1, 0.5);
        saltire.add(stripe.createTransformedArea(nesw));
        saltire.add(stripe.createTransformedArea(nwse));
        redStripe.add(redStripe.createTransformedArea(ew));
        redSaltire.add(redStripe.createTransformedArea(nesw));
        redSaltire.add(redStripe.createTransformedArea(nwse));
        whiteCross.add(whiteCross.createTransformedArea(ns));
        redCross.add(stripe);
        redCross.add(stripe.createTransformedArea(ns));
        flagBase.add(new Area(new Rectangle2D.Double(0, 0, 2, 1)));
    }

    public FlagOfUK() {
        super("United Kingdom");
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase, blue));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(saltire, white));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(redSaltire, red));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(whiteCross, white));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(redCross, red));
    }

    @Override
    public double getFlagRatio() {
        return flagRatioUK;
    }
}
