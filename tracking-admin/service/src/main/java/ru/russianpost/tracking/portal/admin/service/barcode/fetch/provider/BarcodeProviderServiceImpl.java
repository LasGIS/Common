/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;
import ru.russianpost.tracking.portal.admin.exception.BarcodeProviderServiceException;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.BarcodeProviderUser;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.Booking;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.BookingInternational;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.Container;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.DefaultContainerConfiguration;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.IndexContainerConfiguration;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.InternationalContainer;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.AuthoredConfigurationUpdateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.BookingInternalCreateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.BookingInternationalCreateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.ChangeRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.DeleteContainerRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.FileUsersCreateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.IndexMonthContainerConfiguration;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.ResetPasswordRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.UpdateContainerRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.UserCreateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.UserUpdateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BarcodeProviderHistoryRecord;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BarcodeProviderHistoryResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BarcodeProviderResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BarcodeProviderUserResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BarcodeProviderUserSearchResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BatchRegistrationResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BatchRegistrationResponseData;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BookingInternationalResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BookingsInternationalResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BookingsResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.DefaultContainerConfigurationResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.IndexContainerConfigurationResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.InternationalContainersResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.RangesResponse;
import ru.russianpost.tracking.portal.admin.model.errors.ErrorCode;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;
import ru.russianpost.tracking.portal.admin.model.ui.AdminUser;
import ru.russianpost.tracking.portal.admin.model.ui.HistoryEntry;
import ru.russianpost.tracking.portal.admin.repository.ServiceUserDao;
import ru.russianpost.tracking.portal.admin.utils.SecurityUtils;

import java.io.IOException;
import java.net.URI;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static ru.russianpost.tracking.portal.admin.service.ServiceName.BARCODE_PROVIDER_SERVICE;

/**
 * Implementation of {@link BarcodeProviderService}.
 *
 * @author KKiryakov
 */
@Service
public class BarcodeProviderServiceImpl implements BarcodeProviderService {

    private static final Logger LOG = LoggerFactory.getLogger(BarcodeProviderServiceImpl.class);

    private static final String BARCODE_PROVIDER_SERVICE_IS_UNAVAILABLE = BARCODE_PROVIDER_SERVICE + " is unavailable";
    private static final String ERROR_PARSING_MESSAGE_PATTERN = MessageFormat.format(
        "Error to parse {0} error message. '{}'", BARCODE_PROVIDER_SERVICE
    );
    private static final String HTTP_METHOD_PROCESSING_ERROR = MessageFormat.format(
        "Http method processing to {0} caused an error.", BARCODE_PROVIDER_SERVICE
    );

    private final String barcodeProviderUrl;
    private final RestTemplate restTemplate;
    private final ServiceUserDao serviceUserDao;

