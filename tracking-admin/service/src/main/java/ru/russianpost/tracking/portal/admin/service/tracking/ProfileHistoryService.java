/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service.tracking;

import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.web.model.portal.PortalProfileHistoryEvent;

import java.util.List;
import ru.russianpost.tracking.portal.admin.service.exception.ProfileNotFoundServiceException;

/**
 * ProfileHistoryService
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public interface ProfileHistoryService {

    /**
     * Extracts history for profile with specified ID
     *
     * @param profileId profile ID
     * @return list of events
     * @throws ProfileNotFoundServiceException profile not found
     * @throws ServiceUnavailableException     service is unavailable
     */
    List<PortalProfileHistoryEvent> getHistoryFor(Integer profileId)
            throws ProfileNotFoundServiceException, ServiceUnavailableException;
}
