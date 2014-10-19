/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author scott.walker
 */
public class UniqueFlag extends Flag {

    private final Method drawMethod;

    public UniqueFlag(String n, String dm) throws NoSuchMethodException {
        super(n, null);
        drawMethod = GL6Util.class.getDeclaredMethod(dm, (Class<?>[]) null);
    }

    @Override
    protected void drawFlag() {
        BufferedImage img;
        try {
            img = (BufferedImage) drawMethod.invoke(this, (Object[]) null);
            super.setImage(img);
        } catch (IllegalAccessException |
                IllegalArgumentException |
                InvocationTargetException ex) {
            Logger.getLogger(UniqueFlag.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
