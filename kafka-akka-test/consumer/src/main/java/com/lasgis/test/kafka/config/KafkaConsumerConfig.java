/*
 *  @(#)KafkaConsumerConfig.java  last: 24.05.2023
 *
 * Title: LG prototype for kafka + akka (simple or spring)
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.test.kafka.config;

import com.lasgis.test.kafka.bean.SpringLifecycle;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Properties;

@RequiredArgsConstructor
@Configuration
public class KafkaConsumerConfig {

    private final KafkaConsumerProperties consumerProperties;

    @Bean(initMethod = "init")
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public SpringLifecycle getSpringLifecycle() {
        return new SpringLifecycle();
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
