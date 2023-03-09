/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.operation.sms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Information about target phone number and types of sms notifications for this phone.
 * Includes send time for each sms notification.
 * @author MKitchenko
 * @version 1.0 03.09.2018
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsNotificationAdvanced {
    private String smsTel;
    private SmsNotificationType smsType;
    private Long sendTime;
}
