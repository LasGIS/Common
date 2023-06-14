package com.lasgis.prototype.git.admin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * The Class SessionManagementSecurityConfig definition.
 *
 * @author VLaskin
 * @since 14.06.2023 : 17:08
 */
@Configuration
@EnableWebSecurity
public class SessionManagementSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                    .requestMatchers("/**").hasRole("USER")
            )
            .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build();
        UserDetails admin = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("password")
            .roles("ADMIN", "USER")
            .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
/*
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session will be created or used by spring security
            .and().httpBasic()
            .and().authorizeRequests()
            .antMatchers("/api/hello").permitAll()
            .antMatchers("/api/user/**").permitAll() // allow every URI, that begins with '/api/user/'
            .antMatchers("/api/secured").authenticated()
//            .antMatchers("/admin/**").hasRole("ADMIN")
//            .antMatchers("/**").hasRole("USER")
//            .anyRequest().authenticated() // protect all other requests
            .and()
            .csrf().disable();
        return http.build();
    }
*/
}
