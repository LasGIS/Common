/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.operation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Notification
 * @author MKitchenko
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Notification {
    private String shipmentId;
    private Integer operType;
    private Long operDate;
    private Integer zoneOffsetSeconds;
    private String indexOper;
    private String operAddress;
    private Integer operAttr;
    private String indexTo;
    private String destAddress;
    private boolean hidden;
    private String hideReasonDescription;
}
