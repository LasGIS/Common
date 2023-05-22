package com.lasgis.kafka.producer.actors;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MyProducer implements ApplicationListener<ApplicationReadyEvent> {

    private final Producer<String, String> producer;

    public MyProducer(Producer<String, String> producer) {
        this.producer = producer;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("Increment counter");
//        producer.send(new ProducerRecord<>("lg_topic", "key", "value"));
        for (int i = 0; i < 2; i++) {
            producer.send(new ProducerRecord<>("lg_topic",
                "key" + i, "value" + i));
        }
        log.info("Message sent successfully");
        producer.close();
    }
}
