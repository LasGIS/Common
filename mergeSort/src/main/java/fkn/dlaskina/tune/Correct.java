package fkn.dlaskina.tune;

import java.io.*;

/**
 * Definition of the Correct class
 * @author VLaskin
 * @since 02.05.2016.
 */
public class Correct {

    public static final String CHARSET_INPUT = "Windows-1251";
    public static final String CHARSET_OUT = "UTF-8";
    public static final int STRING_LEN = 100;

    public void correct(final String inpFileName, final String outFileName) {
        try (
            final BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(inpFileName), CHARSET_INPUT
                )
            );
            final Writer writer = new OutputStreamWriter(
                new FileOutputStream(outFileName, false), CHARSET_OUT
            );
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                String out;
                while (line.length() > STRING_LEN) {
                    out = line.substring(0, STRING_LEN);
                    line = line.substring(STRING_LEN);
                    writer.write(out);
                    writer.write("\r\n");
                }
                writer.write(line);
                writer.write("\r\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
