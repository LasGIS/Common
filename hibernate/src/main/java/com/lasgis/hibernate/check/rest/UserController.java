/*
 *  @(#)UserController.java  last: 01.06.2023
 *
 * Title: LG prototype for hibernate
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.hibernate.check.rest;

import com.lasgis.hibernate.check.dao.entity.UserEntity;
import com.lasgis.hibernate.check.dao.entity.UserRole;
import com.lasgis.hibernate.check.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for web
 */
@RestController
@CrossOrigin(origins = "*")
@Valid
@RequestMapping("/api/v1/user")
public class UserController {

    @Value("${app.name}")
    private String appName;

    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
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
    public List<UserRole> getRoles() {
        return List.of(UserRole.values());
    }

    /**
     * @return users
     */
    @GetMapping()
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public Optional<UserEntity> getUserById(@PathVariable("id") final Long id) {
        return userRepository.findById(id);
    }

    @GetMapping(path = "login")
    public Optional<UserEntity> getUserByLogin(@RequestParam("login") final String login) {
        return userRepository.findByLogin(login);
    }

    @PostMapping()
    UserEntity newUser(@RequestBody UserEntity newUser) {
        return userRepository.saveAndFlush(newUser);
    }

    @PutMapping("{id}")
    Optional<UserEntity> replaceEmployee(@RequestBody UserEntity newUser, @PathVariable Long id) {
        final Optional<UserEntity> user = userRepository.findById(id);
        UserEntity[] outUser = new UserEntity[1];
        user.ifPresentOrElse(userEntity -> {
            userEntity.setLogin(newUser.getLogin());
            userEntity.setName(newUser.getName());
            userEntity.setPassword(newUser.getPassword());
            userEntity.setRoles(newUser.getRoles());
            userEntity.setArchived(newUser.getArchived());
            outUser[0] = userRepository.saveAndFlush(userEntity);
        }, () -> outUser[0] = userRepository.saveAndFlush(newUser));
        userRepository.flush();
        return Optional.of(outUser[0]);
    }

    @DeleteMapping("{id}")
    void deleteEmployee(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
