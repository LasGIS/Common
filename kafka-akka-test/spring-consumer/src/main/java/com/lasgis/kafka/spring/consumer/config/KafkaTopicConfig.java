package com.lasgis.kafka.spring.consumer.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
/*
    @Value(value = "${bootstrap.servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic("lg_topic", 1, (short) 1);
    }
*/
}