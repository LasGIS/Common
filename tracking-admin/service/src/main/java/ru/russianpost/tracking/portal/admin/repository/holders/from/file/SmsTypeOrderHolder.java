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
 * Sms type id to order map holder.
 * @author MKitchenko
 * @version 1.0 11.09.2018
 */
public class SmsTypeOrderHolder {

    private final Map<Integer, Integer> map;

    /**
     * Constructor.
     * @param map sms type id to order map
     */
    public SmsTypeOrderHolder(Map<Integer, Integer> map) {
        this.map = new HashMap<>(map);
    }

    /**
     * Get Sms type id to order map.
     * @return Sms type id to order map.
     */
    public Map<Integer, Integer> getSmsTypeOrderMap() {
        return this.map;
    }
}
