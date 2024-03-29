package com.lasgis.kafka.spring.producer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaTopicConfig {
    @Value(value = "${bootstrap.servers}")
    private String bootstrapAddress;

/*
    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic() {
        return new NewTopic("lg_topic", 1, (short) 1);
    }
*/
    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> {
            for (final String key : args.getOptionNames()) {
                for (final String data : args.getOptionValues(key)) {
                    template.send("lg_topic", key, data);
                }
            }
        };
    }
}