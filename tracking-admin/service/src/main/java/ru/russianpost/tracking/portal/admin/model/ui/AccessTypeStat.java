/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.ui;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Jan 12, 2016)
 */
public class AccessTypeStat {

    private final Long date;
    private final int value;
    private final String accessType;

    /**
     * UniqueUsersStat
     * @param date date
     * @param value value
     * @param accessType accessType
     */
    @JsonCreator
    public AccessTypeStat(
        @JsonProperty("date") Long date,
        @JsonProperty("value") int value,
        @JsonProperty("accessType") String accessType
    ) {
        this.date = date;
        this.value = value;
        this.accessType = accessType;
    }

    public Long getDate() {
        return date;
    }

    public int getValue() {
        return value;
    }

    public String getAccessType() {
        return accessType;
    }
}
