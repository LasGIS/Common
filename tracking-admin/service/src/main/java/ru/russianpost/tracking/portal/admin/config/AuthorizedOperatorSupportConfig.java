/*
 * Copyright 2019 Russian Post
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
import ru.russianpost.tracking.portal.admin.ConfigHolder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * AuthorizedOperatorSupportConfig.
 * @author MKitchenko
 */
@Configuration
public class AuthorizedOperatorSupportConfig {

    /**
     * authorizedOperatorSupportExecutorService
     * @return ExecutorService
     */
    @Bean
    public ExecutorService authorizedOperatorSupportExecutorService() {
        return Executors.newFixedThreadPool(
            ConfigHolder.CONFIG.getInt("authorized.operator.support.executor.service.threads.count")
        );
    }
}
