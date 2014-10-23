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
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author scott.walker
 */
public class FlagOfScotland extends UniqueFlagA {

    private static final AffineTransform nwse;
    private static final AffineTransform nesw;
    private static final Color blue = new Color(0x6EB6);
    private static final Area rect;
    private static final Area saltire;

    static {
        double fr = 1.25;

        flagRatio = fr;
        double baseAngle = Math.atan2(1, 1.3);
        angle = baseAngle;
        nesw = AffineTransform.getRotateInstance(-baseAngle, fr / 2, 0.5);
        nwse = AffineTransform.getRotateInstance(baseAngle, fr / 2, 0.5);
        double barWidth 
                = 1 / 6.0 * (Math.sin(baseAngle) + Math.cos(baseAngle));

        rect = new Area(new Rectangle2D.Double(-0.25, 0.5 - barWidth / 2,
                1.75, barWidth));
        saltire = rect.createTransformedArea(nwse);
        saltire.add(rect.createTransformedArea(nesw));
    }

    public FlagOfScotland() {
        super("Scotland");
    }

    @Override
    protected void draw(Rectangle2D.Double flag) {
        flagImage = GL6Util.drawBarsInBox(new Color[]{blue}, false, flag);
        Graphics2D myCanvas = flagImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        blowUp = AffineTransform.getScaleInstance(flag.height, flag.height);
        myCanvas.setColor(Color.white);
        myCanvas.fill(saltire.createTransformedArea(blowUp));
    }

}
