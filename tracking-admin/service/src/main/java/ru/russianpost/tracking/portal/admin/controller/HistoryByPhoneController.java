/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller;

import com.ecyrd.speed4j.StopWatchFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.config.aspect.Speed4J;
import ru.russianpost.tracking.portal.admin.controller.exception.InvalidPhoneFormatException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.byphone.HistoryByPhoneRecord;
import ru.russianpost.tracking.portal.admin.model.errors.Error;
import ru.russianpost.tracking.portal.admin.service.byphone.HistoryByPhoneServiceImpl;

import java.util.List;

import static ru.russianpost.tracking.portal.admin.model.errors.ErrorCode.INVALID_PHONE;

/**
 * History by phone Controller.
 *
 * @author VLaskin
 * @since <pre>30.09.2021</pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/history-by-phone")
public class HistoryByPhoneController extends BaseController {

    private static final String INVALID_PHONE_MESSAGE =
        "Неверный формат кода оператора/города!\n"
            + "Код мобильного оператора/города должен быть Российским.";

    private final StopWatchFactory stopWatchFactory;
    private final HistoryByPhoneServiceImpl historyByPhoneService;

    /**
     * Returns list of {@link HistoryByPhoneRecord}
     *
     * @param phone phone
     * @param limit limit
     * @return list of history by phone records for given phone
     * @throws ServiceUnavailableException if hdps is unavailable
     */
    @Speed4J
    @GetMapping
    public List<HistoryByPhoneRecord> getHistoryByPhone(
        @RequestParam(value = "phone") final String phone,
        @RequestParam(value = "limit", required = false) final Integer limit
    ) throws ServiceUnavailableException {
        return historyByPhoneService.getHistoryByPhone(phone, limit);
    }

    /**
     * Handle InvalidPhoneFormatException exceptions.
     *
     * @param ex exception
     * @return {@link Error} object
     */
    @ExceptionHandler({InvalidPhoneFormatException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error invalidPhoneFormatException(final InvalidPhoneFormatException ex) {
        log.warn("Invalid Phone error: " + ex.getMessage());
        return new Error(INVALID_PHONE, INVALID_PHONE_MESSAGE);
    }
}
