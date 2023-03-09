/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.errors;

import java.util.Arrays;

/**
 * Error types
 *
 * @author vlaskin
 * @since <pre>09.06.2022</pre>
 */
public enum ErrorCode {
    /* Common errors */
    /** Field is mandatory, thus is required. */
    FIELD_REQUIRED,
    /** Value of field is invalid */
    INVALID_FIELD_VALUE,
    /** internal service error */
    INTERNAL_SERVER_ERROR,
    /** validation error */
    VALIDATION_ERROR,
    /** Format of field is incorrect. */
    INCORRECT_FIELD_FORMAT,
    /** Value of field is incorrect. */
    INCORRECT_FIELD_VALUE,
    /** UNAUTHORIZED */
    UNAUTHORIZED,
    /** FORBIDDEN */
    FORBIDDEN,

    /* Auth errors */
    /** Authentication error - user credentials are missed */
    AUTH_ERROR_USER_CREDENTIALS_MISSED,
    /** Authentication error - login or password is missed */
    AUTH_ERROR_LOGIN_OR_PASSWORD_MISSED,
    /** Authentication error - login or password didn't match */
    AUTH_ERROR_LOGIN_OR_PASSWORD_MISMATCH,
    /** Authentication error - access is not allowed for the user */
    AUTH_ERROR_ACCESS_NOT_ALLOWED,
    /** Authentication generic error */
    AUTH_ERROR,

    /* S10 management errors */
    /** ufps not found */
    UFPS_NOT_FOUND,
    /** no more limit for this month left */
    NO_LIMIT_LEFT,
    /** the remainder of limit less that requested, allocation amount reduced */
    LIMIT_EXCEEDED,
    /** no suitable container for specified allocation type found */
    SUITABLE_CONTAINER_NOT_FOUND,
    /** container exhausted, allocation amount reduced, preform another request for remaining amount */
    CONTAINER_EXHAUSTED_TRY_AGAIN,

    /* Postponed order errors. */
    /** POSTPONED_ORDER_STATUS_CHANGED */
    POSTPONED_ORDER_STATUS_CHANGED,
    /** POSTPONED_ORDER_ALREADY_PROCESSED */
    POSTPONED_ORDER_ALREADY_PROCESSED,
    /** POSTPONED_ORDER_NOT_FOUND */
    POSTPONED_ORDER_NOT_FOUND,
    /** POSTPONED_ORDER_ALREADY_EXISTS */
    POSTPONED_ORDER_ALREADY_EXISTS,
    /** POSTPONED_ORDER_ALREADY_UNARCHIVED */
    POSTPONED_ORDER_ALREADY_UNARCHIVED,
    /** UNPROCESSED_POSTPONED_ORDER_EXISTS */
    UNPROCESSED_POSTPONED_ORDER_EXISTS,

    /* Barcode fetch provider errors */
    /** Configuration for the index-month already exists */
    CONFIG_MONTH_ALREADY_EXISTS,
    /** Configuration for the index-month does not exist */
    CONFIG_MONTH_DOES_NOT_EXIST,
    /** Index new range conflicts with already existing range for the index in current month */
    CONFIG_RANGE_CONFLICT,
    /** User already exists */
    USER_ALREADY_EXISTS,
    /** User does not exist */
    USER_DOES_NOT_EXIST,
    /** Mismatch of username values from db and request */
    USERNAME_MISMATCH,
    /** Service unavailable */
    SERVICE_UNAVAILABLE,
    /** System error */
    SYSTEM_ERROR,
    /** Booking does not exist */
    BOOKING_DOES_NOT_EXIST,
    /** Incorrect booking: cannot find or recreate booking xml */
    BOOKING_XML_NOT_FOUND,
    /** Deletion of booking is not allowed */
    BOOKING_DELETION_NOT_ALLOWED,
    /** Container does not exist */
    CONTAINER_DOES_NOT_EXIST,
    /** Created booking conflicts with existing bookings */
    BOOKING_CONFLICT,
    /** Created booking conflicts with existing bookings */
    CONTAINER_INTERSECTION_CONFLICT,
    /** Wrong field format */
    WRONG_FIELD_FORMAT,

    /* Multiple Tracking */
    /** Отсутствует обязательный параметр. */
    MISSING_REQUIRED_PARAM,
    /** Во входном файле не найдено ни одного ШПИ. */
    BARCODES_NOT_FOUND,
    /** Количество ШПИ превышает лимит. */
    BARCODE_LIMIT_EXCEEDED,
    /** Ошибка чтения входного файла. */
    INPUT_FILE_PARSING_ERROR,
    /** Неверное расширение входного файла. */
    BAD_FILE_EXTENSION,
    /** Неизвестное название колонки отчёта множетсвенного отслеживания */
    UNKNOWN_REPORT_COLUMN_NAME,

    /** Invalid index */
    INVALID_INDEX,
    /** Index limit exceeded */
    INDEX_LIMIT_EXCEEDED,
    /** Indexes not found */
    INDEXES_NOT_FOUND,
    /** Empty index description */
    EMPTY_INDEX_DESCRIPTION,
    /** User already exist */
    USER_ALREADY_EXIST,

    /** Invalid phone code */
    INVALID_PHONE,
    /** File not found code */
    FILE_NOT_FOUND,
    /** Timeout of Cached Request code */
    TIMEOUT_CACHED_REQUEST,
    /** to many report records */
    TO_MANY_REPORT_RECORDS,
    /** Unknown error code */
    UNKNOWN_ERROR;

    /**
     * Return error code.
     *
     * @param errorCode error code string
     * @return {@link ErrorCode} object
     */
    public static ErrorCode parse(String errorCode) {
        return Arrays.stream(ErrorCode.values()).filter(ec -> ec.name().equals(errorCode)).findFirst().orElse(UNKNOWN_ERROR);
    }
}
