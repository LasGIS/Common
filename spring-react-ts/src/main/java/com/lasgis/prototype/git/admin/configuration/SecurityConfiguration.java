/*
 *  @(#)SecurityConfiguration.java  last: 15.06.2023
 *
 * Title: LG prototype for spring-security + spring-data + react
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.git.admin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * The Class SecurityConfiguration definition.
 *
 * @author VLaskin
 * @since 14.06.2023 : 17:08
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                    .requestMatchers("/settings").hasAuthority("ADMIN")
                    .requestMatchers("/principal").hasAnyAuthority("ADMIN", "USER")
                    .requestMatchers("/api/user/**").hasAuthority("ADMIN")
                    .requestMatchers("/api/info/**").hasAuthority("USER")
                    .requestMatchers("/**").permitAll()
                    .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());
        return http.build();
    }
}
