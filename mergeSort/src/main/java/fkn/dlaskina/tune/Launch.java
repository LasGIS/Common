package fkn.dlaskina.tune;

import java.io.FileNotFoundException;

/**
 * Definition of the Launch class
 * @author VLaskin
 * @since 02.05.2016.
 */
public class Launch {

    public static void main(String[] args) {
        final Correct cor = new Correct();
        cor.correct(args[0], args[1]);
    }
}
