package com.lasgis.kafka.producer.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@RequiredArgsConstructor
@Configuration
public class KafkaProducerConfig {

    private final KafkaProducerProperties producerProperties;

    @Bean
    public Producer<String, String> getProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", producerProperties.getBootstrapServers());
//        props.put("transactional.id", producerProperties.getTransactionalId());
        props.put("acks", producerProperties.getAcks());
        props.put("retries", producerProperties.getRetries());
        props.put("batch.size", producerProperties.getBatchSize());
        props.put("linger.ms", producerProperties.getLingerMs());
        props.put("buffer.memory", producerProperties.getBufferMemory());
        props.put("key.serializer", producerProperties.getKeySerializer());
        props.put("value.serializer", producerProperties.getValueSerializer());
        return new KafkaProducer<>(props);
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
