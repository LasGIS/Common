/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.operation;

import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.operation.CreatedHistoryRecord;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordCreation;

/**
 * Service for registering new operations.
 *
 * @author KKiryakov
 */
public interface OperationRegistrationService {

    /**
     * Sends request for registering new operation.
     * @param creation creation args
     * @return Created History Record
     * @throws ServiceUnavailableException operation service unavailable
     */
    CreatedHistoryRecord register(HistoryRecordCreation creation) throws ServiceUnavailableException;
}
