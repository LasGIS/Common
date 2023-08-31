package com.lasgis.prototype.git.admin.controller;

import com.lasgis.prototype.git.admin.model.user.UserRole;
import com.lasgis.prototype.git.admin.model.user.UserUi;
import com.lasgis.prototype.git.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * UserUi table management Controller
 *
 * @author VLaskin
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
public class UserController {
    private final UserService userService;

    /**
     * @return roles
     */
    @GetMapping("/roles")
    public List<UserRole> getRoles() {
        return List.of(UserRole.values());
    }

    /**
     * @return All users
     */
    @GetMapping()
    public List<UserUi> getUsers() {
        return userService.findAll();
    }

    /**
     * Get user by username
     *
     * @param username username
     * @return user by username
     */
    @GetMapping(path = "{username}")
    public Optional<UserUi> getUserByUsername(@PathVariable("username") final String username) {
        return userService.findByUsername(username);
    }

    /**
     * Create new UserUi
     *
     * @param newUserUi new UserUi
     * @return Created UserUi
     */
    @PostMapping()
//    @Secured("ROLE_ADMIN")
    UserUi newUser(@RequestBody UserUi newUserUi) {
        return userService.save(newUserUi);
    }

    /**
     * Update UserUi
     *
     * @param uppUserUi  info UserUi for Update
     * @param username user name
     * @return Created or Updated UserUi
     */
    @PutMapping(value = "{username}")
    Optional<UserUi> replaceUser(@RequestBody UserUi uppUserUi, @PathVariable String username) {
        return userService.replaceUser(uppUserUi, username);
    }

    @DeleteMapping("{username}")
    UserUi deleteByUsername(@PathVariable String username) {
        return userService.deleteByUsername(username);
    }
}
