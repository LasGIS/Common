package com.lasgis.prototype.git.admin.service;


import com.lasgis.prototype.git.admin.model.user.UserUi;

import java.util.List;
import java.util.Optional;

/**
 * The Class UserService definition.
 *
 * @author VLaskin
 * @since 27.06.2023 : 12:48
 */
public interface UserService {
    List<UserUi> findAll();

    Optional<UserUi> findByUsername(String username);

    UserUi save(UserUi newUserUi);

    Optional<UserUi> replaceUser(UserUi uppUserUi, String username);

    UserUi deleteByUsername(String username);
}
