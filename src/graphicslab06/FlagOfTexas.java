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
public class FlagOfTexas extends Flag {

    private static final Color blank = new Color(0, 0, 0, 0);
    private static final Color blue = new Color(10344);
    private static final double flagRatio = 1.5;
    private static final Color red = new Color(12519984);

    private static final Area star = GL6Util.star;

    private static BufferedImage draw() {
        Rectangle2D.Double flag = GL6Util.makeFlagBox(flagRatio);
        BufferedImage myImage = GL6Util
                .drawBarsInBox(new Color[]{Color.white, red}, false, flag);
        Graphics2D myCanvas = myImage.createGraphics();
        BufferedImage overlay = GL6Util
                .drawBarsInBox(new Color[]{blue, blank, blank}, true, flag);
        myCanvas.drawImage(overlay, null, 0, 0);
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        myCanvas.setColor(Color.white);
        AffineTransform txStar = AffineTransform
                .getTranslateInstance(flag.width / 6.0, flag.width / 3.0);
        txStar.scale(flag.width / 8, flag.width / 8);
        myCanvas.fill(star.createTransformedArea(txStar));
        return myImage;
    }

    public FlagOfTexas() {
        super("Texas", null);
    }

    @Override
    public void drawFlag() {
        super.setImage(GL6Util.paintOnBG(draw()));
    }
}
