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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * RestListHolder
 * @author RSProkhorov
 * @param <T> list element type
 */
public abstract class RestListHolder<T> extends RestDictionaryHolder<T, T> {

    private volatile List<T> list = new ArrayList<>();

    /**
     * RestListHolder
     *
     * @param restTemplate restTemplate
     * @param uri uri
     */
    public RestListHolder(final RestTemplate restTemplate, final String uri) {
        super(uri, restTemplate);
    }

    @Override
    protected void processResponse(final String uri, final DictionaryResponse<T, T> response) {
        final List<T> loadedItems = ofNullable(response.getItems()).orElseGet(Collections::emptyList);
        log().info("Loaded dictionary {} entries: {}", uri, loadedItems.size());
        list = loadedItems;
    }

    /**
     * Return held list
     *
     * @return list
     */
    public final List<T> get() {
        return Collections.unmodifiableList(this.list);
    }
}
