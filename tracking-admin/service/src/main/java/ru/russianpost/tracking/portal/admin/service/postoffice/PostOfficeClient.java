/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.postoffice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.model.operation.OpsInfo;

/**
 * Service responsible for communications with "PostOffice" service.
 * @author sslautin
 * @version 1.0 12.10.2015
 */
@Slf4j
@Service
public class PostOfficeClient {

    private final RestTemplate restTemplate;
    private final String targetUrl;

    /**
     * Constructor
     * @param postofficeServiceUrl  postoffice service url
     * @param restTemplate          instance of {@link RestTemplate}
     */
    public PostOfficeClient(
        @Value("${ru.russianpost.postoffice.service.url}") String postofficeServiceUrl,
        @Qualifier("restTemplate") RestTemplate restTemplate
    ) {
        this.restTemplate = restTemplate;
        this.targetUrl = UriComponentsBuilder.fromUriString(postofficeServiceUrl)
            .queryParam("postalCode", "{postalCode}")
            .queryParam("filterByOfficeType", "false")
            .build()
            .toUriString();
    }

    /**
     * Loads OPS info by index from postoffice service.
     * @param index ops index.
     * @return OPS info object or null, if cannot load (server response is not 200 OK).
     * If server response is 200 OK, but OPS has not been found, then
     * empty (without index) OPS info will be returned.
     */
    public OpsInfo loadOpsInfo(final String index) {

        OpsInfo opsInfo = null;

        if (index != null && !index.isEmpty()) {
            try {
                OpsServiceInfo info = restTemplate.getForObject(targetUrl, OpsServiceInfo.class, index);
                if (info == null || info.getOffice() == null) {
                    log.warn("Cannot load OPS info for index \"{}\". Json part of postoffice service response is empty.", index);
                    opsInfo = new OpsInfo();
                } else {
                    opsInfo = new OpsInfo(
                        index,
                        info.getOffice().getRegion(),
                        info.getOffice().getDistrict(),
                        info.getOffice().getSettlement()
                    );
                }
            } catch (HttpStatusCodeException ex) {
                log.warn("Cannot load OPS info for index \"{}\". Postoffice response status: {}.", index, ex.getStatusCode());
            } catch (Exception e) {
                log.error("Cannot load OPS info for index \"" + index + "\".", e);
            }
        } else {
            log.warn("Cannot load OPS info. Index is empty.");
        }

        return opsInfo;
    }

}
