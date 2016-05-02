package fkn.dlaskina.sort.merge;

import fkn.dlaskina.sort.comparators.CharComparator;
import fkn.dlaskina.sort.comparators.SortMap;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Definition of the IndexLineTest class
 * @author VLaskin
 * @since 02.05.2016.
 */
public class IndexLineTest {

    @BeforeClass
    void beforeClass() {
        SortMap.createSortMap();
    }

    @DataProvider
    Object[][] create() {
        return new Object[][] {
            {"", 0L}, {" ", 0x100000000000000L}, {"  ", 0x101000000000000L}, {"   ", 0x101010000000000L},
            {"    ", 0x101010100000000L}, {"     ", 0x101010101000000L}, {"      ", 0x101010101010000L},
            {"       ", 0x101010101010100L}, {"        ", 0x101010101010101L}, {"        12345678", 0x101010101010101L},
            {"1234567", 0x2425262728292a00L},
            {"11111111", 0x2424242424242424L},
            {"12345678", 0x2425262728292a2bL},
        };
    }

    @Test(dataProvider = "create", enabled = false)
    public void testCreate(final String str, final long index) throws Exception {
        IndexLine line = new IndexLine(str);
        //System.out.println(Long.toHexString(line.getIndex()));
        Assert.assertEquals(line.getIndex(), index);
    }

    enum CompareTo {more, less, equals};

    @DataProvider
    Object[][] compareTo() {
        return new Object[][] {
            {"       ", "       123456789", CompareTo.less},
            {"       123456789", "       ", CompareTo.more},
            {" ", " ", CompareTo.equals},
            {"123456789", "123456789", CompareTo.equals},
            {"12345678", "12345678", CompareTo.equals},
            {" ", "", CompareTo.more},
            {" ", "  ", CompareTo.less},
            {"        ", "zzzzzzz", CompareTo.less},
            {"2", "11", CompareTo.more},
            {"abc", "ABC", CompareTo.more},
            {"ABC", "abc", CompareTo.less},
            {"абв", "АБВ", CompareTo.more},
            {"АБВ", "абв", CompareTo.less},
        };
    }

    @Test(dataProvider = "compareTo")
    public void testCompareTo(final String str1, final String str2, final CompareTo is) throws Exception {
        final IndexLine line1 = new IndexLine(str1);
        final IndexLine line2 = new IndexLine(str2);
        final int comp = line1.compareTo(line2);
        if (comp == 0) {
            Assert.assertEquals(CompareTo.equals, is);
        } else if (comp > 0) {
            Assert.assertEquals(CompareTo.more, is);
        } else if (comp < 0) {
            Assert.assertEquals(CompareTo.less, is);
        }
    }

    @Test(dataProvider = "compareTo")
    public void testCompareToChar(final String str1, final String str2, final CompareTo is) throws Exception {
        final IndexLine line1 = new IndexLine(str1);
        final IndexLine line2 = new IndexLine(str2);
        final int comp = CharComparator.compare(line1.getLine(), line2.getLine());
        if (comp == 0) {
            Assert.assertEquals(CompareTo.equals, is);
        } else if (comp > 0) {
            Assert.assertEquals(CompareTo.more, is);
        } else if (comp < 0) {
            Assert.assertEquals(CompareTo.less, is);
        }
    }
}