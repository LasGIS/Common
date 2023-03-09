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

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import ru.russianpost.tracking.portal.admin.config.ServiceConfig;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
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
 * @author vlaskin
 * @since <pre>25.11.2021</pre>
 */
@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, AnnotationConfigContextLoader.class})
@TestPropertySource(locations = {
    "classpath:application.conf",
    "classpath:external-services.properties"
})
public class InternationalMonitoringServiceImplTestManual {

    private ApplicationContext context;
    private Environment environment;
    private InternationalMonitoringService service;
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
        this.environment = context.getEnvironment();
    }

    @Before
    public void setUp() {
        final RestTemplate restTemplate = (RestTemplate) context.getBean("restTemplateInternationalMonitoring");
        final String baseUrl = environment.getProperty("international-monitoring.service.url");
        final String login = environment.getProperty("international-monitoring.service.login");
        final String password = environment.getProperty("international-monitoring.service.password");
        service = new InternationalMonitoringServiceImpl(restTemplate, baseUrl, login, password);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getMainDashboardEDIReport() throws Exception {
        final MainDashboardEDIReport report = executeRequest(() ->
            service.getMainDashboardEDIReport("01.01.2021", "26.11.2021"));
        log.info("MainDashboardEDIReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getReceptacleListEDIReport() throws Exception {
        final ReceptacleListEDIReport report = executeRequest(() ->
            service.getReceptacleListEDIReport("01.01.2021", "26.11.2021", "RU", "EMS"));
        log.info("ReceptacleListEDIReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getShipmentListEDIReport() throws Exception {
        final ShipmentListEDIReport report = executeRequest(() ->
            service.getShipmentListEDIReport("RUMOWSMNALTACUD10435001010053"));
        log.info("ShipmentListEDIReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getDispatchMainReport() throws Exception {
        final DispatchMainReport report = executeRequest(() ->
            service.getDispatchMainReport("21.11.2021", "21.12.2021"));
        log.info("DispatchMainReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getDispatchDetailReport() throws Exception {
        final DispatchDetailReport report = executeRequest(() ->
            service.getDispatchDetailReport("RULEDLJPKWSAACV1000"));
        log.info("DispatchDetailReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getRtm4601GsdpErrorTotalsReport() throws Exception {
        final Rtm4601GsdpErrorTotalsReport report = executeRequest(() ->
            service.getRtm4601GsdpErrorTotalsReport("01.01.2021", "26.11.2021"));
        log.info("Rtm4601GsdpErrorTotalsReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getRtm4601GsdpErrorDetailsReport() throws Exception {
        final Rtm4601GsdpErrorDetailsReport report = executeRequest(() ->
            service.getRtm4601GsdpErrorDetailsReport(
                "01.01.2021", "26.11.2021", "AE", "GSDP", "HK", "U"));
        log.info("Rtm4601GsdpErrorDetailsReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getRtm4601GsdpErrorShipmentDetailsReport() throws Exception {
        final Rtm4601GsdpErrorShipmentDetailsReport report = executeRequest(() ->
            service.getRtm4601GsdpErrorShipmentDetailsReport(
                "10.10.2021", "26.11.2021", "AE", "GSDP", "HK", "U"));
        log.info("Rtm4601GsdpErrorShipmentDetailsReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getRtm4601GsdpErrorByShipmentIdReport() throws Exception {
        final Rtm4601GsdpErrorsByShipmentReport report = executeRequest(() ->
            service.getRtm4601GsdpErrorsByShipmentIdReport("EZ025916186HK"));
        log.info("Rtm4601GsdpErrorsByShipmentReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getRtm4601GsdpDetailsByErrorsReport() throws Exception {
        final Rtm4601GsdpDetailsByErrorsReport report = executeRequest(() ->
            service.getRtm4601GsdpDetailsByErrorsReport("10.10.2021", "26.11.2021"));
        log.info("getRtm4601GsdpDetailsByErrorsReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getUpuMonitoringReport() throws Exception {
        final UpuMonitoringReport report = executeRequest(() ->
            service.getUpuMonitoringReport("CA012408415RU"));
        log.info("getUpuMonitoringReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getUpuFileBody() throws Exception {
        final UpuFileRawPageResponse report = executeRequest(() ->
            service.getUpuFileBody("INTREF0977004.txt", 2));
        log.info("getRtm4601GsdpDetailsByErrorsReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getCustomsDeclarationErrorReport() throws Exception {
        final CustomsDeclarationErrorReport report = executeRequest(() ->
            service.getCustomsDeclarationErrorReport("02.01.2021", "26.11.2021", null));
        log.info("getCustomsDeclarationErrorReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getCustomsDeclarationWithoutBusinessValidationErrorReport() throws Exception {
        final CustomsDeclarationErrorReport report = executeRequest(() ->
            service.getCustomsDeclarationErrorReport("03.01.2021", "26.11.2021", false));
        log.info("getCustomsDeclarationWithoutBusinessValidationErrorReport report = {}", mapper.writeValueAsString(report));
    }

    @Test
    public void getCustomsDeclarationWithBusinessValidationErrorReport() throws Exception {
        final CustomsDeclarationErrorReport report = executeRequest(() ->
            service.getCustomsDeclarationErrorReport("04.01.2021", "26.11.2021", true));
        log.info("getCustomsDeclarationWithBusinessValidationErrorReport report = {}", mapper.writeValueAsString(report));
    }

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws ServiceUnavailableException;
    }

    private <T> T executeRequest(ThrowingSupplier<T> supplier) throws ServiceUnavailableException {
        int i = 1;
        while (i < 5) {
            try {
                return supplier.get();
            } catch (final InternalServerErrorException ex) {
                log.info("InternalServerErrorException i = {} ; ex {}", i, ex.toString());
                final HttpStatusCodeException statusCodeException = ex.getHttpStatusCodeException();
                Assert.assertNotNull(ex.getHttpStatusCodeException());
                Assert.assertEquals(HttpStatus.LOCKED, statusCodeException.getStatusCode());
            }
            i++;
        }
        return null;
    }
}
