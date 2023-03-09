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

import ru.russianpost.tracking.portal.admin.model.admin.history.UserHistoryEvent;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;

import java.util.Optional;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Jan 26, 2016)
 */
public interface AdminServiceUserService {

    /**
     * Resolves username to service user
     * @param username username
     * @return user object or null if user not found
     */
    Optional<ServiceUser> resolve(String username);

    /**
     * @param event user history event
     */
    void logUserHistoryEvent(UserHistoryEvent event);

}
