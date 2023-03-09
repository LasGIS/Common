/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.config;

import com.ecyrd.speed4j.StopWatchFactory;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import ru.russianpost.tracking.portal.admin.service.AuthorSignInterceptor;
import ru.russianpost.tracking.portal.admin.service.PlusEncoderInterceptor;
import ru.russianpost.tracking.portal.admin.service.SecurityContext;
import ru.russianpost.tracking.web.model.utils.json.AccessTypeDeserializer;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * ServiceConfig
 * @author Roman Prokhorov
 * @version 1.0
 */
@Configuration
public class ServiceConfig {

    /**
     * Creates rest template bean
     * @param securityContext    user info provider
     * @param connectTimeoutInMs connect timeout in ms
     * @param readTimeoutInMs    read timeout in ms
     * @return rest template bean
     */
    @Bean
    @Qualifier("restTemplate")
    public RestTemplate restTemplate(
        final SecurityContext securityContext,
        @Value("${external.service.connect.timeout.in.ms}") int connectTimeoutInMs,
        @Value("${external.service.read.timeout.in.ms}") int readTimeoutInMs
    ) {
        RestTemplate restTemplate = new RestTemplate();

        final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(readTimeoutInMs);
        requestFactory.setConnectTimeout(connectTimeoutInMs);
        restTemplate.setRequestFactory(requestFactory);


        restTemplate.setInterceptors(Stream.of(
                new PlusEncoderInterceptor(),
                new AuthorSignInterceptor(securityContext, EnumSet.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)))
                .collect(toList())
        );
        final MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setPrettyPrint(true);
        jacksonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        jacksonConverter.getObjectMapper().registerModule(new SimpleModule().addDeserializer(
                ru.russianpost.tracking.web.model.core.AccessType.class,
                new AccessTypeDeserializer(Collections.emptyMap())
        ));
        jacksonConverter.getObjectMapper().registerModule(new JavaTimeModule());

        final FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
        formConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.MULTIPART_FORM_DATA));

        final StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
        stringConverter.setSupportedMediaTypes(Arrays.asList(MediaType.TEXT_HTML, MediaType.TEXT_PLAIN));

        restTemplate.setMessageConverters(Arrays.asList(stringConverter, formConverter, jacksonConverter, new ByteArrayHttpMessageConverter()));

        return restTemplate;
    }

    /**
     * Creates rest template bean for multiple tracking
     * @param connectTimeout connect timeout
     * @param readTimeout    read timeout
     * @return rest template bean
     */
    @Bean
    @Qualifier("restTemplateMultipleTracking")
    public RestTemplate restTemplateMultipleTracking(
        @Value("${multiple-tracking.service.connect-timeout}") int connectTimeout,
        @Value("${multiple-tracking.service.read-timeout}") int readTimeout
    ) {
        RestTemplate restTemplate = new RestTemplate();

        final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        restTemplate.setRequestFactory(requestFactory);

        final MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        final FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
        formConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.MULTIPART_FORM_DATA));
        restTemplate.setMessageConverters(Arrays.asList(formConverter, new ByteArrayHttpMessageConverter(), jacksonConverter));
        return restTemplate;
    }

    /**
     * Creates rest template bean for Emsevt Manual sender
     * @param connectTimeout connect timeout
     * @param readTimeout    read timeout
     * @return rest template bean
     */
    @Bean
    @Qualifier("restTemplateEmsevtManual")
    public RestTemplate restTemplateEmsevtManual(
        @Value("${emsevt-manual-sender.service.connect-timeout}") int connectTimeout,
        @Value("${emsevt-manual-sender.service.read-timeout}") int readTimeout
    ) {
        final RestTemplate restTemplate = new RestTemplate();

        final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        restTemplate.setRequestFactory(requestFactory);

        final MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        final FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
        formConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.MULTIPART_FORM_DATA));
        restTemplate.setMessageConverters(Arrays.asList(formConverter, new ByteArrayHttpMessageConverter(), jacksonConverter));
        return restTemplate;
    }

    /**
     * Creates rest template bean for international monitoring
     * @param connectTimeout connect timeout
     * @param readTimeout    read timeout
     * @return rest template bean
     */
    @Bean
    @Qualifier("restTemplateInternationalMonitoring")
    public RestTemplate restTemplateInternationalMonitoring(
        @Value("${international-monitoring.service.connect-timeout}") int connectTimeout,
        @Value("${international-monitoring.service.read-timeout}") int readTimeout
    ) {
        final RestTemplate restTemplate = new RestTemplate();

        final SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(connectTimeout);
        requestFactory.setReadTimeout(readTimeout);
        restTemplate.setRequestFactory(requestFactory);

        final MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
        final FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
        formConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.MULTIPART_FORM_DATA));
        restTemplate.setMessageConverters(Arrays.asList(formConverter, new ByteArrayHttpMessageConverter(), jacksonConverter));
        return restTemplate;
    }

    /**
     * Creates json view bean
     * @return json view bean
     */
    @Bean
    public MappingJackson2JsonView mappingJackson2JsonView() {
        return new MappingJackson2JsonView();
    }

    /**
     * Creates user info provider as security context
     * @return security context
     */
    @Bean
    public SecurityContext securityContext() {
        return new SecurityContext();
    }

    /**
     * Resolver for multipart request data.
     * @return StandardServletMultipartResolver bean.
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    /**
     * Creates TaskExecutor bean
     * @return TaskExecutor bean
     */
    @Bean
    public TaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.setThreadNamePrefix("rtm30SendingTaskExecutor");
        executor.initialize();
        return executor;
    }

    /**
     * StopWatchFactory bean
     * @return instance of {@link StopWatchFactory}
     */
    @Bean
    public StopWatchFactory stopWatchFactory() {
        return StopWatchFactory.getInstance("loggingFactory");
    }
}
