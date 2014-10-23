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
public class FlagOfTexas extends UniqueFlagA {

    private static final Color blank = new Color(0, 0, 0, 0);
    private static final Color blue = new Color(10344);
    private static final Color red = new Color(12519984);

    private static final Area star = GL6Util.star;

    private static final AffineTransform txStarForm;
    private static BufferedImage overlay;

    static{
        flagRatio = 1.5;
        txStarForm = AffineTransform.getTranslateInstance(1 / 6.0, 1 / 3.0);
        txStarForm.scale(1 / 8.0, 1 / 8.0);
        
    }
    
    
    public FlagOfTexas() {
        super("Texas");
    }

    @Override
    public void draw(Rectangle2D.Double flag) {
        flagImage = GL6Util
                .drawBarsInBox(new Color[]{Color.white, red}, false, flag);
        Graphics2D myCanvas = flagImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);        overlay = GL6Util
                .drawBarsInBox(new Color[]{blue, blank, blank}, true, flag);
        myCanvas.drawImage(overlay, null, 0, 0);
        myCanvas.setColor(Color.white);
        AffineTransform txStar = AffineTransform
                .getTranslateInstance(flag.width / 6.0, flag.width / 3.0);
        txStar.scale(flag.width / 8, flag.width / 8);
        myCanvas.fill(star.createTransformedArea(txStar));
    }
}
