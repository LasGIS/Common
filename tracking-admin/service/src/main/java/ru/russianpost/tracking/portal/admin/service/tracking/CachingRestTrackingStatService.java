/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.tracking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.service.exception.ProfileNotFoundServiceException;
import ru.russianpost.tracking.web.model.admin.AdminPageAccessTypeStatistic;
import ru.russianpost.tracking.web.model.admin.AdminPageProfileCounters;
import ru.russianpost.tracking.web.model.admin.AdminProfileStat;
import ru.russianpost.tracking.web.model.admin.AdminProtocolRequestStatistics;
import ru.russianpost.tracking.web.model.core.Protocol;
import ru.russianpost.tracking.web.model.utils.notification.service.Paging;

import java.util.Arrays;
import java.util.List;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 26, 2015)
 */
@Slf4j
@Service
public class CachingRestTrackingStatService implements ProfileStatService {

    private final RestTemplate restTemplate;

    private final String profileCountersUrl;
    private final String profileStatisticsUrl;
    private final String profileUniqueUsersUrl;
    private final String profileAccessTypeUrl;

    /**
     * CachingRestTrackingStatService
     * @param restTemplate       instance of {@link RestTemplate}
     * @param trackingServiceUrl tracking service url
     */
    public CachingRestTrackingStatService(
        @Qualifier("restTemplate") final RestTemplate restTemplate,
        @Value("${ru.russianpost.tracking.portal.backend.service.admin.profile.url}") final String trackingServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.profileCountersUrl = UriComponentsBuilder.fromUriString(trackingServiceUrl)
            .path("/{id}")
            .path("/counters")
            .build()
            .toUriString();
        this.profileAccessTypeUrl = UriComponentsBuilder.fromUriString(trackingServiceUrl)
            .path("/report")
            .path("/access-type")
            .build()
            .toUriString();
        this.profileStatisticsUrl = UriComponentsBuilder.fromUriString(trackingServiceUrl)
            .path("/report")
            .path("/{accessType}")
            .queryParam("from", "{from}")
            .queryParam("count", "{count}")
            .build()
            .toUriString();
        this.profileUniqueUsersUrl = UriComponentsBuilder.fromUriString(trackingServiceUrl)
            .path("/report")
            .path("/unique-users")
            .path("/{protocol}")
            .build()
            .toUriString();
    }

    @Override
    public AdminPageProfileCounters getCounters(Long profileId) throws ProfileNotFoundServiceException, ServiceUnavailableException {
        log.debug("Extracting counters for profile: {} ", profileId);
        try {
            return restTemplate.getForObject(profileCountersUrl, AdminPageProfileCounters.class, profileId);
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProfileNotFoundServiceException(profileId, ex);
            }
            throw new InternalServerErrorException(profileCountersUrl, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(profileCountersUrl, ex);
        }
    }

    @Override
    public List<AdminProfileStat> getProfileStatsByAccessType(String accessType, Paging paging) throws ServiceUnavailableException {
        log.debug("Extracting statistics for profile: {} ", accessType);
        try {
            return Arrays.asList(
                restTemplate.getForObject(profileStatisticsUrl, AdminProfileStat[].class, accessType, paging.getFrom(), paging.getCount())
            );
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            throw new InternalServerErrorException(profileStatisticsUrl, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(profileStatisticsUrl, ex);
        }
    }

    @Override
    public List<AdminProtocolRequestStatistics> getOverallUniqueUserStats(Protocol protocol) throws ServiceUnavailableException {
        log.debug("Extracting statistics for all profiles for protocol: {} ", protocol);
        try {
            return Arrays.asList(
                restTemplate.getForObject(profileUniqueUsersUrl, AdminProtocolRequestStatistics[].class, protocol.getName())
            );
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            throw new InternalServerErrorException(profileUniqueUsersUrl, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(profileUniqueUsersUrl, ex);
        }
    }

    @Override
    public List<AdminPageAccessTypeStatistic> getAccessTypeStatistics() throws ServiceUnavailableException {
        log.debug("Extracting profile access type");
        try {
            return Arrays.asList(
                restTemplate.getForObject(profileAccessTypeUrl, AdminPageAccessTypeStatistic[].class)
            );
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            throw new InternalServerErrorException(profileAccessTypeUrl, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(profileAccessTypeUrl, ex);
        }
    }
}