    private final String viewContainersUrl;
    private final String viewBookingsUrl;
    private final String deleteContainerUrl;
    private final String editContainerUrl;
    private final String defaultConfigurationUrl;
    private final String indexConfigurationUrl;
    private final String getDefaultConfigurationHistoryUrl;
    private final String getIndexConfigurationHistoryUrl;
    private final String indexMonthConfigurationUrl;
    private final String deleteIndexMonthConfigurationUrl;
    private final String userUrl;
    private final String createUsersUrl;
    private final String userFindUrl;
    private final String userWithIdUrl;
    private final String deleteBookingInternalUrl;
    private final String getBookingInternalXml;
    private final String bookingInternalUrl;
    private final String bookingInternationalUrl;
    private final String findInternationalBookingsUrl;
    private final String getInternationalContainersUrl;
    private final String findInternationalBookingsAdvancedUrl;
    private final String getBookingInternationalXml;
    private final String deleteBookingInternationalUrl;
    private final String resetUserPasswordUrl;

    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Constructor
     *
     * @param serviceUserDao     instance of {@link ServiceUserDao}
     * @param restTemplate       instance of {@link RestTemplate}
     * @param barcodeProviderUrl barcode provider url
     * @param credentialsPlain   credentials
     */
    public BarcodeProviderServiceImpl(
        ServiceUserDao serviceUserDao,
        @Qualifier("restTemplate") RestTemplate restTemplate,
        @Value("${ru.russianpost.barcode.fetch.provider.service.url}") String barcodeProviderUrl,
        @Value("${ru.russianpost.barcode.fetch.provider.service.credentials}") String credentialsPlain
    ) {
        this.serviceUserDao = serviceUserDao;
        this.restTemplate = restTemplate;
        this.barcodeProviderUrl = barcodeProviderUrl;

        httpHeaders.add(HttpHeaders.AUTHORIZATION, SecurityUtils.buildBasicAuthHeaderValue(credentialsPlain));

        /* View containers, bookings. */
        viewContainersUrl = buildUriString("/ranges/{index}");
        viewBookingsUrl = buildUriString("/ranges/{index}/{year}/{month}");
        deleteContainerUrl = buildUriString("/ranges/delete/{index}/{year}/{month}");
        editContainerUrl = buildUriString("/ranges/edit/{index}/{year}/{month}");

        /* Configuration. */
        defaultConfigurationUrl = buildUriString("/config/default");
        indexConfigurationUrl = buildUriString("/config/{index}");
        getDefaultConfigurationHistoryUrl = buildUriString("/config/history/default");
        getIndexConfigurationHistoryUrl = buildUriString("/config/history/{index}");
        indexMonthConfigurationUrl = buildUriString("/config/{index}/{year}/{month}");
        deleteIndexMonthConfigurationUrl = buildUriString("/config/delete/{index}/{year}/{month}");

        /* User management. */
        userUrl = buildUriString("/auth/user");
        createUsersUrl = buildUriString("/auth/user/batch");
        userWithIdUrl = buildUriString("/auth/user/{id}");
        userFindUrl = buildUriString("/auth/user/find?query={query}&count={count}");
        resetUserPasswordUrl = buildUriString("/auth/user/resetpasswd/{userId}");

        /* Bookings management /bookings. */
        bookingInternalUrl = buildUriString("/bookings/internal");
        bookingInternationalUrl = buildUriString("/bookings/international");
        findInternationalBookingsUrl = buildUriString(
            "/bookings/international/find?bookingStart={start}&bookingEnd={end}&mailType={mailType}&letterBeg={letterBeg}"
        );
        findInternationalBookingsAdvancedUrl = buildUriString("/bookings/international/find/advanced");
        getBookingInternalXml = buildUriString("/bookings/internal/xml/{id}");
        getBookingInternationalXml = buildUriString("/bookings/international/xml/{id}");
        deleteBookingInternalUrl = buildUriString("/bookings/internal/delete/{id}");
        deleteBookingInternationalUrl = buildUriString("/bookings/international/delete/{id}");

        getInternationalContainersUrl = buildUriString("/s10-containers");
    }

    private String buildUriString(String path) {
        return UriComponentsBuilder.fromUriString(barcodeProviderUrl).path(path).build().toUriString();
    }

    @Override
    public List<Container> getRanges(int index) throws ServiceUnavailableException {
        return processGet(viewContainersUrl, RangesResponse.class, index).getRanges();
    }

    @Override
    public List<Booking> getBookings(int index, int year, int month) throws ServiceUnavailableException {
        return processGet(viewBookingsUrl, BookingsResponse.class, index, year, month).getRanges();
    }

    @Override
    public void deleteContainer(int index, int year, int month, DeleteContainerRequest request) throws ServiceUnavailableException {
        processPost(deleteContainerUrl, request, index, year, month);
    }

    @Override
    public void updateContainer(int index, int year, int month, UpdateContainerRequest request) throws ServiceUnavailableException {
        processPut(editContainerUrl, request, index, year, month);
    }

