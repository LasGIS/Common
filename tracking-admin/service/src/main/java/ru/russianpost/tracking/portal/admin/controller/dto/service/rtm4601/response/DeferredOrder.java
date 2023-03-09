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
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.rtm4601.Rtm4601;
import ru.russianpost.tracking.portal.admin.model.rtm4601.DeferredDataStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author MKitchenko
 * @version 1.0 27.08.19.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeferredOrder {
    @NonNull
    private Integer id;
    @NonNull
    private LocalDateTime orderDateTime;
    @NonNull
    private String clientId;
    @NonNull
    private String apiVersion;
    @NonNull
    private Rtm4601 order;
    @NonNull
    private DeferredDataStatus status;
    @NonNull
    private BigDecimal convertedPrice;
    @NonNull
    private Integer count;
}
