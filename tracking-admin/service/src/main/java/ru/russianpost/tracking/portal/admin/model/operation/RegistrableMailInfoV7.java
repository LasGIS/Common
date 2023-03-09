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

import lombok.Value;

import java.util.List;

/**
 * Representation of registrable mail information.
 * @param <T> history record type
 * @author MKitchenko
 * @version 2.0 (Jun 24, 2019)
 */
@Value
public final class RegistrableMailInfoV7<T> {
    private List<T> history;
    private RpoSummary summary;
    private boolean hasHidden;
    private List<PostalOrderEvent> postalOrderHistory;
    private boolean hasHiddenPostalOrderHistory;
    private List<Notification> notifications;
    private boolean hasHiddenNotificationHistory;
}
