/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.statistics;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Roman S. Prokhorov
 * @version 1.0 (Jan 11, 2016)
 */
public class DataValuesTest {

    public DataValuesTest() {
    }

    @Test
    public void testAndDifferent() {
        DataValues a = DataValues.custom("A", 1);
        DataValues b = DataValues.custom("B", 2);
        DataValues aAndB = a.and(b);
        Map<String, Integer> map = aAndB.getValues();

        assertEquals(2, map.size());

        assertEquals(1, (int) map.get("A"));
        assertEquals(2, (int) map.get("B"));
    }

    @Test
    public void testAndIntersected() {
        DataValues first = DataValues.custom("A", 1).and(DataValues.custom("B", 2));
        DataValues second = DataValues.custom("B", 1).and(DataValues.custom("C", 2));

        DataValues aggregated = first.and(second);
        Map<String, Integer> map = aggregated.getValues();

        assertEquals(3, map.size());

        assertEquals(1, (int) map.get("A"));
        assertEquals(3, (int) map.get("B"));
        assertEquals(2, (int) map.get("C"));
    }
}
