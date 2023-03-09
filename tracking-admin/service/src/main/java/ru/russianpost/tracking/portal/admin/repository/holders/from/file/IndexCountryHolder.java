/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.repository.holders.from.file;

import java.util.HashMap;
import java.util.Map;

/**
 * Indexes to countries map holder.
 * @author MKitchenko
 * @version 1.0 16.08.2018
 */
public class IndexCountryHolder {

    private final Map<String, Integer> indexCountryMap;

    /**
     * Constructor.
     * @param indexCountryMap indexes to countries map
     */
    public IndexCountryHolder(Map<String, Integer> indexCountryMap) {
        this.indexCountryMap = new HashMap<>(indexCountryMap);
    }

    /**
     * Get code of country by index.
     * @param index of place of operation.
     * @return code of country for index.
     */
    public Integer getCountry(final String index) {
        return indexCountryMap.get(index);
    }

}
