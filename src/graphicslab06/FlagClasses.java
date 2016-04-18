/*
 * Copyright (C) 2015 scott.walker
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package graphicslab06;

/**
 *
 * @author scott.walker
 */
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
    TURKEY("Turkey", FlagOfTurkey.class),
    UK("United Kingdom", FlagOfUK.class),
    USA("United States", FlagOfUSA.class),
    blank("", Object.class);
    private final String name;
    private final Class<?> flagClass;

    private FlagClasses(String n, Class<?> c) {
        name = n;
        flagClass = c;
    }

    public String getName() {
        return name;
    }

    public Class<?> getFlagClass() {
        return flagClass;
    }

}
