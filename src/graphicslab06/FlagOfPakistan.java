package graphicslab06;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

public class FlagOfPakistan extends UniqueFlag {

    private static final Color green = new Color(26112);

    private static final AffineTransform placement;
    
    private static final Area leftField;
    private static final Area crescent;
    private static final Area star = GL6Util.star;
    private static final Area whiteZone;
    private static final Area flagBase;

    private static final Ellipse2D.Double c1;
    private static final Ellipse2D.Double c2;
    
    private static final Point2D.Double p1;
    private static final Point2D.Double p2;
    private static final Point2D.Double p3;
    private static final Point2D.Double starCtr;

    static {
        double angle = Math.atan2(1, 1.125);
        
        p1 = new Point2D.Double(15.0 / 16, 0.5);
        p2 = new Point2D.Double(1.5 - 0.65 * Math.cos(angle),
                0.65 * Math.sin(angle));
        p3 = new Point2D.Double(p1.x + 0.3 * Math.cos(angle),
                p1.y - 0.3 * Math.sin(angle));
        starCtr = new Point2D.Double((p2.x + p3.x) / 2, (p2.y + p3.y) / 2);
        
        c1 = new Ellipse2D.Double(p1.x - 0.3, p1.y - 0.3, 0.6, 0.6);
        c2 = new Ellipse2D.Double(p2.x - 0.275, p2.y - 0.275, 0.55, 0.55);
        
        placement = AffineTransform.getRotateInstance(Math.PI / 2 - angle);
        placement.preConcatenate(AffineTransform.getScaleInstance(0.1, 0.1));
        placement.preConcatenate(AffineTransform
                .getTranslateInstance(starCtr.x, starCtr.y));
        
        leftField = new Area(new Rectangle2D.Double(0, 0, 0.375, 1));
        
        crescent = new Area(c1);
        crescent.subtract(new Area(c2));
        
        whiteZone = new Area();
        whiteZone.add(leftField);
        whiteZone.add(crescent);
        whiteZone.add(star.createTransformedArea(placement));

        flagBase = getFlagBase(flagRatio);
    }

    public FlagOfPakistan() {
        super("Pakistan");
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase, green));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(whiteZone,
                Color.white));
    }
}
