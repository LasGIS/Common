/*
 * Copyright 2016 Russian Post
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
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.controller.dto.SmsNotificationInfo;
import ru.russianpost.tracking.portal.admin.model.operation.OpsInfo;
import ru.russianpost.tracking.portal.admin.repository.Dictionary;
import ru.russianpost.tracking.portal.admin.service.postoffice.PostOfficeClient;

import java.util.Collections;
import java.util.List;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;


/**
 * Dictionaries controller.
 * @author KKiryakov
 */
@Slf4j
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class DictionariesController {

    private static final String EMS_INTERNATIONAL_INDEX_TO = "104010";
    private static final String MRPO_INTERNATIONAL_INDEX_TO = "104000";

    private final StopWatchFactory stopWatchFactory;
    private final PostOfficeClient postOfficeClient;
    private final Dictionary dictionary;

    /**
     * Returns JSON with ops info for the given index.
     * @param index ops index.
     * @return JSON response.
     */
    @GetMapping(value = "/ops/{index}")
    public OpsInfo getOpsInfo(@PathVariable final String index) {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("DictionariesController:getOpsInfo");

        log.debug("Loading OPS info by index: \"{}\".", index);
        final OpsInfo opsInfo = (
            !EMS_INTERNATIONAL_INDEX_TO.equals(index) && !MRPO_INTERNATIONAL_INDEX_TO.equals(index)
                ? postOfficeClient.loadOpsInfo(index)
                : null
        );
        log.debug("OPS info: {}", opsInfo);
        stopWatch.stop();
        return opsInfo;
    }

    /**
     * For debug only!
     * Returns list of sms notification order id to sms notification type as JSON.
     * @return list of sms notification order id to sms notification type as JSON.
     */
    @GetMapping(value = "/sms/order")
    public List<SmsNotificationInfo> getSmsOrder() {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("DictionariesController:getSmsOrder");

        List<SmsNotificationInfo> result;

        try {
            result = this.getSmsTypeOrderList();
        } catch (Exception e) {
            result = Collections.emptyList();
            log.error("Cannot load sms notification order list", e);
        } finally {
            stopWatch.stop();
        }

        return result;
    }

    private List<SmsNotificationInfo> getSmsTypeOrderList() {
        return this.dictionary.getSmsNotificationMap().entrySet().stream()
            .map(smsNotificationTypeEntry ->
                new SmsNotificationInfo(
                    this.dictionary.getSmsTypeOrderMap().get(smsNotificationTypeEntry.getKey()),
                    smsNotificationTypeEntry.getValue()
                )
            )
            .sorted(comparingInt(SmsNotificationInfo::getOrderId))
            .collect(toList());
    }
}
