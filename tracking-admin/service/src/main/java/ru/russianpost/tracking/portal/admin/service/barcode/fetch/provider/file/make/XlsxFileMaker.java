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

import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BatchRegistrationResponseData;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.validation.ValidationError;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Excel File Maker.
 * @author MKitchenko
 */
public class XlsxFileMaker extends ExcelFileMaker implements FileMaker {

    @Override
    public byte[] makeFileWithErrors(InputStream inputStream, Map<Integer, List<ValidationError>> errors) throws Exception {
        return super.makeFileWithErrors(new XSSFWorkbook(inputStream), errors);
    }

    @Override
    public byte[] makeFileWithCredentials(InputStream inputStream, Map<Integer, BatchRegistrationResponseData> credentials) throws Exception {
        final XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        final XSSFFont tableHeaderFont = workbook.getSheetAt(0).getRow(0).getCell(0).getCellStyle().getFont();

        return super.makeFileWithCredentials(workbook, tableHeaderFont, credentials);
    }
}
