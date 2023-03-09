/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller;

import com.google.common.collect.ImmutableList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.russianpost.tracking.portal.admin.config.aspect.Speed4J;
import ru.russianpost.tracking.portal.admin.controller.writer.CsvResponse;
import ru.russianpost.tracking.portal.admin.exception.HttpServiceException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.errors.Error;
import ru.russianpost.tracking.portal.admin.model.opm.NewOpsUser;
import ru.russianpost.tracking.portal.admin.model.opm.OpmHistory;
import ru.russianpost.tracking.portal.admin.model.opm.OpsIndex;
import ru.russianpost.tracking.portal.admin.repository.Dictionary;
import ru.russianpost.tracking.portal.admin.service.opm.OnlinePaymentMarkService;
import ru.russianpost.tracking.portal.admin.utils.CsvUtils;
import ru.russianpost.tracking.portal.admin.validation.validators.OpmIndexesValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static ru.russianpost.tracking.portal.admin.model.errors.ErrorCode.BAD_FILE_EXTENSION;
import static ru.russianpost.tracking.portal.admin.model.errors.ErrorCode.INDEXES_NOT_FOUND;
import static ru.russianpost.tracking.portal.admin.model.errors.ErrorCode.INDEX_LIMIT_EXCEEDED;

/**
 * Controller that proxies calls to OnlinePaymentMark service.
 *
 * @author MKitchenko
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/online-payment-mark")
public class OnlinePaymentMarkController extends BaseController {
    private static final List<String> COLUMNS = ImmutableList.of("Индекс", "Логин", "Пароль", "Название УФПС", "Комментарий");

    private final Dictionary dictionary;
    private final OnlinePaymentMarkService service;

    /**
     * Returns opm history by specified id
     *
     * @param id opm id
     * @return completed opm history
     * @throws ServiceUnavailableException if OPM Service is unavailable
     */
    @Speed4J
    @GetMapping(value = "/history/{id}")
    public OpmHistory getOpmHistory(@PathVariable("id") final String id) throws ServiceUnavailableException {
        return service.getHistory(id);
    }

    /**
     * @param multiPartFile    file with indexes
     * @param overrideExisting override existing
     * @return generated opm logins as '.csv' file
     */
    @Speed4J
    @PostMapping(value = "/user/ops/generate", produces = "text/csv;charset=utf-8")
    public ResponseEntity<?> generateOpsUsers(
        @RequestParam("file") final MultipartFile multiPartFile,
        @RequestParam(value = "overrideExisting", required = false, defaultValue = "false") final boolean overrideExisting
    ) {
        if (!"csv".equals(FilenameUtils.getExtension(multiPartFile.getOriginalFilename()))) {
            throw new HttpServiceException(BAD_REQUEST.value(), new Error(BAD_FILE_EXTENSION));
        }

        final List<OpsIndex> opsIndices = CsvUtils.readFromCsv(multiPartFile, OpsIndex.class, "index");
        if (opsIndices.size() > 1000) {
            throw new HttpServiceException(BAD_REQUEST.value(), new Error(INDEX_LIMIT_EXCEEDED));
        }
        if (opsIndices.isEmpty()) {
            throw new HttpServiceException(BAD_REQUEST.value(), new Error(INDEXES_NOT_FOUND));
        }

        OpmIndexesValidator.validateFormat(opsIndices);
        OpmIndexesValidator.validateDescription(opsIndices, dictionary::getUfpsName);

        final List<NewOpsUser> opsUserOutputs = service.generateOpsUsers(opsIndices, overrideExisting);
        return ResponseEntity.ok(new CsvResponse<>(opsUserOutputs, COLUMNS, generateFileName()));
    }

    /**
     * @param ex exception details
     * @return ResponseEntity with error to display on frontend
     */
    @ResponseBody
    @ExceptionHandler(HttpServiceException.class)
    public ResponseEntity<Error> opmServiceError(final HttpServiceException ex) {
        log.warn("Error processing request to opm service. Error = '{}'", ex.getError());
        final HttpStatus status = (ex.getHttpCode() >= 400 && ex.getHttpCode() < 500) ? BAD_REQUEST : SERVICE_UNAVAILABLE;
        return ResponseEntity.status(status).body(ex.getError());
    }

    private String generateFileName() {
        return "newOpsUsers_" + DateTimeFormatter.ofPattern("uuuuMMdd").format(LocalDate.now()) + ".csv";
    }
}
