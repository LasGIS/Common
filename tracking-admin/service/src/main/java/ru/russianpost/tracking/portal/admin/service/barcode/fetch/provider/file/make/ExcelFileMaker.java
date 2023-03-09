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

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BatchRegistrationResponseData;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.FileUserEnum;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.validation.ValidationError;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Excel File Maker.
 * @author MKitchenko
 */
class ExcelFileMaker {

    private static final int ERROR_MESSAGES_CELL_COLUMN_NUMBER = 5;
    private static final int LOGIN_CELL_COLUMN_NUMBER = 5;
    private static final int PASSWORD_CELL_COLUMN_NUMBER = 6;

    byte[] makeFileWithErrors(Workbook workbook, Map<Integer, List<ValidationError>> errors) throws IOException {
        final Sheet sheet = workbook.getSheetAt(0);
        final CellStyle errorCellStyle = getErrorCellStyle(workbook);

        clearErrorCells(sheet, getDefaultCellStyle(workbook), errorCellStyle);

        makeFileWithErrors(sheet, errors, errorCellStyle);

        sheet.autoSizeColumn(ERROR_MESSAGES_CELL_COLUMN_NUMBER);

        return toByteArray(workbook);
    }

    private CellStyle getErrorCellStyle(Workbook workbook) {
        final CellStyle errorCellStyle = workbook.createCellStyle();
        errorCellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        errorCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return errorCellStyle;
    }

    private CellStyle getDefaultCellStyle(Workbook workbook) {
        final CellStyle defaultCellStyle = workbook.createCellStyle();

        defaultCellStyle.setFillPattern(FillPatternType.NO_FILL);
        defaultCellStyle.setBorderBottom(BorderStyle.THIN);
        defaultCellStyle.setBorderLeft(BorderStyle.THIN);
        defaultCellStyle.setBorderTop(BorderStyle.THIN);
        defaultCellStyle.setBorderRight(BorderStyle.THIN);

        return defaultCellStyle;
    }

    private void clearErrorCells(Sheet sheet, CellStyle defaultCellStyle, CellStyle errorCellStyle) {
        final int lastRowNum = sheet.getLastRowNum();
        IntStream.range(1, lastRowNum + 1).parallel()
            .mapToObj(rowNum -> sheet.getRow(rowNum).getCell(ERROR_MESSAGES_CELL_COLUMN_NUMBER))
            .filter(Objects::nonNull)
            .forEach(cell -> {
                cell.setCellValue("");
                setDefaultStyleToRowWhichContainsErrorMessagesCell(cell.getRow(), defaultCellStyle, errorCellStyle);
            });
    }

    private void setDefaultStyleToRowWhichContainsErrorMessagesCell(Row row, CellStyle defaultCellStyle, CellStyle errorCellStyle) {
        IntStream.range(0, ERROR_MESSAGES_CELL_COLUMN_NUMBER)
            .mapToObj(row::getCell)
            .filter(c -> c.getCellStyle().getFillPattern() == errorCellStyle.getFillPattern())
            .filter(c -> c.getCellStyle().getFillForegroundColor() == errorCellStyle.getFillForegroundColor())
            .forEach(c -> c.setCellStyle(defaultCellStyle));
    }

    private void makeFileWithErrors(Sheet sheet, Map<Integer, List<ValidationError>> errors, CellStyle errorCellStyle) {
        errors.forEach((row, errorList) -> {
                final Row currentRow = sheet.getRow(row);
                final StringBuilder errorsMessageBuilder = new StringBuilder();
                for (ValidationError error : errorList) {
                    final int cellNum = FileUserEnum.byField(error.getField()).getCellNum();
                    final Cell cell = Optional.ofNullable(currentRow.getCell(cellNum)).orElseGet(() -> currentRow.createCell(cellNum));

                    cell.setCellStyle(errorCellStyle);

                    errorsMessageBuilder.append(error.getReason()).append('.').append('\n');
                }
                final Cell errorMessagesCell = getErrorMessagesCellByRow(currentRow);
                errorMessagesCell.setCellValue(errorsMessageBuilder.toString().trim());
            }
        );
    }

    private Cell getErrorMessagesCellByRow(Row row) {
        final Cell cell = Optional.ofNullable(row.getCell(ERROR_MESSAGES_CELL_COLUMN_NUMBER))
            .orElseGet(() -> row.createCell(ERROR_MESSAGES_CELL_COLUMN_NUMBER));

        final CellStyle cellStyle = cell.getCellStyle();
        cellStyle.setWrapText(true);
        cell.setCellStyle(cellStyle);
        return cell;
    }

    byte[] makeFileWithCredentials(
        Workbook workbook,
        Font tableHeaderFont,
        Map<Integer, BatchRegistrationResponseData> credentials
    ) throws IOException {
        final Sheet sheet = workbook.getSheetAt(0);

        clearErrorCells(sheet, getDefaultCellStyle(workbook), getErrorCellStyle(workbook));

        final CellStyle tableHeaderCellStyle = getTableHeaderCellStyle(sheet, tableHeaderFont);

        createTableHeaderColumn(sheet, "login", LOGIN_CELL_COLUMN_NUMBER, tableHeaderCellStyle);
        createTableHeaderColumn(sheet, "password", PASSWORD_CELL_COLUMN_NUMBER, tableHeaderCellStyle);

        fillTable(sheet, credentials);

        sheet.autoSizeColumn(LOGIN_CELL_COLUMN_NUMBER);
        sheet.autoSizeColumn(PASSWORD_CELL_COLUMN_NUMBER);

        return toByteArray(workbook);
    }

    private CellStyle getTableHeaderCellStyle(Sheet sheet, Font tableHeaderFont) {
        final CellStyle cellStyle = sheet.getRow(0).getCell(0).getCellStyle();
        tableHeaderFont.setBold(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setFont(tableHeaderFont);
        return cellStyle;
    }

    private void createTableHeaderColumn(Sheet sheet, String name, int columnNumber, CellStyle tableHeaderCellStyle) {
        final Cell cell = sheet.getRow(0).createCell(columnNumber);
        cell.setCellStyle(tableHeaderCellStyle);

        cell.setCellValue(name);
    }

    private void fillTable(Sheet sheet, Map<Integer, BatchRegistrationResponseData> credentials) {
        final CellStyle credentialCellStyle = getCredentialCellStyle(sheet.getRow(1).getCell(0));

        credentials.entrySet().stream().parallel()
            .forEach(entry -> setCredentials(sheet, entry, credentialCellStyle));
    }

    private CellStyle getCredentialCellStyle(Cell cell) {
        final CellStyle cellStyle = cell.getCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        return cellStyle;
    }

    private void setCredentials(Sheet sheet, Map.Entry<Integer, BatchRegistrationResponseData> entry, CellStyle credentialCellStyle) {
        Cell cell = sheet.getRow(entry.getKey()).createCell(LOGIN_CELL_COLUMN_NUMBER);
        cell.setCellStyle(credentialCellStyle);
        cell.setCellValue(entry.getValue().getLogin());

        cell = sheet.getRow(entry.getKey()).createCell(PASSWORD_CELL_COLUMN_NUMBER);
        cell.setCellStyle(credentialCellStyle);
        cell.setCellValue(entry.getValue().getPassword());
    }

    private byte[] toByteArray(Workbook workbook) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        return baos.toByteArray();
    }
}
