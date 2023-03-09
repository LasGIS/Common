/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.russianpost.tracking.portal.admin.model.errors.Error;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static ru.russianpost.tracking.portal.admin.model.errors.ErrorCode.SYSTEM_ERROR;

/**
 * DocumentsController
 * @author aalekseenko
 */
@Controller
@RequestMapping("/documents")
public class DocumentsController {

    private static final Logger LOG = LoggerFactory.getLogger(DocumentsController.class);

    /**
     * @param name file name
     * @return requested file or NOT_FOUND
     * @throws UnsupportedEncodingException instance of {@link UnsupportedEncodingException}
     */
    @GetMapping(value = "/{name:.+}")
    public ResponseEntity<?> get(@PathVariable("name") String name) throws UnsupportedEncodingException {
        Assert.hasText(name);

        Optional<InputStreamResource> doc = findDocument(name);
        String filename = URLEncoder.encode(name, StandardCharsets.UTF_8.name()).replace('+', ' ');

        return doc.<ResponseEntity<?>>map(body -> ok().contentType(APPLICATION_OCTET_STREAM)
            .header("Content-Disposition", "attachment;filename=" + filename)
            .body(body))
            .orElseGet(() -> notFound().build());
    }

    private Optional<InputStreamResource> findDocument(String name) {
        InputStream is = getClass().getResourceAsStream("/documents/" + name);
        return is == null ? Optional.empty() : Optional.of(new InputStreamResource(is));
    }

    /**
     * Handle all unexpected exceptions
     * @param e exception
     * @return {@link Error} object
     */
    @ExceptionHandler({UnsupportedEncodingException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Error systemError(Exception e) {
        LOG.error("Unexpected system error: " + e.getMessage(), e);
        return new Error(SYSTEM_ERROR, "Возникла непредвиденная ошибка");
    }
}
