package com.lasgis.kafka.spring.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "/common-application.yaml")
public class KafkaSpringConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(KafkaSpringConsumerApplication.class, args);
    }
}
