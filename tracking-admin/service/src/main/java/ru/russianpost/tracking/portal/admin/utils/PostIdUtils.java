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

import ru.russianpost.tracking.portal.admin.model.postid.Person;

/**
 * Utilities for operating with Post ID objects.
 *
 * @author KKiryakov
 */
public final class PostIdUtils {

    /**
     * Hidden constructor.
     */
    private PostIdUtils() {
    }

    /**
     * Returns person's first name.
     * @param person person
     * @return first name
     */
    public static String getFirstName(Person person) {
        return PostIdUtils.firstNonNull(person.getFirstNameRawSource(), person.getFirstName());
    }

    /**
     * Returns person's last name.
     *
     * @param person person
     * @return last name
     */
    public static String getLastName(Person person) {
        return PostIdUtils.firstNonNull(person.getLastNameRawSource(), person.getLastName());
    }

    /**
     * Returns person's middle name.
     *
     * @param person person
     * @return middle name
     */
    public static String getMiddleName(Person person) {
        return PostIdUtils.firstNonNull(person.getMiddleNameRawSource(), person.getMiddleName());
    }

    /**
     * Returns {@code first} if it is non-null, otherwise {@code second}.
     *
     * @param <T> type of value
     * @param first first value
     * @param second second value
     * @return {@code first} if it is non-null, otherwise {@code second}. If both are null, then {@code null} will be returned.
     */
    private static <T> T firstNonNull(T first, T second) {
        return first != null ? first : second;
    }
}
