package fkn.dlaskina.sort.merge;

import java.io.File;
import java.io.IOException;

/**
 * Definition of the SortFile class
 * @author VLaskin
 * @since 03.05.2016.
 */
public class SortFile {

    private final File file;

    public SortFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public static SortFile create(String fileName) {
        return new SortFile(new File(fileName));
    }
}
