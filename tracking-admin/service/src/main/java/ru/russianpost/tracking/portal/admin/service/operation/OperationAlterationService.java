/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.operation;

import ru.russianpost.tracking.commons.model.HistoryRecordCorrection;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectionId;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordCreation;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordDeletion;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordEdition;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordRestoration;


/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 04, 2015)
 */
public interface OperationAlterationService {

    /**
     * Sends request for record update
     * @param author  author
     * @param edition update args
     * @return correction id
     * @throws BadRequestException on bad request error
     */
    CorrectionId edit(String author, HistoryRecordEdition edition) throws BadRequestException;

    /**
     * Sends request for record deletion
     * @param author   author
     * @param deletion target record id and args
     * @return correction id
     */
    CorrectionId delete(String author, HistoryRecordDeletion deletion);

    /**
     * Sends request for record restoration after deletion
     * @param author      author
     * @param restoration target record id and args
     * @return correction id
     */
    CorrectionId restore(String author, HistoryRecordRestoration restoration);

    /**
     * Sends request for record creation
     * @param author   author
     * @param creation target record id and args
     * @return true if request handled
     */
    HistoryRecordCorrection create(String author, HistoryRecordCreation creation);
}
