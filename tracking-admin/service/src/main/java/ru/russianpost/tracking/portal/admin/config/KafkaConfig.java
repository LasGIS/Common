/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * KafkaConfig
 * @author MKitchenko
 * @version 1.0 03.08.2020
 */
@Configuration
public class KafkaConfig {

    /**
     * @param kafkaBrokers         kafka brokers info
     * @param kafkaProducerAcks    kafka producer acks
     * @param kafkaProducerRetries kafka producer retries
     * @param kafkaLogin           kafka login
     * @param kafkaPassword        kafka password
     * @return instance of {@link KafkaProducer}
     */
    @Bean(destroyMethod = "close")
    public KafkaProducer<byte[], byte[]> kafkaProducer(
        @Value("${ru.russianpost.kafka.brokers}") String kafkaBrokers,
        @Value("${ru.russianpost.kafka.producer.acks}") String kafkaProducerAcks,
        @Value("${ru.russianpost.kafka.producer.retries}") String kafkaProducerRetries,
        @Value("${ru.russianpost.kafka.login}") String kafkaLogin,
        @Value("${ru.russianpost.kafka.password}") String kafkaPassword
    ) {
        final Properties props = new Properties();
        props.put("bootstrap.servers", kafkaBrokers);
        props.put("acks", kafkaProducerAcks);
        props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put("retries", kafkaProducerRetries);

        if (!kafkaLogin.trim().isEmpty() && !kafkaPassword.trim().isEmpty()) {
            final String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
            final String jaasCfg = String.format(jaasTemplate, kafkaLogin, kafkaPassword);

            props.put("security.protocol", "SASL_PLAINTEXT");
            props.put("sasl.mechanism", "SCRAM-SHA-256");
            props.put("sasl.jaas.config", jaasCfg);
        }

        return new KafkaProducer<>(props);
    }
}
