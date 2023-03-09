/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Roman S. Prokhorov
 * @version 1.0 (Dec 23, 2015)
 */
public class CollectionUtilsTest {

    public CollectionUtilsTest() {
    }

    @Test
    public void testAddOnlyNew() {
        List<Integer> dest = new ArrayList<>();
        dest.add(1);
        dest.add(2);
        dest.add(3);
        List<Integer> source = new ArrayList<>();
        source.add(3);
        source.add(4);
        source.add(5);
        CollectionUtils.addOnlyNew(dest, source, Function.identity());
        assertEquals(5, dest.size());
        int i = 0;
        assertEquals(1, dest.get(i++).intValue());
        assertEquals(2, dest.get(i++).intValue());
        assertEquals(3, dest.get(i++).intValue());
        assertEquals(4, dest.get(i++).intValue());
        assertEquals(5, dest.get(i++).intValue());
    }

    @Test
    public void testAddOnlyNewEmptySource() {
        List<Integer> dest = new ArrayList<>();
        dest.add(1);
        dest.add(2);
        dest.add(3);
        List<Integer> source = new ArrayList<>();
        CollectionUtils.addOnlyNew(dest, source, Function.identity());
        assertEquals(3, dest.size());
        int i = 0;
        assertEquals(1, dest.get(i++).intValue());
        assertEquals(2, dest.get(i++).intValue());
        assertEquals(3, dest.get(i++).intValue());
    }

    @Test
    public void testAddOnlyNewEmptyDest() {
        List<Integer> dest = new ArrayList<>();
        List<Integer> source = new ArrayList<>();
        source.add(3);
        source.add(4);
        source.add(5);
        CollectionUtils.addOnlyNew(dest, source, Function.identity());
        assertEquals(3, dest.size());
        int i = 0;
        assertEquals(3, dest.get(i++).intValue());
        assertEquals(4, dest.get(i++).intValue());
        assertEquals(5, dest.get(i++).intValue());
    }

}
