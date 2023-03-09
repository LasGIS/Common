/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.converter;

import java.util.Collection;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

/**
 * Base converter
 *
 * @param <F> from
 * @param <T> to
 * @author Amosov Maxim
 * @since 20.07.2021 : 13:25
 */
public abstract class Converter<F, T> {
    /**
     * Convert entry from type <F> to type <T> without null check. You should override this method in children
     *
     * @param entry entry of type <F>
     * @return entry of type <T>
     */
    protected abstract T apply(final F entry);

    /**
     * Convert entry from type <F> to type <T> with null check
     *
     * @param entry entry of type <F>
     * @return entry of type <T>
     */
    public T convert(final F entry) {
        return isNull(entry) ? null : this.apply(entry);
    }

    /**
     * Convert collection from type <F> to type <T> with null check
     *
     * @param collection collection of type <F>
     * @return list of type <T>
     */
    public List<T> convertAll(final Collection<F> collection) {
        return isNull(collection) ? null : collection.stream().map(this::convert).collect(toList());
    }
}
