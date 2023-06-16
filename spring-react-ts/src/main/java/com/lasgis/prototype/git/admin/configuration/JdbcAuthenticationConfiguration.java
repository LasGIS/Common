/*
 *  @(#)JdbcAuthenticationConfiguration.java  last: 16.06.2023
 *
 * Title: LG prototype for spring-security + spring-data + react
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.git.admin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * The Class JdbcAuthenticationConfiguration definition.
 *
 * @author VLaskin
 * @since 15.06.2023 : 13:16
 */
@Configuration
public class JdbcAuthenticationConfiguration {
    private final DataSource dataSource;

    public JdbcAuthenticationConfiguration(
        final DataSource dataSource
    ) {
        this.dataSource = dataSource;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
