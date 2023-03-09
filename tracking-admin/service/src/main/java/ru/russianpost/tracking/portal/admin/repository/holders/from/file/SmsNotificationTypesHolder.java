/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.repository.holders.from.file;

import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsNotificationType;

import java.util.Map;
import java.util.Optional;

/**
 * Holder of dictionary info of sms types.
 *
 * @author KKiryakov
 */
public class SmsNotificationTypesHolder {


    private static final String DESCRIPTION_UNKNOWN = "Неизвестный тип СМС уведомления";
    private final Map<Integer, SmsNotificationType> map;

    /**
     * Constructor.
     * @param map sms notification type map
     */
    public SmsNotificationTypesHolder(Map<Integer, SmsNotificationType> map) {
        this.map = map;
    }

    /**
     * Gets sms notification type map.
     * @return the map
     */
    public Map<Integer, SmsNotificationType> getMap() {
        return map;
    }

    /**
     * Gets sms notification type by id.
     *
     * @param smsNotificationTypeId sms notification type id
     * @return sms notification type
     */
    public SmsNotificationType getSmsNotificationType(Integer smsNotificationTypeId) {
        return Optional.ofNullable(map.get(smsNotificationTypeId))
            .orElseGet(() -> new SmsNotificationType(smsNotificationTypeId, DESCRIPTION_UNKNOWN));
    }
}
