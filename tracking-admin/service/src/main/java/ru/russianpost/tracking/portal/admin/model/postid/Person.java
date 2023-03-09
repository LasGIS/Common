/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.postid;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.stream.Stream;

/**
 * Post ID Person
 * @author Roman Prokhorov
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class Person {
    private String personHid;
    private String email;
    private String firstName;
    private String lastName;
    private String middleName;
    private String firstNameRawSource;
    private String lastNameRawSource;
    private String middleNameRawSource;
    private String phone;
    private Phone[] phones;

    public String getPrimaryPhone() {
        return this.phones == null
            ? ""
            : Stream.of(this.phones)
            .min((o1, o2) -> Boolean.compare(o2.isPrimary(), o1.isPrimary()))
            .map(Phone::getFullNumber)
            .orElse("");
    }
}
