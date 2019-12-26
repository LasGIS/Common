/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package com.lasgis.hibernate.check.rest;

import com.lasgis.hibernate.check.dao.entity.RoleEntity;
import com.lasgis.hibernate.check.dao.entity.UserEntity;
import com.lasgis.hibernate.check.dao.repository.RoleRepository;
import com.lasgis.hibernate.check.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * REST controller for web
 */
@RestController
@CrossOrigin(origins = "*")
@Valid
public class SomeController {

    @Value("${app.name}")
    private String appName;

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public SomeController(
        final RoleRepository roleRepository,
        final UserRepository userRepository
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    /**
     * @return app-name
     */
    @GetMapping("/app-name")
    public String getAppName() {
        return appName;
    }

    /**
     * @return roles
     */
    @GetMapping("/roles")
    public List<RoleEntity> getRoles () {
        return roleRepository.findAll();
    }

    /**
     * @return users
     */
    @GetMapping("/users")
    public List<UserEntity> getUsers () {
        return userRepository.findAll();
    }
}
