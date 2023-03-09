/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.parse;

import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.FileType;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * File Parser Factory.
 * @author MKitchenko
 */
public class FileParserFactory {

    private Map<FileType, Supplier<FileParser>> map;

    /**
     * Constructor.
     * @param map file type to file parser map
     */
    public FileParserFactory(Map<FileType, Supplier<FileParser>> map) {
        this.map = map;
    }

    /**
     * Return file maker by file type
     * @param fileType file type {@link FileType}.
     * @return file maker {@link FileParser}
     */
    public FileParser getFileParser(FileType fileType) {
        if (Objects.nonNull(fileType)) {
            final Supplier<FileParser> fileParserSupplier = map.get(fileType);
            if (Objects.nonNull(fileParserSupplier)) {
                return fileParserSupplier.get();
            }
        }
        throw new IllegalArgumentException("No such file parser " + fileType);
    }

}
