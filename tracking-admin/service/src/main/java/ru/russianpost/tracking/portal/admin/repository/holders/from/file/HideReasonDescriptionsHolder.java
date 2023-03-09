/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.repository.holders.from.file;


import java.util.Map;

/**
 * Hide reaso names holder.
 *
 * @author KKiryakov
 */
public class HideReasonDescriptionsHolder {

    private static final String UNKNOWN_REASON_TEMPLATE = "Неизвестная причина скрытия (%s)";
    private final Map<String, String> map;


    /**
     * Constructor.
     * @param map map of hide reason keys to their descriptions
     */
    public HideReasonDescriptionsHolder(Map<String, String> map) {
        this.map = map;
    }

    /**
     * Resolves description for hide reason.
     *
     * @param hideReason hide reason
     * @return human readable description for hideReason
     */
    public String resolve(String hideReason) {
        if (hideReason == null) {
            return null;
        }
        String value = map.get(hideReason);
        if (value != null) {
            return value;
        } else {
            return String.format(UNKNOWN_REASON_TEMPLATE, hideReason);
        }
    }
}
