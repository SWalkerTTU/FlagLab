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
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

/**
 *
 * @author swalker
 */
public class FlagOfROK extends Flag {

    protected static BufferedImage flagOfROK() {
        Rectangle2D.Double flag = GL6Util.makeFlagBox(flagRatio);
        
        double flagUnit = flag.height / 2.0;
        
        BufferedImage myImage = GL6Util.drawBarsInBox(new Color[]{Color.white}, false, flag);
        Graphics2D myCanvas = myImage.createGraphics();
        myCanvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Area tao = new Area(largeCirc);
        tao.subtract(new Area(new Rectangle2D.Double(-0.5, 0, 1, 0.5)));
        tao.add(new Area(smallCirc));
        tao.subtract(new Area(yflip.createTransformedShape(smallCirc)));
        AffineTransform circBlowUp = AffineTransform.getScaleInstance(flagUnit, flagUnit);
        AffineTransform circXlate = AffineTransform.getTranslateInstance(flag.getCenterX(), flag.getCenterY());
        AffineTransform circRotate = AffineTransform.getRotateInstance(angle, flag.getCenterX(), flag.getCenterY());
//        largeCirc = circBlowUp.createTransformedShape(largeCirc);
//        largeCirc = circXlate.createTransformedShape(largeCirc);
        tao.transform(circBlowUp);
        tao.transform(circXlate);
        tao.transform(circRotate);
        Area bagua = new Area();
        IntStream.range(0, 4).mapToObj((int i) -> ROKTrigram.createArea(i, flag)).forEach(bagua::add);
        Area blackout = new Area(new Rectangle2D.Double(0, 0, GL6Util.getWidth(), GL6Util.getHeight()));
        blackout.subtract(new Area(flag));
        myCanvas.setColor(blue);
        myCanvas.fill(largeCirc);
        myCanvas.setColor(red);
        myCanvas.fill(tao);
        myCanvas.setColor(Color.black);
        myCanvas.fill(bagua);
        myCanvas.fill(blackout);
        return myImage;
    }
    private static final Shape smallCirc = new Ellipse2D.Double(-0.5, -0.25, 0.5, 0.5);
    private static final Shape largeCirc = new Ellipse2D.Double(-0.5, -0.5, 1, 1);
    private static final AffineTransform yflip = AffineTransform.getScaleInstance(-1, 1);
    private static final Color blue = new Color(13432);
    private static final Color red = new Color(12979248);
    private static final double flagRatio = 1.5;
    private static final double angle = Math.atan2(1, flagRatio);

    public void drawFlag(){
        super.setImage(flagOfROK());
    }
}
