/*
 * Copyright 2022 Russian Post
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.service.exception.AccessTypeAlreadySetException;
import ru.russianpost.tracking.portal.admin.service.exception.ProfileNotFoundServiceException;
import ru.russianpost.tracking.portal.admin.service.exception.UserAttachingConflictServiceException;
import ru.russianpost.tracking.portal.admin.service.exception.UserAttachingLimitExceededServiceException;
import ru.russianpost.tracking.portal.admin.service.exception.UserAttachingServiceException;
import ru.russianpost.tracking.portal.admin.service.exception.UserDetachingServiceException;
import ru.russianpost.tracking.web.model.admin.AccessChangeRequest;
import ru.russianpost.tracking.web.model.admin.AdminProfileSearchResult;
import ru.russianpost.tracking.web.model.admin.CustomUserSeed;
import ru.russianpost.tracking.web.model.core.AccessType;
import ru.russianpost.tracking.web.model.core.Profile;
import ru.russianpost.tracking.web.model.portal.PortalCompanyEdition;
import ru.russianpost.tracking.web.model.portal.PortalProfileHistoryEvent;

import java.util.Arrays;
import java.util.List;

/**
 * TrackingService REST implementation
 *
 * @author Roman Prokhorov
 * @version 1.0
 */
@Slf4j
@Service
public class RestTrackingService implements
    ProfileHistoryService, ProfileManagementService, ProfileSearchService, ProfileCompanyManagementService {

    private final RestTemplate restTemplate;

    private final String getUrl;
    private final String findUrl;
    private final String accessChangeUrl;
    private final String sendInfoUrl;
    private final String resetPasswordUrl;
    private final String attachUserUrl;
    private final String detachUserUrl;
    private final String editCompanyUrl;
    private final String profileHistoryUrl;

    /**
     * Constructor
     *
     * @param restTemplate       instance of {@link RestTemplate}
     * @param trackingServiceUrl tracking service url
     */
    public RestTrackingService(
        @Qualifier("restTemplate") final RestTemplate restTemplate,
        @Value("${ru.russianpost.tracking.portal.backend.service.admin.profile.url}") final String trackingServiceUrl
    ) {
        this.restTemplate = restTemplate;
        this.findUrl = UriComponentsBuilder.fromUriString(trackingServiceUrl)
            .path("/find")
            .queryParam("query", "{query}")
            .queryParam("count", "{count}")
            .build()
            .toUriString();
        this.getUrl = UriComponentsBuilder.fromUriString(trackingServiceUrl)
            .path("/{id}")
            .build()
            .toUriString();
        this.accessChangeUrl = UriComponentsBuilder.fromUriString(getUrl)
            .path("/access")
            .build()
            .toUriString();
        this.sendInfoUrl = UriComponentsBuilder.fromUriString(getUrl)
            .path("/sendInfo")
            .build()
            .toUriString();
        this.resetPasswordUrl = UriComponentsBuilder.fromUriString(getUrl)
            .path("/resetPassword")
            .build()
            .toUriString();
        this.attachUserUrl = UriComponentsBuilder.fromUriString(getUrl)
            .path("/user")
            .build()
            .toUriString();
        this.detachUserUrl = UriComponentsBuilder.fromUriString(getUrl)
            .path("/user")
            .path("/{userId}")
            .build()
            .toUriString();
        this.editCompanyUrl = UriComponentsBuilder.fromUriString(getUrl)
            .path("/company")
            .build()
            .toUriString();
        this.profileHistoryUrl = UriComponentsBuilder.fromUriString(getUrl)
            .path("/history")
            .build()
            .toUriString();
    }

    @Override
    public Profile get(long profileId) throws ProfileNotFoundServiceException, ServiceUnavailableException {
        try {
            return restTemplate.getForObject(getUrl, Profile.class, profileId);
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProfileNotFoundServiceException(profileId, ex);
            }
            throw new InternalServerErrorException(getUrl, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(getUrl, ex);
        }
    }

    @Override
    public List<AdminProfileSearchResult> find(final String query, final int count) throws ServiceUnavailableException {
        try {
            final AdminProfileSearchResult[] result = restTemplate.getForObject(findUrl, AdminProfileSearchResult[].class, query, count);
            return Arrays.asList(result);
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            throw new InternalServerErrorException(findUrl, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(findUrl, ex);
        }
    }

    @Override
    public Profile setAccessType(
        final long profileId,
        final AccessType newType,
        final String comment
    ) throws
      AccessTypeAlreadySetException,
      ServiceUnavailableException,
      ProfileNotFoundServiceException {
        String targetUrl = accessChangeUrl;
        try {
            HttpEntity<AccessChangeRequest> request = new HttpEntity<>(
                new AccessChangeRequest(newType, comment)
            );
            final ResponseEntity<Profile> exchanged = restTemplate.exchange(
                accessChangeUrl,
                HttpMethod.POST,
                request,
                Profile.class,
                profileId
            );
            return exchanged.getBody();
        } catch (final HttpClientErrorException ex) {
            log.error(ex.getMessage());
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProfileNotFoundServiceException(profileId, ex);
            } else if (ex.getStatusCode() == HttpStatus.CONFLICT) {
                throw new AccessTypeAlreadySetException(ex.getMessage(), ex);
            }
            throw new InternalServerErrorException(targetUrl, ex);
        } catch (final RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(targetUrl, ex);
        }
    }

    @Override
    public void sendInfo(long profileId)
        throws ProfileNotFoundServiceException, ServiceUnavailableException {
        log.debug("Send info for profileId: {}", profileId);
        try {
            restTemplate.postForLocation(sendInfoUrl, null, profileId);
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProfileNotFoundServiceException(profileId, ex);
            }
            throw new InternalServerErrorException(sendInfoUrl, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(sendInfoUrl, ex);
        }
    }

    @Override
    public void resetPassword(long profileId)
        throws ProfileNotFoundServiceException, ServiceUnavailableException {
        log.debug("Reset password for profileId: {}", profileId);
        try {
            restTemplate.postForLocation(resetPasswordUrl, null, profileId);
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProfileNotFoundServiceException(profileId, ex);
            }
            throw new InternalServerErrorException(resetPasswordUrl, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(resetPasswordUrl, ex);
        }
    }

    @Override
    public Profile attachUser(long profileId, CustomUserSeed attaching)
        throws UserAttachingServiceException, ProfileNotFoundServiceException, ServiceUnavailableException {
        log.debug("Attaching user to profile: {}", profileId);
        try {
            return restTemplate.postForEntity(attachUserUrl, attaching, Profile.class, profileId).getBody();
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProfileNotFoundServiceException(profileId, ex);
            }
            if (ex.getStatusCode() == HttpStatus.CONFLICT) {
                throw new UserAttachingConflictServiceException(profileId, ex);
            }
            throw new InternalServerErrorException(attachUserUrl, ex);
        } catch (HttpServerErrorException ex) {
            log.error(ex.getMessage());
            if (ex.getStatusCode() == HttpStatus.NOT_IMPLEMENTED) {
                throw new UserAttachingLimitExceededServiceException(profileId, ex);
            }
            throw new ServiceUnavailableException(attachUserUrl, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(attachUserUrl, ex);
        }
    }

    @Override
    public Profile detachUser(long profileId, long userId)
        throws ProfileNotFoundServiceException, UserDetachingServiceException, ServiceUnavailableException {
        log.debug("Detaching user {} from profile: {} ", userId, profileId);
        try {
            return restTemplate.exchange(detachUserUrl, HttpMethod.DELETE, null, Profile.class, profileId, userId).getBody();
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProfileNotFoundServiceException(profileId, ex);
            }
            if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new UserDetachingServiceException(profileId, ex);
            }
            throw new InternalServerErrorException(detachUserUrl, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(detachUserUrl, ex);
        }
    }

    @Override
    public Profile editCompany(long profileId, PortalCompanyEdition companyInfoEdition)
        throws ProfileNotFoundServiceException, ServiceUnavailableException {
        log.debug("Editing company for profile: {} ", profileId);
        try {
            final HttpEntity<PortalCompanyEdition> entity = new HttpEntity<>(companyInfoEdition);
            return restTemplate.exchange(editCompanyUrl, HttpMethod.POST, entity, Profile.class, profileId).getBody();
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProfileNotFoundServiceException(profileId, ex);
            }
            throw new InternalServerErrorException(editCompanyUrl, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(editCompanyUrl, ex);
        }
    }

    @Override
    public List<PortalProfileHistoryEvent> getHistoryFor(Integer profileId)
        throws ProfileNotFoundServiceException, ServiceUnavailableException {
        log.debug("Extracting profile history for profile: {} ", profileId);
        try {
            return Arrays.asList(restTemplate.getForObject(profileHistoryUrl, PortalProfileHistoryEvent[].class, profileId));
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new ProfileNotFoundServiceException(profileId, ex);
            }
            throw new InternalServerErrorException(profileHistoryUrl, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(profileHistoryUrl, ex);
        }
    }
}
