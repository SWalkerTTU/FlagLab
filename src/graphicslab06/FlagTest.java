package graphicslab06;

import java.applet.Applet;
import java.awt.Graphics;

/**
 *
 * @author scott.walker
 */
public class FlagTest extends Applet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init() {
    }

    @Override
    public void paint(Graphics g) {
        GL6Util.setWidth(this.getWidth());
        GL6Util.setHeight(this.getHeight());

        Flag f = new FlagOfPakistan();
        
        f.drawFlag();
        g.drawImage(f.getImage(), 0, 0, this);

//        f.displayFlag(g, 1);
//        GL6Util.delay(5000);
//        System.exit(0);
    }

}
