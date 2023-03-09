/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.utils;

import org.junit.Test;
import ru.russianpost.tracking.portal.admin.model.postid.Person;

import static org.junit.Assert.*;

/**
 * Tests for {@link PostIdUtils}
 *
 * @author KKiryakov
 */
public class PostIdUtilsTest {

    @Test
    public void getFirstNameReturnsRawSource() {
        Person person = new Person();
        person.setFirstName("Иван");
        person.setFirstNameRawSource("Ivan");
        assertEquals("Ivan", PostIdUtils.getFirstName(person));
    }

    @Test
    public void getFirstNameWithoutRawSource() {
        Person person = new Person();
        person.setFirstName("Иван");
        assertEquals("Иван", PostIdUtils.getFirstName(person));
    }

    @Test
    public void getFirstNameEmpty() {
        Person person = new Person();
        assertNull(PostIdUtils.getFirstName(person));
    }

    @Test
    public void getLastNameReturnsRawSource() {
        Person person = new Person();
        person.setLastName("Иванов");
        person.setLastNameRawSource("Ivanov");
        assertEquals("Ivanov", PostIdUtils.getLastName(person));
    }

    @Test
    public void getLastNameWithoutRawSource() {
        Person person = new Person();
        person.setLastName("Иванов");
        assertEquals("Иванов", PostIdUtils.getLastName(person));
    }

    @Test
    public void getLastNameEmpty() {
        Person person = new Person();
        assertNull(PostIdUtils.getLastName(person));
    }

    @Test
    public void getMiddleNameReturnsRawSource() {
        Person person = new Person();
        person.setMiddleName("Иванович");
        person.setMiddleNameRawSource("Ivanovich");
        assertEquals("Ivanovich", PostIdUtils.getMiddleName(person));
    }

    @Test
    public void getMiddleNameWithoutRawSource() {
        Person person = new Person();
        person.setMiddleName("Иванович");
        assertEquals("Иванович", PostIdUtils.getMiddleName(person));
    }

    @Test
    public void getMiddleNameEmpty() {
        Person person = new Person();
        assertNull(PostIdUtils.getMiddleName(person));
    }
}
