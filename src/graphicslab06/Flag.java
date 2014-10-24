/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author scott.walker
 */
public class Flag {
    private static ArrayList<HashMap.SimpleImmutableEntry<Area, Color>> areas
            = new ArrayList<>();

    public static BufferedImage nameDraw(String name) {
        Font nameFont = new Font("Algerian", Font.BOLD, 48);
        FontRenderContext nameRC
                = new FontRenderContext(new AffineTransform(), true, false);
        TextLayout layout = new TextLayout(name, nameFont, nameRC);
        Rectangle2D textBox = layout.getBounds();
        textBox.setRect(0, 0, textBox.getWidth() + 50,
                textBox.getHeight() + 30);
        BufferedImage nameImage
                = new BufferedImage((int) Math.ceil(textBox.getWidth()),
                        (int) Math.ceil(textBox.getHeight()),
                        BufferedImage.TYPE_INT_RGB);
        Graphics2D myCanvas = nameImage.createGraphics();
        myCanvas.setBackground(Color.white);
        myCanvas.fill(textBox);
        myCanvas.setColor(Color.black);
        myCanvas.draw(textBox);
        layout.draw(myCanvas, 25, (float) textBox.getHeight() - 15);
        return nameImage;
    }

    /**
     * @return the areas
     */
    static ArrayList<HashMap.SimpleImmutableEntry<Area, Color>> getAreas() {
        return areas;
    }

    /**
     * @param aAreas the areas to set
     */
    static void setAreas(ArrayList<HashMap.SimpleImmutableEntry<Area, Color>> aAreas) {
        areas = aAreas;
    }

    private final Color[] colors;
    private BufferedImage image;
    private final String name;
    private BufferedImage nameImage;

    public Flag() {
        colors = null;
        name = null;
        nameImage = Flag.nameDraw("");
    }

    public Flag(String n, Color[] c) {
        name = n;
        colors = c;
        nameImage = Flag.nameDraw(name);
    }

    public void displayFlag(Graphics g, int speed) {
        drawFlag();
        GL6Util.speckleDraw(g, image, speed);
        GL6Util.showName(g, nameImage);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage img) {
        image = img;
    }

    protected void drawFlag() {
        image = GL6Util.drawBarFlag(new Color[]{Color.white}, true);
    }

    protected Color[] getColors() {
        return colors;
    }

    /**
     * @return the nameImage
     */
    BufferedImage getNameImage() {
        return nameImage;
    }

    /**
     * @param nameImage the nameImage to set
     */
    void setNameImage(BufferedImage nameImage) {
        this.nameImage = nameImage;
    }
}
