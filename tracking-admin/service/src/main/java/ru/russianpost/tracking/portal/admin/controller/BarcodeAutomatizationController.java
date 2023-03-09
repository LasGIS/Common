/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller;

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import ru.russianpost.tracking.portal.admin.ConfigHolder;
import ru.russianpost.tracking.portal.admin.controller.exception.BadQueryException;
import ru.russianpost.tracking.portal.admin.controller.exception.BadQueryRedirectException;
import ru.russianpost.tracking.portal.admin.controller.writer.CsvResponse;
import ru.russianpost.tracking.portal.admin.exception.BarcodeAutomatizationServiceException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserHistoryEvent;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserHistoryEventType;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserRolesUpdatedEventArgs;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.AllocationSearchResult;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.BarcodeAllocateResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.BarcodeAllocateUiResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.BarcodesAllocateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.BarcodesAllocateUiRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.SetUserRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.SetUserUiRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.SuitableRangeResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.SuitableRangeUiResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.UFPS;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.UFPSUser;
import ru.russianpost.tracking.portal.admin.model.errors.ServiceError;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;
import ru.russianpost.tracking.portal.admin.model.ui.AdminUser;
import ru.russianpost.tracking.portal.admin.model.ui.BarcodePlainRecord;
import ru.russianpost.tracking.portal.admin.repository.ServiceUserDao;
import ru.russianpost.tracking.portal.admin.service.barcode.automatization.BarcodeAutomatizationService;
import ru.russianpost.tracking.portal.admin.service.barcode.generator.BarcodeGenerator;
import ru.russianpost.tracking.portal.admin.service.users.AdminServiceUserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.util.StringUtils.hasText;
import static ru.russianpost.tracking.portal.admin.security.Role.ROLE_BARCODE_AUTOMATIZATION_USER;
import static ru.russianpost.tracking.portal.admin.service.ServiceName.AUTOMATIZATION_SERVICE;


