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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.russianpost.tracking.portal.admin.config.aspect.Speed4J;
import ru.russianpost.tracking.portal.admin.exception.HttpServiceException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.errors.Error;
import ru.russianpost.tracking.portal.admin.model.multiple.tracking.ResultScope;
import ru.russianpost.tracking.portal.admin.model.multiple.tracking.TaskCreated;
import ru.russianpost.tracking.portal.admin.model.multiple.tracking.TaskInfo;
import ru.russianpost.tracking.portal.admin.service.multiple.tracking.MultipleTrackingService;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

/**
 * Controller that proxies calls to MultipleTracking service.
 *
 * @author KKiryakov
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v2/multiple-tracking")
public class MultipleTrackingController2 extends BaseController {

    /** Header content-type multipart/form-data */
    public static final String CONTENT_TYPE_MULTIPART_FORM_DATA_VALUE = CONTENT_TYPE + '=' + MULTIPART_FORM_DATA_VALUE;

    private final MultipleTrackingService service;

    /**
     * Create multiple tracking task.
     *
     * @param multiPart        the multi part
     * @param hiddenOperations include hidden operations if true
     * @param resultScope      result scope
     * @param csvShowColumns   string with Column separated by comma
     * @param author           author
     * @return the response entity
     * @throws ServiceUnavailableException service unavailable exception
     */
    @Speed4J
    @PostMapping(headers = CONTENT_TYPE_MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public TaskCreated createTask(
        @RequestParam("file") final MultipartFile multiPart,
        @RequestParam(value = "hiddenOperations", required = false, defaultValue = "false") final boolean hiddenOperations,
        @RequestParam(value = "resultScope") final ResultScope resultScope,
        @RequestParam(value = "showColumns", required = false) final String csvShowColumns,
        final Principal author
    ) throws ServiceUnavailableException {
        return service.createTask(multiPart, hiddenOperations, resultScope, csvShowColumns, author.getName());
    }

    /**
     * List completed tasks.
     *
     * @param author author
     * @return response entity
     * @throws ServiceUnavailableException service unavailable exception
     */
    @Speed4J
    @GetMapping(value = "/tasks")
    @ResponseBody
    public List<TaskInfo> listCompletedTasks(final Principal author) throws ServiceUnavailableException {
        return service.listCompletedTasks(author.getName());
    }

    /**
     * Download result file by task id.
     *
     * @param downloadUrl download url
     * @param filename    filename
     * @return response entity
     */
    @Speed4J
    @GetMapping(value = "/download")
    @ResponseBody
    public ResponseEntity<byte[]> downloadResultFile(
        @RequestParam("downloadUrl") final URI downloadUrl,
        @RequestParam(value = "filename", required = false) final String filename
    ) {
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
        } catch (final HttpServiceException ex) {
            final HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/admin.html#/tracking/search-multiple-2?errorCode=" + ex.getError().getCode().name());
            return new ResponseEntity<>(null, headers, HttpStatus.FOUND);
        } catch (final ServiceUnavailableException ex) {
            final HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/admin.html#/tracking/search-multiple-2?errorCode=SERVICE_UNAVAILABLE");
            return new ResponseEntity<>(null, headers, HttpStatus.FOUND);
        }
    }

    /**
     * Multiple tracking service exceptions handler
     *
     * @param ex exception details
     * @return Error to display on frontend
     */
    @ExceptionHandler({HttpServiceException.class})
    @ResponseBody
    public ResponseEntity<Error> multipleTrackingServiceError(HttpServiceException ex) {
        log.warn("Error processing request to multiple tracking service. Error is '{}'", ex.getError());
        final HttpStatus status = (ex.getHttpCode() >= 400 && ex.getHttpCode() < 500) ? HttpStatus.BAD_REQUEST : HttpStatus.SERVICE_UNAVAILABLE;
        return ResponseEntity.status(status).body(ex.getError());
    }
}