    @Override
    public DefaultContainerConfiguration getDefaultConfiguration() throws ServiceUnavailableException {
        return processGet(defaultConfigurationUrl, DefaultContainerConfigurationResponse.class).getConfig();
    }

    @Override
    public IndexContainerConfiguration getIndexConfiguration(int index) throws ServiceUnavailableException {
        return processGet(indexConfigurationUrl, IndexContainerConfigurationResponse.class, index).getConfig();
    }

    @Override
    public void updateDefaultConfiguration(DefaultContainerConfiguration configuration, String author, String comment)
        throws ServiceUnavailableException {
        processPut(defaultConfigurationUrl, new AuthoredConfigurationUpdateRequest<>(configuration, author, comment));
    }

    @Override
    public void updateIndexConfiguration(int index, IndexContainerConfiguration configuration, String author, String comment)
        throws ServiceUnavailableException {
        processPut(indexConfigurationUrl, new AuthoredConfigurationUpdateRequest<>(configuration, author, comment), index);
    }

    @Override
    public List<HistoryEntry> getDefaultConfigurationHistory() throws ServiceUnavailableException {
        return processGet(getDefaultConfigurationHistoryUrl, BarcodeProviderHistoryResponse.class).getHistory()
            .stream()
            .map(this::convertHistoryRecord)
            .collect(toList());
    }

    @Override
    public List<HistoryEntry> getIndexConfigurationHistory(int index) throws ServiceUnavailableException {
        return processGet(getIndexConfigurationHistoryUrl, BarcodeProviderHistoryResponse.class, index).getHistory()
            .stream()
            .map(this::convertHistoryRecord)
            .collect(toList());
    }

    @Override
    public void addIndexMonthConfiguration(
        int index, int year, int month, IndexMonthContainerConfiguration configuration, String author, String comment
    ) throws ServiceUnavailableException {
        processPost(indexMonthConfigurationUrl, new AuthoredConfigurationUpdateRequest<>(configuration, author, comment), index, year, month);
    }

    @Override
    public void updateIndexMonthConfiguration(
        int index, int year, int month, IndexMonthContainerConfiguration configuration, String author, String comment
    ) throws ServiceUnavailableException {
        processPut(indexMonthConfigurationUrl, new AuthoredConfigurationUpdateRequest<>(configuration, author, comment), index, year, month);
    }

    @Override
    public void deleteIndexMonthConfiguration(int index, int year, int month, ChangeRequest request) throws ServiceUnavailableException {
        processPost(deleteIndexMonthConfigurationUrl, request, index, year, month);
    }

    @Override
    public BarcodeProviderUser createUser(UserCreateRequest request) throws ServiceUnavailableException {
        return processPostForEntity(userUrl, request, BarcodeProviderUserResponse.class).getUser();
    }

    @Override
    public Map<Integer, BatchRegistrationResponseData> createUsers(FileUsersCreateRequest request) throws ServiceUnavailableException {
        return processPostForEntity(createUsersUrl, request, BatchRegistrationResponse.class).getUsers();
    }

    @Override
    public void updateUser(long id, UserUpdateRequest request) throws ServiceUnavailableException {
        processPut(userWithIdUrl, request, id);
    }

    @Override
    public BarcodeProviderUser getUser(long id) throws ServiceUnavailableException {
        return processGet(userWithIdUrl, BarcodeProviderUserResponse.class, id).getUser();
    }

    @Override
    public List<BarcodeProviderUser> findUser(String query, int count) throws ServiceUnavailableException {
        return processGet(userFindUrl, BarcodeProviderUserSearchResponse.class, query, count).getUsers();
    }

