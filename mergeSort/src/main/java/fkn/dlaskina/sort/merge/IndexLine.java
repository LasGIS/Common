package fkn.dlaskina.sort.merge;

import fkn.dlaskina.sort.comparators.CharComparator;
import fkn.dlaskina.sort.comparators.SortMap;

/**
 * Definition of the IndexLine class
 * @author VLaskin
 * @since 02.05.2016.
 */
public class IndexLine implements Comparable<IndexLine> {

    private final long index;
    private final String line;

    public IndexLine(final String line) {
        this.index = createIndex(line);
        this.line = line;
    }

    private long createIndex(String line) {
        long ind = 0;
        int i;
        for (i = 0; i < 7 && i < line.length(); i++) {
            final Character chr = line.charAt(i);
            Integer index = SortMap.get(chr);
            ind <<= 8;
            ind |= (index + 1) & 0xff;
        }
        for (; i < 7; i++) {
            ind <<= 8;
        }
        return ind;
    }

    public long getIndex() {
        return index;
    }

    public String getLine() {
        return line;
    }

    @Override
    public int compareTo(final IndexLine o) {
        return (o == null) ? 1 : ((index > o.index) ? 1
            : (index == o.index ? CharComparator.compare(this.getLine(), o.getLine()) : -1));
        //return CharComparator.compare(this.getLine(), o.getLine());
    }
}
