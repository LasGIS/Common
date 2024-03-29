package com.lasgis.kafka.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "/common-application.yaml")
public class KafkaProducerApp {
    public static void main(String[] args) {
        SpringApplication.run(KafkaProducerApp.class, args);
    }
}