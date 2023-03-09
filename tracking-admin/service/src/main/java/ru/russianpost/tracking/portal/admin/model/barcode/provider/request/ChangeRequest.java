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

/**
 * Change request DTO.
 *
 * @author KKiryakov
 */
public class ChangeRequest {

    @NotEmpty
    private final String comment;
    private String author;

    /**
     * Constructor
     *
     * @param comment comment
     * @param author author
     */
    @JsonCreator
    public ChangeRequest(
        @JsonProperty(required = true, value = "comment") String comment,
        @JsonProperty("author") String author
    ) {
        this.comment = comment;
        this.author = author;
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
