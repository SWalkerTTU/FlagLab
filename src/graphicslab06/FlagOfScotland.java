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
 * @author scott.walker
 */
public class FlagOfScotland extends Flag{

    protected static BufferedImage flagOfScotland() {
        Color blue = new Color(26045);
        BufferedImage myImage = GL6Util.drawBars(new Color[]{blue}, false);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        double angle = Math.atan2(GL6Util.getHeight(), GL6Util.getWidth());
        double centerX = GL6Util.getWidth() / 2.0;
        double centerY = GL6Util.getHeight() / 2.0;
        AffineTransform nesw = AffineTransform.getRotateInstance(angle, centerX, centerY);
        AffineTransform nwse = AffineTransform.getRotateInstance(-angle, centerX, centerY);
        Area rect = new Area(new Rectangle2D.Double(centerX - GL6Util.getWidth() * 0.6, centerY - GL6Util.getHeight() / 10.0, GL6Util.getWidth() * 1.2, GL6Util.getHeight() / 5.0));
        Area saltire = rect.createTransformedArea(nesw);
        saltire.add(rect.createTransformedArea(nwse));
        myCanvas.setColor(Color.white);
        myCanvas.fill(saltire);
        return myImage;
    }
    
    
    
}
