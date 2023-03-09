/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.russianpost.tracking.portal.admin.config.aspect.Speed4J;
import ru.russianpost.tracking.portal.admin.controller.exception.BadQueryException;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.exception.BarcodeProviderServiceException;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.BarcodeProviderFileUser;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.BarcodeProviderUser;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.Booking;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.BookingInternational;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.Container;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.DefaultContainerConfiguration;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.IndexContainerConfiguration;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.InternationalContainer;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.BookingInternalCreateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.BookingInternationalCreateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.ChangeRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.ConfigurationUpdateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.DeleteContainerRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.FileUsersCreateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.IndexMonthContainerConfiguration;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.ResetPasswordRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.UpdateContainerRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.UserCreateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.UserUpdateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.BatchRegistrationResponseData;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.response.ContainerDetails;
import ru.russianpost.tracking.portal.admin.model.errors.Error;
import ru.russianpost.tracking.portal.admin.model.ui.HistoryEntry;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.BarcodeProviderService;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.FileType;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.make.FileMaker;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.make.FileMakerFactory;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.parse.FileParserFactory;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.validation.ValidationError;
import ru.russianpost.tracking.portal.admin.service.barcode.fetch.provider.file.validation.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.russianpost.tracking.portal.admin.service.ServiceName.BARCODE_PROVIDER_SERVICE;


