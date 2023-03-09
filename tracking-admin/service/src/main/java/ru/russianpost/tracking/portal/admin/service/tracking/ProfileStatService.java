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
import ru.russianpost.tracking.portal.admin.service.exception.ProfileNotFoundServiceException;
import ru.russianpost.tracking.web.model.admin.AdminPageAccessTypeStatistic;
import ru.russianpost.tracking.web.model.admin.AdminPageProfileCounters;
import ru.russianpost.tracking.web.model.admin.AdminProfileStat;
import ru.russianpost.tracking.web.model.admin.AdminProtocolRequestStatistics;
import ru.russianpost.tracking.web.model.core.Protocol;
import ru.russianpost.tracking.web.model.utils.notification.service.Paging;

import java.util.List;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 18, 2015)
 */
public interface ProfileStatService {

    /**
     * Counter values for profile with specified ID
     * @param profileId id
     * @return counter values object
     * @throws ProfileNotFoundServiceException profile not found
     * @throws ServiceUnavailableException     service is not available
     */
    AdminPageProfileCounters getCounters(Long profileId) throws ProfileNotFoundServiceException, ServiceUnavailableException;

    /**
     * Profile statistics by accessType
     * @param accessType accessType
     * @param paging     paging
     * @return list of profiles with statistics
     * @throws ServiceUnavailableException service is not available
     */
    List<AdminProfileStat> getProfileStatsByAccessType(String accessType, Paging paging) throws ServiceUnavailableException;

    /**
     * Profile statistics (unique users) by protocol
     * @param protocol protocol
     * @return list of statistic entries: day - users
     * @throws ServiceUnavailableException service is not available
     */
    List<AdminProtocolRequestStatistics> getOverallUniqueUserStats(Protocol protocol) throws ServiceUnavailableException;

    /**
     * Profile by access type statistics
     * @return list of statistics
     * @throws ServiceUnavailableException service is not available
     */
    List<AdminPageAccessTypeStatistic> getAccessTypeStatistics() throws ServiceUnavailableException;
}
