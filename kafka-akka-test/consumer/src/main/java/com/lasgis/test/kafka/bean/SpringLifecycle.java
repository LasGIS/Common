/*
 *  @(#)SpringLifecycle.java  last: 24.05.2023
 *
 * Title: LG prototype for kafka + akka (simple or spring)
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.test.kafka.bean;

import com.lasgis.test.kafka.actors.MyConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * The Class CircularDependencyA definition.
 *
 * @author VLaskin
 * @since 23.05.2023 : 12:08
 */
@Slf4j
public class SpringLifecycle implements ApplicationContextAware, InitializingBean, DisposableBean, ApplicationRunner, CommandLineRunner {

    private ApplicationContext applicationContext;

    /**
     * 1 - Inside Constructor
     * 2 - setApplicationContext realization of ApplicationContextAware
     * 3 - Inside @PostConstruct method
     * 4 - afterPropertiesSet realization of InitializingBean
     * 5 - Inside @Bean(initMethod = "init") method
     * --- Started KafkaConsumerApp in 0.636 seconds (JVM running for 2.174)
     * 6 - run(ApplicationArguments args) realization of ApplicationRunner
     * 7 - run(String... args) realization of CommandLineRunner
     * 8 - Inside @EventListener method for ApplicationReadyEvent event
     * ---------------------
     * - down 1 - Inside @PreDestroy method
     * - down 2 - destroy() realization of DisposableBean
     */
    public SpringLifecycle() {
        log.info("1 - Inside Constructor");
    }

    /**
     * realization of ApplicationContextAware
     */
    @Override
    public void setApplicationContext(@NonNull final ApplicationContext applicationContext) throws BeansException {
        log.info("2 - setApplicationContext realization of ApplicationContextAware");
        this.applicationContext = applicationContext;
    }

    /**
     *
     */
    @PostConstruct
    public void postConstruct() {
        log.info("3 - Inside @PostConstruct method");
    }

    /**
     * realization of InitializingBean
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        final MyConsumer myConsumer = applicationContext.getBean(MyConsumer.class);
        log.info("4 - afterPropertiesSet realization of InitializingBean");
    }

    public void init() {
        log.info("5 - Inside @Bean(initMethod = \"init\") method");
    }

    /**
     * realization of ApplicationRunner
     *
     * @param args incoming application arguments
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("6 - run(ApplicationArguments args) realization of ApplicationRunner");
        log.info("args.getNonOptionArgs() = {}", args.getNonOptionArgs());
        log.info("args.getOptionNames() = {}", args.getOptionNames());
        for (final String optionName : args.getOptionNames()) {
            log.info("Option({}) = {}", optionName, args.getOptionValues(optionName));
        }
    }

    /**
     * realization of CommandLineRunner
     *
     * @param args incoming main method arguments
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("7 - run(String... args) realization of CommandLineRunner");
        log.info(" args.length = {}", args.length);
    }

    /**
     * Некоторые наиболее важные события spring boot,
     * - ApplicationContextInitializedEvent: запускается после подготовки ApplicationContext и вызова ApplicationContextInitializers, но до загрузки определений bean-компонентов
     * - ApplicationPreparedEvent:           запускается после загрузки определений bean-компонентов
     * - ApplicationStartedEvent:            запускается после обновления контекста, но до вызова командной строки и запуска приложений.
     * - ApplicationReadyEvent:              запускается после вызова любого приложения и запуска командной строки
     * - ApplicationFailedEvent:             срабатывает, если есть исключение при запуске
     * Можно создать несколько ApplicationListeners. Их можно упорядочивать с помощью аннотации @Order или с помощью интерфейса Ordered.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void runEventListenerApplicationReadyEvent() {
        log.info("8 - Inside @EventListener method for ApplicationReadyEvent event");
    }

    @PreDestroy
    void preDestroy() {
        log.info("down 1 - Inside @PreDestroy method");
    }

    @Override
    public void destroy() throws Exception {
        log.info("down 2 - destroy() realization of DisposableBean");
    }
}
