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

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.BarcodeProviderFileUser;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.FileUserEnum;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Excel File Parser.
 * @author MKitchenko
 */
public class ExcelFileParser implements FileParser {

    private static final DataFormatter DATA_FORMATTER = new DataFormatter(Locale.forLanguageTag("ru-RU"));

    @Override
    public Map<Integer, BarcodeProviderFileUser> parseUsers(InputStream inputStream) throws Exception {
        final Map<Integer, BarcodeProviderFileUser> map = new HashMap<>();

        try (final Workbook workbook = WorkbookFactory.create(inputStream)) {
            final Sheet sheetAt = workbook.getSheetAt(0);
            for (int i = 1; i <= sheetAt.getLastRowNum(); i++) {
                final Row row = sheetAt.getRow(i);
                if (Objects.isNull(row)) {
                    break;
                }
                final BarcodeProviderFileUser user = getUser(row);
                if (!isEmpty(user)) {
                    map.put(row.getRowNum(), user);
                }
            }
        }
        return map;
    }

    private BarcodeProviderFileUser getUser(Row row) {
        return new BarcodeProviderFileUser(
            getCellValueByCellNum(row, FileUserEnum.COMPANY_NAME.getCellNum()),
            getCellValueByCellNum(row, FileUserEnum.INN.getCellNum()),
            getCellValueByCellNum(row, FileUserEnum.DAT_DOG.getCellNum()),
            getCellValueByCellNum(row, FileUserEnum.POSTAL_CODE.getCellNum()),
            getCellValueByCellNum(row, FileUserEnum.NOTIFICATION_EMAIL.getCellNum())
        );
    }

    private String getCellValueByCellNum(Row row, int cellNum) {
        return DATA_FORMATTER.formatCellValue(row.getCell(cellNum)).trim();
    }

    private boolean isEmpty(BarcodeProviderFileUser user) {
        return user.getCompanyName().isEmpty() &&
            user.getInn().isEmpty() &&
            user.getDatDog().isEmpty() &&
            user.getPostalCode().isEmpty() &&
            user.getNotificationEmail().isEmpty();
    }
}
