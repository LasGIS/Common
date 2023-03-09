/*
 * Copyright 2018 Russian Post
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsHistoryRecord;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsHistoryResponse;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsNotificationAdvanced;
import ru.russianpost.tracking.portal.admin.repository.Dictionary;
import ru.russianpost.tracking.portal.admin.service.hdps.HdpsClient;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author MKitchenko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sms-history")
public class SmsHistory extends BaseController {

    private final StopWatchFactory stopWatchFactory;

    private final Dictionary dictionary;
    private final HdpsClient hdpsClient;

    /**
     * Returns sms history by specified barcode
     * @param barcode barcode
     * @return completed history
     * @throws ServiceUnavailableException if HDPS is unavailable
     */
    @GetMapping(value = "{barcode}")
    public SmsHistoryResponse getSmsHistory(
        @PathVariable("barcode") final String barcode
    ) throws ServiceUnavailableException {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("SmsHistory:getSmsHistory");
        try {
            final List<SmsHistoryRecord> smsHistory = hdpsClient.getSmsHistory(barcode);
            final List<SmsNotificationAdvanced> fullSmsHistory = buildFullSmsHistoryInfo(smsHistory);
            return new SmsHistoryResponse(barcode, fullSmsHistory);
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Build full sms history.
     * @param smsHistory list of {@link SmsHistoryRecord}
     * @return full sms history
     */
    private List<SmsNotificationAdvanced> buildFullSmsHistoryInfo(final List<SmsHistoryRecord> smsHistory) {
        return smsHistory.stream()
                .map(item -> new SmsNotificationAdvanced(
                        item.getPhoneNumber(),
                        dictionary.getSmsNotificationType(item.getSmsType()),
                        item.getSendTime()
                ))
                .collect(toList());
    }
}
