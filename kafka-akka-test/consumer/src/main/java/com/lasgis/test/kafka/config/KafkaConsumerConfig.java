package com.lasgis.test.kafka.config;

import com.lasgis.test.kafka.actors.MyConsumer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@RequiredArgsConstructor
@Configuration
public class KafkaConsumerConfig {

    private final KafkaConsumerProperties consumerProperties;

    @Bean(initMethod = "initMethod")
    public MyConsumer getMyConsumer(Consumer<String, String> producer) {
        return new MyConsumer(producer);
    }

    @Bean
    public Consumer<String, String> getConsumer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", consumerProperties.getBootstrapServers());
        props.put("group.id", consumerProperties.getGroupId());
        props.put("enable.auto.commit", consumerProperties.getEnableAutoCommit());
        props.put("auto.commit.interval.ms", consumerProperties.getAutoCommitIntervalMs());
        props.put("session.timeout.ms", consumerProperties.getSessionTimeoutMs());
        props.put("key.deserializer", consumerProperties.getKeyDeserializer());
        props.put("value.deserializer", consumerProperties.getValueDeserializer());
        return new KafkaConsumer<>(props);
    }
}
