/*
 * Copyright 2019 Russian Post
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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.BasketData;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.errors.Error;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.request.ProcessDataRequestUi;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.UnprocessedData;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.ui.BasketDataUi;
import ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.response.ui.DeferredDataInfoUi;
import ru.russianpost.tracking.portal.admin.exception.Rtm4601ServiceException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.service.authorized.operator.support.AuthorizedOperatorSupportDtoMapper;
import ru.russianpost.tracking.portal.admin.service.authorized.operator.support.AuthorizedOperatorSupportService;

import java.security.Principal;
import java.util.List;

/**
 * Authorized Operator Support controller.
 * @author MKitchenko
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authorized-operator-support")
public class AuthorizedOperatorSupportController extends BaseController {

    private final StopWatchFactory stopWatchFactory;

    private final AuthorizedOperatorSupportService service;
    private final AuthorizedOperatorSupportDtoMapper dtoMapper;

    /**
     * Returns deferred data info
     * @param type requested deferred data type
     * @return deferred data info
     * @throws ServiceUnavailableException if service is unavailable
     */
    @GetMapping(value = "/{type}")
    public DeferredDataInfoUi getDeferredData(@PathVariable("type") String type) throws ServiceUnavailableException {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("AuthorizedOperatorSupportController:getDeferredData");
        try {
            log.debug("Get deferred data");
            return dtoMapper.mapDeferredDataToUi(service.getDeferredData(type));
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Send deferred data to declaration.
     * @param request request from ui includes id and trackingNumber
     * @param author  author
     * @return list of unprocessed deferred data with errors
     */
    @PostMapping(value = "/to-declaration")
    public List<UnprocessedData> toDeclaration(
        @RequestBody ProcessDataRequestUi request,
        Principal author
    ) {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("AuthorizedOperatorSupportController:toDeclaration");
        try {
            log.info("Send data to declaration");

            final List<UnprocessedData> unprocessed = service
                .toDeclaration(request.getDataToProcess(), author.getName());

            unprocessed
                .forEach(data -> log.error(
                    "Send data to declaration with id {} and trackingNumber {} crashed with error: {}",
                    data.getId(), data.getTrackingNumber(), data.getReason()
                    )
                );

            return unprocessed;
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Send deferred data to basket.
     * @param request request from ui includes id and trackingNumber
     * @param author  author
     * @return list of unprocessed deferred data with errors
     */
    @PostMapping(value = "/to-basket")
    public List<UnprocessedData> toBasket(
        @RequestBody ProcessDataRequestUi request,
        Principal author
    ) {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("AuthorizedOperatorSupportController:toBasket");
        try {
            log.info("Send data to basket");

            final List<UnprocessedData> unprocessed = service
                .toBasket(request.getDataToProcess(), author.getName());

            unprocessed
                .forEach(data -> log.error(
                    "Send data to basket with id {} and trackingNumber {} crashed with error: {}",
                    data.getId(), data.getTrackingNumber(), data.getReason()
                    )
                );

            return unprocessed;
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Get basket data as list with filter by date
     * @param dateFrom date from
     * @param dateTo   date to
     * @return basket data as list
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @GetMapping(value = "/basket")
    public List<BasketDataUi> basketData(
        @RequestParam(value = "dateFrom") String dateFrom,
        @RequestParam(value = "dateTo") String dateTo
    ) throws ServiceUnavailableException {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("AuthorizedOperatorSupportController:basketData");
        try {
            log.debug("Get basket data");
            final List<BasketData> basketData = service.getBasketData(dateFrom, dateTo);
            return dtoMapper.mapBasketDataListToUi(basketData);
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Send basket data to deferred.
     * @param request request from ui includes id and trackingNumber
     * @param author  author
     * @return list of unprocessed basket data with errors
     */
    @PostMapping(value = "/to-deferred")
    public List<UnprocessedData> toDeferred(
        @RequestBody ProcessDataRequestUi request,
        Principal author
    ) {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("AuthorizedOperatorSupportController:toDeferred");
        try {
            log.info("Send data to deferred");

            final List<UnprocessedData> unprocessed = service
                .toDeferred(request.getDataToProcess(), author.getName());

            unprocessed
                .forEach(data -> log.error(
                    "Send data to deferred with id {} and trackingNumber {} crashed with error: {}",
                    data.getId(), data.getTrackingNumber(), data.getReason()
                    )
                );

            return unprocessed;
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Rtm4601 service exceptions handler
     * @param e exception details
     * @return Error to display on frontend
     */
    @ExceptionHandler({Rtm4601ServiceException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public Error rtm4601ServiceError(Rtm4601ServiceException e) {
        final Error error = e.getError();
        log.warn("Error processing request to rtm4601 service. Error is '{}'", error);
        return error;
    }
}
