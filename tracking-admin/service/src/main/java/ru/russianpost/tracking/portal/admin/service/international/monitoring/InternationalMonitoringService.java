/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service.international.monitoring;

import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
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
import ru.russianpost.tracking.portal.admin.model.international.monitoring.UpuFileRawPageResponse;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.UpuMonitoringReport;

/**
 * Service interface for read International Monitoring data from tracking-international-monitoring-web service.
 *
 * @author vlaskin
 * @since <pre>25.11.2021</pre>
 */
public interface InternationalMonitoringService {

    /** Формат даты/времени, в котором она должна приходить с UI */
    String TIMESTAMP_PATTERN = "dd.MM.yyyy";
    /** Формат даты, в котором она должна приходить с UI */
    String DATE_PATTERN = "dd.MM.yyyy";

    /**
     * Возвращает данные для отчета
     * <br>"Мониторинг количества отправленных и полученных данных EDI. Основной Dashboard".
     * <br>или "Main Dashboard EDI Report<br>
     *
     * @param dateFrom Начало периода.
     *                 Формат {@value #TIMESTAMP_PATTERN}
     * @param dateTo   Окончание периода.
     *                 Формат {@value #TIMESTAMP_PATTERN}
     * @return список строк отчета
     * @throws ServiceUnavailableException exception
     */
    MainDashboardEDIReport getMainDashboardEDIReport(String dateFrom, String dateTo) throws ServiceUnavailableException;

    /**
     * Возвращает данные для отчета
     * <br>"Мониторинг количества отправленных и полученных данных EDI. Drill-down (1) по ёмкостям".
     * <br>или "Receptacle List EDI Report".
     *
     * @param dateFrom        Начало периода.
     *                        Формат {@value #TIMESTAMP_PATTERN}
     * @param dateTo          Окончание периода.
     *                        Формат {@value #TIMESTAMP_PATTERN}
     * @param countryToA2Code A2 код страны назначения, например, RU
     * @param postalItemType  Тип почтового отправления
     * @return список строк отчета
     * @throws ServiceUnavailableException exception
     */
    ReceptacleListEDIReport getReceptacleListEDIReport(
        String dateFrom,
        String dateTo,
        String countryToA2Code,
        String postalItemType
    ) throws ServiceUnavailableException;

    /**
     * Возвращает данные для отчета
     * <br>"Мониторинг количества отправленных и полученных данных EDI. Drill-down (2) по отправлениям".
     * <br>aka "Shipment List EDI Report".
     *
     * @param receptacleIdentifier Номер емкости.
     * @return список строк отчета
     * @throws ServiceUnavailableException exception
     */
    ShipmentListEDIReport getShipmentListEDIReport(String receptacleIdentifier) throws ServiceUnavailableException;


    /**
     * Возвращает данные для отчета:
     * <br>"Трекинг по депешам, основной"
     * <br>или "Dispatch Main Report".
     *
     * @param dateFrom Начало периода.
     *                 Формат {@value #TIMESTAMP_PATTERN}
     * @param dateTo   Окончание периода.
     *                 Формат {@value #TIMESTAMP_PATTERN}
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException Service Unavailable Exception
     */
    DispatchMainReport getDispatchMainReport(String dateFrom, String dateTo) throws ServiceUnavailableException;

    /**
     * Возвращает данные для отчета
     * <br>"Трекинг по депешам".
     * <br>aka "Dispatch Report".
     *
     * @param dispatchIdentifier Номер депеши.
     * @return список строк отчета
     * @throws ServiceUnavailableException exception
     */
    DispatchDetailReport getDispatchDetailReport(String dispatchIdentifier) throws ServiceUnavailableException;

    /**
     * Возвращает данные для отчета
     * <br>"Кол-во ошибок обработки РТМ-4601 и GSDP"
     * <br>или "RTM4601/GSDP Error Totals Report"
     *
     * @param dateFrom Дата начала периода, за который собирается отчет.
     *                 Формат {@value #DATE_PATTERN}
     * @param dateTo   Дата окончания периода (включительно), за который собирается отчет.
     *                 Формат {@value #DATE_PATTERN}
     * @return список строк отчета
     * @throws ServiceUnavailableException exception
     */
    Rtm4601GsdpErrorTotalsReport getRtm4601GsdpErrorTotalsReport(String dateFrom, String dateTo) throws ServiceUnavailableException;

