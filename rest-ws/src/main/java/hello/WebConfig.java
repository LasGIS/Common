package hello;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Class WebConfig.
 * @author Vladimir Laskin
 * @version 1.0
 */
@Configuration
public class WebConfig {
    @Bean
     public Filter getCORSFilter() {
       return new CORSFilter();
     }
}
