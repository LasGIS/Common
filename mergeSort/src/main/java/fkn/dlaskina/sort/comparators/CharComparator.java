package fkn.dlaskina.sort.comparators;

import java.util.Comparator;

/**
 * Definition of the CharComparator class
 * @author VLaskin
 * @since 02.05.2016.
 */
public class CharComparator implements Comparator<Character> {

    private static final CharComparator SINGLETON = new CharComparator();

    private CharComparator() {
    }

    public static CharComparator singleton() {
        return SINGLETON;
    }

    public static int compare(final String str1, final String str2) {
        final int len1 = str1.length();
        final int len2 = str2.length();
        for (int i = 0; i < len1 && i < len2; i++) {
            int res = SINGLETON.compare(str1.charAt(i), str2.charAt(i));
            if (res != 0) return res;
        }
        if (len1 != len2) {
            return len1 - len2;
        }
        return 0;
    }

    @Override
    public int compare(final Character o1, final Character o2) {
        final Integer ind1 = SortMap.get(o1);
        final Integer ind2 = SortMap.get(o2);
        if (ind1 != null) {
            if (ind2 != null) {
                return ind1 - ind2;
            } else {
                return -1;
            }
        } else if (ind2 != null) {
            return 1;
        } else {
            return o1.compareTo(o2);
        }
    }
}
