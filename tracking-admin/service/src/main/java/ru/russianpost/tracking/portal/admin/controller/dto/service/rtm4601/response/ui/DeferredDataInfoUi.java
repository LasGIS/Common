/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.ui;

import lombok.NonNull;
import lombok.Value;

import java.util.List;

/**
 * @author MKitchenko
 * @version 1.0 14.01.20.
 */
@Value
public class DeferredDataInfoUi {
    @NonNull
    private List<DeferredOrderUi> orders;
    @NonNull
    private Integer total;
    @NonNull
    private Integer typeCount;
}
