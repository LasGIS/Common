/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;
import ru.russianpost.tracking.portal.admin.exception.HttpServiceException;
import ru.russianpost.tracking.portal.admin.model.errors.Error;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static ru.russianpost.tracking.portal.admin.model.errors.ErrorCode.INPUT_FILE_PARSING_ERROR;

/**
 * @author Amosov Maxim
 * @since 16.08.2022 : 13:27
 */
@UtilityClass
public class CsvUtils {
    /**
     * @param multiPart    multiPart file
     * @param tClass       type of items
     * @param <T>          type of items
     * @param nameMappings name mappings
     * @return List of items
     */
    public <T> List<T> readFromCsv(final MultipartFile multiPart, Class<T> tClass, String... nameMappings) {
        try (final InputStreamReader inputStreamReader = new InputStreamReader(multiPart.getInputStream());
             final CsvBeanReader beanReader = new CsvBeanReader(inputStreamReader, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE)
        ) {
            T row;
            final ArrayList<T> rows = new ArrayList<>();
            while ((row = beanReader.read(tClass, nameMappings)) != null) {
                rows.add(row);
            }
            return rows;
        } catch (final Exception ex) {
            throw new HttpServiceException(BAD_REQUEST.value(), new Error(INPUT_FILE_PARSING_ERROR, "Unable read from csv file!"), ex);
        }
    }
}
