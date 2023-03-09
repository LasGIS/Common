/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.russianpost.tracking.diagnostic.checker.Checker;
import ru.russianpost.tracking.diagnostic.checker.PostgresqlChecker;
import ru.russianpost.tracking.portal.admin.utils.monitoring.RestTemplateChecker;
import ru.russianpost.tracking.portal.admin.utils.monitoring.databus.storage.DatabusStorageChecker;

import javax.sql.DataSource;
import java.util.concurrent.ForkJoinPool;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Nov 24, 2015)
 */
@Configuration
@RequiredArgsConstructor
public class MonitoringConfig {

    @Qualifier("restTemplate")
    private final RestTemplate restTemplate;

    /**
     * Hdps Service Checker
     * @param hdpsPingUrl checked url
     * @return instance of {@link Checker}
     */
    @Bean
    public Checker hdpsServiceChecker(
        @Value("${ru.russianpost.hdps.ping.url}") String hdpsPingUrl
    ) {
        return new RestTemplateChecker("Hdps", hdpsPingUrl, restTemplate);
    }

    /**
     * PostId Service Checker
     * @param postIdPingUrl checked url
     * @return instance of {@link Checker}
     */
    @Bean
    public Checker postIdServiceChecker(
        @Value("${ru.russianpost.postid.service.ping.url}") String postIdPingUrl
    ) {
        return new RestTemplateChecker("PostID", postIdPingUrl, restTemplate);
    }

    /**
     * Portal Backend Service Checker
     * @param portalBackendPingUrl checked url
     * @return instance of {@link Checker}
     */
    @Bean
    public Checker portalBackendChecker(
        @Value("${ru.russianpost.tracking.portal.backend.service.ping.url}") String portalBackendPingUrl
    ) {
        return new RestTemplateChecker("Tracking Portal Backend", portalBackendPingUrl, restTemplate);
    }

    /**
     * Barcode Fetch Provider Service Checker
     * @param barcodePingUrl checked url
     * @return instance of {@link Checker}
     */
    @Bean
    public Checker barcodeProviderServiceChecker(
        @Value("${ru.russianpost.barcode.fetch.provider.service.ping.url}") String barcodePingUrl
    ) {
        return new RestTemplateChecker("Barcode Fetch Provider Service", barcodePingUrl, restTemplate);
    }

    /**
     * Multiple Tracking Service Checker
     * @param multipleTrackingPingUrl checked url
     * @return instance of {@link Checker}
     */
    @Bean
    public Checker multipleTrackingServiceChecker(
        @Value("${multiple-tracking.service.ping.url}") String multipleTrackingPingUrl
    ) {
        return new RestTemplateChecker("Multiple Tracking Service", multipleTrackingPingUrl, restTemplate);
    }

    /**
     * Online Payment Mark Service Checker
     * @param onlinePaymentMarkPingUrl checked url
     * @return instance of {@link Checker}
     */
    @Bean
    public Checker onlinePaymentMarkServiceChecker(
        @Value("${ru.russianpost.online.payment.mark.service.ping.url}") String onlinePaymentMarkPingUrl
    ) {
        return new RestTemplateChecker("OnlinePaymentMark Service", onlinePaymentMarkPingUrl, restTemplate);
    }

    /**
     * Elastic Statistics Service Checker
     * @param elasticStatisticsPingUrl checked url
     * @return instance of {@link Checker}
     */
    @Bean
    public Checker elasticStatisticsServiceChecker(
        @Value("${ru.russianpost.elastic.statistics.ping.url}") String elasticStatisticsPingUrl
    ) {
        return new RestTemplateChecker("Elastic Statistics Service", elasticStatisticsPingUrl, restTemplate);
    }

    /**
     * PostOffice Service checker
     * @param postOfficePingUrl post office ping url
     * @return bean connectivity watcher
     */
    @Bean
    public Checker postOfficeServiceChecker(
        @Value("${ru.russianpost.postoffice.service.ping.url}") String postOfficePingUrl
    ) {
        return new RestTemplateChecker("PostOffice", postOfficePingUrl, restTemplate);
    }

    /**
     * Rtm4601 Service Checker
     * @param rtm4601PingUrl checked url
     * @return instance of {@link Checker}
     */
    @Bean
    public Checker rtm4601ServiceChecker(
        @Value("${ru.russianpost.rtm4601.service.ping.url}") String rtm4601PingUrl
    ) {
        return new RestTemplateChecker("Rtm4601 Service", rtm4601PingUrl, restTemplate);
    }

    /**
     * Databus Storage Checker
     * @param url         databus storage ping url
     * @param accessToken databus storage access token
     * @return instance of {@link Checker}
     */
    @Bean
    public Checker databusStorageChecker(
        @Value("${databus-storage.ping.url}") String url,
        @Value("${databus-storage.ping.access-token}") String accessToken
    ) {
        return new DatabusStorageChecker(restTemplate, url, accessToken);
    }

    /**
     * Postgresql Checker
     * @param dataSource instance of {@link DataSource}
     * @return instance of {@link Checker}
     */
    @Bean
    public Checker postgresqlChecker(final DataSource dataSource) {
        return new PostgresqlChecker(dataSource, 3, "PostgreSQL connectivity checking");
    }

    /**
     * Selftest ForkJoinPool
     * @param parallelism parallelism
     * @return instance of {@link ForkJoinPool}
     */
    @Bean(destroyMethod = "shutdown")
    @Qualifier("selftestForkJoinPool")
    public ForkJoinPool forkJoinPool(@Value("${selftest.parallelism}") int parallelism) {
        return new ForkJoinPool(parallelism);
    }
}
