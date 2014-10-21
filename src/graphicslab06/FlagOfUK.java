/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author swalker
 */
public class FlagOfUK extends Flag {

    private static final double flagRatio = 2.0;

    private static final double angle = Math.atan2(1, flagRatio);
    private static AffineTransform blowUp;

    private static final Color blue = new Color(11135);
    private static final AffineTransform ew = AffineTransform.getQuadrantRotateInstance(2, 30, 15);

    private static final AffineTransform nesw
            = AffineTransform.getRotateInstance(-angle, 30, 15);
    private static final AffineTransform ns = AffineTransform.getQuadrantRotateInstance(1, 30, 15);
    private static final AffineTransform nwse
            = AffineTransform.getRotateInstance(angle, 30, 15);
    private static final Color red
            = new Color(13504806);
    private static final Area redCross
            = new Area();

    private static final Area redSaltire = new Area();
    private static final Area redStripe = new Area();
    private static final Area saltire = new Area();
    private static final Area stripe = new Area(
            new Rectangle2D.Double(-5, 12, 70, 6));
    private static final Area whiteCross = new Area(
            new Rectangle2D.Double(-5, 10, 70, 10));

    static {
        saltire.add(stripe.createTransformedArea(nesw));
        saltire.add(stripe.createTransformedArea(nwse));
        redStripe.add(new Area(new Rectangle2D.Double(30, 13, 35, 2)));
        redStripe.add(redStripe.createTransformedArea(ew));
        redSaltire.add(redStripe.createTransformedArea(nesw));
        redSaltire.add(redStripe.createTransformedArea(nwse));
        whiteCross.add(whiteCross.createTransformedArea(ns));
        redCross.add(stripe);
        redCross.add(stripe.createTransformedArea(ns));
    }

    protected static BufferedImage draw() {

        Rectangle2D.Double flag = GL6Util.makeFlagBox(flagRatio);

        blowUp = AffineTransform
                .getScaleInstance(flag.width / 60, flag.width / 60);

        BufferedImage flagImage = new BufferedImage(
                (int) Math.round(flag.width),
                (int) Math.round(flag.height), BufferedImage.TYPE_INT_RGB);
        Graphics2D flagCanvas = flagImage.createGraphics();
        flagCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        flagCanvas.setColor(blue);
        flagCanvas.fill(flag);
        flagCanvas.setColor(Color.white);
        flagCanvas.fill(saltire.createTransformedArea(blowUp));
        flagCanvas.setColor(red);
        flagCanvas.fill(redSaltire.createTransformedArea(blowUp));
        flagCanvas.setColor(Color.white);
        flagCanvas.fill(whiteCross.createTransformedArea(blowUp));
        flagCanvas.setColor(red);
        flagCanvas.fill(redCross.createTransformedArea(blowUp));

        return flagImage;
    }

    public FlagOfUK() {
        super("United Kingdom", null);
    }

    @Override
    public void drawFlag() {
        super.setImage(GL6Util.paintOnBG(draw()));
    }
}
