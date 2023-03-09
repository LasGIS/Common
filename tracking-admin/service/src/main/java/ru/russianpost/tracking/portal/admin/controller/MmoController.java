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
import ru.russianpost.tracking.portal.admin.model.mmo.MmoSearchResult;
import ru.russianpost.tracking.portal.admin.service.hdps.HdpsClient;
import ru.russianpost.tracking.portal.admin.service.mappers.MmoMapper;

/**
 * MmoController.
 * @author MKitchenko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mmo")
public class MmoController extends BaseController {

    private final StopWatchFactory stopWatchFactory;
    private final HdpsClient hdpsClient;
    private final MmoMapper mapper;

    /**
     * Returns instance of {@link MmoSearchResult}
     * @param id multiplace barcode
     * @return instance of {@link MmoSearchResult}
     * @throws ServiceUnavailableException if hdps is unavailable
     */
    @GetMapping(value = "/info/{id}")
    public MmoSearchResult getMmoInfoByMmoId(
        @PathVariable("id") final String id
    ) throws ServiceUnavailableException {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("MmoController:getMmoInfoByMmoId");
        try {
            return mapper.toMmoSearchResult(hdpsClient.getMmoInfoByMmoId(id));
        } finally {
            stopWatch.stop();
        }
    }
}
