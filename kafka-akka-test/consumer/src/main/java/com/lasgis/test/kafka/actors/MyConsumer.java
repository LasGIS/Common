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
        log.info("args.getNonOptionArgs() = {}", args.getNonOptionArgs());
        log.info("args.getOptionNames() = {}", args.getOptionNames());
        for (final String optionName : args.getOptionNames()) {
            log.info("Option({}) = {}", optionName, args.getOptionValues(optionName));
        }
    }

    public void initMethod() {
        log.info("Increment counter");
        consumer.subscribe(List.of("lg_topic"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                // print the offset,key and value for the consumer records.
                System.out.printf("offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
            }
        }
    }
}
