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
import ru.russianpost.tracking.portal.admin.model.operation.Country;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Nov 27, 2015)
 */
public class CountryHolder extends RestDictionaryHolder<Country, Country> {

    private volatile Map<Integer, Country> map = new HashMap<>();
    private volatile List<Country> list = new ArrayList<>();

    /**
     * CountryHolder
     * @param uri          uri
     * @param restTemplate restTemplate
     */
    public CountryHolder(final String uri, final RestTemplate restTemplate) {
        super(uri, restTemplate);
    }

    @Override
    protected void processResponse(String partUri, DictionaryResponse<Country, Country> response) {
        final List<Country> loadedItems = ofNullable(response.getItems()).orElseGet(Collections::emptyList);
        log().info("Loaded dictionary {} entries: {}", uri(), loadedItems.size());
        list = loadedItems;
        map = list.stream().collect(Collectors.toMap(Country::getId, Function.identity()));
    }

    @Override
    protected Class<? extends DictionaryResponse<Country, Country>> responseType() {
        return CountryResponse.class;
    }

    /**
     * CountryResponse
     */
    private static class CountryResponse extends DictionaryResponse<Country, Country> {
    }

    /**
     *
     * Returns country by id
     * @param id country id
     * @return instance of {@link Country}
     */
    public Country getCountryById(Integer id) {
        return this.map.get(id);
    }

    public List<Country> getCountries() {
        return list;
    }
}
