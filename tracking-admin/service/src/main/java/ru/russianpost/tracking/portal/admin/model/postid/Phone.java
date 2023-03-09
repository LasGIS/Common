/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.postid;

import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Phone
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
public class Phone {

    private boolean primary;
    private String rawSource;
    private String countryCode;
    private String cityCode;
    private String number;

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public String getRawSource() {
        return rawSource;
    }

    public void setRawSource(String rawSource) {
        this.rawSource = rawSource;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * Returns full number
     *
     * @return full number
     */
    public String getFullNumber() {
        return StringUtils.hasLength(rawSource) ? rawSource
                : Stream.of(countryCode, cityCode, number).filter(Objects::nonNull).collect(Collectors.joining());
    }
}
