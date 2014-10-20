/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    private static final Shape smallCirc
            = new Ellipse2D.Double(-0.5, -0.25, 0.5, 0.5);
    private static final Shape largeCirc
            = new Ellipse2D.Double(-0.5, -0.5, 1, 1);
    private static final AffineTransform yflip
            = AffineTransform.getScaleInstance(-1, 1);
    private static final Color blue = new Color(13432);
    private static final Color red = new Color(12979248);
    private static final double flagRatio = 1.5;
    private static final double angle = Math.atan2(1, flagRatio);

    private static final boolean[][] tgBars = new boolean[][]{
        {false, false, false},
        {true, false, true},
        {true, true, true},
        {false, true, false}};
    private static AffineTransform blowUp;
    private static final AffineTransform placement = new AffineTransform();
    private static AffineTransform rotation;
    private static Area trigram = new Area();
    private static final double[] angles
            = new double[]{angle, Math.PI - angle,
                Math.PI + angle, -angle};

    private static final Area tao = new Area(largeCirc);
    private static final Rectangle2D.Double bottomHalfCirc
            = new Rectangle2D.Double(-0.5, 0, 1, 0.5);

    private static final Area bagua = new Area();

    static {
        tao.subtract(new Area(bottomHalfCirc));
        tao.add(new Area(smallCirc));
        tao.subtract(new Area(yflip.createTransformedShape(smallCirc)));
        IntStream.range(0, tgBars.length)
                .mapToObj((int i) -> {
                    Area tg = buildBars(tgBars[i]);
                    placement.setToRotation(angles[i]);
                    placement.preConcatenate(AffineTransform.getTranslateInstance(1.5, 1));

                    return tg.createTransformedArea(placement);
                }).forEach(bagua::add);
    }

    protected static BufferedImage flagOfROK() {
        Rectangle2D.Double flag = GL6Util.makeFlagBox(flagRatio);

        double flagUnit = flag.height / 2.0;

        blowUp = AffineTransform.getScaleInstance(flagUnit, flagUnit);

        BufferedImage myImage = GL6Util.drawBarsInBox(new Color[]{Color.white}, false, flag);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

//        IntStream.range(0, 4).mapToObj((int i) -> ROKTrigram.createArea(i, flag)).forEach(bagua::add);
//        Area blackout = new Area(new Rectangle2D.Double(0, 0, GL6Util.getWidth(), GL6Util.getHeight()));
//        blackout.subtract(new Area(flag));
        myCanvas.setColor(blue);
        myCanvas.fill(blowUp.createTransformedShape(largeCirc));
        myCanvas.setColor(red);
        myCanvas.fill(tao.createTransformedArea(blowUp));
        myCanvas.setColor(Color.black);
        myCanvas.fill(bagua.createTransformedArea(blowUp));
//        myCanvas.fill(blackout);
        return myImage;
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

    /**
     *
     * @author swalker
     */
//    public static class ROKTrigram {
//
//        private ROKTrigram(int index, Rectangle2D.Double flag) {
//            super();
//            buildBars(tgBars[index]);
//            double flagUnit = flag.width / 3.0;
//            double factor = 11.0 / 12.0 * flagUnit;
//            double angle = this.angles[index];
//            double hXlate = flag.getCenterX() + factor * Math.cos(angle);
//            double vXlate = flag.getCenterY() + factor * Math.sin(angle);
//            this.blowUp = AffineTransform.getScaleInstance(flagUnit, flagUnit);
//            this.placement = AffineTransform.getRotateInstance(angle);
//            this.placement.preConcatenate(AffineTransform.getTranslateInstance(hXlate, vXlate));
//            this.trigram.transform(AffineTransform.getTranslateInstance(-1.0 / 6, -1.0 / 4));
//            this.trigram.transform(this.blowUp);
//            this.trigram.transform(this.placement);
//        }
//    }
    public void drawFlag() {
        super.setImage(GL6Util.paintOnBG(flagOfROK()));
    }
}
