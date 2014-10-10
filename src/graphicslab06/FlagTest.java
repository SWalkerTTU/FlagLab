package graphicslab06;

import java.applet.Applet;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        Flag f = null;
        try {
            f = new UniqueFlag("South Korea","flagOfROK");
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(FlagTest.class.getName()).log(Level.SEVERE, null, ex);
        }
//                new BarFlag("Test Pattern", GL6Util.tpGen(), true);
//        f.drawFlag();
//        g.drawImage(f.getImage(), 0, 0, this);
        
//        f.displayFlag(g, 1);
//        GL6Util.delay(2000);
        System.exit(0);
    }

}
