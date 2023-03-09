/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import ru.russianpost.tracking.portal.admin.ConfigHolder;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @author Amosov Maxim
 * @since 31.08.2021 : 12:38
 */
@Order(1)
@Configuration
public class IdmSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final String USERNAME = ConfigHolder.CONFIG.getString("idm.username");
    private static final String PASSWORD = ConfigHolder.CONFIG.getString("idm.password");

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser(USERNAME).password(PASSWORD).authorities("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final BasicAuthenticationEntryPoint authEntryPoint = new BasicAuthenticationEntryPoint();
        authEntryPoint.setRealmName("tracking-portal-admin");

        http.antMatcher("/api/v1/idm/**")
            .csrf().disable()
            .authorizeRequests(authorize -> authorize.anyRequest().authenticated())
            .httpBasic()
            .authenticationEntryPoint(authEntryPoint)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(STATELESS);
    }
}
