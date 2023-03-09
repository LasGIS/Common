/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 03, 2015)
 */
@Service
public class KafkaServiceImpl implements KafkaService {

    private static final String CANNOT_CONFIRM_RECORD_SENDING = "Cannot confirm record sending.";

    private final String trackingInputTopic;
    private final String alterationTopic;
    private final long sendTimeoutSeconds;

    private final KafkaProducer<byte[], byte[]> producer;

    /**
     * Constructor
     * @param trackingInputTopic tracking input topic
     * @param alterationTopic    alteration topic
     * @param sendTimeoutSeconds send timeout seconds
     * @param producer           instance of {@link KafkaProducer}
     */
    public KafkaServiceImpl(
        @Value("${ru.russianpost.kafka.input.topic}") String trackingInputTopic,
        @Value("${ru.russianpost.kafka.alteration.topic}") String alterationTopic,
        @Value("${ru.russianpost.kafka.producer.send.timeout.seconds}") long sendTimeoutSeconds,
        KafkaProducer<byte[], byte[]> producer
    ) {
        this.trackingInputTopic = trackingInputTopic;
        this.alterationTopic = alterationTopic;
        this.sendTimeoutSeconds = sendTimeoutSeconds;
        this.producer = producer;
    }

    @Override
    public void produceToInput(byte[] key, byte[] value) throws Exception {
        produce(trackingInputTopic, key, value);
    }

    @Override
    public void produceToAlteration(byte[] key, byte[] value) throws Exception {
        produce(alterationTopic, key, value);
    }

    private void produce(String topic, byte[] key, byte[] value) throws Exception {
        final ProducerRecord<byte[], byte[]> record = new ProducerRecord<>(topic, key, value);
        final Future<RecordMetadata> future = producer.send(record);
        try {
            future.get(sendTimeoutSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new Exception(CANNOT_CONFIRM_RECORD_SENDING, e);
        } catch (ExecutionException | TimeoutException e) {
            throw new Exception(CANNOT_CONFIRM_RECORD_SENDING, e);
        }
    }
}
