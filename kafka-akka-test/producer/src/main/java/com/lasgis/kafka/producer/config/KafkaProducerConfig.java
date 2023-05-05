package com.lasgis.kafka.producer.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public Producer<String, String> getProducer(
            @Value("${simple.kafka.bootstrap.servers}") String servers,
            @Value("${simple.kafka.transactional.id}") String transactionalId
    ) {
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        props.put("transactional.id", transactionalId);
        return new KafkaProducer<>(props, new StringSerializer(), new StringSerializer());
    }

/*
    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("lg_topic")
                .partitions(10)
                .replicas(1)
                .build();
    }
*/

/*
    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> {
            template.send("lg_topic", "test");
        };
    }
*/
}
