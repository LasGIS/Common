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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.config.aspect.Speed4J;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.UpuFileRawPageResponse;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.UpuMonitoringReport;
import ru.russianpost.tracking.portal.admin.service.international.monitoring.InternationalMonitoringService;

/**
 * Controller for transmit Upu Tracking data from tracking-international-monitoring-web service to frontend.
 *
 * @author vlaskin
 * @since <pre>02.03.2022</pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/upu-monitoring")
public class UpuMonitoringController extends BaseController {

    private final InternationalMonitoringService imService;

    /**
     * Возвращает данные для отчета:
     * <br>"Отслеживание сообщений по обмену данными с ВПС"
     * <br>или "Upu Monitoring Report".
     *
     * @param id Receptacle Identifier or ShipmentId.
     * @return Upu Monitoring Report data
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("{id}")
    public UpuMonitoringReport getUpuMonitoringById(
        @PathVariable("id") final String id
    ) throws ServiceUnavailableException {
        return imService.getUpuMonitoringReport(id);
    }

    /**
     * Возвращает содержимое файла
     *
     * @param fileName file Name
     * @param offset   offset
     * @return UpuFileRawDTO содержимое файла
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("file")
    public UpuFileRawPageResponse getUpuFileBody(
        @RequestParam("fileName") final String fileName,
        @RequestParam(value = "offset", required = false, defaultValue = "0") final int offset
    ) throws ServiceUnavailableException {
        return imService.getUpuFileBody(fileName, offset);
    }
}
