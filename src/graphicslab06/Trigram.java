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
public class Trigram {

    private Area trigram;
    private double flagUnit;

    public Trigram(boolean[] barPat, double flagU, AffineTransform xf) {
        flagUnit = flagU;
        Rectangle2D.Double trigramBar = new Rectangle2D.Double(0, 0,
                flagUnit / 12.0, flagUnit / 2.0);
        Area trigramOne = new Area(trigramBar);
        Area trigramZero = new Area(trigramBar);

        trigramZero.subtract(new Area(
                new Rectangle2D.Double(0, 11 * flagUnit / 48,
                        flagUnit / 12, flagUnit / 24)));

        trigram = new Area();

        IntStream.range(0, barPat.length).mapToObj(i -> {
            AffineTransform barShift = AffineTransform
                    .getTranslateInstance(i * flagUnit / 8, 0);
            return barPat[i]
                    ? trigramOne.createTransformedArea(barShift)
                    : trigramZero.createTransformedArea(barShift);
        }).forEach(trigram::add);
                
        trigram.transform(xf);
    }

    public Area getTrigram() {
        return trigram;
    }
}
