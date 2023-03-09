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

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.eorder.EOrderShipmentRecord;
import ru.russianpost.tracking.portal.admin.service.eorder.EOrderShipmentsService;

import java.util.List;

/**
 * @author Amosov Maxim
 * @since 08.07.2021 : 12:30
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/eorder-shipments")
public class EOrderShipmentsController extends BaseController {
    private final StopWatchFactory stopWatchFactory;
    private final EOrderShipmentsService eorderShipmentsService;

    /**
     * @param id eorder id
     * @return list of eorder shipments
     * @throws ServiceUnavailableException if hdps is unavailable
     */
    @GetMapping
    public List<EOrderShipmentRecord> getEOrderShipments(@RequestParam(value = "id") final String id) throws ServiceUnavailableException {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("EOrderShipmentsController:getEOrderShipments");
        try {
            return eorderShipmentsService.getEOrderShipments(id);
        } finally {
            stopWatch.stop();
        }
    }
}
