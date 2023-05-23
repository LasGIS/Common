/*
 *  @(#)MyConsumer.java  last: 23.05.2023
 *
 * Title: LG prototype for kafka + akka (simple or spring)
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.test.kafka.actors;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
public class MyConsumer implements ApplicationRunner {

    private final Consumer<String, String> consumer;

    public MyConsumer(Consumer<String, String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run(ApplicationArguments args) {
        consumer.subscribe(List.of("lg_topic"));
        new Thread(() -> {
            while (!Thread.interrupted()) {
                // System.err.println("while processed-----------");
                final ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (final ConsumerRecord<String, String> record : records) {
                    // print the offset,key and value for the consumer records.
                    log.info("partition = {}, offset = {}, key = {}, value = {}",
                        record.partition(), record.offset(), record.key(), record.value());
                }
            }
        }).start();
    }
}
