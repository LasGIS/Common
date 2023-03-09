/*
 * Copyright 2016 Russian Post
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
import com.google.common.base.Strings;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.controller.exception.BadQueryException;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.controller.exception.NotModifiedException;
import ru.russianpost.tracking.portal.admin.controller.exception.ServiceUserNotFoundException;
import ru.russianpost.tracking.portal.admin.controller.exception.ValidationException;
import ru.russianpost.tracking.portal.admin.model.admin.UpdateUserInfoRequest;
import ru.russianpost.tracking.portal.admin.model.admin.UpdateUserRolesRequest;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserHistoryEvent;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserHistoryEventDescriptionResolver;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserHistoryEventType;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserInfo;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserInfoUpdatedEventArgs;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserRolesUpdatedEventArgs;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;
import ru.russianpost.tracking.portal.admin.model.ui.AdminUser;
import ru.russianpost.tracking.portal.admin.model.ui.HistoryEntry;
import ru.russianpost.tracking.portal.admin.model.ui.ServiceUserUI;
import ru.russianpost.tracking.portal.admin.repository.ServiceUserDao;
import ru.russianpost.tracking.portal.admin.service.users.AdminServiceUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Service controller for managing portal users.
 * @author KKiryakov
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController extends BaseController {

    private static final Gson GSON = new Gson();

    private static final Integer DEFAULT_FIND_COUNT = 10;
    private static final String BAD_QUERY_ERROR_MSG = "Bad query: it must contain at least 1 character";

    private final StopWatchFactory stopWatchFactory;

    private final ServiceUserDao serviceUserDao;
    private final AdminServiceUserService adminServiceUserService;

    /**
     * Get service user.
     * @param username the username
     * @return the service user
     * @throws ServiceUserNotFoundException if the service user is not found
     */
    @GetMapping(value = "/user/{username:.+}")
    public ServiceUserUI getUser(@PathVariable String username) {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("AdminController:getUser");
        try {
            ServiceUser serviceUser = serviceUserDao.getServiceUser(username);
            if (serviceUser == null) {
                log.debug("Service user \"{}\" not found.", username);
                stopWatch.stop("AdminController:getUser:fail");
                throw new ServiceUserNotFoundException(username);
            }
            return new ServiceUserUI(serviceUser);
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Update user.
     * @param author   the author
     * @param username username
     * @param request  updated service user
     * @param br       Binding result with validation status
     * @throws ValidationException  if validation failed
     * @throws BadRequestException  if request is invalid
     * @throws NotModifiedException if user information wasn't modified
     */
    @PutMapping(value = "/user/{username:.+}/info", consumes = APPLICATION_JSON_VALUE)
    public void updateUserInfo(
        @PathVariable String username,
        @Valid @NotNull @RequestBody UpdateUserInfoRequest request,
        BindingResult br,
        Principal author
    ) throws ValidationException, BadRequestException, NotModifiedException {
        if (br.hasErrors()) {
            throw new ValidationException(br);
        }
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("AdminController:updateUserInfo");
        try {
            final ServiceUser oldServiceUser = serviceUserDao.getServiceUser(username);
            if (oldServiceUser == null) {
                throw new BadRequestException("User with username '" + username + "' does not exist");
            }
            final UserInfo oldUserInfo = new UserInfo(oldServiceUser);
            final UserInfo userInfo = UserInfo.makeNormalized(request.getUserInfo());
            if (oldUserInfo.equals(userInfo)) {
                log.info("User information is not modified");
                throw new NotModifiedException("User information is not modified");
            } else {
                serviceUserDao.updateUserInfo(username, userInfo.getName(), userInfo.getSurname(), userInfo.getPatronymic(), userInfo.getEmail());
                adminServiceUserService.logUserHistoryEvent(new UserHistoryEvent(
                    username,
                    null,
                    author.getName(),
                    UserHistoryEventType.USER_INFORMATION_UPDATED,
                    GSON.toJson(new UserInfoUpdatedEventArgs(oldUserInfo, userInfo)),
                    request.getComment()
                ));
            }
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Update user.
     * @param author   author
     * @param username username
     * @param request  request
     * @param br       Binding result with validation status
     * @throws ValidationException  if validation failed
     * @throws BadRequestException  if request is invalid
     * @throws NotModifiedException if roles weren't modified
     */
    @PutMapping(value = "/user/{username:.+}/access", consumes = APPLICATION_JSON_VALUE)
    public void updateUserRoles(
        Principal author,
        @PathVariable String username,
        @RequestBody @Valid UpdateUserRolesRequest request,
        BindingResult br
    ) throws ValidationException, BadRequestException, NotModifiedException {
        if (br.hasErrors()) {
            throw new ValidationException(br);
        }
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("AdminController:updateUserRoles");
        try {
            final ServiceUser user = serviceUserDao.getServiceUser(username);
            if (user == null) {
                throw new BadRequestException("User with username '" + username + "' does not exist");
            }
            final String authorityString = StringUtils.arrayToCommaDelimitedString(request.getRoles());
            final List<String> newRoles = Arrays.asList(request.getRoles());
            final List<String> oldRoles = Arrays.asList(user.getAuthorityString().split(","));
            final List<String> addedRoles = newRoles.stream().filter(role -> !oldRoles.contains(role)).collect(Collectors.toList());
            final List<String> removedRoles = oldRoles.stream()
                .filter(role -> !Strings.isNullOrEmpty(role))
                .filter(role -> !newRoles.contains(role))
                .collect(Collectors.toList());
            boolean isModified = !addedRoles.isEmpty() || !removedRoles.isEmpty();
            if (!isModified) {
                log.info("User roles are not modified");
                throw new NotModifiedException("User roles are not modified");
            } else {
                serviceUserDao.updateAuthorityString(username, authorityString);
                adminServiceUserService.logUserHistoryEvent(new UserHistoryEvent(
                    username,
                    null,
                    author.getName(),
                    UserHistoryEventType.USER_ROLES_UPDATED,
                    GSON.toJson(new UserRolesUpdatedEventArgs(addedRoles, removedRoles)),
                    request.getComment()
                ));
            }
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Searches admin portal user by query string.
     * @param query the query
     * @param count the count
     * @return list of admin users
     * @throws BadQueryException on query validation failure
     */
    @GetMapping(value = "/finduser")
    public List<AdminUser> findUser(
        @RequestParam("query") String query,
        @RequestParam(value = "count", required = false) Integer count
    ) throws BadQueryException {
        if (query.isEmpty()) {
            throw new BadQueryException(BAD_QUERY_ERROR_MSG);
        }
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("AdminController:findUser");
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
     * Returns all history for specified username
     * @param username username
     * @return all history
     */
    @GetMapping(value = "/user/{username:.+}/history")
    public List<HistoryEntry> getHistory(@PathVariable String username) {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("AdminController:getHistory");
        try {
            return serviceUserDao.getUserHistory(username).stream().map(e -> {
                final ServiceUser serviceUser = serviceUserDao.getServiceUser(e.getAuthor());
                return new HistoryEntry(
                    e.getDatetime(),
                    isNull(serviceUser) ? new AdminUser(e.getAuthor()) : new AdminUser(serviceUser),
                    UserHistoryEventDescriptionResolver.buildDescription(e),
                    e.getComment()
                );
            }).collect(Collectors.toList());
        } finally {
            stopWatch.stop();
        }
    }

    /**
     * Bad query exception handler.
     * @param req HttpServletRequest
     * @param ex  BadQueryException
     */
    @ExceptionHandler({BadQueryException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = BAD_QUERY_ERROR_MSG)
    public void badQuery(HttpServletRequest req, BadQueryException ex) {
        log.info("Bad request to URI '{}': {}", req.getRequestURI(), ex.getMessage());
    }
}
