package com.lasgis.kafka.spring.consumer.actors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {
    @KafkaListener(id = "my-transactional-id", topics = "${topic}")
    public void listen(
        @Payload String message,
        @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition
    ) {
        log.info("KafkaListener key={}, message=\"{}\", partition={}", key, message, partition);
    }
}
