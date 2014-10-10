/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author scott.walker
 */
class ColorRect {
    private final Color color;
    private final Rectangle2D.Double rect;

    ColorRect(Rectangle2D.Double r, Color c) {
        rect = r;
        color = c;
    }

    public Color getColor() {
        return color;
    }

    public Rectangle2D.Double getRect() {
        return rect;
    }
    
}
