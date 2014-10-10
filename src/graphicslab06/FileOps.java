/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicslab06;

import java.awt.Color;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author scott.walker
 */
public class FileOps {

    private static String prefix = "";
    private static final String bfHead = "[[BarFlags]]";
    private static final String nfHead = "[[NordicFlags]]";
    private static final String ufHead = "[[UniqueFlags]]";
    private static final String[] headers;
    private static final Path flagFile;

    static {
        headers = new String[]{bfHead, nfHead, ufHead};
        flagFile = Paths.get("flags.ini", "");
    }

//    public static void writeFlagFile(Flag[][] flagArrs) {
//        ArrayList<String> lines = new ArrayList<>();
//        
//        Arrays.asList(flagArrs).forEach(fa -> Arrays.asList(fa));
//        
//        for (Flag[] arr : flagArrs) {
//            if (arr[0] instanceof BarFlag) {
//                lines.add("[[BarFlags]]");
//                for (Flag f : arr) {
//                    BarFlag bf = (BarFlag) f;
//                    String line = "\"" + bf.getName() + "\",";
//                    for (Color c : bf.getColors()) {
//                        line += c.getRed() + ",";
//                        line += c.getGreen() + ",";
//                        line += c.getBlue() + ",";
//                    }
//                    if (bf.isVertical()) {
//                        line += "V";
//                    } else {
//                        line += "H";
//                    }
//                    lines.add(line);
//                }
//            }
//            if (arr[0].getClass() == UniqueFlag.class) {
//                lines.add("[[UniqueFlags]]");
//                for (Flag f : arr) {
//                    UniqueFlag uf = (UniqueFlag) f;
//                    String line = "\"" + uf.getName() + "\",\"" + uf.getDrawMethod() + "\"";
//                    lines.add(line);
//                }
//            }
//            if (arr[0].getClass() == Flag.class) {
//                lines.add("[[NordicFlags]]");
//                for (Flag f : arr) {
//                    String line = "\"" + f.getName() + "\",";
//                    for (Color c : f.getColors()) {
//                        line += c.getRed() + ",";
//                        line += c.getGreen() + ",";
//                        line += c.getBlue() + ",";
//                    }
//                    lines.add(line);
//                }
//            }
//            lines.add("");
//        }
//        if (Files.exists(flagFile, LinkOption.NOFOLLOW_LINKS)) {
//            try {
//                Files.delete(flagFile);
//            } catch (IOException ex) {
//                Logger.getLogger(FileOps.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        try {
//            Files.write(flagFile, lines, Charset.forName("US-ASCII"), StandardOpenOption.CREATE);
//        } catch (IOException ex) {
//            Logger.getLogger(FileOps.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public static Flag[][] readFlagFile() throws IOException {
        ArrayList<String> lines = new ArrayList<>(
                Files.lines(flagFile, Charset.forName("US-ASCII"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList())
        );

        Map<String, List<String>> sections = lines.stream()
                .map(s -> {
                    boolean isPrefix = s.startsWith("[[");
                    if (isPrefix) {
                        prefix = s;
                    }
                    return (isPrefix) ? "" : prefix + ":" + s;
                }).filter(s -> !s.isEmpty())
                .collect(Collectors.groupingBy(s -> {
                    String[] sp = s.split(":");
                    return sp[0];
                }));

        return Arrays.stream(headers).map(s
                -> sections.get(s).stream().sequential()
                .map(FileOps::flagFactory)
                .toArray(Flag[]::new))
                .toArray(Flag[][]::new);
    }

    private static Flag flagFactory(String flagString) {
        int i = flagString.indexOf(':');
        String type = flagString.substring(0, i);
        flagString = flagString.substring(i + 1);

        String[] p = flagString.split(",");
        String name = p[0].replace("\"", "");

        if (type.equals(ufHead)) {
            try {
                return new UniqueFlag(name, p[1].replace("\"", ""));
            } catch (NoSuchMethodException ex) {
                Logger.getLogger(FileOps.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }

        boolean vertical = p[p.length - 1].equals("V");

        Color[] colors = parseColors(Arrays.copyOfRange(p, 1, p.length - 1));

        if (bfHead.equals(type)) {
            return new BarFlag(name, colors, vertical);
        } else {
            return new Flag(name, colors);
        }
    }

    private static Color[] parseColors(String[] tokens) {
        int numColors = tokens.length / 3;

        int[] rVals = IntStream.range(0, numColors * 3).filter(i -> i % 3 == 0)
                .map(i -> Integer.parseInt(tokens[i])).toArray();
        int[] gVals = IntStream.range(0, numColors * 3).filter(i -> i % 3 == 1)
                .map(i -> Integer.parseInt(tokens[i])).toArray();
        int[] bVals = IntStream.range(0, numColors * 3).filter(i -> i % 3 == 2)
                .map(i -> Integer.parseInt(tokens[i])).toArray();

        return IntStream.range(0, numColors)
                .mapToObj(i -> new Color(rVals[i], gVals[i], bVals[i]))
                .toArray(Color[]::new);
    }

}
