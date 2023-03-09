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

import org.springframework.http.ResponseEntity;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.CustomsDeclarationErrorRequest;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.DateIntervalRequest;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.ReceptacleListEDIRequest;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.Rtm4601GsdpErrorShipmentDetailsRequest;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.excel.ExcelReportFileTask;
import ru.russianpost.tracking.portal.admin.model.international.monitoring.excel.ExcelReportFileType;

import java.util.List;

/**
 * Service interface for read International Monitoring data from tracking-international-monitoring-web service.
 *
 * @author vlaskin
 * @since <pre>25.11.2021</pre>
 */
public interface InternationalMonitoringExcelService {

    /**
     * Create Task to Create Excel Report File for RTM4601/GSDP Details By Errors Report.
     * <br>RTM4601_GSDP_TOTAL - Мониторинг качества/Количество ошибок обработки РТМ-4601 и GSDP
     * <br>или "RTM4601/GSDP Error Totals Report"
     *
     * @param author  author login
     * @param request Объект, содержащий параметры запроса для RTM4601/GSDP Error Totals Report.
     * @return Данные для создания отчетов в формате EXCEL
     * @throws ServiceUnavailableException exception
     */
    ExcelReportFileTask createRtm4601GsdpErrorTotalsTask(String author, DateIntervalRequest request) throws ServiceUnavailableException;

    /**
     * Create Task to Create Excel Report File for RTM4601/GSDP Details By Errors Report.
     * <br>RTM4601_GSDP_DETAILS - Мониторинг качества/Детализация ошибок обработки РТМ-4601 и GSDP
     * <br>или "RTM4601/GSDP Details By Errors Report"
     *
     * @param author  author login
     * @param request Объект, содержащий параметры запроса для RTM4601/GSDP Details By Errors Report.
     * @return Данные для создания отчетов в формате EXCEL
     * @throws ServiceUnavailableException exception
     */
    ExcelReportFileTask createRtm4601GsdpErrorShipmentDetailsTask(
        String author, Rtm4601GsdpErrorShipmentDetailsRequest request
    ) throws ServiceUnavailableException;

    /**
     * Create Task to Create Excel Report File for Main Dashboard EDI Report.
     * <br>EDI_REPORT_MAIN - Мониторинг количества / Количество отправленных и полученных данных EDI
     * <br>или "Main Dashboard EDI Report<br>
     *
     * @param author  author login
     * @param request Объект, содержащий параметры задачи для Main Dashboard EDI отчета
     * @return Данные для создания отчетов в формате EXCEL
     * @throws ServiceUnavailableException exception
     */
    ExcelReportFileTask createMainDashboardEDITask(String author, DateIntervalRequest request) throws ServiceUnavailableException;

    /**
     * Create Task to Create Excel Report File for Receptacle List EDI Report.
     * <br>EDI_REPORT_RECEPTACLES - Мониторинг количества / Отчет по емкостям
     * <br>или "Receptacle List EDI Report".
     *
     * @param author  author login
     * @param request Объект, содержащий параметры запроса для Receptacle List EDI Report.
     * @return Данные для создания отчетов в формате EXCEL
     * @throws ServiceUnavailableException exception
     */
    ExcelReportFileTask createReceptacleListEDITask(String author, ReceptacleListEDIRequest request) throws ServiceUnavailableException;

    /**
     * Create Task to Create Excel Report File for Dispatch Main Report.
     * <br>DISPATCH_MAIN - Трекинг по депешам/Трекинг по депешам
     * <br>или "Dispatch Main Report".
     *
     * @param author  author login
     * @param request Объект, содержащий параметры запроса для Dispatch Main Report.
     * @return Данные для создания отчетов в формате EXCEL
     * @throws ServiceUnavailableException exception
     */
    ExcelReportFileTask createDispatchMainTask(String author, DateIntervalRequest request) throws ServiceUnavailableException;

    /**
     * Create Task to Create Excel Report File for Dispatch Report.
     * <br>DISPATCH_SHIPMENTS - Трекинг по депешам/Отчет для депеши
     * <br>или "Dispatch Report".
     *
     * @param author             author login
     * @param dispatchIdentifier Номер депеши.
     * @return Данные для создания отчетов в формате EXCEL
     * @throws ServiceUnavailableException exception
     */
    ExcelReportFileTask createDispatchTask(String author, String dispatchIdentifier) throws ServiceUnavailableException;

    /**
     * Create Task to Create Excel Report File for Customs Declaration Error Report.
     * <br>DECLARATION_ERROR - Ошибки при сохранении электронной таможенной декларации (ЭТД)
     * <br>или "Customs Declaration Error Report".
     *
     * @param author  author login
     * @param request Объект, содержащий параметры запроса для CustomsDeclarationError отчета
     * @return Данные для создания отчетов в формате EXCEL
     * @throws ServiceUnavailableException exception
     */
    ExcelReportFileTask createCustomsDeclarationErrorTask(String author, CustomsDeclarationErrorRequest request) throws ServiceUnavailableException;


    /**
     * Возвращает список файлов, доступных к выгрузке на UI
     *
     * @param type   Возможные типы Excel файла отчета
     * @param author author login
     * @return list of Excel Report Files
     * @throws ServiceUnavailableException on error
     */
    List<ExcelReportFileTask> listCompletedTasks(ExcelReportFileType type, String author) throws ServiceUnavailableException;

    /**
     * Returns result file.
     *
     * @param downloadUrl download url
     * @return result file.
     * @throws ServiceUnavailableException on error
     */
    ResponseEntity<byte[]> getResultFile(String downloadUrl) throws ServiceUnavailableException;
}
