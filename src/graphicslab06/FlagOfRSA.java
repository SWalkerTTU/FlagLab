package graphicslab06;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class FlagOfRSA extends UniqueFlag {

    private static final Color gold = new Color(16561428);
    private static final Color blue = new Color(793740);
    private static final Color red = new Color(14826792);
    private static final Color green = new Color(31833);

    private static final AffineTransform centering;
    private static final AffineTransform sw;
    private static final AffineTransform nw;
    private static final AffineTransform shrink;

    private static final Area whiteBar;
    private static final Area whiteFimbre;
    private static final Area greenFimbre;
    private static final Area goldTri;
    private static final Area blackTri;
    private static final Area flagBase;
    private static final Area bottomHalf;

    private static final Path2D.Double bigTri = new Path2D.Double();

    static {
        double angle = Math.atan2(1, flagRatio);

        centering = AffineTransform.getTranslateInstance(0.75, 0.5);
        nw = AffineTransform.getRotateInstance(Math.PI + angle);
        sw = AffineTransform.getRotateInstance(Math.PI - angle);
        shrink = AffineTransform.getScaleInstance(0.6, 0.6);

        whiteBar = new Area(new Rectangle2D.Double(0, - 1 / 6.0, 2.5, 1 / 3.0));
        whiteFimbre = new Area(whiteBar);
        whiteFimbre.add(whiteBar.createTransformedArea(nw));
        whiteFimbre.add(whiteBar.createTransformedArea(sw));

        greenFimbre = new Area(whiteFimbre.createTransformedArea(shrink));

        whiteFimbre.transform(centering);
        greenFimbre.transform(centering);

        bigTri.moveTo(0, 0);
        bigTri.lineTo(0.75, 0.5);
        bigTri.lineTo(0, 1);
        bigTri.closePath();

        goldTri = new Area(bigTri);

        goldTri.subtract(greenFimbre);

        blackTri = new Area(goldTri);
        blackTri.subtract(whiteFimbre);

        flagBase = getFlagBase(flagRatio);
        bottomHalf = new Area(new Rectangle2D.Double(0, 0.5, flagRatio, 0.5));
    }

    public FlagOfRSA() {
        super("South Africa");
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase, red));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(bottomHalf, blue));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(whiteFimbre,
                Color.white));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(greenFimbre, green));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(goldTri, gold));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(blackTri,
                Color.black));
    }
}
