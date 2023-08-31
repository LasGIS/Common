package com.lasgis.prototype.git.admin.service.impl;

import com.lasgis.prototype.git.admin.model.user.LockStatus;
import com.lasgis.prototype.git.admin.model.user.UserRole;
import com.lasgis.prototype.git.admin.model.user.UserUi;
import com.lasgis.prototype.git.admin.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The Class UserServiceImpl definition.
 *
 * @author VLaskin
 * @since 27.06.2023 : 13:25
 */
@Slf4j
@Controller
public class UserServiceImpl implements UserService {
    private final Map<String, UserUi> USER_MAP = new HashMap<>();

    @PostConstruct
    public void postConstruct() {
        final List<UserUi> userUis = List.of(
            UserUi.builder()
                .username("Pupkin")
                .email("VPupkin@email.ru")
                .status(LockStatus.UNLOCKED)
                .roles(List.of(UserRole.ADMIN, UserRole.OPERATOR))
                .createDt(ZonedDateTime.of(LocalDateTime.of(2020, Month.APRIL, 1, 9, 35), ZoneId.of("GMT+03:00")))
                .lastDt(ZonedDateTime.of(LocalDateTime.of(2022, Month.FEBRUARY, 24, 12, 25), ZoneId.of("GMT+07:00")))
                .build(),
            UserUi.builder()
                .username("Petrov")
                .email("APetrov@email.ru")
                .status(LockStatus.UNLOCKED)
                .roles(List.of(UserRole.ADMIN, UserRole.OPERATOR))
                .createDt(ZonedDateTime.of(LocalDateTime.of(1963, Month.FEBRUARY, 22, 9, 15), ZoneId.of("GMT+06:00")))
                .lastDt(ZonedDateTime.now(ZoneId.of("GMT+07:00")))
                .build()
        );
        userUis.forEach(userUi -> USER_MAP.put(userUi.getUsername(), userUi));
    }


    @Override
    public List<UserUi> findAll() {
        return USER_MAP.values().stream().toList();
    }

    @Override
    public Optional<UserUi> findByUsername(String username) {
        return Optional.ofNullable(USER_MAP.get(username));
    }

    @Override
    public UserUi save(UserUi newUserUi) {
        return USER_MAP.put(newUserUi.getUsername(), newUserUi);
    }

    @Override
    public Optional<UserUi> replaceUser(UserUi uppUserUi, String username) {
        USER_MAP.put(uppUserUi.getUsername(), uppUserUi);
        return Optional.ofNullable(uppUserUi);
    }

    @Override
    public UserUi deleteByUsername(String username) {
        return USER_MAP.remove(username);
    }
}
