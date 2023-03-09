/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.ui;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Dec 18, 2015)
 */
public class Counter {

    private final Integer count;
    private final Integer limit;

    /**
     * Counter
     * @param count count
     * @param limit limit
     */
    public Counter(Integer count, Integer limit) {
        this.count = count;
        this.limit = limit;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getLimit() {
        return limit;
    }
}
