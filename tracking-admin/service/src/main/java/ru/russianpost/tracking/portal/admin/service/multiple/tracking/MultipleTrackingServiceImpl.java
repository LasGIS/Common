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

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.exception.HttpServiceException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.errors.Error;
import ru.russianpost.tracking.portal.admin.model.errors.ErrorCode;
import ru.russianpost.tracking.portal.admin.model.multiple.tracking.ResultScope;
import ru.russianpost.tracking.portal.admin.model.multiple.tracking.TaskCreated;
import ru.russianpost.tracking.portal.admin.model.multiple.tracking.TaskInfo;
import ru.russianpost.tracking.portal.admin.utils.SecurityUtils;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static ru.russianpost.tracking.portal.admin.model.errors.ErrorCode.SYSTEM_ERROR;

/**
 * MultipleTrackingService implementation.
 *
 * @author KKiryakov
 */
@Slf4j
@Service
public class MultipleTrackingServiceImpl implements MultipleTrackingService {

    private static final ParameterizedTypeReference<List<TaskInfo>> LIST_TASKS_RESPONSE_TYPE = new ParameterizedTypeReference<List<TaskInfo>>() {
    };

    private static final List<MediaType> ACCEPTABLE_MEDIA_TYPES = Arrays.stream(MultipleTrackingFileExtension.values())
        .map(ext -> MediaType.parseMediaType(ext.getContentType()))
        .collect(Collectors.toList());

    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final HttpHeaders httpHeadersMultipleTracking;
    private final HttpHeaders httpHeadersDatabusStorage;

    /**
     * Constructor.
     *
     * @param restTemplate              rest template
     * @param baseUrl                   base url
     * @param login                     login
     * @param password                  password
     * @param databusStorageAccessToken databus storage access token
     */
    public MultipleTrackingServiceImpl(
        @Qualifier("restTemplateMultipleTracking") RestTemplate restTemplate,
        @Value("${multiple-tracking.service.url}") String baseUrl,
        @Value("${multiple-tracking.service.login}") String login,
        @Value("${multiple-tracking.service.password}") String password,
        @Value("${databus-storage.access-token}") String databusStorageAccessToken
    ) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;

        httpHeadersMultipleTracking = new HttpHeaders();
        httpHeadersMultipleTracking.add(HttpHeaders.AUTHORIZATION, SecurityUtils.buildBasicAuthHeaderValue(login, password));

        httpHeadersDatabusStorage = new HttpHeaders();
        httpHeadersDatabusStorage.add(HttpHeaders.AUTHORIZATION, databusStorageAccessToken);
        httpHeadersDatabusStorage.setAccept(ACCEPTABLE_MEDIA_TYPES);
    }

    /**
     * Class that extends ByteArrayResource with filename field.
     */
    @EqualsAndHashCode(callSuper = true)
    private static class NamedByteArrayResource extends ByteArrayResource {
        private final String filename;

        /**
         * Instantiates a new Named byte array resource.
         *
         * @param byteArray the byte array
         * @param filename  the filename
         */
        NamedByteArrayResource(byte[] byteArray, String filename) {
            super(byteArray);
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return filename;
        }
    }

    @Override
    public TaskCreated createTask(
        final MultipartFile file,
        final boolean hiddenOperations,
        final ResultScope resultScope,
        final String csvShowColumns,
        final String author
    ) throws ServiceUnavailableException {
        final UriComponentsBuilder uriComponents = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .path("/task")
            .queryParam("author", author)
            .queryParam("resultScope", resultScope)
            .queryParam("hiddenOperations", hiddenOperations);
        if (csvShowColumns != null) {
            uriComponents.queryParam("showColumns", csvShowColumns);
        }
        final URI uri = uriComponents.build().encode().toUri();
        try {
            final String filename = file.getOriginalFilename();
            final MultipleTrackingFileExtension extension = MultipleTrackingFileExtension.byFilename(filename);
            if (extension == null) {
                throw new BadRequestException("Bad file extension. Allowed extensions are: " + MultipleTrackingFileExtension.getAllowedExtensions());
            }
            final HttpHeaders headers = new HttpHeaders();
            httpHeadersMultipleTracking.forEach((key, values) -> values.forEach(value -> headers.add(key, value)));
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setAccept(singletonList(MediaType.APPLICATION_JSON));
            final Resource byteArrayResource = new NamedByteArrayResource(file.getBytes(), filename);
            final MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
            map.put("file", singletonList(byteArrayResource));
            return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(map, headers), TaskCreated.class).getBody();
        } catch (final Exception e) {
            throw handleException(e, uri);
        }
    }

    @Override
    public List<TaskInfo> listCompletedTasks(String author) throws ServiceUnavailableException {
        final URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
            .path("/task/{author}").buildAndExpand(author).encode().toUri();
        try {
            return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeadersMultipleTracking), LIST_TASKS_RESPONSE_TYPE).getBody();
        } catch (final Exception e) {
            throw handleException(e, uri);
        }
    }

    @Override
    public ResponseEntity<byte[]> getResultFile(final String downloadUrl) throws ServiceUnavailableException {
        final URI uri = URI.create(downloadUrl);
        try {
            return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(httpHeadersDatabusStorage), byte[].class);
        } catch (final HttpClientErrorException.NotAcceptable ex) {
            throw new HttpServiceException(ex.getStatusCode().value(), new Error(ErrorCode.FILE_NOT_FOUND, ex.getMessage()));
        } catch (final Exception ex) {
            log.warn("Request to Databus Storage service failed.");
            throw new ServiceUnavailableException(uri.toString(), ex);
        }
    }

    private HttpServiceException handleException(final Exception ex, final URI uri) throws ServiceUnavailableException {
        if (ex instanceof HttpStatusCodeException) {
            final HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) ex;
            try {
                return new HttpServiceException(
                    httpStatusCodeException.getStatusCode().value(),
                    errorByHttpStatusCodeException(httpStatusCodeException)
                );
            } catch (IOException ex1) {
                log.warn("Error to parse error response from Multiple Tracking service", ex);
                throw new ServiceUnavailableException(uri.toString(), ex);
            }
        } else {
            log.warn("Request to Multiple Tracking service failed.", ex);
            throw new ServiceUnavailableException(uri.toString(), ex);
        }
    }

    private Error errorByHttpStatusCodeException(final HttpStatusCodeException ex) throws IOException {
        final byte[] byteArray = ex.getResponseBodyAsByteArray();
        if (byteArray.length > 0) {
            return mapper.readValue(byteArray, Error.class);
        } else {
            return new Error(SYSTEM_ERROR, ex.getMessage());
        }
    }
}
