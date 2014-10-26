package graphicslab06;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.stream.IntStream;

public class FlagOfROK extends UniqueFlag {

    private static final Color blue = new Color(13432);
    private static final Color red = new Color(12979248);

    private static final double[] angles;

    private static final Area bagua = new Area();
    private static final Area disc;
    private static final Area tao;
    private static final Area flagBase;

    private static final Shape largeCirc;
    private static final Shape smallCirc;
    private static final Shape bottomHalfCirc;

    private static final AffineTransform centering;
    private static final AffineTransform placement = new AffineTransform();
    private final static AffineTransform rotation;
    private static final AffineTransform tgWide;
    private static final AffineTransform yflip;

    private static final boolean[][] tgBars = new boolean[][]{
        {false, false, false},
        {true, false, true},
        {true, true, true},
        {false, true, false}};

    static {
        setFlagRatio(1.5);
        double angle = (Math.atan2(1, getFlagRatio()));
        flagBase = getFlagBase(getFlagRatio());

        angles = new double[]{angle, Math.PI - angle,
            Math.PI + angle, -angle};

        rotation = AffineTransform.getRotateInstance(angle);
        centering = AffineTransform.getTranslateInstance(0.75, 0.5);
        tgWide = AffineTransform.getTranslateInstance(11.0 / 24, -0.125);
        yflip = AffineTransform.getScaleInstance(-1, 1);

        largeCirc = new Ellipse2D.Double(-0.25, -0.25, 0.5, 0.5);
        smallCirc = new Ellipse2D.Double(-0.25, -0.125, 0.25, 0.25);
        bottomHalfCirc = new Rectangle2D.Double(-0.25, 0, 0.5, 0.25);

        disc = new Area(largeCirc);

        tao = new Area(largeCirc);
        tao.subtract(new Area(bottomHalfCirc));
        tao.add(new Area(smallCirc));
        tao.subtract(new Area(yflip.createTransformedShape(smallCirc)));
        tao.transform(rotation);
        tao.transform(centering);
        disc.transform(centering);

        IntStream.range(0, tgBars.length)
                .mapToObj((int i) -> {
                    placement.setToRotation(angles[i], 0.75, 0.5);
                    Area tg = buildBars(tgBars[i]);
                    tg.transform(centering);
                    tg.transform(tgWide);
                    tg.transform(placement);
                    return tg;
                }).forEach(bagua::add);
    }

    private static Area buildBars(boolean[] barPat) {
        Area bars = new Area();
        Rectangle2D.Double trigramBar
                = new Rectangle2D.Double(0, 0, 1 / 24.0, 1 / 4.0);
        Area trigramOne = new Area(trigramBar);
        Area trigramZero = new Area(trigramBar);
        trigramZero.subtract(new Area(
                new Rectangle2D.Double(0, 11.0 / 96, 1.0 / 24, 1.0 / 48)));
        IntStream.range(0, barPat.length).mapToObj((int i) -> {
            AffineTransform barShift = AffineTransform
                    .getTranslateInstance(i / 16.0, 0);
            return barPat[i]
                    ? trigramOne.createTransformedArea(barShift)
                    : trigramZero.createTransformedArea(barShift);
        }).forEach(bars::add);
        return bars;
    }

    public FlagOfROK() {
        super("South Korea");
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase,
                Color.white));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(disc, blue));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(tao, red));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(bagua, Color.black));
    }
}
