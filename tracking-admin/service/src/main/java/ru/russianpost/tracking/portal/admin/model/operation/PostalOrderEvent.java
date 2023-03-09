/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.operation;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author MKitchenko
 * @version 2.0 (Jun 24, 2019)
 */
@Builder
@Getter
@Setter
public class PostalOrderEvent {
    private String number;
    private String barcode;
    private String eventType;
    private long eventDateTime;
    private int timeZoneOffsetInSeconds;
    private String indexEvent;
    private String eventAddress;
    private String indexTo;
    private String destAddress;
    private long sumPaymentForward;
    private boolean hidden;
    private String hideReasonDescription;
}
