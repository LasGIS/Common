/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.repository.holders;

import org.junit.Test;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsNotificationType;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SmsNotificationTypesHolder;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Tests for {@link SmsNotificationTypesHolder} class.
 *
 * @author KKiryakov
 */
public class SmsNotificationTypesHolderTest {

    private final Map<Integer, SmsNotificationType> map = Stream.of(
        new SmsNotificationType(1, "О поступлении отправления в адресное ОПС"),
        new SmsNotificationType(2, "О вручении отправления")
    ).collect(Collectors.toMap(SmsNotificationType::getId, Function.identity()));
    private final SmsNotificationTypesHolder holder = new SmsNotificationTypesHolder(map);

    @Test
    public void testGetSmsNotificationType() throws Exception {
        final SmsNotificationType smsNotificationType = holder.getSmsNotificationType(1);
        assertEquals(Integer.valueOf(1), smsNotificationType.getId());
        assertEquals("О поступлении отправления в адресное ОПС", smsNotificationType.getDescription());
    }

    @Test
    public void testGetSmsNotificationTypeUnknown() throws Exception {
        final SmsNotificationType smsNotificationType = holder.getSmsNotificationType(777);
        assertEquals(Integer.valueOf(777), smsNotificationType.getId());
        assertEquals("Неизвестный тип СМС уведомления", smsNotificationType.getDescription());
    }

    @Test
    public void testGetSmsNotificationTypeNull() throws Exception {
        final SmsNotificationType smsNotificationType = holder.getSmsNotificationType(null);
        assertNull(smsNotificationType.getId());
        assertEquals("Неизвестный тип СМС уведомления", smsNotificationType.getDescription());
    }

    @Test
    public void testGetMap() throws Exception {
        assertSame(map, holder.getMap());
    }


}
