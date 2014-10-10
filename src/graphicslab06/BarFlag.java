/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

import java.awt.Color;

/**
 *
 * @author scott.walker
 */
public class BarFlag extends Flag {
    private final boolean vertical;

    public BarFlag(){
        vertical = false;
    }

    public BarFlag(String name, Color[] colors, boolean isVertical){
        super(name,colors);
        vertical = isVertical;
    }

    public boolean isVertical(){
        return vertical;
    }
    
    @Override
    protected void drawFlag(){
        image = GL6Util.drawBars(getColors(), vertical);
    }
}