/**
 * Service controller for barcode automatization service.
 *
 * @author MKitchenko
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/barcode-automatization")
@RequiredArgsConstructor
public class BarcodeAutomatizationController extends BaseController {

    private static final Gson GSON = new Gson();

    private static final Integer DEFAULT_FIND_COUNT = 10;
    private static final String BAD_QUERY_ERROR_MSG = "Bad query: it must contain at least 1 character";
    private static final String INVALID_INTERVAL_ERROR_MSG = "Start value should be less than end value";
    private static final String BARCODE_LIMIT_EXCEEDED_MSG = "The number of barcodes for download exceeds the limit";
    private static final String ADD_NEW_ROLE_PATTERN = "Barcode automatization: {0} added new role to {1}";
    private static final String ERROR_PROCESSING_REQUEST = MessageFormat.format(
        "Error processing request to {0}.", AUTOMATIZATION_SERVICE
    );
    private static final Integer BARCODE_LIMIT = ConfigHolder.CONFIG.getInt("barcode.automatization.barcode.limit");

    private final StopWatchFactory stopWatchFactory;
    private final BarcodeAutomatizationService barcodeAutomatizationService;
    private final AdminServiceUserService adminServiceUserService;
    private final ServiceUserDao serviceUserDao;
    private final BarcodeGenerator generator;

    /**
     * Get ufps user by user login.
     *
     * @param userLogin user login
     * @return ufps user
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @GetMapping(value = "/user/{login:.+}")
    public UFPSUser getUser(
        @PathVariable(value = "login") String userLogin
    ) throws ServiceUnavailableException {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("BarcodeAutomatizationController:getUser");
        try {
            return barcodeAutomatizationService.getUfpsUser(userLogin);
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Set ufps user.
     *
     * @param userLogin        user login
     * @param setUserUiRequest update request from ui
     * @param author           author
     * @throws ServiceUnavailableException on error during barcode provider service call
     * @throws BadQueryException           on bad query error
     */
    @PutMapping(value = "/user/{login:.+}", consumes = APPLICATION_JSON_VALUE)
    public void setUser(
        @PathVariable(value = "login") String userLogin,
        @Valid @RequestBody SetUserUiRequest setUserUiRequest,
        Principal author
    ) throws ServiceUnavailableException, BadQueryException {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("BarcodeAutomatizationController:setUser");
        try {
            if (!Objects.equals(setUserUiRequest.getUser().getLogin(), userLogin)) {
                throw new BadQueryException("User login in url path doesn't match to user login in request content");
            }

            final ServiceUser serviceUser = adminServiceUserService.resolve(userLogin).orElse(null);
            if (serviceUser == null) {
                throw new BadQueryException("User with username '" + userLogin + "' does not exist");
            }

            String authorityString = serviceUser.getAuthorityString();
            final List<String> oldRoles = Arrays.asList(authorityString.split(","));

            if (!oldRoles.contains(ROLE_BARCODE_AUTOMATIZATION_USER.name())) {
                authorityString = String.join(",", authorityString, ROLE_BARCODE_AUTOMATIZATION_USER.name());
                serviceUserDao.updateAuthorityString(userLogin, authorityString);
                adminServiceUserService.logUserHistoryEvent(
                    new UserHistoryEvent(
                        userLogin,
                        null,
                        author.getName(),
                        UserHistoryEventType.USER_ROLES_UPDATED,
                        GSON.toJson(
                            new UserRolesUpdatedEventArgs(Collections.singletonList(ROLE_BARCODE_AUTOMATIZATION_USER.name()),
                                emptyList())
                        ),
                        MessageFormat.format(ADD_NEW_ROLE_PATTERN, author.getName(), userLogin)
                    )
                );
            }

            barcodeAutomatizationService.setUfpsUser(new SetUserRequest(setUserUiRequest.getUser(), author.getName(), setUserUiRequest.getComment()));
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Get ufps list.
     *
     * @return ufps list
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @GetMapping(value = "/ufps")
    public List<UFPS> getUfpsList() throws ServiceUnavailableException {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("BarcodeAutomatizationController:getUfpsList");
        try {
            return barcodeAutomatizationService.getUfpsList();
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Barcode allocation.
     *
     * @param barcodesAllocateUiRequest barcode allocation request from ui
     * @param author                    author
     * @return instance of {@link BarcodeAllocateUiResponse}
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @PostMapping(value = "/allocate", consumes = APPLICATION_JSON_VALUE)
    public BarcodeAllocateUiResponse allocate(
        @Valid @RequestBody BarcodesAllocateUiRequest barcodesAllocateUiRequest,
        Principal author
    ) throws ServiceUnavailableException {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("BarcodeAutomatizationController:allocate");
        try {
            final BarcodesAllocateRequest barcodesAllocateRequest = new BarcodesAllocateRequest(
                barcodesAllocateUiRequest.getUfpsId(),
                barcodesAllocateUiRequest.getContainerType(),
                author.getName(),
                hasText(barcodesAllocateUiRequest.getComment()) ? barcodesAllocateUiRequest.getComment() : null,
                barcodesAllocateUiRequest.getQuantity()
            );

            final BarcodeAllocateResponse barcodeAllocateResponse = barcodeAutomatizationService.allocate(barcodesAllocateRequest);

            final ServiceError error = barcodeAllocateResponse.getError();
            final String errorMessage = isNull(error) ? null : error.getMessage();

            return new BarcodeAllocateUiResponse(
                barcodeAllocateResponse.getMailType(),
                barcodeAllocateResponse.getLetterBeg(),
                barcodeAllocateResponse.getStart(),
                barcodeAllocateResponse.getEnd(),
                errorMessage
            );
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Defines free S10 range suitable for specified criteria.
     *
     * @param ufpsId        ufps id
     * @param containerType container type
     * @param quantity      number of barcodes to allocate
     * @return suitable S10 range
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @GetMapping(value = "/suitable-range")
    public SuitableRangeUiResponse getSuitableRange(
        @RequestParam(value = "ufpsId") Integer ufpsId,
        @RequestParam(value = "containerType") String containerType,
        @RequestParam(value = "quantity") Integer quantity
    ) throws ServiceUnavailableException {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("BarcodeAutomatizationController:suitableRange");
        try {
            final SuitableRangeResponse suitableRangeResponse = barcodeAutomatizationService.getSuitableRange(ufpsId, containerType, quantity);

            final ServiceError error = suitableRangeResponse.getError();

            return new SuitableRangeUiResponse(
                suitableRangeResponse.getMailType(),
                suitableRangeResponse.getLetterBeg(),
                suitableRangeResponse.getStart(),
                suitableRangeResponse.getEnd(),
//                suitableRangeResponse.getAvailableQuantity(),
                isNull(error) ? null : error.getMessage()
            );
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Get allocation search results.
     *
     * @param ufpsId ufps id
     * @param from   date from
     * @param to     date to
     * @return allocation search results
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    @GetMapping(value = "/allocation/search")
    public AllocationSearchResult allocationSearch(
        @RequestParam(value = "ufpsId") Integer ufpsId,
        @RequestParam(value = "from") String from,
        @RequestParam(value = "to") String to
    ) throws ServiceUnavailableException {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("BarcodeAutomatizationController:allocationSearch");
        try {
            return new AllocationSearchResult(barcodeAutomatizationService.getAllocationResults(ufpsId, from, to));
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Searches admin portal user by query string.
     *
     * @param query the query
     * @param count the count
     * @return list of admin portal users
     * @throws BadQueryException on query validation failure
     */
    @GetMapping(value = "/admin-portal-user")
    public List<AdminUser> adminUsers(
        @RequestParam("query") String query,
        @RequestParam(value = "count", required = false) Integer count
    ) throws BadQueryException {
        if (query.isEmpty()) {
            throw new BadQueryException(BAD_QUERY_ERROR_MSG);
        }
        final StopWatch stopWatch = stopWatchFactory
            .getStopWatch("BarcodeAutomatizationController:user");
        try {
            return this.serviceUserDao.findServiceUser(query, Optional.ofNullable(count).orElse(DEFAULT_FIND_COUNT))
                .stream()
                .map(AdminUser::new)
                .collect(Collectors.toList());
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Generate CSV file with list of S10 barcodes.
     *
     * @param prefix container of S10 barcodes
     * @param start  range start
     * @param end    range end
     * @return csv file with list of barcodes
     * @throws BadQueryRedirectException on query failure
     */
    @GetMapping(value = "/barcodes/{prefix}/{start}/{end}", produces = "text/csv;charset=utf-8")
    public CsvResponse<BarcodePlainRecord> getBarcodes(
        @PathVariable("prefix") String prefix,
        @PathVariable("start") Integer start,
        @PathVariable("end") Integer end
    ) throws BadQueryRedirectException {
        if (start > end) {
            throw new BadQueryRedirectException("INVALID_INTERVAL_ERROR", INVALID_INTERVAL_ERROR_MSG);
        } else if (end - start + 1 > BARCODE_LIMIT) {
            throw new BadQueryRedirectException("BARCODE_LIMIT_EXCEEDED", BARCODE_LIMIT_EXCEEDED_MSG);
        }
        return new CsvResponse<>(
            generator.generateS10Barcodes(prefix, start, end, "RU").stream().map(BarcodePlainRecord::new).collect(Collectors.toList()),
            null, String.format("barcodes_%s_from_%s_to_%s.csv", prefix, start, end));
    }

    /**
     * Bad query exception handler.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param ex       BadQueryException
     */
    @SneakyThrows
    @ExceptionHandler({BadQueryException.class})
    public void badQuery(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final BadQueryException ex
    ) {
        log.warn("Bad request to URI '{}': {}", request.getRequestURI(), ex.getMessage());
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
    }

    /**
     * Bad query exception handler.
     *
     * @param request HttpServletRequest
     * @param ex      BadQueryRedirectException
     * @return response with redirect and errorCode as parameter
     */
    @ExceptionHandler({BadQueryRedirectException.class})
    public ResponseEntity<byte[]> badQueryWithRedirect(
        final HttpServletRequest request,
        final BadQueryRedirectException ex
    ) {
        final Cookie[] cookies = request.getCookies();
        String rollbackUrl = Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals("rollbackUrl"))
            .map(Cookie::getValue).findFirst().orElse("/barcode-automatization/search");
        log.warn("Bad request redirect from URI '{}' to URI '{}': {}", request.getRequestURI(), rollbackUrl, ex.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/admin.html#" + rollbackUrl + "?errorCode=" + ex.getErrorCode());
        return new ResponseEntity<>(null, headers, HttpStatus.FOUND);
    }

    /**
     * Barcode automatization service exceptions handler
     *
     * @param e exception details
     * @return Error to display on frontend
     */
    @ExceptionHandler({BarcodeAutomatizationServiceException.class})
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ServiceError barcodeAutomatizationServiceError(BarcodeAutomatizationServiceException e) {
        final ServiceError error = e.getError();
        log.warn("{} Error is '{}'", ERROR_PROCESSING_REQUEST, error);
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
}
