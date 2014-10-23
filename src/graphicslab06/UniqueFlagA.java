/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author swalker
 */
public abstract class UniqueFlagA extends Flag {
    protected static double flagRatio;
    protected static double angle;
    protected static AffineTransform blowUp;

    protected BufferedImage flagImage;
    protected Rectangle2D.Double flag;

    public UniqueFlagA(String n) {
        super(n, null);
    }
    
    @Override
    public void drawFlag(){
        flag = GL6Util.makeFlagBox(flagRatio);
        draw(flag);
        image = GL6Util.paintOnBG(flagImage);
    }
    
    protected abstract void draw(Rectangle2D.Double flag);
    
}
