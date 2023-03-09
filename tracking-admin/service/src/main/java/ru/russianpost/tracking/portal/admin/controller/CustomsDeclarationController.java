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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.customs.declaration.CustomsDeclaration;
import ru.russianpost.tracking.portal.admin.service.hdps.HdpsClient;

import java.util.List;

/**
 * Customs Declaration Controller.
 *
 * @author VLaskin
 * @since <pre>17.09.2021</pre>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customs-declaration")
public class CustomsDeclarationController extends BaseController {

    private final StopWatchFactory stopWatchFactory;
    private final HdpsClient hdpsClient;

    /**
     * Returns list of {@link CustomsDeclaration}
     *
     * @param barcode barcode
     * @return instance of {@link CustomsDeclaration}
     * @throws ServiceUnavailableException if hdps is unavailable
     */
    @GetMapping(value = "/all/{barcode}")
    public List<CustomsDeclaration> getAllCustomsDeclarations(
        @PathVariable("barcode") final String barcode
    ) throws ServiceUnavailableException {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("CustomsDeclarationController:getAllCustomsDeclarations");
        try {
            return hdpsClient.getAllCustomsDeclarations(barcode);
        } finally {
            stopWatch.stop();
        }
    }
}
