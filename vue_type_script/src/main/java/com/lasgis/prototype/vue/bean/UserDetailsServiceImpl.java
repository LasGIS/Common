/*
 *  @(#)UserDetailsServiceImpl.java  last: 13.06.2023
 *
 * Title: LG prototype for java-spring-jdbc + vue-type-script
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */

package com.lasgis.prototype.vue.bean;

import com.lasgis.prototype.vue.model.UserDto;
import com.lasgis.prototype.vue.model.UserRole;
import com.lasgis.prototype.vue.repository.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * The Class UserDetailsServiceImpl definition.
 *
 * @author VLaskin
 * @since 13.06.2023 : 17:53
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        final UserDto user = userDao.findByLogin(login).orElseThrow(
            () -> new UsernameNotFoundException(String.format("User with such login %s not found!", login)));
        log.info("Found user {}", user);
        return User.builder()
            .username(user.getName())
            .password("{noop}" + user.getPassword())
            .disabled(user.getArchived())
            .roles(user.getRoles().stream().map(UserRole::name).toArray(String[]::new))
            .build();
    }
}
