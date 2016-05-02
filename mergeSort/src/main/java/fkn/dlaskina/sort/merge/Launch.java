package fkn.dlaskina.sort.merge;

import fkn.dlaskina.sort.comparators.SortMap;

import java.io.*;
import java.util.*;

/**
 * Created by Laskina Daria on 23.04.2016.
 *
 */
public class Launch {

    public static final String TEMP_DIR = "C:\\Prj\\mergeSort\\temp\\";

    public static void main(String[] args) throws FileNotFoundException {
        SortMap.createSortMap();
        final Sort sort = new Sort(args[0], args[1]);
        sort.run();
    }
}