package com.lasgis.test.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "kafka.consumer")
public class KafkaConsumerProperties {
    private String bootstrapServers;
    private String groupId;
    private Boolean enableAutoCommit;
    private Integer autoCommitIntervalMs;
    private Integer sessionTimeoutMs;
    private String keyDeserializer;
    private String valueDeserializer;
}
