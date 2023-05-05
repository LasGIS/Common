package com.lasgis.kafka.spring.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.ResourceUtils;

@SpringBootApplication
@PropertySource(ResourceUtils.CLASSPATH_URL_PREFIX + "common-application.yaml")
public class KafkaSpringProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringProducerApplication.class, args);
	}

}
