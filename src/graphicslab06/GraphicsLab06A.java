package graphicslab06;

import java.applet.Applet;
import java.awt.Dimension;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphicsLab06A extends Applet {

    private static int speed = 1;
    protected static final int width = 1000;
    protected static final int height = 650;

    private static Flag[][] flags;
    private static final long serialVersionUID = 1L;

    @Override
    public void init() {
        this.setSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
        this.setMinimumSize(new Dimension(width, height));
        try {
            speed = GL6Util.enterIntGUI(
                    "How fast is your computer?\n"
                    + "1 = Really Slow\n"
                    + "2 = Normal Speed\n"
                    + "3 = Really Fast\n"
                    + "4 = Ultra Fast");
            flags = FileOps.readFlagFile();
        } catch (IOException ex) {
            Logger.getLogger(GraphicsLab06A.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paint(Graphics g) {
        GL6Util.setBounds(this.getBounds());
        GL6Util.titlePage(g, "Scott Walker", 0);
        Arrays.stream(flags).forEach(
                fa -> Arrays.stream(fa).forEach(
                        f -> f.displayFlag(g, speed)));

        System.exit(0);
    }
}
