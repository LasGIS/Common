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

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 22, 2015)
 */
public class CsvHttpMessageConverter extends AbstractHttpMessageConverter<CsvResponse> {

    private static final MediaType MEDIA_TYPE = new MediaType("text", "csv", StandardCharsets.UTF_8);

    /**
     * new converter
     */
    public CsvHttpMessageConverter() {
        super(MEDIA_TYPE);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return CsvResponse.class.equals(clazz);
    }

    @Override
    protected void writeInternal(CsvResponse response, HttpOutputMessage output) throws IOException {
        output.getHeaders().set(HttpHeaders.CONTENT_TYPE, MEDIA_TYPE.toString());
        output.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + response.getFilename());

        try (final Writer writer = new OutputStreamWriter(output.getBody(), StandardCharsets.UTF_8)) {
            try (final CsvBeanWriter beanWriter = new CsvBeanWriter(writer, CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE)) {
                final List<String> columns = response.getColumns();
                if (columns != null && !columns.isEmpty()) {
                    final String[] headerArr = columns.toArray(new String[0]);
                    beanWriter.writeHeader(headerArr);
                }
                String[] fieldsArr = null;
                for (Object record : response.getRecords()) {
                    if (fieldsArr == null) {
                        fieldsArr = Stream.of(record.getClass().getDeclaredFields()).map(Field::getName).toArray(String[]::new);
                    }
                    beanWriter.write(record, fieldsArr);
                }
            }
        }
    }

    @Override
    protected CsvResponse readInternal(Class<? extends CsvResponse> clazz, HttpInputMessage inputMessage) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
