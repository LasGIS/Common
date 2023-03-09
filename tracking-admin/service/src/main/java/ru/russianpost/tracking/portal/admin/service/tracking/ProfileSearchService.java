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
import ru.russianpost.tracking.web.model.admin.AdminProfileSearchResult;

import java.util.List;

/**
 * ProfileSearchService
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public interface ProfileSearchService {

    /**
     * Searches for a profile by specific query
     *
     * @param query query
     * @param count max result records
     * @return organizationInfo
     * @throws ServiceUnavailableException service unavailable
     */
    List<AdminProfileSearchResult> find(String query, int count) throws ServiceUnavailableException;
}
