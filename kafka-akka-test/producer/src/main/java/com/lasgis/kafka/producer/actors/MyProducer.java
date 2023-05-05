package com.lasgis.kafka.producer.actors;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyProducer {

    private final Producer<String, String> producer;

    public MyProducer(Producer<String, String> producer) {
        this.producer = producer;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("Increment counter");
        producer.send(new ProducerRecord<>("lg_topic", "key", "value"));
    }
}
