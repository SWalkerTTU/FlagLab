package graphicslab06;

import java.awt.Color;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.stream.IntStream;

public class BarFlag extends Flag {

    public BarFlag(String name, Color[] c, boolean isVertical) {
        super(name);
        double fr = getFlagRatio();
        double rectW = (isVertical) ? fr / c.length : fr;
        double rectH = (isVertical) ? 1 : 1.0 / c.length;
        double rectX = (isVertical) ? rectW : 0;
        double rectY = (isVertical) ? 0 : rectH;
        IntStream.range(0, c.length).mapToObj(i -> {
            double x = rectX * i;
            double y = rectY * i;
            Area rect = new Area(new Rectangle2D.Double(x, y, fr - x, 1.0 - y));
            return new HashMap.SimpleImmutableEntry<>(rect, c[i]);
        }).forEach(getAreas()::add);
    }
}
