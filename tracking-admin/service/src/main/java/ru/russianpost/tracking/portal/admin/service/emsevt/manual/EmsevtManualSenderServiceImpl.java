/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.emsevt.manual;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.exception.HttpServiceException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.emsevt.manual.TaskInfo;
import ru.russianpost.tracking.portal.admin.model.errors.Error;
import ru.russianpost.tracking.portal.admin.service.exception.EmsevtManualFileNotFoundException;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.Objects.isNull;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;
import static ru.russianpost.tracking.portal.admin.service.emsevt.manual.EmsevtManualSenderFileExtension.getAllowedExtensions;

/**
 * @author Amosov Maxim
 * @since 04.08.2021 : 17:14
 */
@Slf4j
@Service
public class EmsevtManualSenderServiceImpl implements EmsevtManualSenderService {
    private static final ParameterizedTypeReference<List<TaskInfo>> LIST_TASKS_RESPONSE_TYPE = new ParameterizedTypeReference<List<TaskInfo>>() {
    };
    private static final List<MediaType> ACCEPTABLE_MEDIA_TYPES = Arrays.stream(EmsevtManualSenderFileExtension.values())
        .map(ext -> MediaType.parseMediaType(ext.getContentType()))
        .collect(Collectors.toList());

    private final String createTaskUrl;
    private final String getTasksUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpHeaders httpHeadersEmsevtManual = new HttpHeaders();
    private final HttpHeaders httpHeadersDatabusStorage = new HttpHeaders();

    /**
     * @param restTemplate              rest template
     * @param baseUrl                   base url
     * @param login                     login
     * @param password                  password
     * @param databusStorageAccessToken databus storage access token
     */
    public EmsevtManualSenderServiceImpl(
        @Qualifier("restTemplateEmsevtManual") final RestTemplate restTemplate,
        @Value("${emsevt-manual-sender.service.url}") final String baseUrl,
        @Value("${emsevt-manual-sender.service.login}") final String login,
        @Value("${emsevt-manual-sender.service.password}") final String password,
        @Value("${databus-storage.access-token}") final String databusStorageAccessToken
    ) {
        this.restTemplate = restTemplate;
        this.createTaskUrl = fromUriString(baseUrl)
            .path("/task")
            .queryParam("author", "{author}")
            .build().toUriString();
        this.getTasksUrl = fromUriString(baseUrl)
            .path("/task")
            .queryParam("author", "{author}")
            .build().toUriString();
        httpHeadersEmsevtManual.setBasicAuth(login, password);
        httpHeadersDatabusStorage.setAccept(ACCEPTABLE_MEDIA_TYPES);
        httpHeadersDatabusStorage.add(HttpHeaders.AUTHORIZATION, databusStorageAccessToken);
    }

    @Override
    public TaskInfo createTask(final MultipartFile file, final String author) throws ServiceUnavailableException {
        try {
            final String filename = file.getOriginalFilename();
            final EmsevtManualSenderFileExtension extension = EmsevtManualSenderFileExtension.byFilename(filename);
            if (isNull(extension)) {
                throw new BadRequestException("Bad file extension. Allowed extensions are: " + getAllowedExtensions());
            }
            final HttpHeaders headers = new HttpHeaders();
            headers.addAll(httpHeadersEmsevtManual);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setAccept(singletonList(MediaType.APPLICATION_JSON));
            final Resource byteArrayResource = new NamedByteArrayResource(file.getBytes(), filename);
            final MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.put("file", singletonList(byteArrayResource));
            return restTemplate.exchange(createTaskUrl, POST, new HttpEntity<>(map, headers), TaskInfo.class, author).getBody();
        } catch (final Exception e) {
            throw handleException(e, createTaskUrl);
        }
    }

    @Override
    public List<TaskInfo> getTasks(final String author) throws ServiceUnavailableException {
        try {
            return restTemplate.exchange(getTasksUrl, GET, new HttpEntity<>(httpHeadersEmsevtManual), LIST_TASKS_RESPONSE_TYPE, author).getBody();
        } catch (final Exception ex) {
            throw handleException(ex, getTasksUrl);
        }
    }

    @Override
    public ResponseEntity<byte[]> getResultFile(final String downloadUrl) throws ServiceUnavailableException {
        final URI uri = URI.create(downloadUrl);
        try {
            return restTemplate.exchange(uri, GET, new HttpEntity<>(httpHeadersDatabusStorage), byte[].class);
        } catch (final HttpClientErrorException ex) {
            if (ex.getStatusCode() == HttpStatus.NOT_ACCEPTABLE) {
                throw new EmsevtManualFileNotFoundException(downloadUrl, ex);
            }
            throw new ServiceUnavailableException(uri.toString(), ex);
        } catch (final Exception ex) {
            throw new ServiceUnavailableException(uri.toString(), ex);
        }
    }

    private HttpServiceException handleException(final Exception ex, final String uri) throws ServiceUnavailableException {
        if (ex instanceof HttpStatusCodeException) {
            final HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) ex;
            try {
                final Error error = mapper.readValue(httpStatusCodeException.getResponseBodyAsByteArray(), Error.class);
                return new HttpServiceException(httpStatusCodeException.getStatusCode().value(), error);
            } catch (final IOException e1) {
                log.warn("Error to parse error response from Emsevt manual sender service", ex);
                throw new ServiceUnavailableException(uri, ex);
            }
        } else {
            log.warn("Request to Dispatch Segments service failed!", ex);
            throw new ServiceUnavailableException(uri, ex);
        }
    }

    /**
     * Class that extends ByteArrayResource with filename field
     */
    @EqualsAndHashCode(callSuper = true)
    private static class NamedByteArrayResource extends ByteArrayResource {
        @Getter
        private final String filename;

        /**
         * Instantiates a new Named byte array resource.
         *
         * @param byteArray the byte array
         * @param filename  the filename
         */
        NamedByteArrayResource(final byte[] byteArray, final String filename) {
            super(byteArray);
            this.filename = filename;
        }
    }
}
