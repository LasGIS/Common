/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserHistoryEvent;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;
import ru.russianpost.tracking.portal.admin.repository.ServiceUserDao;

import java.text.MessageFormat;
import java.util.Optional;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Jan 26, 2016)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceUserServiceImpl implements AdminServiceUserService {

    private final ServiceUserDao serviceUserDao;

    @Override
    public Optional<ServiceUser> resolve(String username) {
        try {
            return Optional.ofNullable(serviceUserDao.getServiceUser(username));
        } catch (Exception ex) {
            log.error(
                MessageFormat.format("Could not resolve service user by username {0}. Message: {1}",
                    username, ex.getMessage()), ex
            );
            return Optional.empty();
        }
    }

    @Override
    public void logUserHistoryEvent(UserHistoryEvent event) {
        try {
            serviceUserDao.logEvent(event);
        } catch (Exception e) {
            log.warn("Could not log event to database. Event: {}. Message: {}", event, e.getMessage());
        }
    }
}
