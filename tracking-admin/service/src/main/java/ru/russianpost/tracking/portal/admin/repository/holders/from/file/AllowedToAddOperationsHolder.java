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

import java.util.List;
import java.util.Map;

/**
 * AllowedToAddOperationsHolder.
 * @author KKiryakov
 */
public class AllowedToAddOperationsHolder {

    private Map<Integer, List<Integer>> allowedToAddOperations;

    /**
     * Constructor.
     * @param allowedToAddOperations map where key is operation type code and value is a map of operation attribute codes to its names
     */
    public AllowedToAddOperationsHolder(Map<Integer, List<Integer>> allowedToAddOperations) {
        this.allowedToAddOperations = allowedToAddOperations;
    }

    public Map<Integer, List<Integer>> getAllowedToAddOperations() {
        return allowedToAddOperations;
    }

    public void setAllowedToAddOperations(Map<Integer, List<Integer>> allowedToAddOperations) {
        this.allowedToAddOperations = allowedToAddOperations;
    }
}
