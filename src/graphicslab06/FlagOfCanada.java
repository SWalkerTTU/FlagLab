package graphicslab06;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class FlagOfCanada extends UniqueFlagA {

    private static final Area flagBase;
    private static final Area redZone;

    private static final AffineTransform centering
            = AffineTransform.getTranslateInstance(1, 0.5);

    static {
        setFlagRatio(2.0);

        flagBase = GL6Util.getFlagBase(getFlagRatio());
        redZone = new Area();
        redZone.add(new Area(new Rectangle2D.Double(0, 0, 0.5, 1)));
        redZone.add(new Area(new Rectangle2D.Double(1.5, 0, 0.5, 1)));
        redZone.add(new Area(buildMapleLeaf())
                .createTransformedArea(centering));

        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase,
                Color.white));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(redZone, Color.red));
    }

    static Path2D.Double buildMapleLeaf() {
        final double radius = 0.4;
        return new Path2D.Double(new Rectangle2D.Double(-25.0 / 64, -13 / 32.0,
                25 / 32.0, 27 / 32.0));
    }

//    static BufferedImage flagOfCanada() {
//        Color[] base = new Color[]{Color.red, Color.white, Color.white, Color.red};
//        BufferedImage myImage = GL6Util.drawBars(base, true);
//        int imgW = myImage.getWidth();
//        int imgH = myImage.getHeight();
//        int blackBarHeight = (2 * imgH - imgW) / 4;
//        Path2D.Float mapleLeaf;
//        Graphics2D myCanvas = myImage.createGraphics();
//        myCanvas.setColor(Color.black);
//        myCanvas.fillRect(0, 0, imgW, blackBarHeight);
//        myCanvas.fillRect(0, imgH - blackBarHeight, imgW, blackBarHeight);
//        myCanvas.setColor(Color.red);
//        mapleLeaf = buildMapleLeaf();
//        AffineTransform mlXform = new AffineTransform();
//        mlXform.setTransform(mlXform);
//        return myImage;
//    }
    public FlagOfCanada() {
        super("Canada");
    }

}
