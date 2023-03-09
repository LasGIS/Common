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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.config.aspect.Speed4J;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ReportRecordsLimitException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.exception.TimeoutCachedRequestException;
import ru.russianpost.tracking.portal.admin.model.errors.Error;
import ru.russianpost.tracking.portal.admin.model.errors.ServiceError;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.CustomsDeclarationErrorReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.DispatchDetailReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.DispatchMainReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.MainDashboardEDIReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.ReceptacleListEDIReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.Rtm4601GsdpDetailsByErrorsReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.Rtm4601GsdpErrorDetailsReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.Rtm4601GsdpErrorShipmentDetailsReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.Rtm4601GsdpErrorTotalsReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.Rtm4601GsdpErrorsByShipmentReport;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.ShipmentListEDIReport;
import ru.russianpost.tracking.portal.admin.service.international.monitoring.InternationalMonitoringService;

import static java.util.Objects.nonNull;
import static ru.russianpost.tracking.portal.admin.model.errors.ErrorCode.TIMEOUT_CACHED_REQUEST;
import static ru.russianpost.tracking.portal.admin.model.errors.ErrorCode.TO_MANY_REPORT_RECORDS;

/**
 * Controller for transmit International Monitoring data from tracking-international-monitoring-web service to frontend.
 *
 * @author vlaskin
 * @since <pre>29.11.2021</pre>
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/international-monitoring")
public class InternationalMonitoringController extends BaseController {

    private final InternationalMonitoringService imService;

    /**
     * Возвращает данные для отчета:
     * <br>"Мониторинг количества отправленных и полученных данных EDI. Основной Dashboard"
     * <br>или "Main Dashboard EDI Report".
     *
     * @param dateFrom Дата/время начала периода.
     *                 Формат {@value InternationalMonitoringService#TIMESTAMP_PATTERN}
     * @param dateTo   Дата/время окончания периода.
     *                 Формат {@value InternationalMonitoringService#TIMESTAMP_PATTERN}
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("edi/main-dashboard")
    public MainDashboardEDIReport getMainDashboardEDIReport(
        @RequestParam("dateFrom")
        final String dateFrom,
        @RequestParam("dateTo")
        final String dateTo
    ) throws ServiceUnavailableException {
        return imService.getMainDashboardEDIReport(dateFrom, dateTo);
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
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("edi/receptacle-list")
    public ReceptacleListEDIReport getReceptacleListEDIReport(
        @RequestParam("dateFrom")
        final String dateFrom,
        @RequestParam("dateTo")
        final String dateTo,
        @RequestParam("countryTo")
        final String countryToA2Code,
        @RequestParam("postalItemType")
        final String postalItemType
    ) throws ServiceUnavailableException {
        return imService.getReceptacleListEDIReport(dateFrom, dateTo, countryToA2Code, postalItemType);
    }

    /**
     * Возвращает данные для отчета:
     * <br>"Мониторинг количества отправленных и полученных данных EDI. Drill-down (2) по отправлениям"
     * <br>или "Shipment List EDI Report".
     *
     * @param receptacleIdentifier Номер емкости.
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("edi/shipment-list")
    public ShipmentListEDIReport getShipmentListEDIReport(
        @RequestParam("receptacleIdentifier")
        final String receptacleIdentifier
    ) throws ServiceUnavailableException {
        return imService.getShipmentListEDIReport(receptacleIdentifier);
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
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException if service is unavailable
     * @throws ReportRecordsLimitException Too many result rows
     */
    @Speed4J
    @GetMapping("dispatch-main")
    public DispatchMainReport getDispatchMainReport(
        @RequestParam("dateFrom")
        final String dateFrom,
        @RequestParam("dateTo")
        final String dateTo
    ) throws ServiceUnavailableException, ReportRecordsLimitException {
        final DispatchMainReport report = imService.getDispatchMainReport(dateFrom, dateTo);
        if (nonNull(report.getError())) {
            final ServiceError error = report.getError();
            if (error.getErrorCode() == TO_MANY_REPORT_RECORDS) {
                throw new ReportRecordsLimitException(error.getMessage());
            }
            throw new InternalServerErrorException(error.getMessage());
        }
        return report;
    }

    /**
     * Возвращает данные для отчета:
     * <br>"Трекинг по депешам"
     * <br>или "Dispatch Report".
     *
     * @param dispatchIdentifier Номер депеши.
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException if service is unavailable
     * @throws ReportRecordsLimitException Too many result rows
     */
    @Speed4J
    @GetMapping("dispatch")
    public DispatchDetailReport getDispatchDetailReport(
        @RequestParam("dispatchIdentifier")
        final String dispatchIdentifier
    ) throws ServiceUnavailableException, ReportRecordsLimitException {
        final DispatchDetailReport report = imService.getDispatchDetailReport(dispatchIdentifier);
        if (nonNull(report.getError())) {
            final ServiceError error = report.getError();
            if (error.getErrorCode() == TO_MANY_REPORT_RECORDS) {
                throw new ReportRecordsLimitException(error.getMessage());
            }
            throw new InternalServerErrorException(error.getMessage());
        }
        return report;
    }

    /**
     * Возвращает данные для отчета
     * <br>"Кол-во ошибок обработки РТМ-4601 и GSDP"
     * <br>или "RTM4601/GSDP Error Totals Report"
     *
     * @param dateFrom Дата начала периода, за который собирается отчет.
     *                 Формат {@value  InternationalMonitoringService#DATE_PATTERN}
     * @param dateTo   Дата окончания периода (включительно), за который собирается отчет.
     *                 Формат {@value  InternationalMonitoringService#DATE_PATTERN}
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("rtm4601-gsdp/error/totals")
    public Rtm4601GsdpErrorTotalsReport getRtm4601GsdpErrorTotalsReport(
        @RequestParam("dateFrom")
        final String dateFrom,
        @RequestParam("dateTo")
        final String dateTo
    ) throws ServiceUnavailableException {
        return imService.getRtm4601GsdpErrorTotalsReport(dateFrom, dateTo);
    }

    /**
     * Возвращает данные для отчета
     * <br>"Кол-во ошибок обработки РТМ-4601 и GSDP"
     * <br>или "RTM4601/GSDP Error Details Report"
     *
     * @param dateFrom   Дата начала периода, за который собирается отчет.
     *                   Формат {@value  InternationalMonitoringService#DATE_PATTERN}
     * @param dateTo     Дата окончания периода (включительно), за который собирается отчет.
     *                   Формат {@value  InternationalMonitoringService#DATE_PATTERN}
     * @param dataSource Содержимое колонки "Источник данных" ("AE", "Joom", "iHerb")
     * @param dataFormat Содержимое колонки "Формат данных" ("GSDP", "4601")
     * @param ipa        ИПА - Иностранная почтовая администрация. Определяется суффиксом ШПИ - HK, SG, CN
     * @param postType   Данные колонки "Вид почты". Определяется префиксом ШПИ - U, R, E, LC, LD
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("rtm4601-gsdp/error/details")
    public Rtm4601GsdpErrorDetailsReport getRtm4601GsdpErrorDetailsReport(
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
        final String postType
    ) throws ServiceUnavailableException {
        return imService.getRtm4601GsdpErrorDetailsReport(dateFrom, dateTo, dataSource, dataFormat, ipa, postType);
    }

    /**
     * Возвращает данные для отчета
     * <br>"Отчёт по РПО, в которых были выявлены ошибки в данных РТМ-4601 или GSDP"
     * <br>или "RTM4601/GSDP Error Shipment Details Report"
     *
     * @param dateFrom   Дата начала периода, за который собирается отчет.
     *                   Формат {@value  InternationalMonitoringService#DATE_PATTERN}
     * @param dateTo     Дата окончания периода (включительно), за который собирается отчет.
     *                   Формат {@value  InternationalMonitoringService#DATE_PATTERN}
     * @param dataSource Содержимое колонки "Источник данных" ("AE", "Joom", "iHerb")
     * @param dataFormat Содержимое колонки "Формат данных" ("GSDP", "4601")
     * @param ipa        ИПА - Иностранная почтовая администрация. Определяется суффиксом ШПИ - HK, SG, CN
     * @param postType   Данные колонки "Вид почты". Определяется префиксом ШПИ - U, R, E, LC, LD
     * @return список строк отчета
     * @throws ServiceUnavailableException if service is unavailable
     * @throws ReportRecordsLimitException Too many result rows
     */
    @Speed4J
    @GetMapping("rtm4601-gsdp/error/shipment-details")
    public Rtm4601GsdpErrorShipmentDetailsReport getRtm4601GsdpErrorShipmentDetailsReport(
        @RequestParam("dateFrom")
        final String dateFrom,
        @RequestParam("dateTo")
        final String dateTo,
        @RequestParam("dataSource")
        final String dataSource,
        @RequestParam("dataFormat")
        final String dataFormat,
        @RequestParam(value = "ipa", required = false, defaultValue = "")
        final String ipa,
        @RequestParam(value = "postType", required = false, defaultValue = "")
        final String postType
    ) throws ServiceUnavailableException, ReportRecordsLimitException {
        final Rtm4601GsdpErrorShipmentDetailsReport report =
            imService.getRtm4601GsdpErrorShipmentDetailsReport(dateFrom, dateTo, dataSource, dataFormat, ipa, postType);
        if (nonNull(report.getError())) {
            final ServiceError error = report.getError();
            if (error.getErrorCode() == TO_MANY_REPORT_RECORDS) {
                throw new ReportRecordsLimitException(error.getMessage());
            }
            throw new InternalServerErrorException(error.getMessage());
        }
        return report;
    }

    /**
     * Возвращает данные для отчета
     * <br>"Поиск ошибок по РПО в данных РТМ-4601 или GSDP"
     * <br>или "RTM4601/GSDP Errors By Shipment Report"
     *
     * @param shipmentId ШПИ (РПО)
     * @return список строк отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("rtm4601-gsdp/error/errors-by-shipment")
    public Rtm4601GsdpErrorsByShipmentReport getRtm4601GsdpErrorsByShipmentReport(
        @RequestParam("shipmentId")
        final String shipmentId
    ) throws ServiceUnavailableException {
        return imService.getRtm4601GsdpErrorsByShipmentIdReport(shipmentId);
    }

    /**
     * Возвращает данные для отчета
     * <br>"Детализация по ошибкам в данных РТМ-4601 или GSDP"
     * <br>или "RTM4601/GSDP Details By Errors Report"
     *
     * @param dateFrom Дата начала периода, за который собирается отчет.
     *                 Формат {@value  InternationalMonitoringService#DATE_PATTERN}
     * @param dateTo   Дата окончания периода (включительно), за который собирается отчет.
     *                 Формат {@value  InternationalMonitoringService#DATE_PATTERN}
     * @return список строк отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("rtm4601-gsdp/error/details-by-errors")
    public Rtm4601GsdpDetailsByErrorsReport getRtm4601GsdpDetailsByErrorsReport(
        @RequestParam("dateFrom")
        final String dateFrom,
        @RequestParam("dateTo")
        final String dateTo
    ) throws ServiceUnavailableException {
        return imService.getRtm4601GsdpDetailsByErrorsReport(dateFrom, dateTo);
    }

    /**
     * Возвращает данные для отчета "Ошибки в декларациях" или "Customs Declaration Error Report"
     *
     * @param dateFrom                  Дата начала периода, за который собирается отчет.
     *                                  Формат {@link  InternationalMonitoringService#DATE_PATTERN}
     * @param dateTo                    Дата окончания периода (включительно), за который собирается отчет.
     *                                  Формат {@link  InternationalMonitoringService#DATE_PATTERN}
     * @param includeBusinessValidation Включить в отчет ошибки, не препятствующие сохранению эТД в БД
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    @Speed4J
    @GetMapping("customs-declaration/error")
    public CustomsDeclarationErrorReport getCustomsDeclarationErrorReport(
        @RequestParam("dateFrom")
        final String dateFrom,
        @RequestParam("dateTo")
        final String dateTo,
        @RequestParam(value = "includeBusinessValidation", required = false, defaultValue = "false")
        final boolean includeBusinessValidation
    ) throws ServiceUnavailableException {
        return imService.getCustomsDeclarationErrorReport(dateFrom, dateTo, includeBusinessValidation);
    }

    /**
     * Cached request timed out Handler
     *
     * @param ex TimeoutCachedRequestException instance
     * @return error with TIMEOUT_CACHED_REQUEST code
     */
    @ExceptionHandler({TimeoutCachedRequestException.class})
    public ResponseEntity<Error> getErrorOnTimeoutCachedRequestException(TimeoutCachedRequestException ex) {
        return ResponseEntity.ok(new Error(TIMEOUT_CACHED_REQUEST, ex.getMessage()));
    }

    /**
     * Too many result rows Exception Handler
     *
     * @param ex ReportRecordsLimitException instance
     * @return error with TO_MANY_REPORT_RECORDS code
     */
    @ExceptionHandler({ReportRecordsLimitException.class})
    public ResponseEntity<Error> getErrorReportRecordsLimitException(ReportRecordsLimitException ex) {
        return ResponseEntity.ok(new Error(TO_MANY_REPORT_RECORDS, ex.getMessage()));
    }
}
