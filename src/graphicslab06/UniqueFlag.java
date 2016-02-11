package graphicslab06;

import java.awt.Color;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UniqueFlag extends Flag {
    
    private static final BarFlag blank = new BarFlag("0",
            new Color[]{Color.BLACK}, false);
    
    private static final Set<FlagClasses> flagTypes
            = EnumSet.allOf(FlagClasses.class);

    static Flag create(String name) {
        try {
            return (Flag) flagTypes.stream()
                    .filter((FlagClasses ft) -> name.contains(ft.getName()))
                    .findFirst().orElse(FlagClasses.blank)
                    .getFlagClass().newInstance();

        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(UniqueFlag.class.getName())
                    .log(Level.SEVERE, null, ex);
            return blank;
        }
    }

    public UniqueFlag(String n) {
        super(n);
    }

}
