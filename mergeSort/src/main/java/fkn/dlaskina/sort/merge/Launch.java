package fkn.dlaskina.sort.merge;

import fkn.dlaskina.sort.comparators.SortMap;

import java.io.*;
import java.util.*;

/**
 * Created by Laskina Daria on 23.04.2016.
 *
 */
public class Launch {

    public static final File TEMP_DIR = new File("C:\\Prj\\Common.git\\mergeSort\\temp\\");
    public static final long SIZE_CHUNK = 100000L;

    public static void main(String[] args) throws FileNotFoundException {
        if (!TEMP_DIR.exists() && !TEMP_DIR.mkdirs()) {
            System.err.println("Невозможно создать временный путь");
            return;
        }
        SortMap.createSortMap();
        final Sort sort = new Sort(args[0], args[1]);
        sort.run();
    }
}