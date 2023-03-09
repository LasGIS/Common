/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.utils;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.russianpost.tracking.api.protobuf.messages.PostalEvent;
import ru.russianpost.tracking.portal.admin.model.operation.NiipsOperation;

import static lombok.AccessLevel.PRIVATE;

/**
 * Class responsible for creating logs for Kibana.
 * @author sslautin
 * @version 1.0 02.11.2015
 */
@Slf4j
@NoArgsConstructor(access = PRIVATE)
@SuppressWarnings("checkstyle:HideUtilityClassConstructor")
public final class KibanaLogger {

    /**
     * Logs info about the registered operation.
     * @param niipsOperation form data.
     * @param username       user login name.
     * @param result         action success.
     * @param reason         reason of success/fail.
     * @param event          protobuf event, converted from form data.
     */
    public static void logOperationRegistrationInfo(
        final NiipsOperation niipsOperation,
        final String username,
        final boolean result,
        final String reason,
        final PostalEvent.Event event
    ) {

        log.info(
            "page: {}; action: {}; login: {}; result: {}; reason: {}; barcode: {}; operDate: {}; operType: {}; operAttr: {}; indexOper: {};",
            niipsOperation.getFormType(),
            niipsOperation.getAction(),
            username,
            result,
            reason,
            event != null && event.hasShipmentId() ? event.getShipmentId() : null,
            event != null && event.hasOperDate() ? event.getOperDate() : null,
            event != null && event.hasOperType() ? event.getOperType() : null,
            event != null && event.hasOperAttr() ? event.getOperAttr() : null,
            event != null && event.hasIndexOper() ? event.getIndexOper() : null
        );
    }

    /**
     * Logs info about the registered operation.
     * @param username user login name.
     * @param result   action success.
     * @param reason   reason of success/fail.
     * @param event    protobuf event, converted from form data.
     */
    public static void logOperationRegistrationInfo(
        final String username,
        final boolean result,
        final String reason,
        final PostalEvent.Event event
    ) {
        log.info(
            "login: {}; result: {}; reason: {}; barcode: {}; operDate: {}; operType: {}; operAttr: {}; indexOper: {};",
            username,
            result,
            reason,
            event != null && event.hasShipmentId() ? event.getShipmentId() : null,
            event != null && event.hasOperDate() ? event.getOperDate() : null,
            event != null && event.hasOperType() ? event.getOperType() : null,
            event != null && event.hasOperAttr() ? event.getOperAttr() : null,
            event != null && event.hasIndexOper() ? event.getIndexOper() : null
        );
    }
}
