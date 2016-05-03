package fkn.dlaskina.sort.merge;

import java.io.File;
import java.io.IOException;

/**
 * Definition of the TempSortFile class
 * @author VLaskin
 * @since 03.05.2016.
 */
public class TempSortFile extends SortFile {

    public static final String PREFIX = "pref";
    public static final String SUFFIX = ".txt";

    public TempSortFile() throws IOException {
        super(File.createTempFile(PREFIX, SUFFIX, Launch.TEMP_DIR));
    }
    public static TempSortFile createTemp() {
        try {
            return new TempSortFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
