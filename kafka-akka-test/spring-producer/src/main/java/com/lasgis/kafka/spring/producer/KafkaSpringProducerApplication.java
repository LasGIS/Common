package com.lasgis.kafka.spring.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "/common-application.yaml")
public class KafkaSpringProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaSpringProducerApplication.class, args);
	}

}
