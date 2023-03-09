/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.validation.validators;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * <description>
 *
 * @author VLaskin
 * @since <pre>30.07.2020</pre>
 */
public class EmailValidatorTest {

    private EmailValidator validator;

    private static final String[] VALID_EMAIL_ADDRESS = {
        "email@example.com",
        "firstname.lastname@example.com",
        "email@subdomain.example.com",
        "firstname+lastname@example.com",
//        "email@123.123.123.123",
//        "email@[123.123.123.123]",
//        "\"email\"@example.com",
        "1234567890@example.com",
        "email@example-one.com",
        "_______@example.com",
        "email@example.name",
        "email@example.museum",
        "email@example.co.jp",
        "firstname-lastname@example.com"
    };
    private static final String[] INVALID_EMAIL_ADDRESS = {
        "plainaddress",
        "#@%^%#$@#$@#.com",
        "@example.com",
        "Joe Smith <email@example.com>",
        "email.example.com",
        "email@example@example.com",
        ".email@example.com",
        "email.@example.com",
        "email..email@example.com",
        "あいうえお@example.com",
        "email@example.com (Joe Smith)",
        "email@example",
        "email@-example.com",
//        "email@example.web",
        "email@111.222.333.44444",
        "email@example..com",
        "Abc..123@example.com"
    };

    @Before
    public void before() {
        validator = new EmailValidator();
    }

    @Test
    public void validEmail() throws Exception {
        Arrays.stream(VALID_EMAIL_ADDRESS).forEach(email -> {
            assertTrue("Test on valid email: \"" + email + "\"", validator.isValid(email, null));
//            System.out.println("email: \"" + email + "\" is " + (validator.isValid(email, null) ? "valid" : "INVALID"));
        });
    }

    @Test
    public void invalidEmail() throws Exception {
        Arrays.stream(INVALID_EMAIL_ADDRESS).forEach(email -> {
            assertFalse("Test on invalid email: \"" + email + "\"", validator.isValid(email, null));
//            System.out.println("email: \"" + email + "\" is " + (validator.isValid(email, null) ? "VALID" : "invalid"));
        });
    }
}
