package com.lasgis.prototype.vue.configuration;

import com.lasgis.prototype.vue.dto.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No session will be created or used by spring security
            .and().httpBasic()
            .and().authorizeRequests()
            .antMatchers("/api/hello").permitAll()
            .antMatchers("/api/user/**").permitAll() // allow every URI, that begins with '/api/user/'
            .antMatchers("/api/secured").authenticated()
            //.anyRequest().authenticated() // protect all other requests
            .and()
            .csrf().disable(); // disable cross site request forgery, as we don't use cookies - otherwise ALL PUT, POST, DELETE will get HTTP 403!
//        http.authorizeHttpRequests((authorizeHttpRequests) ->                authorizeHttpRequests
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/**").hasRole("USER")
//            )
//            .formLogin(withDefaults());
//            .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
                // todo get DtoUser from DB by username
                final List<String> roles = new ArrayList<>();
                if (username.toLowerCase().contains("oper")) {
                    roles.add(UserRole.OPERATOR.name());
                }
                if (username.toLowerCase().contains("adm")) {
                    roles.add(UserRole.ADMIN.name());
                }
                if (roles.isEmpty()) {
                    roles.add(UserRole.CHIEF.name());
                }
                return User.builder()
                    .username(username)
                    .password("{noop}password")
                    .roles(roles.toArray(new String[0]))
                    .build();
            }
        };
/*
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
*/
    }
}
