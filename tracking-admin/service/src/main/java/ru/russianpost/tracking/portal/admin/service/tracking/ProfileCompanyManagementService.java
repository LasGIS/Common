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
import ru.russianpost.tracking.web.model.core.Profile;
import ru.russianpost.tracking.web.model.portal.PortalCompanyEdition;

/**
 * ProfileCompanyManagementService
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public interface ProfileCompanyManagementService {

    /**
     * Edits company of specified profile
     *
     * @param profileId          profile id
     * @param companyInfoEdition company info
     * @return updated profile
     * @throws ProfileNotFoundServiceException profile not found
     * @throws ServiceUnavailableException     service unavailable
     */
    Profile editCompany(long profileId, PortalCompanyEdition companyInfoEdition)
            throws ProfileNotFoundServiceException, ServiceUnavailableException;
}
