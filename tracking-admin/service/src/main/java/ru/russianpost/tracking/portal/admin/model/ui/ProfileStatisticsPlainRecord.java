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

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 22, 2015)
 */
@Getter
@AllArgsConstructor
public class ProfileStatisticsPlainRecord {
    private final String date;
    private final String company;
    private final String emails;
    private final String login;
    private final Integer rtm34;
    private final Integer fc;
    private final String clientType;
    private final String internalComment;
}
