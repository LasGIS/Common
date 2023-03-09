/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.statistics;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Jan 11, 2016)
 */
public final class DataValues {

    /**
     * ZERO
     */
    public static final DataValues ZERO = new DataValues();

    /**
     * Values should be always sorted by key
     */
    private final TreeMap<String, Integer> values = new TreeMap<>();

    private DataValues() {
    }

    private DataValues(Map<String, Integer> values) {
        this.values.putAll(values);
    }

    private DataValues(Integer limited, Integer unlimited) {
        values.put("limited", limited);
        values.put("unlimited", unlimited);
    }

    /**
     * New values
     *
     * @param limited limited registrations
     * @return limited = 1, data values
     */
    public static DataValues limited(Integer limited) {
        if (limited == null || limited == 0) {
            return ZERO;
        }
        return new DataValues(limited, 0);
    }

    /**
     * New values
     *
     * @param unlimited unlimited registrations
     * @return unlimited = 1, data values
     */
    public static DataValues unlimited(Integer unlimited) {
        if (unlimited == null || unlimited == 0) {
            return ZERO;
        }
        return new DataValues(0, unlimited);
    }

    /**
     * New values
     *
     * @param name name of custom access type
     * @param value custom access type registrations
     * @return custom = 1, data values
     */
    public static DataValues custom(String name, Integer value) {
        if (value == null || value == 0) {
            return ZERO;
        }
        return new DataValues(Collections.singletonMap(name, value));
    }

    /**
     * Adds another values
     *
     * @param that other values
     * @return new instance of data values
     */
    public DataValues and(DataValues that) {
        if (that == ZERO) {
            return this;
        }
        if (this == ZERO) {
            return that;
        }
        Map<String, Integer> collected = Stream.concat(
            this.values.entrySet().stream(),
            that.values.entrySet().stream()
        ).collect(
            Collectors.groupingBy(Map.Entry::getKey,
                Collectors.mapping(
                    Map.Entry::getValue,
                    Collectors.reducing(0, Integer::sum)
                )
            )
        );
        return new DataValues(collected);
    }

    public Map<String, Integer> getValues() {
        return Collections.unmodifiableMap(values);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        for (Map.Entry<String, Integer> entry : this.values.entrySet()) {
            hash = 97 * hash + Objects.hashCode(entry.getKey());
            hash = 97 * hash + Objects.hashCode(entry.getValue());
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataValues other = (DataValues) obj;
        Iterator<Map.Entry<String, Integer>> thisIt = this.values.entrySet().iterator();
        Iterator<Map.Entry<String, Integer>> otherIt = other.values.entrySet().iterator();
        while (thisIt.hasNext()) {
            Map.Entry<String, Integer> thisNext = thisIt.next();
            if (!otherIt.hasNext()) {
                return false;
            }
            Map.Entry<String, Integer> otherNext = otherIt.next();
            if (!Objects.equals(thisNext.getKey(), otherNext.getKey())) {
                return false;
            }
            if (!Objects.equals(thisNext.getValue(), otherNext.getValue())) {
                return false;
            }
        }
        return true;
    }

}
