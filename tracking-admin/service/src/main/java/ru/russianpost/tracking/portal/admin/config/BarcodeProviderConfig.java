/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.FileType;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.make.FileMaker;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.make.FileMakerFactory;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.make.XlsFileMaker;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.make.XlsxFileMaker;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.parse.ExcelFileParser;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.parse.FileParser;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.parse.FileParserFactory;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

import static ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.FileType.XLS;
import static ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.FileType.XLSX;

/**
 * Barcode Provider Config.
 * @author MKitchenko
 */
@Configuration
public class BarcodeProviderConfig {

    /**
     * File Parser Factory
     * @return FileParserFactory
     */
    @Bean
    public FileParserFactory fileParserFactory() {
        final Map<FileType, Supplier<FileParser>> map = new EnumMap<>(FileType.class);
        map.put(XLS, ExcelFileParser::new);
        map.put(XLSX, ExcelFileParser::new);

        return new FileParserFactory(map);
    }

    /**
     * File Maker Factory
     * @return FileMakerFactory
     */
    @Bean
    public FileMakerFactory fileMakerFactory() {
        final Map<FileType, Supplier<FileMaker>> map = new EnumMap<>(FileType.class);
        map.put(XLS, XlsFileMaker::new);
        map.put(XLSX, XlsxFileMaker::new);

        return new FileMakerFactory(map);
    }
}
