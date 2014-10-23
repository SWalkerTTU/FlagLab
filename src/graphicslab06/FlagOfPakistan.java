/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author swalker
 */
public class FlagOfPakistan extends UniqueFlagA {

    private static final Color green = new Color(26112);

    private static final Area leftField;
    private static final Area crescent;
    
    private static final Point2D.Double p1;
    private static final Point2D.Double p2;
    private static final Point2D.Double p3;

    static {
        double fr = 1.5;
        flagRatio = fr;

        leftField = new Area(new Rectangle2D.Double(0, 0, 0.375, 1));
        
        p1 = new Point2D.Double(15.0 / 16, 0.5);
        p2 = new Point2D.Double();
    }

    protected static BufferedImage flagOfPakistan() {
        double fieldWidth = 1.5;
        Point2D.Double topLeft = new Point2D.Double(0, 0);
        Point2D.Double bottomRight = new Point2D.Double(1.5, 1.0);
        Point2D.Double ctr1 = new Point2D.Double(fieldWidth * 5 / 8, 1.0 / 2.0);
        double rad1 = 0.3 * 1.0;
        double c2locRad = 0.65 * 1.0;
        Point2D.Double ctr2 = new Point2D.Double((2 * 1.5 - 3 * 1.0) / 4.0 + fieldWidth - c2locRad * 9 / Math.sqrt(145), c2locRad * 8 / Math.sqrt(145));
        double rad2 = 0.275 * 1.0;
        Area crescent = new Area(new Ellipse2D.Double(ctr1.x - rad1, ctr1.y - rad1, 2 * rad1, 2 * rad1));
        crescent.subtract(new Area(new Ellipse2D.Double(ctr2.x - rad2, ctr2.y - rad2, 2 * rad2, 2 * rad2)));
        
        
        myCanvas.setColor(green);
        myCanvas.fill(new Rectangle2D.Double((2 * 1.5 - 3 * 1.0) / 4.0, 0, fieldWidth, 1.0));
        myCanvas.setColor(Color.white);
        myCanvas.fill(new Rectangle2D.Double((2 * 1.5 - 3 * 1.0) / 4.0, 0, fieldWidth / 4.0, 1.0));
        myCanvas.fill(crescent);
        Point2D.Double ctr3 = new Point2D.Double((2 * 1.5 - 3 * 1.0) / 4.0 + fieldWidth - 0.55 * 1.0 * 9 / Math.sqrt(145), 0.55 * 1.0 * 8 / Math.sqrt(145));
        AffineTransform starLoc = new AffineTransform(0.1 * 1.0, 0, 0, 0.1 * 1.0, ctr3.x, ctr3.y);
        starLoc.rotate(Math.atan2((2 * 1.5 - 3 * 1.0) / 4.0 + fieldWidth - ctr3.x, ctr3.y));
        myCanvas.fill(starLoc.createTransformedShape(star));
        myCanvas.setColor(Color.black);
        myCanvas.fill(new Rectangle2D.Double(0, 0, (2 * 1.5 - 3 * 1.0) / 4.0, 1.0));
        myCanvas.fill(new Rectangle2D.Double(1.5 - (2 * 1.5 - 3 * 1.0) / 4.0, 0, (2 * 1.5 - 3 * 1.0) / 4.0, 1.0));
    }

    public FlagOfPakistan() {
        super("Pakistan");
    }

    @Override
    protected void draw(Rectangle2D.Double flag) {
        flagImage = GL6Util.drawBarsInBox(new Color[]{green}, true, flag);
        Graphics2D myCanvas = flagImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

}
