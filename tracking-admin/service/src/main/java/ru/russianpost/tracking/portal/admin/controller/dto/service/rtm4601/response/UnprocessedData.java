/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author MKitchenko
 * @version 1.0 (Nov 19, 2019)
 */
@AllArgsConstructor
@Getter
public class UnprocessedData {
    private Integer id;
    private String trackingNumber;
    private String reason;
}
