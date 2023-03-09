/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller;

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.admin.AdminHistoryResponseV7;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.operation.HdpsScope;
import ru.russianpost.tracking.portal.admin.model.operation.RegistrableMailInfoV7;
import ru.russianpost.tracking.portal.admin.service.hdps.HdpsClient;
import ru.russianpost.tracking.portal.admin.service.hdps.HistoryRecordCompletionServiceV7;
import ru.russianpost.tracking.portal.admin.service.opm.OnlinePaymentMarkService;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static ru.russianpost.tracking.portal.admin.model.operation.HdpsScope.CORRECTIONS;
import static ru.russianpost.tracking.portal.admin.model.operation.HdpsScope.DELIVERY;
import static ru.russianpost.tracking.portal.admin.model.operation.HdpsScope.ESPP;
import static ru.russianpost.tracking.portal.admin.model.operation.HdpsScope.HIDDEN;
import static ru.russianpost.tracking.portal.admin.model.operation.HdpsScope.HYPERLOCAL_DELIVERY;
import static ru.russianpost.tracking.portal.admin.model.operation.HdpsScope.MULTIPLACE;
import static ru.russianpost.tracking.portal.admin.model.operation.HdpsScope.NOTIFICATIONS;
import static ru.russianpost.tracking.portal.admin.model.operation.HdpsScope.PACKAGE_INFO;
import static ru.russianpost.tracking.portal.admin.model.operation.HdpsScope.PROPERTIES;
import static ru.russianpost.tracking.portal.admin.model.operation.HdpsScope.RETURN_PARCEL;
import static ru.russianpost.tracking.portal.admin.model.operation.HdpsScope.SUMMARY_ACNT;
import static ru.russianpost.tracking.portal.admin.model.operation.HdpsScope.SUMMARY_INN;

/**
 * @author MKitchenko
 * @version 2.0 (Jun 13, 2019)
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/barcode")
public class OperationHistory extends BaseController {

    private static final EnumSet<HdpsScope> DEFAULT_ADMIN_SCOPES = EnumSet.of(
        ESPP, HIDDEN, HYPERLOCAL_DELIVERY, NOTIFICATIONS, MULTIPLACE, SUMMARY_INN, SUMMARY_ACNT, PACKAGE_INFO, DELIVERY, PROPERTIES, RETURN_PARCEL
    );

    private final StopWatchFactory stopWatchFactory;

    private final HdpsClient hdpsClient;
    private final HistoryRecordCompletionServiceV7 completionServiceV7;
    private final OnlinePaymentMarkService onlinePaymentMarkService;

    /**
     * Returns completed with names history by specified barcode
     *
     * @param barcode     barcode
     * @param corrections corrections
     * @param isOpmIds    linked opm
     * @return completed history
     * @throws ServiceUnavailableException if HDPS is unavailable
     */
    @GetMapping(value = "/{barcode}/history")
    public RegistrableMailInfoV7<?> getOperationHistory(
        @PathVariable("barcode") final String barcode,
        @RequestParam(value = "verbose", required = false, defaultValue = "false") final boolean corrections,
        @RequestParam(value = "opm", required = false, defaultValue = "false") final boolean isOpmIds
    ) throws ServiceUnavailableException {
        final EnumSet<HdpsScope> scope = EnumSet.copyOf(DEFAULT_ADMIN_SCOPES);

        if (corrections) {
            scope.add(CORRECTIONS);
        }

        final StopWatch stopWatch = stopWatchFactory.getStopWatch(buildHistoryTag(corrections));

        try {
            final AdminHistoryResponseV7 history = hdpsClient.getHistory(barcode, scope);
            final RegistrableMailInfoV7<?> registrableMailInfoV7;

            if (scope.contains(CORRECTIONS)) {
                registrableMailInfoV7 = this.completionServiceV7.buildFullInfo(history);
            } else {
                registrableMailInfoV7 = this.completionServiceV7.buildInfo(history);
            }

            resolveOnlinePaymentMark(isOpmIds, barcode, registrableMailInfoV7);

            return registrableMailInfoV7;
        } finally {
            stopWatch.stop();
        }
    }

    private void resolveOnlinePaymentMark(final boolean isOpmIds, final String barcode, final RegistrableMailInfoV7<?> registrableMailInfoV7) {
        try {
            final List<String> opmIds = isOpmIds ? onlinePaymentMarkService.getOpmIdsByBarcode(barcode) : emptyList();
            registrableMailInfoV7.getSummary().setOpmIds(opmIds);
        } catch (ServiceUnavailableException ex) {
            log.error("Online Payment Mark Service unavailable - {}", ex.getMessage());
            registrableMailInfoV7.getSummary().setOpmServiceUnavailable(true);
        }
    }

    private String buildHistoryTag(final boolean corrections) {
        return Stream.of(corrections ? "verbose" : null)
            .filter(Objects::nonNull)
            .collect(joining(", ", "OperationHistory:getOperationHistory(", ")"));
    }
}