/**
 * Service controller for managing configuration of barcode fetch provider.
 *
 * @author KKiryakov
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/barcode-provider")
public class BarcodeProviderController extends BaseController {

    private static final Integer FIND_USER_DEFAULT_COUNT = 10;
    private static final int FIND_USER_MIN_QUERY_LENGTH = 4;
    private static final String FIND_USER_BAD_QUERY_ERROR_MSG = "Bad query: it must contain at least " + FIND_USER_MIN_QUERY_LENGTH + " characters";
    private static final String ERROR_PROCESSING_REQUEST = MessageFormat.format(
        "Error processing request to {0}.", BARCODE_PROVIDER_SERVICE
    );

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9_.-]{3,30}$";
    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$";
    private static final int RANGE_MAX_VALUE_INTERNAL = 99999;
    private static final int RANGE_MAX_VALUE_INTERNATIONAL = 99999999;

    private final BarcodeProviderService service;
    private final FileParserFactory fileParserFactory;
    private final FileMakerFactory fileMakerFactory;
    private final Validator fileUsersValidator;

    /**
     * Get containers by index.
     *
     * @param index index
     * @return list of containers
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @GetMapping(value = "/view/{index:\\d{6}}")
    public List<Container> getContainers(@PathVariable int index) throws ServiceUnavailableException {
        return service.getRanges(index);
    }

    /**
     * Get bookings by index and month.
     *
     * @param index index
     * @param year  year
     * @param month month (from 1 to 12)
     * @return list of bookings
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @GetMapping(value = "/view/{index:\\d{6}}//{year:\\d+}/{month:[1-9]|1[0-2]}")
    public List<Booking> getBookingsInternal(@PathVariable int index, @PathVariable int year, @PathVariable int month)
        throws ServiceUnavailableException {
        return service.getBookings(index, year, month);
    }

    /**
     * Deletes range container
     *
     * @param index   index
     * @param year    year
     * @param month   month (from 1 to 12)
     * @param request delete request
     * @param author  author
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @DeleteMapping(value = "/range/{index:\\d{6}}//{year:\\d+}/{month:[1-9]|1[0-2]}")
    public void deleteContainer(
        @PathVariable int index,
        @PathVariable int year,
        @PathVariable int month,
        @Valid @RequestBody DeleteContainerRequest request,
        Principal author
    ) throws ServiceUnavailableException {
        request.setAuthor(author.getName());
        service.deleteContainer(index, year, month, request);
    }

    /**
     * Deletes range container
     *
     * @param index   index
     * @param year    year
     * @param month   month (from 1 to 12)
     * @param request update request
     * @param author  author
     * @throws ServiceUnavailableException on error during barcode provider service call
     * @throws BadRequestException         on bad request
     */
    @Speed4J
    @PutMapping(value = "/range/{index:\\d{6}}//{year:\\d+}/{month:[1-9]|1[0-2]}")
    public void editContainer(
        @PathVariable int index,
        @PathVariable int year,
        @PathVariable int month,
        @Valid @RequestBody UpdateContainerRequest request,
        Principal author
    ) throws ServiceUnavailableException, BadRequestException {
        final Integer allocationSize = service.getAllocationSize(index);
        int range = request.getMax() - request.getMin();
        if (range <= 0) {
            throw new BadRequestException("Max value should be greater than min value");
        }
        if (allocationSize >= range) {
            throw new BadRequestException("Allocation size should be lower than container's range");
        }
        request.setAuthor(author.getName());
        service.updateContainer(index, year, month, request);
    }

    /**
     * Get default configuration
     *
     * @return default configuration
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @GetMapping(value = "/config/default")
    public DefaultContainerConfiguration getDefaultConfiguration() throws ServiceUnavailableException {
        return service.getDefaultConfiguration();
    }

    /**
     * Get configuration for specified index
     *
     * @param index index
     * @return configuration index configuration
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @GetMapping(value = "/config/{index:\\d{6}}")
    public IndexContainerConfiguration getIndexConfiguration(@PathVariable int index) throws ServiceUnavailableException {
        return service.getIndexConfiguration(index);
    }

    /**
     * Get container details (allocation size + bookings)
     *
     * @param index index
     * @param year  year
     * @param month month (from 1 to 12)
     * @return container details (allocation size + bookings)
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @GetMapping(value = "/range/{index:\\d{6}}//{year:\\d+}/{month:[1-9]|1[0-2]}/container-details")
    public ContainerDetails getContainerDetails(
        @PathVariable int index,
        @PathVariable int year,
        @PathVariable int month
    ) throws ServiceUnavailableException {
        return new ContainerDetails(service.getAllocationSize(index), service.getBookings(index, year, month));
    }

    /**
     * Update default configuration.
     *
     * @param updateRequest update request
     * @param author        author
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @PutMapping(value = "/config/default", consumes = APPLICATION_JSON_VALUE)
    public void updateDefaultConfiguration(
        @Valid @RequestBody ConfigurationUpdateRequest<DefaultContainerConfiguration> updateRequest,
        Principal author
    ) throws ServiceUnavailableException {
        service.updateDefaultConfiguration(updateRequest.getConfiguration(), author.getName(), updateRequest.getComment());
    }

    /**
     * Update index configuration
     *
     * @param index         index
     * @param updateRequest update request
     * @param author        author
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @PutMapping(value = "/config/{index:\\d{6}}", consumes = APPLICATION_JSON_VALUE)
    public void updateIndexConfiguration(
        @PathVariable int index,
        @Valid @RequestBody ConfigurationUpdateRequest<IndexContainerConfiguration> updateRequest,
        Principal author
    ) throws ServiceUnavailableException {
        service.updateIndexConfiguration(index, updateRequest.getConfiguration(), author.getName(), updateRequest.getComment());
    }

    /**
     * Get default configuration history
     *
     * @return default configuration history
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @GetMapping(value = "/config/history/default")
    public List<HistoryEntry> getDefaultConfigurationHistory() throws ServiceUnavailableException {
        return service.getDefaultConfigurationHistory();
    }

    /**
     * Get index configuration history
     *
     * @param index index
     * @return index configuration history
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @GetMapping(value = "/config/history/{index:\\d{6}}")
    public List<HistoryEntry> getIndexConfigurationHistory(@PathVariable int index) throws ServiceUnavailableException {
        return service.getIndexConfigurationHistory(index);
    }


    /**
     * Defines new index configuration for specific month
     *
     * @param index   index
     * @param year    year
     * @param month   month (from 1 to 12)
     * @param request update request
     * @param author  author
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @PostMapping(
        value = "/config/{index:\\d{6}}//{year:\\d{4}}/{month:[1-9]|1[0-2]}",
        consumes = APPLICATION_JSON_VALUE
    )
    public void addIndexMonthConfiguration(
        @PathVariable int index,
        @PathVariable int year,
        @PathVariable int month,
        @Valid @RequestBody ConfigurationUpdateRequest<IndexMonthContainerConfiguration> request,
        Principal author
    ) throws ServiceUnavailableException {
        service.addIndexMonthConfiguration(index, year, month, request.getConfiguration(), author.getName(), request.getComment());
    }

    /**
     * Updates index configuration of specific month
     *
     * @param index   index
     * @param year    year
     * @param month   month (from 1 to 12)
     * @param request update request
     * @param author  author
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @PutMapping(
        value = "/config/{index:\\d{6}}//{year:\\d{4}}/{month:[1-9]|1[0-2]}",
        consumes = APPLICATION_JSON_VALUE
    )
    public void updateIndexMonthConfiguration(
        @PathVariable int index,
        @PathVariable int year,
        @PathVariable int month,
        @Valid @RequestBody ConfigurationUpdateRequest<IndexMonthContainerConfiguration> request,
        Principal author
    ) throws ServiceUnavailableException {
        service.updateIndexMonthConfiguration(index, year, month, request.getConfiguration(), author.getName(), request.getComment());
    }

    /**
     * Deletes index configuration of specific month
     *
     * @param index   index
     * @param year    year
     * @param month   month (from 1 to 12)
     * @param request update request
     * @param author  author
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @DeleteMapping(
        value = "/config/{index:\\d{6}}//{year:\\d{4}}/{month:[1-9]|1[0-2]}",
        consumes = APPLICATION_JSON_VALUE
    )
    public void deleteIndexMonthConfiguration(
        @PathVariable int index,
        @PathVariable int year,
        @PathVariable int month,
        @Valid @RequestBody ChangeRequest request,
        Principal author
    ) throws ServiceUnavailableException {
        request.setAuthor(author.getName());
        service.deleteIndexMonthConfiguration(index, year, month, request);
    }

    /**
     * Searches barcode provider users
     *
     * @param query query (substring of username/inn/companyName)
     * @param count limit of results
     * @return list of suggestions (partially filled users)
     * @throws BadQueryException           on too short query string
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @GetMapping(value = "/user/find")
    public List<BarcodeProviderUser> findUser(
        @RequestParam("query") String query,
        @RequestParam(value = "count", required = false) Integer count
    ) throws BadQueryException, ServiceUnavailableException {
        if (query.length() < FIND_USER_MIN_QUERY_LENGTH) {
            throw new BadQueryException(FIND_USER_BAD_QUERY_ERROR_MSG);
        }
        return service.findUser(query, Optional.ofNullable(count).orElse(FIND_USER_DEFAULT_COUNT));
    }

    /**
     * Get user.
     *
     * @param id user ID
     * @return barcode provider user
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @GetMapping(value = "/user/{id:\\d+}")
    public BarcodeProviderUser getUser(@PathVariable long id) throws ServiceUnavailableException {
        return service.getUser(id);
    }

    /**
     * Update user
     *
     * @param id      user ID
     * @param request update request
     * @param author  author
     * @throws ServiceUnavailableException on error during barcode provider service call
     * @throws BadQueryException           on bad query error
     */
    @Speed4J
    @PutMapping(value = "/user/{id:\\d+}", consumes = APPLICATION_JSON_VALUE)
    public void updateUser(
        @PathVariable long id,
        @Valid @RequestBody UserUpdateRequest request,
        Principal author
    ) throws ServiceUnavailableException, BadQueryException {
        if (request.getUser().getId() != id) {
            throw new BadQueryException("User ID in url path doesn't match to user ID in request content");
        }
        request.setAuthor(author.getName());
        service.updateUser(id, request);
    }

    /**
     * Create user
     *
     * @param request update request
     * @param author  author
     * @return BarcodeProviderUser object
     * @throws ServiceUnavailableException on error during barcode provider service call
     * @throws BadQueryException           on bad query error
     */
    @Speed4J
    @PostMapping(value = "/user", consumes = APPLICATION_JSON_VALUE)
    public BarcodeProviderUser createUser(
        @Valid @RequestBody UserCreateRequest request,
        Principal author
    ) throws ServiceUnavailableException, BadQueryException {
        final String username = request.getUser().getUsername();
        final String pass = request.getUser().getPassword();
        if (username != null && !username.matches(USERNAME_REGEX)) {
            throw new BadQueryException("Username doesn't matches regex: " + USERNAME_REGEX);
        }
        if (pass != null && !pass.matches(PASSWORD_REGEX)) {
            throw new BadQueryException("Password doesn't matches regex: " + PASSWORD_REGEX);
        }
        request.setAuthor(author.getName());
        return service.createUser(request);
    }

    /**
     * Create users from file
     *
     * @param multiPart the multipart
     * @param author    author
     * @return ResponseEntity object
     * @throws ServiceUnavailableException on error during barcode provider service call
     * @throws BadQueryException           on bad query error
     */
    @Speed4J
    @PostMapping(value = "/users", consumes = MediaType.MULTIPART_FORM_DATA_VALUE + ";charset=utf-8")
    public ResponseEntity<byte[]> createUsers(
        @RequestParam("file") final MultipartFile multiPart,
        Principal author
    ) throws ServiceUnavailableException, BadQueryException {
        try {
            final FileType fileType = FileType.valueOf(multiPart.getOriginalFilename().split("\\.")[1].toUpperCase());

            final Map<Integer, BarcodeProviderFileUser> users = fileParserFactory.getFileParser(fileType).parseUsers(multiPart.getInputStream());
            final Map<Integer, List<ValidationError>> errors = fileUsersValidator.validateBarcodeProviderFileUsers(users);
            final FileMaker fileMaker = fileMakerFactory.getFileMaker(fileType);

            if (!errors.isEmpty()) {
                final byte[] file = fileMaker.makeFileWithErrors(multiPart.getInputStream(), errors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Base64.getEncoder().encode(file));
            }

            if (users.isEmpty()) {
                throw new BadQueryException("Загруженный файл не содержит пользователей");
            }

            final Map<Integer, BatchRegistrationResponseData> credentials = service.createUsers(new FileUsersCreateRequest(users, author.getName()));
            final byte[] file = fileMaker.makeFileWithCredentials(multiPart.getInputStream(), credentials);

            return ResponseEntity.status(HttpStatus.OK).body(Base64.getEncoder().encode(file));
        } catch (ServiceUnavailableException | BadQueryException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage(), e);
        }
    }

    /**
     * Reset user password by user id.
     *
     * @param userId  user id
     * @param request reset password request contains notification emails
     * @throws ServiceUnavailableException service unavailable
     */
    @Speed4J
    @PostMapping(value = "user/reset-password/{userId}")
    public void resetUserPassword(
        @PathVariable("userId") final String userId,
        @RequestBody ResetPasswordRequest request
    ) throws ServiceUnavailableException {
        service.resetUserPassword(userId, request);
    }

    /**
     * Deletes booking by its ID
     *
     * @param id      booking ID
     * @param request update request
     * @param author  author
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @DeleteMapping(value = "/booking/{id:\\d+}", consumes = APPLICATION_JSON_VALUE)
    public void deleteBooking(
        @PathVariable long id,
        @Valid @RequestBody ChangeRequest request,
        Principal author
    ) throws ServiceUnavailableException {
        request.setAuthor(author.getName());
        service.deleteBooking(id, request);
    }

    /**
     * Gets XML file for the booking.
     * <p>Note: This method proxies call to Barcode Provider service and returns its answer in case of success</p>
     *
     * @param id booking ID
     * @return the response entity
     */
    @Speed4J
    @GetMapping(value = "/booking/internal/{id:\\d+}/xml")
    @ResponseBody
    public ResponseEntity<byte[]> getBookingXml(@PathVariable long id) {
        return service.getBookingXml(id);
    }


    /**
     * Get international bookings.
     *
     * @param start     start
     * @param mailType  mailType
     * @param letterBeg letterBeg
     * @param end       end
     * @return list of bookings
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @GetMapping(value = "/booking/international/find")
    public List<BookingInternational> getBookingsInternational(
        @RequestParam(value = "start") Integer start,
        @RequestParam(value = "mailType") String mailType,
        @RequestParam(value = "letterBeg") String letterBeg,
        @RequestParam(value = "end", required = false) Integer end
    ) throws ServiceUnavailableException {
        return service.findBookingsInternational(start, Optional.ofNullable(end).orElse(start), mailType, letterBeg);
    }

    /**
     * Get international bookings advanced.
     *
     * @param postalIndex postal postalIndex
     * @param inn         inn
     * @param start       start
     * @param end         end
     * @param mailType    mailType
     * @param letterBeg   letterBeg
     * @return list of bookings
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @GetMapping(value = "/booking/international/find/advanced")
    public List<BookingInternational> getBookingsInternationalAdvanced(
        @RequestParam(value = "postalIndex", required = false) String postalIndex,
        @RequestParam(value = "inn", required = false) String inn,
        @RequestParam(value = "start", required = false) String start,
        @RequestParam(value = "end", required = false) String end,
        @RequestParam(value = "mailType") String mailType,
        @RequestParam(value = "letterBeg") String letterBeg
    ) throws ServiceUnavailableException {
        return service.findBookingsInternationalAdvanced(postalIndex, inn, start, end, mailType, letterBeg);
    }

    /**
     * Gets XML file for the booking.
     * <p>Note: This method proxies call to Barcode Provider service and returns its answer in case of success</p>
     *
     * @param id booking ID
     * @return the response entity
     */
    @Speed4J
    @GetMapping(value = "/booking/international/{id:\\d+}/xml")
    @ResponseBody
    public ResponseEntity<byte[]> getBookingInternationalXml(@PathVariable long id) {
        return service.getBookingInternationalXml(id);
    }

    /**
     * Create internal booking
     *
     * @param request update request
     * @param author  author
     * @throws ServiceUnavailableException on error during barcode provider service call
     * @throws BadQueryException           on bad query error
     */
    @Speed4J
    @PostMapping(value = "/booking/internal", consumes = APPLICATION_JSON_VALUE)
    public void createBookingInternal(
        @Valid @RequestBody BookingInternalCreateRequest request,
        Principal author
    ) throws ServiceUnavailableException, BadQueryException {
        if (RANGE_MAX_VALUE_INTERNAL < (request.getStartNumber() + request.getCount() - 1)) {
            throw new BadQueryException("The booking does not fit in the range");
        }
        request.setAuthor(author.getName());
        service.createBookingInternal(request);
    }

    /**
     * Create international booking
     *
     * @param request update request
     * @param author  author
     * @return Booking international
     * @throws ServiceUnavailableException on error during barcode provider service call
     * @throws BadQueryException           on bad query error
     */
    @Speed4J
    @PostMapping(value = "/booking/international", consumes = APPLICATION_JSON_VALUE)
    public BookingInternational createBookingInternational(
        @Valid @RequestBody BookingInternationalCreateRequest request,
        Principal author
    ) throws ServiceUnavailableException, BadQueryException {
        if (
            request.getStartNumber() != null
                && RANGE_MAX_VALUE_INTERNATIONAL < (request.getStartNumber() + request.getCount() - 1)
        ) {
            throw new BadQueryException("The booking does not fit in the range");
        }
        if (request.getMailType().equals("U") && !request.getLetterBeg().equals("A")) {
            throw new BadQueryException("Invalid letter beg for U-type barcodes. Only \"A\" is possible");
        }
        request.setAuthor(author.getName());
        return service.createBookingInternational(request);
    }

    /**
     * Deletes international booking by its ID
     *
     * @param id      booking ID
     * @param request update request
     * @param author  author
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @Speed4J
    @DeleteMapping(value = "/booking/international/{id:\\d+}", consumes = APPLICATION_JSON_VALUE)
    public void deleteBookingInternational(
        @PathVariable long id,
        @Valid @RequestBody ChangeRequest request,
        Principal author
    ) throws ServiceUnavailableException {
        request.setAuthor(author.getName());
        service.deleteBookingInternational(id, request);
    }

    /**
     * Get all international containers
     *
     * @return all international containers (S10)
     * @throws ServiceUnavailableException on error during Get all international containers call
     */
    @Speed4J
    @GetMapping(value = "/international-containers")
    public List<InternationalContainer> getInternationalContainers() throws ServiceUnavailableException {
        return service.getInternationalContainers();
    }

    /**
     * Bad query exception handler.
     *
     * @param req HttpServletRequest
     * @param ex  BadQueryException
     * @return Error to display on frontend
     */
    @ExceptionHandler({BadQueryException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Error barcodeProviderServiceError(HttpServletRequest req, BadQueryException ex) {
        log.info("Bad request to URI '{}': {}", req.getRequestURI(), ex.getMessage());
        final Error error = new Error();
        error.setMessage(ex.getMessage());
        return error;
    }

    /**
     * Barcode provider service exceptions handler
     *
     * @param e exception details
     * @return Error to display on frontend
     */
    @ExceptionHandler({BarcodeProviderServiceException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public Error barcodeProviderServiceError(BarcodeProviderServiceException e) {
        log.warn("{} Error is '{}'", ERROR_PROCESSING_REQUEST, e.getError());
        final Error error = e.getError();
        error.setMessage(resolveErrorMessage(e.getError()));
        return error;
    }

    /**
     * Common external service exceptions handler
     *
     * @param e exception details
     */
    @ExceptionHandler({ServiceUnavailableException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @Override
    public void externalServiceError(ServiceUnavailableException e) {
        log.warn(ERROR_PROCESSING_REQUEST, e);
    }

    private String resolveErrorMessage(Error error) {
        final StringBuilder sb = new StringBuilder("Сервис выдачи ШПИ вернул ошибку.\n");
        switch (error.getCode()) {
            case VALIDATION_ERROR:
                sb.append("Ошибка валидации: Неверный формат поля - ");
                sb.append(error.getValidationErrors().get(0).getMessage().replaceFirst(".+:", "").trim());
                break;
            case CONFIG_MONTH_ALREADY_EXISTS:
                sb.append("Конфигурация для данного месяца уже создана");
                break;
            case CONFIG_MONTH_DOES_NOT_EXIST:
                sb.append("Конфигурация для данного месяца была удалена");
                break;
            case CONFIG_RANGE_CONFLICT:
                sb.append("Конфликт диапазонов: указанный в новой конфигурации диапазон не совпадает с уже существующим диапазоном текущего месяца");
                break;
            case USER_ALREADY_EXISTS:
                sb.append("Пользователь с таким логином уже существует");
                break;
            case USER_DOES_NOT_EXIST:
                sb.append("Пользователя с таким ID не существует");
                break;
            case USERNAME_MISMATCH:
                sb.append("Имя пользователя не может быть изменено");
                break;
            case BOOKING_DOES_NOT_EXIST:
                sb.append("Указанная выдача не существует");
                break;
            case BOOKING_XML_NOT_FOUND:
                sb.append("XML для данной выдачи не найден");
                break;
            case BOOKING_DELETION_NOT_ALLOWED:
                sb.append("Удаление данной выдачи запрещено");
                break;
            case CONTAINER_DOES_NOT_EXIST:
                sb.append("Контейнер не существует");
                break;
            case BOOKING_CONFLICT:
            case CONTAINER_INTERSECTION_CONFLICT:
                sb.append(error.getMessage());
                break;
            default:
                sb.append(error.getCode()).append(": ").append(error.getMessage());
        }
        return sb.toString();
    }
}
