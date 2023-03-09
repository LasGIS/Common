/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller;

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrection;
import ru.russianpost.tracking.portal.admin.config.aspect.Speed4J;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.controller.exception.ValidationException;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.operation.CompletedHistoryRecord;
import ru.russianpost.tracking.portal.admin.model.operation.CreatedHistoryRecord;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectedCompletedHistoryRecord;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectedCompletedHistoryRecordCorrection;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectionId;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectionType;
import ru.russianpost.tracking.portal.admin.model.operation.correction.Corrections;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordCreation;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordDeletion;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordEdition;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordRestoration;
import ru.russianpost.tracking.portal.admin.service.hdps.HistoryRecordCompletionService;
import ru.russianpost.tracking.portal.admin.service.operation.OperationAlterationService;
import ru.russianpost.tracking.portal.admin.service.operation.OperationRegistrationService;
import ru.russianpost.tracking.portal.admin.service.users.CorrectionAuthorInfoService;
import ru.russianpost.tracking.portal.admin.utils.KibanaLogger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Dec 02, 2015)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/operation")
public class Operation extends BaseController {

    private final StopWatchFactory stopWatchFactory;

    private final OperationAlterationService operationAlterationService;
    private final OperationRegistrationService operationRegistrationService;
    private final HistoryRecordCompletionService completionService;
    private final CorrectionAuthorInfoService correctionAuthorInfoService;

    /**
     * Edits selected operation
     *
     * @param author  author
     * @param edition edition data
     * @param br      binding result
     * @return id of submitted correction
     * @throws ValidationException validation errors
     * @throws BadRequestException bad request error
     */
    @Speed4J
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public CorrectionId edit(
        Principal author,
        @Valid @NotNull @RequestBody HistoryRecordEdition edition,
        BindingResult br
    ) throws ValidationException, BadRequestException {
        if (br.hasErrors()) {
            throw new ValidationException(br);
        }
        return operationAlterationService.edit(author.getName(), edition);
    }

    /**
     * Deletes operation.
     *
     * @param author   author
     * @param deletion target record args
     * @param br       binding result
     * @return id of submitted correction
     * @throws ValidationException validation errors
     */
    @Speed4J
    @PostMapping(value = "/delete", consumes = APPLICATION_JSON_VALUE)
    public CorrectionId delete(
        Principal author,
        @Valid @NotNull @RequestBody HistoryRecordDeletion deletion,
        BindingResult br
    ) throws ValidationException {
        if (br.hasErrors()) {
            throw new ValidationException(br);
        }
        return operationAlterationService.delete(author.getName(), deletion);
    }

    /**
     * Restores deleted operation.
     *
     * @param author      author
     * @param restoration target record args
     * @param br          binding result
     * @return id of submitted correction
     * @throws ValidationException validation errors
     */
    @Speed4J
    @PostMapping(value = "/restore", consumes = APPLICATION_JSON_VALUE)
    public CorrectionId restore(
        Principal author,
        @Valid @NotNull @RequestBody
            HistoryRecordRestoration restoration, BindingResult br
    ) throws ValidationException {
        if (br.hasErrors()) {
            throw new ValidationException(br);
        }
        return operationAlterationService.restore(author.getName(), restoration);
    }

    /**
     * Registers new operation.
     *
     * @param author   author
     * @param creation target record args
     * @param br       validation binding result
     * @return {@code true} if request submitted
     * @throws ValidationException         validation errors
     * @throws ServiceUnavailableException operation service unavailable
     */
    @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE)
    public CorrectedCompletedHistoryRecord create(
        Principal author,
        @Valid @NotNull @RequestBody HistoryRecordCreation creation,
        BindingResult br
    ) throws ValidationException, ServiceUnavailableException {
        final String username = author.getName();
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("Operation:create");
        if (br.hasErrors()) {
            KibanaLogger.logOperationRegistrationInfo(username, false, "validationError", null);
            stopWatch.stop("Operation:create:validationError");
            throw new ValidationException(br);
        }
        try {
            final CreatedHistoryRecord historyRecord = operationRegistrationService.register(creation);
            KibanaLogger.logOperationRegistrationInfo(username, true, "ok", null);
            final HistoryRecordCorrection correction = operationAlterationService.create(username, creation);
            final CompletedHistoryRecord completedHistoryRecord = completionService.complete(historyRecord, false);
            final CorrectedCompletedHistoryRecordCorrection completedCorrection = new CorrectedCompletedHistoryRecordCorrection(
                CorrectionType.CREATE,
                correction.getInitiator(),
                correctionAuthorInfoService.resolveAuthor(username),
                correction.getCreated(),
                correction.getComment(),
                Corrections.from(correction),
                completedHistoryRecord
            );
            log.info("Operation \"{}\" has been successfully registered by user \"{}\".", creation, username);
            return new CorrectedCompletedHistoryRecord(completedHistoryRecord, Collections.singletonList(completedCorrection));
        } catch (InternalServerErrorException e) {
            log.error("Cannot register operation: " + creation, e);
            KibanaLogger.logOperationRegistrationInfo(username, false, "internalError", null);
            stopWatch.stop("Operation:create:fail");
            throw e;
        } finally {
            stopWatch.stop();
        }
    }
}
