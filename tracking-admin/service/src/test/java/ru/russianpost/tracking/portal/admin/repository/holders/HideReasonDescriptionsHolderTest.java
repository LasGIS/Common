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

import org.junit.Before;
import org.junit.Test;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.HideReasonDescriptionsHolder;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests for {@link HideReasonDescriptionsHolder}.
 *
 * @author KKiryakov
 */
public class HideReasonDescriptionsHolderTest {

    private HideReasonDescriptionsHolder holder;

    @Before
    public void setUp() throws Exception {
        HashMap<String, String> map = new HashMap<>();
        map.put("hideReason1", "description1");
        map.put("hideReason2", "description2");
        holder = new HideReasonDescriptionsHolder(map);

    }

    @Test
    public void resolve() throws Exception {
        assertEquals("description1", holder.resolve("hideReason1"));
        assertEquals("description2", holder.resolve("hideReason2"));
    }

    @Test
    public void resolveNull() throws Exception {
        assertNull(null, holder.resolve(null));
    }

    @Test
    public void resolveUnknown() throws Exception {
        assertEquals("Неизвестная причина скрытия (hideReason3)", holder.resolve("hideReason3"));
    }

}
