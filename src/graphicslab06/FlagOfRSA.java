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
 * @author swalker
 */
public class FlagOfRSA extends UniqueFlagA {

    private static final Color gold = new Color(16561428);
    private static final Color blue = new Color(793740);
    private static final Color red = new Color(14826792);
    private static final Color green = new Color(31833);

    private static final AffineTransform centering;
    private static final AffineTransform sw;
    private static final AffineTransform nw;
    private static final AffineTransform shrink;

    private static final Area whiteBar;
    private static final Area whiteFimbre;
    private static final Area greenFimbre;
    private static final Area goldTri;
    private static final Area blackTri;
    
    private static final Path2D.Double bigTri = new Path2D.Double();
    
    static {
        double fr = 1.5;
        flagRatio = fr;
        double baseAngle = Math.atan2(1, fr);
        angle = baseAngle;

        centering = AffineTransform.getTranslateInstance(0.75, 0.5);
        nw = AffineTransform.getRotateInstance(Math.PI + baseAngle);
        sw = AffineTransform.getRotateInstance(Math.PI - baseAngle);
        shrink = AffineTransform.getScaleInstance(0.6, 0.6);

        whiteBar = new Area(new Rectangle2D.Double(0, - 1 / 6.0, 2.5, 1 / 3.0));
        whiteFimbre = new Area(whiteBar);
        whiteFimbre.add(whiteBar.createTransformedArea(nw));
        whiteFimbre.add(whiteBar.createTransformedArea(sw));
        
        greenFimbre = new Area(whiteFimbre.createTransformedArea(shrink));

        whiteFimbre.transform(centering);
        greenFimbre.transform(centering);
        
        bigTri.moveTo(0, 0);
        bigTri.lineTo(0.75, 0.5);
        bigTri.lineTo(0, 1);
        bigTri.closePath();
                
        goldTri = new Area(bigTri);

        goldTri.subtract(greenFimbre);

        blackTri = new Area(goldTri);
        blackTri.subtract(whiteFimbre);
    }

    public FlagOfRSA() {
        super("South Africa");
    }

    @Override
    protected void draw(Rectangle2D.Double flag) {
        flagImage = GL6Util.drawBarsInBox(new Color[]{red, blue}, false, flag);
        Graphics2D myCanvas = flagImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        blowUp = AffineTransform.getScaleInstance(flag.height, flag.height);

        myCanvas.setColor(Color.white);
        myCanvas.fill(whiteFimbre.createTransformedArea(blowUp));
        myCanvas.setColor(green);
        myCanvas.fill(greenFimbre.createTransformedArea(blowUp));
        myCanvas.setColor(gold);
        myCanvas.fill(goldTri.createTransformedArea(blowUp));
        myCanvas.setColor(Color.black);
        myCanvas.fill(blackTri.createTransformedArea(blowUp));
    }

}
