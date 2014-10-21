package graphicslab06;

import java.applet.Applet;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.logging.Level;
//import java.util.logging.Logger;

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
        f = new BarFlag("Test Pattern", GL6Util.tpGen(), true);

        try {
//            f = new FlagOfUSA();
//            f = new UniqueFlag("China", "flagOfPRC");
//            f = new UniqueFlag("ROK", "flagOfROK");
//            f = new UniqueFlag("Texas", "flagOfTexas");
            f = new UniqueFlag("UK", "flagOfUK");
//            f = new UniqueFlag("USA", "flagOfUSA");
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(FlagTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        f = new FlagOfROK();
        
//        f.drawFlag();
//        g.drawImage(f.getImage(), 0, 0, this);

        f.displayFlag(g, 2);
//        GL6Util.delay(5000);
//        System.exit(0);
    }

}
