package graphicslab06;

import java.awt.Color;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;

public class FlagOfJapan extends UniqueFlagA {

    private static final Color red = new Color(16711705);

    private static final Area flagBase;
    private static final Area redSun;

    static {
        setFlagRatio(1.5);

        flagBase = GL6Util.getFlagBase(getFlagRatio());
        redSun = new Area(new Ellipse2D.Double(0.45, 0.2, 0.6, 0.6));
        
        getAreas().add(new HashMap.SimpleImmutableEntry<>(flagBase,
                Color.white));
        getAreas().add(new HashMap.SimpleImmutableEntry<>(redSun, red));
    }

    public FlagOfJapan() {
        super("Japan");
    }
}
