package graphicslab06;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class FlagOfTexas extends UniqueFlag {

    private static final Color blue = new Color(10344);
    private static final Color red = new Color(12519984);
    private static final Color white = Color.white;

    private static final Area star = GL6Util.star;
    private static final Area flagBase;
    private static final Area bottomHalf;
    private static final Area field;
    private static final Area txStar;

    private static final AffineTransform txStarForm;

    static {
        txStarForm = AffineTransform.getTranslateInstance(1 / 4.0, 1 / 2.0);
        txStarForm.scale(0.1875, 0.1875);

        flagBase = getFlagBase(flagRatio);
        bottomHalf = new Area(new Rectangle2D.Double(0, 0.5, 1.5, 0.5));
        field = new Area(new Rectangle2D.Double(0, 0, 0.5, 1));
        txStar = star.createTransformedArea(txStarForm);
    }

    public FlagOfTexas() {
        super("Texas");
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase, white));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(bottomHalf, red));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(field, blue));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(txStar, white));
    }
}
