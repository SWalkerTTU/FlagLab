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
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

/**
 *
 * @author swalker
 */
public class FlagOfUSA extends Flag {

    private static AffineTransform blowUp;

    private static final Color blue = new Color(3947374);

    private static final Rectangle2D.Double canton
            = new Rectangle2D.Double(0, 0, 0.76, 7.0 / 13.0);
    private static final Color red = new Color(11674164);
    private static final Area star = GL6Util.star;
    private static final double starDiam = 2.0 / 65.0;
    private static final Area starField = new Area();
    private static final double starHSpace = 19.0 / 300.0;

    private static final AffineTransform starScale = AffineTransform
            .getScaleInstance(starDiam, starDiam);
    private static final double starVSpace = 7.0 / 130.0;
    private static final Color[] stripes = new Color[13];
    private static final Color white = Color.white;

    static {
        IntStream.range(0, stripes.length)
                .filter((int i) -> i % 2 == 0)
                .forEach((int i) -> {
                    stripes[i] = red;
                });
        IntStream.range(0, stripes.length)
                .filter((int i) -> i % 2 == 1)
                .forEach((int i) -> {
                    stripes[i] = white;
                });
        IntStream.range(0, 99)
                .filter((int i) -> i % 2 == 0)
                .mapToObj((int i) -> {
                    double hShift = (i % 11 + 1) * starHSpace;
                    double vShift = (i / 11 + 1) * starVSpace;
                    AffineTransform xlate = AffineTransform
                    .getTranslateInstance(hShift, vShift);
                    Shape s = star;
                    s = starScale.createTransformedShape(s);
                    s = xlate.createTransformedShape(s);
                    return new Area(s);
                })
                .forEach(starField::add);
    }

    private static BufferedImage draw() {

        Rectangle2D.Double flag = GL6Util.makeFlagBox(1.9);
        BufferedImage flagImage = GL6Util.drawBarsInBox(stripes, false, flag);

        Graphics2D myCanvas = flagImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        double flagUnit = flag.height;

        blowUp = AffineTransform.getScaleInstance(flagUnit, flagUnit);

        myCanvas.setColor(blue);
        myCanvas.fill(blowUp.createTransformedShape(canton));
        myCanvas.setColor(white);
        myCanvas.fill(blowUp.createTransformedShape(starField));

        return flagImage;
    }

    public FlagOfUSA() {
        super("United States", null);
    }

    @Override
    protected void drawFlag() {
        super.setImage(GL6Util.paintOnBG(draw()));
    }
}
