package graphicslab06;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.stream.IntStream;

public class FlagOfUSA extends UniqueFlag {

    private static final Color blue = new Color(0x3c3b6e);
    private static final Color red = new Color(0xb22234);
    private static final Color white = Color.white;

    private static final Area canton
            = new Area(new Rectangle2D.Double(0, 0, 0.76, 7.0 / 13.0));
    private static final Area star = GL6Util.star;
    private static final Area starField = new Area();
    private static final Area flagBase;
    private static final Area whiteStripes = new Area();

    private static final double starDiam = 2.0 / 65.0;
    private static final double starHSpace = 19.0 / 300.0;
    private static final double starVSpace = 7.0 / 130.0;

    private static final AffineTransform starScale;
    private static final double flagRatioUS = 1.9;

    static {
        starScale = AffineTransform.getScaleInstance(starDiam, starDiam);

        flagBase = getFlagBase(flagRatioUS);

        IntStream.range(0, 13).filter((int i) -> i % 2 == 1)
                .forEach((int i) -> {
                    Area stripe = new Area(
                            new Rectangle2D.Double(0, i / 13.0,
                                    flagRatioUS, 1 / 13.0));
                    whiteStripes.add(stripe);
                });

        IntStream.range(0, 99).filter((int i) -> i % 2 == 0)
                .mapToObj((int i) -> {
                    double hShift = (i % 11 + 1) * starHSpace;
                    double vShift = (i / 11 + 1) * starVSpace;
                    AffineTransform xlate = AffineTransform
                    .getTranslateInstance(hShift, vShift);
                    Shape s = star;
                    s = starScale.createTransformedShape(s);
                    s = xlate.createTransformedShape(s);
                    return new Area(s);
                }).forEach(starField::add);
    }

    public FlagOfUSA() {
        super("United States");
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase, red));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(whiteStripes, white));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(canton, blue));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(starField, white));
    }

    @Override
    public double getFlagRatio() {
        return flagRatioUS;
    }    
}
