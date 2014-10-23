package graphicslab06;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.stream.IntStream;

public class FlagOfPRC extends UniqueFlagA {

    private static final double[] littleCtrXs = new double[]{10, 12, 12, 10};
    private static final double[] littleCtrYs = new double[]{2, 4, 7, 9};

    private static final Area star = GL6Util.star;
    private static final Area starField;
    private static final Area flagBase;

    private static final AffineTransform bigStar;
    private static final AffineTransform lilStar;

    static {
        double fr = 1.5;
        setFlagRatio(fr);

        bigStar = AffineTransform.getTranslateInstance(0.25, 0.25);
        bigStar.scale(0.15, 0.15);
        lilStar = new AffineTransform();
        
        starField = star.createTransformedArea(bigStar);

        IntStream.range(0, littleCtrXs.length)
                .mapToObj((int i)
                        -> new Point2D.Double(littleCtrXs[i] * 0.05,
                                littleCtrYs[i] * 0.05))
                .map(p2d -> {
                    double theta = Math.atan2(0.25 - p2d.x, p2d.y - 0.25);
                    lilStar.setTransform(AffineTransform
                            .getTranslateInstance(p2d.x, p2d.y));
                    lilStar.scale(0.05, 0.05);
                    lilStar.rotate(theta);
                    return star.createTransformedArea(lilStar);
                }).forEach(starField::add);
        
        flagBase = GL6Util.getFlagBase(getFlagRatio());
        
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase,
                Color.red));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(starField,
                Color.yellow));
    }

    public FlagOfPRC() {
        super("China");
    }
}
