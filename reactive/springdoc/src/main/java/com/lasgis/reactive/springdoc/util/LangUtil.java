/*
 *  @(#)LangUtil.java  last: 05.11.2023
 *
 * Title: LG prototype for java-reactive-jdbc + type-script-react-redux-antd
 * Description: Program for support Prototype.
 * Copyright (c) 2023, LasGIS Company. All Rights Reserved.
 */
package com.lasgis.reactive.springdoc.util;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toUnmodifiableMap;
import static java.util.stream.Collectors.toUnmodifiableSet;
import static org.apache.commons.lang3.BooleanUtils.toBoolean;
import static org.apache.commons.lang3.ObjectUtils.firstNonNull;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@SuppressWarnings("unused")
public final class LangUtil {

    private LangUtil() {
    }

    public static boolean allAreNulls(Object... values) {
        return firstNonNull(values) == null;
    }

    @NonNull
    public static <T, R> List<R> transform(
        @Nullable final Collection<T> collection,
        @NonNull final Function<T, R> mapFunction
    ) {
        if (isEmpty(collection)) {
            return new ArrayList<>();
        }
        return collection.stream()
            .map(mapFunction)
            .toList();
    }

    @NonNull
    public static <T, R> Set<R> transformToUnmodifiableSet(
        @Nullable final Collection<T> collection,
        @NonNull final Function<T, R> transform
    ) {
        return streamOf(collection)
            .map(transform)
            .collect(toUnmodifiableSet());
    }

    /**
     * Null-safe transformation of a collection of values to a key/value pairs.<br/>
     * All values with the same key will be replaced by the last one in the result.<br/>
     * I.e. if the following collection is passed as an argument:
     * <pre>
     * {@code
     * [
     *   {"key_1": "value_1"},
     *   {"key_1": "value_2"},
     *   {"key_2": "value_2"},
     * ]}
     * </pre>the following map is returned
     * <pre>
     * {@code
     *   {
     *     "key_1": "value_1",
     *     "key_2": "value_2",
     *   }}
     * </pre>
     * if null or empty map is passed then empty map is returned.<br/>
     *
     * @param collection Nullable collection to map
     * @param keyMapper  Nonnull key mapper
     * @param <K>        key type
     * @param <V>        value type
     * @return Map of key value pairs
     */
    @NonNull
    public static <K, V> Map<K, V> transformToUnmodifiableMap(
        @Nullable final Collection<V> collection,
        @NonNull final Function<V, K> keyMapper
    ) {
        return streamOf(collection)
            .collect(toUnmodifiableMap(keyMapper, Function.identity(), (i1, i2) -> i2));
    }

    @NonNull
    public static <T, R> R ifNotEmpty(
        @Nullable final List<T> list,
        @NonNull final Function<List<T>, R> function,
        @NonNull final R def
    ) {
        if (isNotEmpty(list)) {
            return function.apply(list);
        }
        return def;
    }

    @NonNull
    public static <T, R> R ifNotEmptyCollection(
        @Nullable final Collection<T> collection,
        @NonNull final Function<Collection<T>, R> function,
        @NonNull final R def
    ) {
        if (isNotEmpty(collection)) {
            return function.apply(collection);
        }
        return def;
    }

    public static <T> void ifNotEmpty(@Nullable final Set<T> set, @NonNull final Consumer<Set<T>> consumer) {
        if (isNotEmpty(set)) {
            consumer.accept(set);
        }
    }

    @NonNull
    public static <T, R> R ifNotNull(
        @Nullable final T object, @NonNull final Function<T, R> function, @NonNull final R def
    ) {
        if (nonNull(object)) {
            return function.apply(object);
        }
        return def;
    }

    @Nullable
    public static <T, R> R ifNotNull(@Nullable final T value, @NonNull final Function<T, R> function) {
        if (nonNull(value)) {
            return function.apply(value);
        }
        return null;
    }

    @Nullable
    public static <T1, T2, R> R ifAllNotNull(
        @Nullable final T1 v1,
        @Nullable final T2 v2,
        @NonNull final BiFunction<T1, T2, R> transform
    ) {
        if (ObjectUtils.allNotNull(v1, v2)) {
            return transform.apply(v1, v2);
        }
        return null;
    }

    @Nullable
    public static <T1, T2, R> R ifAllNotNull(
        @Nullable final T1 v1,
        @Nullable final T2 v2,
        @NonNull final BiFunction<T1, T2, R> transform,
        @NonNull final R def
    ) {
        if (ObjectUtils.allNotNull(v1, v2)) {
            return transform.apply(v1, v2);
        }
        return def;
    }

    @Nullable
    public static <T1, T2, R> R ifAllNull(
        @Nullable final T1 v1,
        @Nullable final T2 v2,
        final R def,
        final BiFunction<T1, T2, R> transform
    ) {
        if (ObjectUtils.allNull(v1, v2)) {
            return def;
        }
        return transform.apply(v1, v2);
    }

    public static <T> void ifNotNullConsume(
        @Nullable final T value, @NonNull final Consumer<T> consumer1
    ) {
        if (nonNull(value)) {
            consumer1.accept(value);
        }
    }

    public static <T> void ifNotNullConsume(
        @Nullable final T value, @NonNull final Consumer<T> consumer1, @NonNull final Consumer<T> consumer2
    ) {
        if (nonNull(value)) {
            consumer1.andThen(consumer2).accept(value);
        }
    }

    @NonNull
    public static <T> List<T> flatten(@Nullable final List<List<T>> listOfLists) {
        if (isEmpty(listOfLists)) {
            return new ArrayList<>();
        }
        return listOfLists
            .stream()
            .collect(ArrayList::new, List::addAll, List::addAll);
    }

    public static void ifThen(@Nullable Boolean condition, Runnable ifTrue) {
        if (toBoolean(condition)) {
            ifTrue.run();
        }
    }

    public static void ifThenOrElse(@Nullable Boolean condition, Runnable ifTrue, Runnable orElse) {
        if (toBoolean(condition)) {
            ifTrue.run();
        } else {
            orElse.run();
        }
    }

    public static <T> Stream<T> streamOf(final Collection<T> collection) {
        return collection == null ? Stream.empty() : collection.stream();
    }
}