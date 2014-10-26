package graphicslab06;

import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UniqueFlag extends Flag {

    private static final Set<FlagClasses> flagTypes
            = EnumSet.allOf(FlagClasses.class);

    static Flag create(String name){
        Class fc = flagTypes.stream()
                .filter(ft -> ft.getName().equals(name))
                .findFirst().get().getFlagClass();
        try {
            return (Flag) fc.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(UniqueFlag.class.getName())
                    .log(Level.SEVERE, null, ex);
            return new BarFlag("",null,false);
        }
    }

    public UniqueFlag(String n) {
        super(n);
    }

    public enum FlagClasses {

        BRAZIL("Brazil", FlagOfBrazil.class),
        CANADA("Canada", FlagOfCanada.class),
        JAPAN("Japan", FlagOfJapan.class),
        CHINA("China", FlagOfPRC.class),
        PAKISTAN("Pakistan", FlagOfPakistan.class),
        SOUTH_KOREA("South Korea", FlagOfROK.class),
        SOUTH_AFRICA("South Africa", FlagOfRSA.class),
        SCOTLAND("Scotland", FlagOfScotland.class),
        SWITZERLAND("Switzerland", FlagOfSuisse.class),
        TEXAS("Texas", FlagOfTexas.class),
        UK("United Kingdom", FlagOfUK.class),
        USA("United States", FlagOfUSA.class);

        private final String name;
        private final Class flagClass;

        private FlagClasses(String n, Class c) {
            name = n;
            flagClass = c;
        }

        public String getName() {
            return name;
        }

        public Class getFlagClass() {
            return flagClass;
        }
    }
}
