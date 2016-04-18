package graphicslab06;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FlagTest extends Applet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init() {
    }

    @Override
    public void paint(Graphics g) {
        GL6Util.setBounds(this.getBounds());
        Flag f;
        f = new FlagOfTurkey();
//        f = new BarFlag("Test Pattern", GL6Util.tpGen(), true);
//        f = new NordicFlag("Finland", new Color[]{Color.white, Color.blue});
//
//        f = new FlagOfUK();
        f.drawFlag();
        f.displayFlag(g, 3);
        GL6Util.delay(5000);
        System.exit(0);
        //        GL6Util.titlePage(g, "", 0);
        //        starTest(g);
//        brazil();
    }

    private void brazil() {
        FlagOfBrazil.BrazilStars[] stars = FlagOfBrazil.BrazilStars.values();
        HashMap<String, double[]> starMap = new HashMap<>();
        Arrays.stream(stars).forEach((FlagOfBrazil.BrazilStars star) -> {
            String name = star.name();
            double[] vals = new double[]{
                star.xLoc, star.yLoc, star.size
            };
            starMap.put(name, vals);
        });
        starMap.forEach((String s, double[] d) -> {
            d[0] = 0.35 * d[0] + 10;
            d[1] = 0.35 * d[1] + 7;
            d[2] = FlagOfBrazil.starSizes[(int) d[2]];
        });
        starMap.entrySet().stream().map(e -> {
            String lName = e.getKey().toLowerCase();
            String xLoc = Double.toString(e.getValue()[0]);
            String yLoc = Double.toString(e.getValue()[1]);
            String scale = Double.toString(e.getValue()[2] / 2.0);
            return "<use id=\"" + lName + "\"" + " class=\"st4\""
                    + " xlink:href=\"#star\"" + " transform=\"matrix("
                    + scale + " 0 0 " + scale + " " + xLoc + " " + yLoc
                    + ")\"/>";
        }).forEach(System.out::println);
    }

    private void starTest(Graphics g) {
        double[] coords = new double[6];
        ArrayList<double[]> segments = new ArrayList<>();
        ArrayList<Integer> segTypes = new ArrayList<>();
        PathIterator pi = GL6Util.star.getPathIterator(null);
        while (!pi.isDone()) {
            int segType = pi.currentSegment(coords);
            segTypes.add(segType);
            segments.add(Arrays.copyOf(coords, coords.length));
            pi.next();
        }
        segments.stream().map(Arrays::toString).forEach(System.out::println);
        System.out.println(segTypes);
        g.setColor(Color.black);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        Area testStar = GL6Util.star.createTransformedArea(new AffineTransform(250, 0, 0, 250, 500, 325));
        g.setColor(Color.red);
        ((Graphics2D) g).fill(testStar);
    }

}
