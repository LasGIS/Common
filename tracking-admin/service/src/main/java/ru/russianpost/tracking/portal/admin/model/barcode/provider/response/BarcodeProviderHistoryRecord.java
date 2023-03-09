/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * BarcodeProviderHistoryRecord.
 *
 * @author KKiryakov
 */
public class BarcodeProviderHistoryRecord {

    private final Long datetime;
    private final BarcodeProviderAuthor author;
    private final String description;
    private final String comment;

    /**
     * Constructor.
     *
     * @param datetime datetime
     * @param author author
     * @param description description
     * @param comment comment
     */
    @JsonCreator
    public BarcodeProviderHistoryRecord(
        @JsonProperty("datetime") Long datetime,
        @JsonProperty("author") BarcodeProviderAuthor author,
        @JsonProperty("description") String description,
        @JsonProperty("comment") String comment
    ) {
        this.datetime = datetime;
        this.author = author;
        this.description = description;
        this.comment = comment;
    }

    public Long getDatetime() {
        return datetime;
    }

    public BarcodeProviderAuthor getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getComment() {
        return comment;
    }
}
