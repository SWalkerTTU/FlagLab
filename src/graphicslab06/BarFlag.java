package graphicslab06;

import java.awt.Color;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.stream.IntStream;

public class BarFlag extends Flag {

    static {
        setFlagRatio(1.5);
    }

    public BarFlag(String name, Color[] c, boolean isVertical) {
        super(name);
        double fr = getFlagRatio();
        double rectW = (isVertical) ? fr / c.length : fr;
        double rectH = (isVertical) ? 1 : 1.0 / c.length;
        double rectX = (isVertical) ? rectW : 0;
        double rectY = (isVertical) ? 0 : rectH;
        IntStream.range(0, c.length).mapToObj(i -> {
            Area rect
                    = new Area(new Rectangle2D.Double(rectX * i, rectY * i,
                                    rectW, rectH));
            return new HashMap.SimpleImmutableEntry<>(rect, c[i]);
        }).forEach(getAreas()::add);
    }
}
