package com.lasgis.test.kafka.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public Producer<String, String> getProducer(
        @Value("${bootstrap.servers}") String servers,
        @Value("${transactional.id}") String transactionalId
    ) {
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        props.put("transactional.id", transactionalId);
        return new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
    }
}
