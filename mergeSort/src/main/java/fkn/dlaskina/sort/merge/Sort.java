package fkn.dlaskina.sort.merge;

import java.io.*;
import java.util.*;

import static fkn.dlaskina.sort.comparators.SortMap.CHARSET;

/**
 * Definition of the Sort class
 * @author VLaskin
 * @since 02.05.2016.
 */
public class Sort {

    private final String inputFileName;
    private final String outputFileName;

    public Sort(
        String inputFileName,
        String outputFileName
    ) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    public void run() {
        List<IndexLine> list = new ArrayList<>();
        try (
            final BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(inputFileName), CHARSET
                )
            );
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                final IndexLine xLine = new IndexLine(line);
                list.add(xLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        saveOut(list);
    }

    private void saveOut(List<IndexLine> list) {
        Collections.sort(list);
        try (
            final Writer writer = new OutputStreamWriter(
                new FileOutputStream(outputFileName, false), CHARSET
            );
        ) {
            for (IndexLine xLine : list) {
                writer.write(xLine.getLine());
                writer.write("\r\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
