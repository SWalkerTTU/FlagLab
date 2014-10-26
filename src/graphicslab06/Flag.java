package graphicslab06;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Flag {

    private static AffineTransform blowUp = new AffineTransform();
    private static double flagRatio;

    static Area getFlagBase(double flagRatio) {
        return new Area(new Rectangle2D.Double(0, 0, flagRatio, 1));
    }

    public static AffineTransform getBlowUp() {
        return blowUp;
    }

    public static double getFlagRatio() {
        return flagRatio;
    }

    public static void setFlagRatio(double aFlagRatio) {
        flagRatio = aFlagRatio;
    }
    private final ArrayList<HashMap.SimpleImmutableEntry<Area, Color>> areas
            = new ArrayList<>();

    private final Color[] colors;
    private BufferedImage image;
    private final String name;
    private final BufferedImage nameImage;
    protected BufferedImage flagImage;
    protected Rectangle2D.Double flagRect;

    public Flag() {
        colors = null;
        name = null;
        nameImage = GL6Util.nameDraw("");
    }

    public Flag(String n) {
        name = n;
        colors = null;
        nameImage = GL6Util.nameDraw(name);
    }

    ArrayList<HashMap.SimpleImmutableEntry<Area, Color>> getAreas() {
        return areas;
    }

    public void displayFlag(Graphics g, int speed) {
        drawFlag();
        GL6Util.speckleDraw(g, image, speed);
        GL6Util.showName(g, nameImage);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void drawFlag() {
        flagRect = GL6Util.makeFlagBox(getFlagRatio());
        flagImage = GL6Util.imageBase(flagRect);
        Graphics2D canvas = flagImage.createGraphics();
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        blowUp.setToScale(flagRect.height, flagRect.height);
        areas.stream()
                .forEachOrdered((AbstractMap.SimpleImmutableEntry<Area, Color> aMap) -> {
                    canvas.setColor(aMap.getValue());
                    canvas.fill(aMap.getKey()
                            .createTransformedArea(getBlowUp()));
                });
        image = GL6Util.paintOnBG(flagImage);
    }
}
