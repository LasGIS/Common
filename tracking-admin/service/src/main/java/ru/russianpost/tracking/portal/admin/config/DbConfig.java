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

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * DbConfig
 *
 * @author MKitchenko
 * @version 1.0 23.09.2020
 */
@Configuration
public class DbConfig {

    /**
     * @param driverClassName        driver classname
     * @param url                    url
     * @param username               username
     * @param password               password
     * @param validationQueryTimeout validation query timeout
     * @return instance of {@link BasicDataSource}
     */
    @Bean(destroyMethod = "close")
    public BasicDataSource dataSource(
        @Value("${jdbc.driverClassName}") String driverClassName,
        @Value("${jdbc.url}") String url,
        @Value("${jdbc.username}") String username,
        @Value("${jdbc.password}") String password,
        @Value("${jdbc.validationQueryTimeout}") int validationQueryTimeout
    ) {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setValidationQueryTimeout(validationQueryTimeout);
        return dataSource;
    }

    /**
     * @param dataSource instance of {@link DataSource}
     * @return instance of {@link DataSourceTransactionManager}
     */
    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * @param dataSource instance of {@link DataSource}
     * @return instance of {@link JdbcTemplate}
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * @param dataSource instance of {@link DataSource}
     * @return instance of {@link Flyway}
     */
    @Bean(initMethod = "migrate")
    public Flyway flyway(final DataSource dataSource) {
        return new FluentConfiguration()
            .dataSource(dataSource)
            .locations("classpath:/db/migration")
            .baselineOnMigrate(true)
            .load();
    }
}
