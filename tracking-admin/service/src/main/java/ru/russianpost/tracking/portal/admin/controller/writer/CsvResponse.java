/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller.writer;

import java.util.List;

/**
 *
 * @author RSProkhorov
 * @param <T> record type
 */
public class CsvResponse<T> {

    private final String filename;
    private final List<T> records;
    private final List<String> columns;

    /**
     * CsvResponse
     *
     * @param records records
     * @param columns columns
     * @param filename filename
     */
    public CsvResponse(List<T> records, List<String> columns, String filename) {
        this.records = records;
        this.filename = filename;
        this.columns = columns;
    }

    public String getFilename() {
        return filename;
    }

    public List<T> getRecords() {
        return records;
    }

    public List<String> getColumns() {
        return columns;
    }
}
