/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * ConfigurationUpdateRequest.
 *
 * @param <T> Configuration type
 * @author KKiryakov
 */
public class ConfigurationUpdateRequest<T> {

    /** Configuration. */
    @Valid
    private final T configuration;
    /** Comment for the change. */
    @NotEmpty
    private final String comment;

    /**
     * Constructor.
     *
     * @param configuration configuration
     * @param comment comment
     */
    @JsonCreator
    public ConfigurationUpdateRequest(
        @JsonProperty(required = true, value = "configuration") T configuration,
        @JsonProperty(required = true, value = "comment") String comment
    ) {
        this.configuration = configuration;
        this.comment = comment;
    }

    @Override
    public String toString() {
        return String.format("ConfigurationUpdateRequest{configuration=%s, comment='%s'}", configuration, comment);
    }

    public T getConfiguration() {
        return configuration;
    }

    public String getComment() {
        return comment;
    }
}
