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

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Dec 23, 2015)
 */
public final class CollectionUtils {

    private CollectionUtils() {
    }

    /**
     * Adds only new list objects to destination list
     *
     * @param <T> type of list objects
     * @param <I> type of list object id
     * @param dest destination list
     * @param source source list
     * @param identity id extraction function
     */
    public static <T, I> void addOnlyNew(List<T> dest, List<T> source, Function<T, I> identity) {
        dest.addAll(source
            .stream()
            .filter(s -> dest
                .stream()
                .noneMatch(d -> Objects.equals(identity.apply(d), identity.apply(s)))
            )
            .collect(toList()));
    }
}
