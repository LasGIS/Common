/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.ui;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Dec 26, 2015)
 */
public class ProfileRegistration {

    private final long date;
    private final int id;
    private final String accessType;

    /**
     * ProfileRegistration
     * @param date date
     * @param id id
     * @param accessType access type
     */
    public ProfileRegistration(long date, int id, String accessType) {
        this.date = date;
        this.id = id;
        this.accessType = accessType;
    }

    public long getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getAccessType() {
        return accessType;
    }
}
