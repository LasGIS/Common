/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend;

import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

/**
 * RestMapHolder
 *
 * @author RSProkhorov
 * @version 1.0 (Nov 27, 2015)
 * @param <K> map key type
 * @param <DK> defaults key type
 * @param <V> map entry value type
 * @param <ItemsType> type of response items
 * @param <DefaultsType> type of default values
 */
public abstract class RestMapHolder<K, DK, V, ItemsType, DefaultsType> extends RestDictionaryHolder<ItemsType, DefaultsType> {

    private volatile Map<K, V> map = new HashMap<>();
    private volatile Map<DK, V> defaults = new HashMap<>();

    private final Function<ItemsType, K> itemsKeyMapper;
    private final Function<ItemsType, V> itemsValueMapper;

    private final Function<DefaultsType, DK> defaultsKeyMapper;
    private final Function<DefaultsType, V> defaultsValueMapper;

    /**
     * RestMapHolder
     *
     * @param uri uri
     * @param restTemplate restTemplate
     * @param itemsKeyMapper mapper
     * @param itemsValueMapper mapper
     * @param defaultsKeyMapper mapper
     * @param defaultsValueMapper mapper
     */
    protected RestMapHolder(
        final String uri,
        final RestTemplate restTemplate,
        final Function<ItemsType, K> itemsKeyMapper,
        final Function<ItemsType, V> itemsValueMapper,
        final Function<DefaultsType, DK> defaultsKeyMapper,
        final Function<DefaultsType, V> defaultsValueMapper
    ) {
        super(uri, restTemplate);

        this.itemsKeyMapper = requireNonNull(itemsKeyMapper);
        this.itemsValueMapper = requireNonNull(itemsValueMapper);

        this.defaultsKeyMapper = requireNonNull(defaultsKeyMapper);
        this.defaultsValueMapper = requireNonNull(defaultsValueMapper);
    }

    @Override
    protected void processResponse(final String uri, final DictionaryResponse<ItemsType, DefaultsType> response) {
        final List<ItemsType> loadedItems = ofNullable(response.getItems()).orElseGet(Collections::emptyList);
        final List<DefaultsType> loadedDefaults = ofNullable(response.getDefaultValues()).orElseGet(Collections::emptyList);

        log().info("Loaded dictionary {} entries: {}", uri, loadedItems.size());
        this.map = loadedItems.stream().collect(toMap(this.itemsKeyMapper, this.itemsValueMapper, (value1, value2) -> {
            log().warn("Duplicate key found: {}", itemsKeyMapper.apply((ItemsType) value1));
            return value1;
        }));
        this.defaults = loadedDefaults.stream().collect(toMap(this.defaultsKeyMapper, this.defaultsValueMapper));
    }

    /**
     * Gets value by key
     *
     * @param key key
     * @return value
     */
    public final V get(final K key) {
        return this.map.get(key);
    }

    /**
     * Gets default value by key
     *
     * @param key key
     * @return default value if exists
     */
    public final V getDefaultValue(final K key) {
        final DK defKey = toDefaultKey(key);
        return defKey != null ? this.defaults.get(defKey) : null;
    }

    /**
     * @param key item key
     * @return default key
     */
    protected DK toDefaultKey(final K key) {
        return null;
    }

    /**
     * Gets map.
     * @return the map
     */
    protected Map<K, V> getMap() {
        return map;
    }

}
