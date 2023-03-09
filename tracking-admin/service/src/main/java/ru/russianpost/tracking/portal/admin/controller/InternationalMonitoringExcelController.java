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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.config.aspect.Speed4J;
import ru.russianpost.tracking.portal.admin.exception.HttpServiceException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.errors.Error;
import ru.russianpost.tracking.portal.admin.model.errors.ErrorCode;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.CustomsDeclarationErrorRequest;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.DateIntervalRequest;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.ReceptacleListEDIRequest;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.Rtm4601GsdpErrorShipmentDetailsRequest;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.excel.ExcelReportFileTask;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.excel.ExcelReportFileType;
import ru.russianpost.tracking.portal.admin.service.international.monitoring.InternationalMonitoringExcelService;
import ru.russianpost.tracking.portal.admin.service.international.monitoring.InternationalMonitoringService;

import java.net.URI;
import java.security.Principal;
import java.util.List;

/**
 * Controller for transmit International Monitoring data from tracking-international-monitoring-web service to frontend.
 *
 * @author vlaskin
 * @since <pre>29.11.2021</pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/international-monitoring/excel")
public class InternationalMonitoringExcelController extends BaseController {

    private final InternationalMonitoringExcelService service;

    /**
     * Возвращает данные для отчета
     * <br>"Кол-во ошибок обработки РТМ-4601 и GSDP"
     * <br>или "RTM4601/GSDP Error Totals Report"
     *
     * @param dateFrom Дата начала периода, за который собирается отчет.
     *                 Формат {@value  InternationalMonitoringService#DATE_PATTERN}
     * @param dateTo   Дата окончания периода (включительно), за который собирается отчет.
     *                 Формат {@value  InternationalMonitoringService#DATE_PATTERN}
     * @param author   author login
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("rtm4601-gsdp/error/totals")
    public ExcelReportFileTask createRtm4601GsdpErrorTotalsTask(
        @RequestParam("dateFrom")
        final String dateFrom,
        @RequestParam("dateTo")
        final String dateTo,
        final Principal author
    ) throws ServiceUnavailableException {
        return service.createRtm4601GsdpErrorTotalsTask(author.getName(), DateIntervalRequest.of(dateFrom, dateTo));
    }

    /**
     * Возвращает данные для отчета
     * <br>"Детализация по ошибкам в данных РТМ-4601 или GSDP"
     * <br>или "RTM4601/GSDP Details By Errors Report"
     *
     * @param dateFrom   Дата начала периода, за который собирается отчет.
     *                   Формат {@value  InternationalMonitoringService#DATE_PATTERN}
     * @param dateTo     Дата окончания периода (включительно), за который собирается отчет.
     *                   Формат {@value  InternationalMonitoringService#DATE_PATTERN}
     * @param dataSource Содержимое колонки "Источник данных" ("AE", "Joom", "iHerb")
     * @param dataFormat Содержимое колонки "Формат данных" ("GSDP", "4601")
     * @param ipa        ИПА - Иностранная почтовая администрация. Определяется суффиксом ШПИ - HK, SG, CN
     * @param postType   Данные колонки "Вид почты". Определяется префиксом ШПИ - U, R, E, LC, LD
     * @param author     author login
     * @return список строк отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("rtm4601-gsdp/error/shipment-details")
    public ExcelReportFileTask createRtm4601GsdpErrorShipmentDetailsTask(
        @RequestParam("dateFrom")
        final String dateFrom,
        @RequestParam("dateTo")
        final String dateTo,
        @RequestParam("dataSource")
        final String dataSource,
        @RequestParam("dataFormat")
        final String dataFormat,
        @RequestParam(value = "ipa", required = false)
        final String ipa,
        @RequestParam(value = "postType", required = false)
        final String postType,
        final Principal author
    ) throws ServiceUnavailableException {
        return service.createRtm4601GsdpErrorShipmentDetailsTask(author.getName(), Rtm4601GsdpErrorShipmentDetailsRequest.of(
            dateFrom, dateTo, dataSource, dataFormat, ipa, postType
        ));
    }

    /**
     * Возвращает данные для отчета:
     * <br>"Мониторинг количества отправленных и полученных данных EDI. Основной Dashboard"
     * <br>или "Main Dashboard EDI Report".
     *
     * @param dateFrom Дата/время начала периода.
     *                 Формат {@value InternationalMonitoringService#TIMESTAMP_PATTERN}
     * @param dateTo   Дата/время окончания периода.
     *                 Формат {@value InternationalMonitoringService#TIMESTAMP_PATTERN}
     * @param author   author login
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("edi/main-dashboard")
    public ExcelReportFileTask createMainDashboardEDITask(
        @RequestParam("dateFrom")
        final String dateFrom,
        @RequestParam("dateTo")
        final String dateTo,
        final Principal author
    ) throws ServiceUnavailableException {
        return service.createMainDashboardEDITask(author.getName(), DateIntervalRequest.of(dateFrom, dateTo));
    }

    /**
     * Возвращает данные для отчета:
     * <br>"Мониторинг количества отправленных и полученных данных EDI. Drill-down (1) по ёмкостям"
     * <br>или "Receptacle List EDI Report".
     *
     * @param dateFrom        Дата/время начала периода.
     *                        Формат {@value  InternationalMonitoringService#TIMESTAMP_PATTERN}
     * @param dateTo          Дата/время окончания периода.
     *                        Формат {@value  InternationalMonitoringService#TIMESTAMP_PATTERN}
     * @param countryToA2Code A2 код страны назначения отправления, например, RU
     * @param postalItemType  Тип почтового отправления - "EMS", "PARCEL", "LETTER"
     * @param author          author login
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("edi/receptacle-list")
    public ExcelReportFileTask createReceptacleListEDITask(
        @RequestParam("dateFrom")
        final String dateFrom,
        @RequestParam("dateTo")
        final String dateTo,
        @RequestParam("countryTo")
        final String countryToA2Code,
        @RequestParam("postalItemType")
        final String postalItemType,
        final Principal author
    ) throws ServiceUnavailableException {
        return service.createReceptacleListEDITask(author.getName(), ReceptacleListEDIRequest.of(dateFrom, dateTo, countryToA2Code, postalItemType));
    }

    /**
     * Возвращает данные для отчета:
     * <br>"Трекинг по депешам, основной"
     * <br>или "Dispatch Main Report".
     *
     * @param dateFrom Дата/время начала периода.
     *                 Формат {@value  InternationalMonitoringService#TIMESTAMP_PATTERN}
     * @param dateTo   Дата/время окончания периода.
     *                 Формат {@value  InternationalMonitoringService#TIMESTAMP_PATTERN}
     * @param author   author login
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("dispatch-main")
    public ExcelReportFileTask createDispatchMainTask(
        @RequestParam("dateFrom")
        final String dateFrom,
        @RequestParam("dateTo")
        final String dateTo,
        final Principal author
    ) throws ServiceUnavailableException {
        return service.createDispatchMainTask(author.getName(), DateIntervalRequest.of(dateFrom, dateTo));
    }

    /**
     * Возвращает данные для отчета:
     * <br>"Трекинг по депешам"
     * <br>или "Dispatch Report".
     *
     * @param dispatchIdentifier Номер депеши.
     * @param author             author login
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("dispatch")
    public ExcelReportFileTask createDispatchTask(
        @RequestParam("dispatchIdentifier")
        final String dispatchIdentifier,
        final Principal author
    ) throws ServiceUnavailableException {
        return service.createDispatchTask(author.getName(), dispatchIdentifier);
    }

    /**
     * Возвращает данные для отчета "Ошибки в декларациях" или "Customs Declaration Error Report"
     *
     * @param dateFrom                  Дата начала периода, за который собирается отчет.
     *                                  Формат {@link InternationalMonitoringService#DATE_PATTERN}
     * @param dateTo                    Дата окончания периода (включительно), за который собирается отчет.
     *                                  Формат {@link InternationalMonitoringService#DATE_PATTERN}
     * @param includeBusinessValidation Включить в отчет ошибки, не препятствующие сохранению эТД в БД
     * @param author                    author login
     * @return Объект с результатом
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("customs-declaration/error")
    public ExcelReportFileTask createCustomsDeclarationErrorTask(
        @RequestParam("dateFrom")
        final String dateFrom,
        @RequestParam("dateTo")
        final String dateTo,
        @RequestParam(value = "includeBusinessValidation", required = false)
        final Boolean includeBusinessValidation,
        final Principal author
    ) throws ServiceUnavailableException {
        return service.createCustomsDeclarationErrorTask(
            author.getName(),
            new CustomsDeclarationErrorRequest(dateFrom, dateTo, includeBusinessValidation)
        );
    }

    /**
     * List completed tasks.
     *
     * @param typeStr Возможные типы Excel файла отчета.
     * @param author  author login
     * @return list of Completed Tasks
     * @throws Exception exception
     */
    @Speed4J
    @GetMapping(value = "/tasks")
    @ResponseBody
    public List<ExcelReportFileTask> listCompletedTasks(
        @RequestParam("type")
        final String typeStr,
        final Principal author
    ) throws Exception {
        final ExcelReportFileType type = ExcelReportFileType.valueOf(typeStr);
        return service.listCompletedTasks(type, author.getName());
    }

    /**
     * Download result file by task id.
     *
     * @param redirectPath обратный URL jn error.
     * @param downloadUrl  download url
     * @param filename     filename
     * @return response entity
     */
    @Speed4J
    @GetMapping(value = "/download")
    @ResponseBody
    public ResponseEntity<byte[]> downloadResultFile(
        @RequestParam("redirectPath")
        final URI redirectPath,
        @RequestParam("downloadUrl")
        final URI downloadUrl,
        @RequestParam(value = "filename", required = false)
        final String filename
    ) {
        try {
            final ResponseEntity<byte[]> resultFile = service.getResultFile(downloadUrl.toString());
            if (filename != null) {
                return ResponseEntity.status(resultFile.getStatusCode())
                    .header("Content-Disposition", "attachment; filename=\"" + filename + "\"")
                    .contentType(resultFile.getHeaders().getContentType())
                    .body(resultFile.getBody());
            } else {
                return resultFile;
            }
        } catch (final HttpServiceException ex) {
            return createRedirectResponse(redirectPath, ex.getError().getCode());
        } catch (final ServiceUnavailableException ex) {
            return createRedirectResponse(redirectPath, ErrorCode.SERVICE_UNAVAILABLE);
        }
    }

    private ResponseEntity<byte[]> createRedirectResponse(final URI redirectPath, final ErrorCode errorCode) {
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/admin.html#" + redirectPath.toString() + "&errorCode=" + errorCode);
        return new ResponseEntity<>(null, headers, HttpStatus.FOUND);
    }

    /**
     * Http service exception handler
     *
     * @param ex exception details
     * @return Error to display on frontend
     */
    @ExceptionHandler({HttpServiceException.class})
    public ResponseEntity<Error> httpServiceExceptionError(HttpServiceException ex) {
        log.warn("Error processing in international monitoring get excel service. Error is '{}'", ex.getError());
        final HttpStatus status = (ex.getHttpCode() >= 400 && ex.getHttpCode() < 500) ? HttpStatus.BAD_REQUEST : HttpStatus.SERVICE_UNAVAILABLE;
        return ResponseEntity.status(status).body(ex.getError());
    }

}
