/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;

/**
 * MonthConfigurationDeleteRequest.
 *
 * @author KKiryakov
 */
public class MonthConfigurationDeleteRequest {

    /** Deletion comment. */
    @NotEmpty
    private final String comment;

    /**
     * Constructor.
     * @param comment deletion comment
     */
    @JsonCreator
    public MonthConfigurationDeleteRequest(
        @JsonProperty("comment") String comment
    ) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return String.format("MonthConfigurationDeleteRequest{comment='%s'}", comment);
    }

    public String getComment() {
        return comment;
    }
}
