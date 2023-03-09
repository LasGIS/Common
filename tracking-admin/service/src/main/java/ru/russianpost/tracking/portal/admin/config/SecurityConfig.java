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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.russianpost.tracking.portal.admin.security.CustomDaoAuthenticationProvider;
import ru.russianpost.tracking.portal.admin.security.ExtendedPersistentTokenRepository;
import ru.russianpost.tracking.portal.admin.security.ExtendedPersistentTokenRepositoryImpl;

import javax.sql.DataSource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * SecurityConfig
 * @author MKitchenko
 * @version 1.0 19.10.2020
 */
@Configuration
public class SecurityConfig {

    /**
     * ExtendedPersistentTokenRepository
     * @param dataSource instance of {@link DataSource}
     * @return ExtendedPersistentTokenRepository
     */
    @Bean
    public ExtendedPersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        final ExtendedPersistentTokenRepositoryImpl repository = new ExtendedPersistentTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

    /**
     * @return instance of {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * @param userDetailsService instance of {@link UserDetailsService} that will be set in result
     * @return instance of {@link DaoAuthenticationProvider}
     * @throws Exception if any exception occurs while creating {@link CustomDaoAuthenticationProvider}
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService) throws Exception {
        final DaoAuthenticationProvider result = new CustomDaoAuthenticationProvider();
        result.setUserDetailsService(userDetailsService);
        result.setPasswordEncoder(passwordEncoder());
        result.afterPropertiesSet();
        return result;
    }

    /**
     * ScheduledExecutorService bean
     * @return instance of {@link ScheduledExecutorService}
     */
    @Bean(destroyMethod = "shutdown")
    public ScheduledExecutorService rememberMeExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }
}
