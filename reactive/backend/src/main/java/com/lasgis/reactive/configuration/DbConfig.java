/*
 *  @(#)DbConfig.java  last: 31.08.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.reactive.configuration;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.flywaydb.core.internal.jdbc.DriverDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * DbConfig
 *
 * @author VLaskin
 * @version 1.0 23.09.2020
 */
@Configuration
@ConditionalOnProperty(value = "flyway.migration", havingValue = "RUN")
public class DbConfig {

    /**
     * @param driverClassName driver classname
     * @param url             url
     * @param username        username
     * @param password        password
     * @return instance of {@link DataSource}
     */
    @Bean
    public DataSource dataSource(
        @Value("${flyway.datasource.driverClassName}") String driverClassName,
        @Value("${flyway.datasource.url}") String url,
        @Value("${flyway.datasource.username}") String username,
        @Value("${flyway.datasource.password}") String password
    ) {
        return new DriverDataSource(this.getClass().getClassLoader(), driverClassName, url, username, password);
    }

    /**
     * @param dataSource instance of {@link DataSource}
     * @return instance of {@link Flyway}
     */
    @Bean(initMethod = "migrate")
    public Flyway flyway(
        final DataSource dataSource,
        @Value("${flyway.locations}") String locations,
        @Value("${flyway.baselineOnMigrate}") Boolean baselineOnMigrate,
        @Value("${flyway.datasource.schema:}") String schema
    ) {
        final FluentConfiguration configuration = new FluentConfiguration()
            .dataSource(dataSource)
            .locations(locations)
            .baselineOnMigrate(baselineOnMigrate);
        if (isNotBlank(schema)) {
            configuration.schemas(schema);
        }
        return configuration.load();
    }
}
