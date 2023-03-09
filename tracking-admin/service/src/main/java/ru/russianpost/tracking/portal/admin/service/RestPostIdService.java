/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.postid.InfoProfile;
import ru.russianpost.tracking.portal.admin.model.ui.PostIdSearchResult;
import ru.russianpost.tracking.portal.admin.service.exception.PostUserNotFoundServiceException;
import ru.russianpost.tracking.portal.admin.validation.validators.EmailValidator;

import java.text.MessageFormat;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * RestPostIdService
 * @author Roman Prokhorov
 * @version 1.0
 */
@Slf4j
@Service
public class RestPostIdService implements PostIdService {

    private static final EmailValidator EMAIL_VALIDATOR = new EmailValidator();

    private final RestTemplate restTemplate;
    private final String postIdApiUrl;

    private final String infoProfileUri;
    private final String searchUserUri;
    private final String authorization;

    /**
     * Constructor
     * @param restTemplate instance of {@link RestTemplate}
     * @param postIdApiUrl postId api url
     * @param clientId     client id
     * @param clientSecret client secret
     */
    public RestPostIdService(
        @Qualifier("restTemplate") RestTemplate restTemplate,
        @Value("${ru.russianpost.postid.service.url}") String postIdApiUrl,
        @Value("${ru.russianpost.postid.ouath2.clientid}") String clientId,
        @Value("${ru.russianpost.postid.ouath2.clientsecret}") String clientSecret
    ) {
        this.restTemplate = restTemplate;
        this.postIdApiUrl = postIdApiUrl;
        this.infoProfileUri = UriComponentsBuilder.fromUriString(postIdApiUrl)
            .path("/v2.3")
            .path("/account")
            .path("/info.profile")
            .path("/{hid}")
            .build()
            .toUriString();
        this.searchUserUri = UriComponentsBuilder.fromUriString(postIdApiUrl)
            .path("/v2.0")
            .path("/cdi")
            .path("/search")
            .queryParam("email", "{email}")
            .build()
            .toUriString();
        this.authorization = setAuthorization(clientId, clientSecret);
    }

    private static String setAuthorization(String clientId, String clientSecret) {
        return MessageFormat.format("Basic {0}", Base64Utils.encodeToString((clientId + ":" + clientSecret).getBytes(UTF_8)));
    }

    @Override
    public InfoProfile getUserProfile(String hid) throws PostUserNotFoundServiceException, ServiceUnavailableException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", authorization);
            HttpEntity httpEntity = new HttpEntity(headers);

            ResponseEntity<InfoProfile> response =
                restTemplate.exchange(infoProfileUri, HttpMethod.GET, httpEntity, InfoProfile.class, hid);

            return response.getBody();

        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new PostUserNotFoundServiceException(ex);
            }
            throw new InternalServerErrorException(infoProfileUri, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(this.postIdApiUrl, ex);
        }
    }

    @Override
    public PostIdSearchResult findUserByEmail(String email)
        throws PostUserNotFoundServiceException, BadRequestException, ServiceUnavailableException {

        try {
            if (EMAIL_VALIDATOR.isValid(email, null)) {
                return restTemplate.postForObject(searchUserUri, null, PostIdSearchResult.class, email);
            } else {
                throw new BadRequestException("Invalid Email: \"" + email + "\"");
            }
        } catch (HttpClientErrorException ex) {
            log.error(ex.getMessage());
            if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new PostUserNotFoundServiceException(ex);
            }
            throw new InternalServerErrorException(searchUserUri, ex);
        } catch (RestClientException ex) {
            log.error(ex.getMessage());
            throw new ServiceUnavailableException(this.postIdApiUrl, ex);
        }
    }
}
