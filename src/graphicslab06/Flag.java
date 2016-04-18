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

    static Area getFlagBase(double flagRatio) {
        return new Area(new Rectangle2D.Double(0, 0, flagRatio, 1));
    }

    public static AffineTransform getBlowUp() {
        return blowUp;
    }
    static final double flagRatio = 1.5;

    private final ArrayList<HashMap.SimpleImmutableEntry<Area, Color>> areas;

    private final Color[] colors;
    private BufferedImage image;
    private final String name;
    private final BufferedImage nameImage;
    private BufferedImage flagImage;
    private Rectangle2D.Double flagRect;

    public Flag() {
        areas = new ArrayList<>();
        colors = null;
        name = null;
        nameImage = GL6Util.nameDraw("");
    }

    public Flag(String n) {
        areas = new ArrayList<>();
        name = n;
        colors = null;
        nameImage = GL6Util.nameDraw(name);
    }

    /**
     * @return the flagImage
     */
    public BufferedImage getFlagImage() {
        return flagImage;
    }

    /**
     * @param flagImage the flagImage to set
     */
    public void setFlagImage(BufferedImage flagImage) {
        this.flagImage = flagImage;
    }

    public double getFlagRatio() {
        return flagRatio;
    }

    /**
     * @return the flagRect
     */
    public Rectangle2D.Double getFlagRect() {
        return flagRect;
    }

    /**
     * @param flagRect the flagRect to set
     */
    public void setFlagRect(Rectangle2D.Double flagRect) {
        this.flagRect = flagRect;
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
        flagRect = GL6Util.makeFlagBox(this.getFlagRatio());
        flagImage = GL6Util.imageBase(flagRect);
        Graphics2D canvas = flagImage.createGraphics();
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        blowUp.setToScale(flagRect.height, flagRect.height);
        areas.stream()
                .forEachOrdered((AbstractMap.SimpleImmutableEntry<Area, Color> aMap) -> {
                    canvas.setColor(aMap.getValue());
                    canvas.fill(aMap.getKey()
                            .createTransformedArea(blowUp));
                });
        image = GL6Util.paintOnBG(flagImage);
    }

    @Override
    public String toString() {
        return "Flag of " + name;
    }
}
