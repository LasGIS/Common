/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service.multiple.tracking;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * File extension
 *
 * @author kpodolyak
 * @version 1.0 11.04.2017
 */
public enum MultipleTrackingFileExtension {
    /**
     * Microsoft Excel
     */
    XLS(".xls", "application/vnd.ms-excel"),
    /**
     * Microsoft Excel 2007
     */
    XLSX(".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    /**
     * Open Document
     */
    ODS(".ods", "application/vnd.oasis.opendocument.spreadsheet"),
    /**
     * Open Document (Мой Офис)
     */
    XODS(".xods", "application/vnd.collabio.xodocuments.spreadsheet");

    private final String extension;
    private final String contentType;

    MultipleTrackingFileExtension(final String extension, final String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    /**
     * Returns an extension by file name
     *
     * @param filename filename
     * @return extension
     */
    public static MultipleTrackingFileExtension byFilename(String filename) {
        for (MultipleTrackingFileExtension extension : values()) {
            if (filename.endsWith(extension.extension)) {
                return extension;
            }
        }
        return null;
    }

    public String getExtension() {
        return extension;
    }

    public String getContentType() {
        return contentType;
    }

    public static List<String> getAllowedExtensions() {
        return Arrays.stream(values()).map(MultipleTrackingFileExtension::getExtension).collect(Collectors.toList());
    }

}
