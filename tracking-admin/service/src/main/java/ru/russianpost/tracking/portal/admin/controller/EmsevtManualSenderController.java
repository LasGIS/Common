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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.russianpost.tracking.portal.admin.config.aspect.Speed4J;
import ru.russianpost.tracking.portal.admin.exception.HttpServiceException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.emsevt.manual.TaskInfo;
import ru.russianpost.tracking.portal.admin.model.errors.Error;
import ru.russianpost.tracking.portal.admin.model.errors.ErrorCode;
import ru.russianpost.tracking.portal.admin.service.emsevt.manual.EmsevtManualSenderService;
import ru.russianpost.tracking.portal.admin.service.exception.EmsevtManualFileNotFoundException;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

/**
 * @author Amosov Maxim
 * @since 04.08.2021 : 17:21
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/emsevt-manual")
public class EmsevtManualSenderController extends BaseController {
    private final EmsevtManualSenderService service;

    /**
     * @param multiPart the multipart file
     * @param author    author
     * @return created task info
     * @throws ServiceUnavailableException when service is unavailable
     */
    @Speed4J
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public TaskInfo createTask(
        @RequestParam("file") final MultipartFile multiPart,
        final Principal author
    ) throws ServiceUnavailableException {
        return service.createTask(multiPart, author.getName());
    }

    /**
     * @param author author
     * @return list of existing tasks
     * @throws ServiceUnavailableException when service is unavailable
     */
    @Speed4J
    @GetMapping(value = "/tasks")
    public List<TaskInfo> getTasks(final Principal author) throws ServiceUnavailableException {
        return service.getTasks(author.getName());
    }

    /**
     * @param downloadUrl download url
     * @param filename    filename
     * @return file
     * @throws ServiceUnavailableException when service is unavailable
     */
    @Speed4J
    @GetMapping(value = "/download")
    public ResponseEntity<byte[]> downloadResultFile(
        @RequestParam("downloadUrl") final URI downloadUrl,
        @RequestParam(value = "filename", required = false) final String filename
    ) throws ServiceUnavailableException {
        try {
            final ResponseEntity<byte[]> resultFile = service.getResultFile(downloadUrl.toString());
            if (filename != null) {
                return ResponseEntity.status(resultFile.getStatusCode())
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .contentType(resultFile.getHeaders().getContentType())
                    .body(resultFile.getBody());
            } else {
                return resultFile;
            }
        } catch (final EmsevtManualFileNotFoundException e) {
            return createRedirectResponse(ErrorCode.FILE_NOT_FOUND);
        } catch (final ServiceUnavailableException e) {
            return createRedirectResponse(ErrorCode.SERVICE_UNAVAILABLE);
        }
    }

    private ResponseEntity<byte[]> createRedirectResponse(final ErrorCode errorCode) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/admin.html#/emsevt-manual?errorCode=" + errorCode);
        return new ResponseEntity<>(null, headers, HttpStatus.FOUND);
    }

    /**
     * Emsevt manual service exceptions handler
     *
     * @param ex exception details
     * @return ResponseEntity with error to display on frontend
     */
    @ExceptionHandler(HttpServiceException.class)
    public ResponseEntity<Error> emsevtManualSenderServiceError(final HttpServiceException ex) {
        log.warn("Error processing request to emsevt manual sender service. Error = '{}'", ex.getError());
        final HttpStatus status = (ex.getHttpCode() >= 400 && ex.getHttpCode() < 500) ? BAD_REQUEST : SERVICE_UNAVAILABLE;
        return ResponseEntity.status(status).body(ex.getError());
    }
}