    /**
     * Возвращает данные для отчета
     * <br>"Детализация ошибок обработки РТМ-4601 и GSDP"
     * <br>или "RTM4601/GSDP Error Details Report"
     *
     * @param dateFrom         Дата начала периода, за который собирается отчет.
     *                         Формат {@value #DATE_PATTERN}
     * @param dateTo           Дата окончания периода (включительно), за который собирается отчет.
     *                         Формат {@value #DATE_PATTERN}
     * @param reportDataSource Содержимое колонки "Источник данных" ("AE", "Joom", "iHerb")
     * @param dataFormat       Содержимое колонки "Формат данных" ("GSDP", "4601")
     * @param ipa              ИПА - Иностранная почтовая администрация. Определяется суффиксом ШПИ - HK, SG, CN
     * @param postType         Данные колонки "Вид почты". Определяется префиксом ШПИ - U, R, E, LC, LD
     * @return список строк отчета
     * @throws ServiceUnavailableException exception
     */
    Rtm4601GsdpErrorDetailsReport getRtm4601GsdpErrorDetailsReport(
        String dateFrom,
        String dateTo,
        String reportDataSource,
        String dataFormat,
        String ipa,
        String postType
    ) throws ServiceUnavailableException;

    /**
     * Возвращает данные для отчета
     * <br>"Отчёт по РПО, в которых были выявлены ошибки в данных РТМ-4601 или GSDP"
     * <br>или "RTM4601/GSDP Error Shipment Details Report"
     *
     * @param dateFrom         Дата начала периода, за который собирается отчет.
     *                         Формат {@value #DATE_PATTERN}
     * @param dateTo           Дата окончания периода (включительно), за который собирается отчет.
     *                         Формат {@value #DATE_PATTERN}
     * @param reportDataSource Содержимое колонки "Источник данных" ("AE", "Joom", "iHerb")
     * @param dataFormat       Содержимое колонки "Формат данных" ("GSDP", "4601")
     * @param ipa              ИПА - Иностранная почтовая администрация. Определяется суффиксом ШПИ - HK, SG, CN
     * @param postType         Данные колонки "Вид почты". Определяется префиксом ШПИ - U, R, E, LC, LD
     * @return список строк отчета
     * @throws ServiceUnavailableException exception
     */
    Rtm4601GsdpErrorShipmentDetailsReport getRtm4601GsdpErrorShipmentDetailsReport(
        String dateFrom,
        String dateTo,
        String reportDataSource,
        String dataFormat,
        String ipa,
        String postType
    ) throws ServiceUnavailableException;

    /**
     * Возвращает данные для отчета
     * <br>"Поиск ошибок по РПО в данных РТМ-4601 или GSDP"
     * <br>или "RTM4601/GSDP Errors By Shipment Report"
     *
     * @param shipmentId ШПИ (РПО)
     * @return список строк отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    Rtm4601GsdpErrorsByShipmentReport getRtm4601GsdpErrorsByShipmentIdReport(String shipmentId) throws ServiceUnavailableException;

    /**
     * Возвращает данные для отчета
     * <br>"Детализация по ошибкам в данных РТМ-4601 или GSDP"
     * <br>или "RTM4601/GSDP Details By Errors Report"
     *
     * @param dateFrom Дата начала периода, за который собирается отчет.
     *                 Формат {@value #DATE_PATTERN}
     * @param dateTo   Дата окончания периода (включительно), за который собирается отчет.
     *                 Формат {@value #DATE_PATTERN}
     * @return список строк отчета
     * @throws ServiceUnavailableException if service is unavailable
     */
    Rtm4601GsdpDetailsByErrorsReport getRtm4601GsdpDetailsByErrorsReport(
        String dateFrom, String dateTo
    ) throws ServiceUnavailableException;

    /**
     * Возвращает данные для отчета:
     * <br>"Отслеживание сообщений по обмену данными с ВПС"
     * <br>или "Upu Monitoring Report".
     *
     * @param id Receptacle Identifier or ShipmentId.
     * @return Upu Monitoring Report data
     * @throws ServiceUnavailableException if service is unavailable
     */
    UpuMonitoringReport getUpuMonitoringReport(String id) throws ServiceUnavailableException;

    /**
     * Возвращает содержимое файла
     *
     * @param fileName file Name
     * @param offset   offset
     * @return UpuFileRawDTO содержимое файла
     * @throws ServiceUnavailableException if service is unavailable
     */
    UpuFileRawPageResponse getUpuFileBody(String fileName, int offset) throws ServiceUnavailableException;


    /**
     * Возвращает данные для отчета "Ошибки в декларациях"
     * <br>или "Customs Declaration Error Report"
     *
     * @param dateFrom                  Дата начала периода, за который собирается отчет.Формат {@link #DATE_PATTERN}
     * @param dateTo                    Дата окончания периода (включительно), за который собирается отчет. Формат {@link #DATE_PATTERN}
     * @param includeBusinessValidation Включить в отчет ошибки, не препятствующие сохранению эТД в БД
     * @return Объект со строками отчета
     * @throws ServiceUnavailableException exception
     */
    CustomsDeclarationErrorReport getCustomsDeclarationErrorReport(
        String dateFrom, String dateTo, Boolean includeBusinessValidation
    ) throws ServiceUnavailableException;
}
