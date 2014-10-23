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
public class FlagOfROK extends UniqueFlagA {

    private static final Color blue = new Color(13432);
    private static final Color red = new Color(12979248);

    private static final double[] angles;

    private static final Area bagua = new Area();
    private static final Area disc;
    private static final Area tao;
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
        double fr = 1.5;
        flagRatio = fr;
        double baseAngle = Math.atan2(1, fr);
        angle = baseAngle;

        angles = new double[]{baseAngle, Math.PI - baseAngle,
            Math.PI + baseAngle, -baseAngle};

        rotation = AffineTransform.getRotateInstance(baseAngle);
        centering = AffineTransform.getTranslateInstance(1.5, 1);
        tgWide = AffineTransform.getTranslateInstance(11.0 / 12, -0.25);
        yflip = AffineTransform.getScaleInstance(-1, 1);

        largeCirc = new Ellipse2D.Double(-0.5, -0.5, 1, 1);
        smallCirc = new Ellipse2D.Double(-0.5, -0.25, 0.5, 0.5);
        bottomHalfCirc = new Rectangle2D.Double(-0.5, 0, 1, 0.5);

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

    public FlagOfROK() {
        super("South Korea");
    }

    @Override
    protected void draw(Rectangle2D.Double flag) {
        blowUp = AffineTransform.getScaleInstance(flag.height / 2.0,
                flag.height / 2.0);

        flagImage = GL6Util.drawBarsInBox(new Color[]{Color.white}, false, flag);
        Graphics2D myCanvas = flagImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        myCanvas.setColor(blue);
        myCanvas.fill(disc.createTransformedArea(blowUp));
        myCanvas.setColor(red);
        myCanvas.fill(tao.createTransformedArea(blowUp));
        myCanvas.setColor(Color.black);
        myCanvas.fill(bagua.createTransformedArea(blowUp));

    }
}
