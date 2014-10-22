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
public class FlagOfSuisse extends UniqueFlagA {

    static final Area cross;
    
    static{
        cross = new Area(new Rectangle2D.Double(1 / 6.0, 0.4,
                2.0 / 3, 0.2));
        AffineTransform ns = AffineTransform
                .getQuadrantRotateInstance(1, 0.5, 0.5);
        cross.add(cross.createTransformedArea(ns));
    }

    @Override
    public void drawFlag() {
        Rectangle2D.Double flag = GL6Util.makeFlagBox(flagRatio);

        myImage = new BufferedImage((int) Math.round(flag.width),
                (int) Math.round(flag.height), BufferedImage.TYPE_INT_RGB);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        blowUp = AffineTransform.getScaleInstance(flag.width, flag.width);        
        
        myCanvas.setColor(Color.red);
        myCanvas.fill(blowUp.createTransformedShape(flag));
        myCanvas.setColor(Color.white);
        myCanvas.fill(cross.createTransformedArea(blowUp));

        image = GL6Util.paintOnBG(myImage);
    }

    public FlagOfSuisse() {
        super("Switzerland", 1);
    }
}
