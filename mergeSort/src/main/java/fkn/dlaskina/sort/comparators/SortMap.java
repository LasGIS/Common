package fkn.dlaskina.sort.comparators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Definition of the SortMap class
 * @author VLaskin
 * @since 02.05.2016.
 */
public class SortMap {

    public static final String CHARSET = "UTF-8";
    private static String SORT_RULES;
    public static final Map<Character, Integer> SORT_MAP = new HashMap<>();

    public static void createSortMap() {
        try {
            final InputStream in = SortMap.class.getClassLoader().getResourceAsStream("SORT_RULES.txt");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in, CHARSET));
            String line = reader.readLine();
            if (line != null) {
                SORT_RULES = line;
            }
        } catch (IOException e){
            System.out.println("Incorrect resource name");
        }

        for (int i = 0; i < SORT_RULES.length(); i++) {
            SORT_MAP.put(SORT_RULES.charAt(i), i);
        }
    }

    public static Integer get(Character chr) {
        Integer index = SORT_MAP.get(chr);
        if (index == null) {
            index = SORT_MAP.size() + 1;
            SORT_MAP.put(chr, index);
        }
        return index;
    }
}
