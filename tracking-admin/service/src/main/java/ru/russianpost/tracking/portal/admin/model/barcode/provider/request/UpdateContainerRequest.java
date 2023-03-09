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
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * UpdateContainerRequest.
 *
 * @author KKiryakov
 */
public class UpdateContainerRequest {

    /** Container's minimum. */
    @NotNull @Min(1) @Max(99999)
    private final Integer min;
    /** Container's maximum. */
    @NotNull @Min(1) @Max(99999)
    private final Integer max;
    /** Comment for the change. */
    @NotNull @NotEmpty
    private final String comment;
    private String author;

    /**
     * Constructor.
     *
     * @param min min
     * @param max max
     * @param comment comment
     */
    @JsonCreator
    public UpdateContainerRequest(
        @JsonProperty(value = "min") Integer min,
        @JsonProperty(value = "max") Integer max,
        @JsonProperty(value = "comment") String comment
    ) {
        this.min = min;
        this.max = max;
        this.comment = comment;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public String getComment() {
        return comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
