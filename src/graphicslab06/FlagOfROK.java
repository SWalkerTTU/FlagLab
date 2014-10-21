package graphicslab06;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

/**
 *
 * @author swalker
 */
public class FlagOfROK extends Flag {

    private static final double flagRatio = 1.5;
    private static final double angle
            = Math.atan2(1, flagRatio);
    private static final double[] angles
            = new double[]{angle, Math.PI - angle,
                Math.PI + angle, -angle};
    private static final Area bagua
            = new Area();
    private static AffineTransform blowUp;
    private static final Color blue = new Color(13432);
    private static final Rectangle2D.Double bottomHalfCirc
            = new Rectangle2D.Double(-0.5, 0, 1, 0.5);
    private static final AffineTransform centering
            = AffineTransform.getTranslateInstance(1.5, 1);
    private static final Shape largeCirc
            = new Ellipse2D.Double(-0.5, -0.5, 1, 1);
    private static final Area disc = new Area(largeCirc);

    private static final AffineTransform placement = new AffineTransform();
    private static final Color red = new Color(12979248);
    private final static AffineTransform rotation
            = AffineTransform.getRotateInstance(angle);
    private final static Shape smallCirc
            = new Ellipse2D.Double(-0.5, -0.25, 0.5, 0.5);
    private static final Area tao = new Area(largeCirc);
    private static final boolean[][] tgBars = new boolean[][]{
        {false, false, false},
        {true, false, true},
        {true, true, true},
        {false, true, false}};
    private final static AffineTransform tgWide
            = AffineTransform.getTranslateInstance(11.0 / 12, -0.25);
    private static final AffineTransform yflip
            = AffineTransform.getScaleInstance(-1, 1);

    static {
        tao.subtract(new Area(bottomHalfCirc));
        tao.add(new Area(smallCirc));
        tao.subtract(new Area(yflip.createTransformedShape(smallCirc)));
        tao.transform(rotation);
        tao.transform(centering);
        disc.transform(centering);

        IntStream.range(0, tgBars.length)
                .mapToObj((int i) -> {
                    placement.setToRotation(angles[i], 1.5, 1);
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
                = new Rectangle2D.Double(0, 0, 1 / 12.0, 1 / 2.0);
        Area trigramOne = new Area(trigramBar);
        Area trigramZero = new Area(trigramBar);
        trigramZero.subtract(new Area(
                new Rectangle2D.Double(0, 11.0 / 48, 1.0 / 12, 1.0 / 24)));
        IntStream.range(0, barPat.length).mapToObj((int i) -> {
            AffineTransform barShift = AffineTransform
                    .getTranslateInstance(i / 8.0, 0);
            return barPat[i]
                    ? trigramOne.createTransformedArea(barShift)
                    : trigramZero.createTransformedArea(barShift);
        }).forEach(bars::add);
        return bars;
    }

    protected static BufferedImage flagOfROK() {
        Rectangle2D.Double flag = GL6Util.makeFlagBox(flagRatio);

        double flagUnit = flag.height / 2.0;

        blowUp = AffineTransform.getScaleInstance(flagUnit, flagUnit);

        BufferedImage myImage 
                = GL6Util.drawBarsInBox(new Color[]{Color.white}, false, flag);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        myCanvas.setColor(blue);
        myCanvas.fill(disc.createTransformedArea(blowUp));
        myCanvas.setColor(red);
        myCanvas.fill(tao.createTransformedArea(blowUp));
        myCanvas.setColor(Color.black);
        myCanvas.fill(bagua.createTransformedArea(blowUp));

        return myImage;
    }
    
    public FlagOfROK(){
        super("South Korea", null);
    }

    public void drawFlag() {
        super.setImage(GL6Util.paintOnBG(flagOfROK()));
    }
}