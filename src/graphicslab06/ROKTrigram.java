/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.stream.IntStream;

/**
 *
 * @author swalker
 */
public class ROKTrigram {

    public static Area createArea(int index, Rectangle2D.Double flag){
        return new ROKTrigram(index, flag).trigram;
    }

    private final double angle = Math.atan2(2, 3);
    private final double[] angles = new double[]{
        angle, Math.PI - angle,
        Math.PI + angle, -angle};
    private final boolean[][] tgBars = new boolean[][]{
        {false, false, false},
        {true, false, true},
        {true, true, true},
        {false, true, false}
    };

    private final Area trigram = new Area();
    private final AffineTransform blowUp;
    private final AffineTransform placement;

    private ROKTrigram(int index, Rectangle2D.Double flag) {
        buildBars(tgBars[index]);
        double flagUnit = flag.width / 3.0;
        double factor = 11.0 / 12.0 * flagUnit;

        blowUp = AffineTransform.getScaleInstance(flagUnit, flagUnit);
        placement = new AffineTransform(Math.cos(angles[index]),
                Math.sin(angles[index]),
                -Math.sin(angles[index]),
                Math.cos(angles[index]),
                flag.getCenterX() + factor * Math.cos(angles[index]),
                flag.getCenterY() + factor * Math.sin(angles[index]));

        trigram.transform(AffineTransform.getTranslateInstance(-1.0 / 6,
                -1.0 / 4));
        trigram.transform(blowUp);
        trigram.transform(placement);
    }

    private void buildBars(boolean[] barPat) {
        Rectangle2D.Double trigramBar = new Rectangle2D.Double(0, 0,
                1 / 12.0, 1 / 2.0);
        Area trigramOne = new Area(trigramBar);
        Area trigramZero = new Area(trigramBar);

        trigramZero.subtract(new Area(
                new Rectangle2D.Double(0, 11.0 / 48,
                        1.0 / 12, 1.0 / 24)));

        IntStream.range(0, barPat.length).mapToObj(i -> {
            AffineTransform barShift = AffineTransform
                    .getTranslateInstance(i / 8.0, 0);
            return barPat[i]
                    ? trigramOne.createTransformedArea(barShift)
                    : trigramZero.createTransformedArea(barShift);
        }).forEach(trigram::add);
    }
}
