/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model;

import ru.russianpost.tracking.portal.admin.model.postid.Phone;
import ru.russianpost.tracking.portal.admin.model.postid.Person;
import org.junit.Assert;
import org.junit.Test;

public class PersonTest {

    @Test
    public void testGetPrimaryPhoneOne() throws Exception {
        final Person person = new Person();

        final Phone phone = new Phone();
        phone.setRawSource("123");
        phone.setPrimary(true);

        person.setPhones(new Phone[]{phone});
        Assert.assertEquals("123", person.getPrimaryPhone());
    }

    @Test
    public void testGetPrimaryPhoneFromPhones() throws Exception {
        final Person person = new Person();

        final Phone phone1 = new Phone();
        phone1.setRawSource("789");
        phone1.setPrimary(false);

        final Phone phone2 = new Phone();
        phone2.setRawSource("345");
        phone2.setPrimary(true);

        person.setPhones(new Phone[]{phone1, phone2});
        Assert.assertEquals("345", person.getPrimaryPhone());
    }

    @Test
    public void testGetPrimaryPhoneFromNotPrimaryPhones() throws Exception {
        final Person person = new Person();

        final Phone phone1 = new Phone();
        phone1.setRawSource("789");
        phone1.setPrimary(false);

        final Phone phone2 = new Phone();
        phone2.setRawSource("345");
        phone2.setPrimary(false);

        person.setPhones(new Phone[]{phone1, phone2});
        Assert.assertEquals("789", person.getPrimaryPhone());
    }

    @Test
    public void testGetEmptyPrimaryPhone() throws Exception {
        final Person person = new Person();

        person.setPhones(new Phone[]{});
        Assert.assertEquals("", person.getPrimaryPhone());
    }
}
