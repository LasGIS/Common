package com.lasgis.hibernate.check;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <description>
 *
 * @author VLaskin
 * @since <pre>03.04.2019</pre>
 */
@SpringBootApplication
@EnableTransactionManagement
public class Application {

    /**
     * Hidden constructor
     */
    protected Application() {
        super();
    }

    /**
     * com.lasgis.hibernate.check.Main method
     *
     * @param args cmd line arguments
     */
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
//                .web(WebApplicationType.NONE)
//            .bannerMode(Banner.Mode.OFF)
            .run(args);
    }

}
