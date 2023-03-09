/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service.multiple.tracking;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.web.client.RestTemplate;
import ru.russianpost.tracking.portal.admin.config.ServiceConfig;
import ru.russianpost.tracking.portal.admin.exception.HttpServiceException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.errors.ErrorCode;

import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;

/**
 * @author vlaskin
 * @since <pre>15.06.2022</pre>
 */
@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, AnnotationConfigContextLoader.class})
@TestPropertySource(locations = {
    "classpath:application.conf",
    "classpath:external-services.properties"
})
public class MultipleTrackingServiceImplTestManual {

    private ApplicationContext context;
    private Environment environment;
    private MultipleTrackingService service;

    @Autowired
    public void setContext(ApplicationContext context) {
        this.context = context;
        this.environment = context.getEnvironment();
    }

    @Before
    public void setUp() throws Exception {
        final RestTemplate restTemplate = (RestTemplate) context.getBean("restTemplateMultipleTracking");
        final String baseUrl = environment.getProperty("multiple-tracking.service.url");
        final String login = environment.getProperty("multiple-tracking.service.login");
        final String password = environment.getProperty("multiple-tracking.service.password");
        final String databusStorageAccessToken = environment.getProperty("databus-storage.access-token");
        service = new MultipleTrackingServiceImpl(restTemplate, baseUrl, login, password, databusStorageAccessToken);
    }

    @Test
    public void getResultFile() {
        String downloadUrl
            = "https://databus.test.russianpost.ru/storage/api/v3.0/files/dev-tracking-emsevt-manual-sender/declaration-error_author_444f45b6-76d7-4ffc-82da-156928c80f32.xlsx";
//            = "https://databus.test.russianpost.ru/storage/api/v3.0/files/dev-tracking-emsevt-manual-sender/declaration-error_Ext-V.Laskin_d7776b3c-d9c9-49f3-af0a-cad8cd0955c4.xlsx";
        try {
            final ResponseEntity<byte[]> result = service.getResultFile(downloadUrl);
            final HttpHeaders headers = result.getHeaders();
            final ContentDisposition contentDisposition = headers.getContentDisposition();
            final String fileName = contentDisposition.getFilename();
            try (final FileOutputStream fos = new FileOutputStream(fileName)) {
                fos.write(result.getBody());
            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
                fail();
            }

            log.info("ResponseEntity<byte[]> result = {}", result.getBody());
        } catch (HttpServiceException ex) {
            log.info(ex.getMessage(), ex);
            assertEquals(NOT_ACCEPTABLE.value(), ex.getHttpCode());
            assertEquals(ErrorCode.FILE_NOT_FOUND, ex.getError().getCode());
        } catch (final ServiceUnavailableException ex) {
            fail();
        }
    }
}
