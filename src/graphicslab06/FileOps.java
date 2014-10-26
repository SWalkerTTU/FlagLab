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

    public static Flag[][] readFlagFile() throws IOException {
        ArrayList<String> lines
                = Files.lines(flagFile, Charset.forName("US-ASCII"))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(ArrayList::new));

        Map<String, List<String>> sections = lines.stream()
                .map(s -> {
                    boolean isPrefix = s.startsWith("[[");
                    if (isPrefix) {
                        prefix = s;
                    }
                    return (isPrefix) ? "" : prefix + ":" + s;
                }).filter(s -> !s.isEmpty())
                .collect(Collectors.groupingBy(s -> s.split(":")[0]));

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
            return UniqueFlag.create(name);
        }

        if (bfHead.equals(type)) {
            boolean vertical = p[p.length - 1].equals("V");
            Color[] colors = parseColors(Arrays.copyOfRange(p, 1, p.length - 1));
            return new BarFlag(name, colors, vertical);
        } else {
            Color[] colors = parseColors(Arrays.copyOfRange(p, 1, p.length));
            return new NordicFlag(name, colors);
        }
    }

    private static Color[] parseColors(String[] tokens) {
        return Arrays.stream(tokens).map(Color::decode).toArray(Color[]::new);
    }

}