    @Override
    public ResponseEntity<byte[]> getBookingXml(long id) {
        try {
            return restTemplate.exchange(getBookingInternalXml, HttpMethod.GET, new HttpEntity<>(httpHeaders), byte[].class, id);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new InternalServerErrorException(getBookingInternalXml, e);
            }
            return buildError(e);
        } catch (RestClientException e) {
            LOG.warn(BARCODE_PROVIDER_SERVICE_IS_UNAVAILABLE, e);
            return buildRedirectErrorResponse(ErrorCode.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            LOG.warn("Unexpected error during call BarcodeProviderServiceImpl.getBookingInternalXml", e);
            return buildRedirectErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public void deleteBooking(long id, ChangeRequest request) throws ServiceUnavailableException {
        processPost(deleteBookingInternalUrl, request, id);
    }

    @Override
    public List<BookingInternational> findBookingsInternational(
        int start, int end, String mailType, String letterBeg
    ) throws ServiceUnavailableException {
        return processGet(findInternationalBookingsUrl, BookingsInternationalResponse.class, start, end, mailType, letterBeg).getBookings();
    }

    @Override
    public List<BookingInternational> findBookingsInternationalAdvanced(
        String index, String inn, String start, String end, String mailType, String letterBeg
    ) throws ServiceUnavailableException {
        final MultiValueMap<String, String> param = new LinkedMultiValueMap<>();

        addNonNullParam(param, "postalCode", index);
        addNonNullParam(param, "inn", inn);
        addNonNullParam(param, "bookingStart", start);
        addNonNullParam(param, "bookingEnd", end);
        addNonNullParam(param, "mailType", mailType);
        addNonNullParam(param, "letterBeg", letterBeg);

        final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(findInternationalBookingsAdvancedUrl);
        final URI uri = uriComponentsBuilder.queryParams(param).build().encode().toUri();
        return processHttpMethod(HttpMethod.GET, uri.toString(), null, BookingsInternationalResponse.class)
            .getBookings();
    }

    private void addNonNullParam(MultiValueMap<String, String> param, String key, String value) {
        if (value != null) {
            param.add(key, value);
        }
    }

    @Override
    public ResponseEntity<byte[]> getBookingInternationalXml(long id) {
        try {
            return restTemplate.exchange(getBookingInternationalXml, HttpMethod.GET, new HttpEntity<>(httpHeaders), byte[].class, id);
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new InternalServerErrorException(getBookingInternationalXml, e);
            }
            return buildError(e);
        } catch (RestClientException e) {
            LOG.warn(BARCODE_PROVIDER_SERVICE_IS_UNAVAILABLE, e);
            return buildRedirectErrorResponse(ErrorCode.SERVICE_UNAVAILABLE);
        } catch (Exception e) {
            LOG.warn("Unexpected error during call BarcodeProviderServiceImpl.getBookingInternationalXml", e);
            return buildRedirectErrorResponse(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public void createBookingInternal(BookingInternalCreateRequest request) throws ServiceUnavailableException {
        processPost(bookingInternalUrl, request);
    }

    @Override
    public BookingInternational createBookingInternational(BookingInternationalCreateRequest request) throws ServiceUnavailableException {
        return processPostForEntity(bookingInternationalUrl, request, BookingInternationalResponse.class).getBooking();
    }

    @Override
    public void deleteBookingInternational(long id, ChangeRequest request) throws ServiceUnavailableException {
        processPost(deleteBookingInternationalUrl, request, id);
    }

    @Override
    public Integer getAllocationSize(int index) throws ServiceUnavailableException {
        IndexContainerConfiguration indexConfiguration = this.getIndexConfiguration(index);
        if (indexConfiguration != null && indexConfiguration.getAllocationSize() != null) {
            return indexConfiguration.getAllocationSize();
        } else {
            return getDefaultConfiguration().getAllocationSize();
        }
    }

    @Override
    public void resetUserPassword(String userId, ResetPasswordRequest request) throws ServiceUnavailableException {
        processPost(resetUserPasswordUrl, request, userId);
    }

    @Override
    public List<InternationalContainer> getInternationalContainers() throws ServiceUnavailableException {
        return processGet(getInternationalContainersUrl, InternationalContainersResponse.class).getContainers();
    }

    private ResponseEntity<byte[]> buildError(HttpStatusCodeException ex) {
        try {
            final BarcodeProviderResponse resp = mapper.readValue(ex.getResponseBodyAsByteArray(), BarcodeProviderResponse.class);
            return buildRedirectErrorResponse(resp.getError().getCode());
        } catch (IOException e1) {
            LOG.warn(ERROR_PARSING_MESSAGE_PATTERN, e1.getMessage());
            return buildRedirectErrorResponse(ErrorCode.SERVICE_UNAVAILABLE);
        }
    }

    private ResponseEntity<byte[]> buildRedirectErrorResponse(ErrorCode errorCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/admin.html#/barcode-provider/view/internal?errorCode=" + errorCode);
        return new ResponseEntity<>(null, headers, HttpStatus.FOUND);
    }

    private HistoryEntry convertHistoryRecord(BarcodeProviderHistoryRecord record) {
        final ServiceUser serviceUser = serviceUserDao.getServiceUser(record.getAuthor().getUsername());
        AdminUser author = (serviceUser != null) ? new AdminUser(serviceUser) : new AdminUser(record.getAuthor().getUsername());
        return new HistoryEntry(record.getDatetime(), author, record.getDescription(), record.getComment());
    }

    private <T extends BarcodeProviderResponse> T processGet(final String url, final Class<T> responseType, final Object... urlVariables)
        throws ServiceUnavailableException {
        return processHttpMethod(HttpMethod.GET, url, null, responseType, urlVariables);
    }

    private void processPut(final String url, final Object requestBody, final Object... urlVariables) throws ServiceUnavailableException {
        processHttpMethod(HttpMethod.PUT, url, requestBody, BarcodeProviderResponse.class, urlVariables);
    }

    private void processPost(final String url, final Object requestBody, final Object... urlVariables) throws ServiceUnavailableException {
        processHttpMethod(HttpMethod.POST, url, requestBody, BarcodeProviderResponse.class, urlVariables);
    }

    private <T extends BarcodeProviderResponse> T processPostForEntity(
        final String url,
        final Object requestBody,
        final Class<T> responseType,
        final Object... urlVariables
    ) throws ServiceUnavailableException {
        return processHttpMethod(HttpMethod.POST, url, requestBody, responseType, urlVariables);
    }

    private <T extends BarcodeProviderResponse> T processHttpMethod(
        HttpMethod httpMethod,
        final String url,
        final Object requestBody,
        final Class<T> responseType,
        final Object... urlVariables
    ) throws ServiceUnavailableException {
        try {
            final ResponseEntity<T> response = restTemplate.exchange(
                url,
                httpMethod,
                new HttpEntity<>(requestBody, httpHeaders),
                responseType,
                urlVariables
            );
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new InternalServerErrorException(response.getBody().getError().getMessage());
            }
            return response.getBody();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new InternalServerErrorException(url, e);
            }
            try {
                final BarcodeProviderResponse resp = mapper.readValue(e.getResponseBodyAsByteArray(), BarcodeProviderResponse.class);
                throw new BarcodeProviderServiceException(resp.getError());
            } catch (IOException e1) {
                LOG.warn(ERROR_PARSING_MESSAGE_PATTERN, e1.getMessage());
                throw new ServiceUnavailableException(new UriTemplate(url).expand(urlVariables).toString(), e);
            }
        } catch (RestClientException e) {
            LOG.warn(BARCODE_PROVIDER_SERVICE_IS_UNAVAILABLE, e);
            throw new ServiceUnavailableException(new UriTemplate(url).expand(urlVariables).toString(), e);
        } catch (Exception e) {
            LOG.warn(HTTP_METHOD_PROCESSING_ERROR, e);
            throw new ServiceUnavailableException(new UriTemplate(url).expand(urlVariables).toString(), e);
        }
    }
}
