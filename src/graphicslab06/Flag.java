/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author scott.walker
 */
public class Flag {
    private final Color[] colors;
    private final String name;
    protected BufferedImage image;

    public Flag() {
        colors = null;
        name = null;
    }

    public Flag(String n, Color[] c){
        name = n;
        colors = c;
    }

    protected Color[] getColors() {
        return colors;
    }

    protected void drawFlag(){
        image = GL6Util.nordicCross(colors);
    }
    
    public String getName(){
        return name;
    }
    
    public void displayFlag(Graphics g, int speed){
        drawFlag();
        GL6Util.speckleDraw(g, image, speed);
        GL6Util.showName(g, name);
    }
    
    public BufferedImage getImage(){
        return image;
    }
}