/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.make;

import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.FileType;

import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * File Maker Factory.
 * @author MKitchenko
 */
public class FileMakerFactory {

    private Map<FileType, Supplier<FileMaker>> map;

    /**
     * Constructor.
     * @param map file type to file maker map
     */
    public FileMakerFactory(Map<FileType, Supplier<FileMaker>> map) {
        this.map = map;
    }

    /**
     * Return file maker by file type
     * @param fileType file type {@link FileType}.
     * @return file maker {@link FileMaker}
     */
    public FileMaker getFileMaker(FileType fileType) {
        if (Objects.nonNull(fileType)) {
            final Supplier<FileMaker> fileMakerSupplier = map.get(fileType);
            if (Objects.nonNull(fileMakerSupplier)) {
                return fileMakerSupplier.get();
            }
        }
        throw new IllegalArgumentException("No such file maker " + fileType);
    }

}
