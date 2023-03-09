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
 * HistoryEntry
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public class HistoryEntry {

    private final Long datetime;
    private final AdminUser author;
    private final String description;
    private final String comment;

    /**
     * Creates new instance
     *
     * @param datetime    event time
     * @param author      author name
     * @param description event description
     * @param comment     event comment
     */
    public HistoryEntry(Long datetime, AdminUser author, String description, String comment) {
        this.datetime = datetime;
        this.author = author;
        this.description = description;
        this.comment = comment;
    }

    public Long getDatetime() {
        return datetime;
    }

    public AdminUser getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getComment() {
        return comment;
    }
}
