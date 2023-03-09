/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.admin.history;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * UserHistoryEvent.
 *
 * @author KKiryakov
 */
@Getter
@RequiredArgsConstructor
public class UserHistoryEvent {
    private final String username;
    private final Long datetime;
    private final String author;
    private final UserHistoryEventType eventType;
    private final String eventArgs;
    private final String comment;

    /**
     * @param username  username
     * @param author    author
     * @param eventType eventType
     * @param eventArgs eventArgs
     */
    public UserHistoryEvent(final String username, final String author, final UserHistoryEventType eventType, final String eventArgs) {
        this(username, null, author, eventType, eventArgs, null);
    }
}
