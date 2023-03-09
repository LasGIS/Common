/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.controller.exception.BadAccessTypeException;
import ru.russianpost.tracking.portal.admin.controller.exception.ValidationException;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;
import ru.russianpost.tracking.portal.admin.model.ui.AdminUser;
import ru.russianpost.tracking.portal.admin.model.ui.CompanyInfoEdition;
import ru.russianpost.tracking.portal.admin.model.ui.Counters;
import ru.russianpost.tracking.portal.admin.model.ui.HistoryEntry;
import ru.russianpost.tracking.portal.admin.model.ui.PostUser;
import ru.russianpost.tracking.portal.admin.model.ui.ProfilePage;
import ru.russianpost.tracking.portal.admin.service.UserInfoPopulationService;
import ru.russianpost.tracking.portal.admin.service.exception.AccessTypeAlreadySetException;
import ru.russianpost.tracking.portal.admin.service.exception.ProfileNotFoundServiceException;
import ru.russianpost.tracking.portal.admin.service.tracking.ProfileCompanyManagementService;
import ru.russianpost.tracking.portal.admin.service.tracking.ProfileHistoryService;
import ru.russianpost.tracking.portal.admin.service.tracking.ProfileManagementService;
import ru.russianpost.tracking.portal.admin.service.tracking.ProfileStatService;
import ru.russianpost.tracking.portal.admin.service.users.AdminServiceUserService;
import ru.russianpost.tracking.portal.admin.service.util.UserInfoCompletion;
import ru.russianpost.tracking.web.model.admin.AdminPageProfileCounters;
import ru.russianpost.tracking.web.model.attributes.FcParams;
import ru.russianpost.tracking.web.model.attributes.ProtocolParams;
import ru.russianpost.tracking.web.model.attributes.Rtm34Params;
import ru.russianpost.tracking.web.model.core.AccessType;
import ru.russianpost.tracking.web.model.core.ProfileClientType;
import ru.russianpost.tracking.web.model.portal.PortalCompanyEdition;
import ru.russianpost.tracking.web.model.portal.PortalProfileHistoryEvent;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Profile controller.
 * @author Roman Prokhorov
 * @version 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController extends BaseController {

    /**
     * Type change command DTO
     */
    @Data
    public static class TypeChange {
        @Size(max = 50)
        @NotNull
        private String type;
        private Rtm34Params rtm34Params;
        private FcParams fcParams;
        @Size(max = 255)
        @NotNull
        private String comment;
    }

    private final ProfileManagementService profileManagementService;
    private final UserInfoPopulationService userInfoPopulationService;
    private final ProfileCompanyManagementService companyManagementService;
    private final ProfileHistoryService profileHistoryService;
    private final ProfileStatService profileStatService;
    private final AdminServiceUserService userService;

    /**
     * Returns all information related to profile
     * @param id profile id
     * @return profile info
     * @throws ProfileNotFoundServiceException profile with specified id not found
     * @throws ServiceUnavailableException     tracking service is unavailable
     */
    @GetMapping(value = "/{id}")
    public ProfilePage getProfile(@PathVariable long id) throws ProfileNotFoundServiceException, ServiceUnavailableException {
        ProfilePage profilePage = ProfilePage.from(profileManagementService.get(id));
        profilePage.getPostUsers().forEach(userInfoPopulationService::populate);
        return profilePage;
    }

    /**
     * Sends a request to change access type for specified profile
     * @param id            profile id
     * @param typeChange    type change parameters
     * @param bindingResult bindingResult
     * @return profile info
     * @throws ValidationException             validation errors
     * @throws AccessTypeAlreadySetException   access type already set
     * @throws BadAccessTypeException          unrecognized access type
     * @throws ProfileNotFoundServiceException profile with specified id not found
     * @throws ServiceUnavailableException     tracking service unavailable
     */
    @PostMapping(value = "/{id}/access")
    public ProfilePage changeAccessType(
        @PathVariable long id,
        @Valid @NotNull @RequestBody TypeChange typeChange,
        BindingResult bindingResult
    ) throws ValidationException,
        AccessTypeAlreadySetException,
        BadAccessTypeException,
        ProfileNotFoundServiceException,
        ServiceUnavailableException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        List<ProtocolParams> protocolParams = new ArrayList<>(2);
        if (typeChange.getRtm34Params() != null) {
            protocolParams.add(typeChange.getRtm34Params());
        }
        if (typeChange.getFcParams() != null) {
            protocolParams.add(typeChange.getFcParams());
        }
        final AccessType accessType = (
            (protocolParams.isEmpty())
                ? new AccessType(typeChange.getType())
                : new AccessType(
                typeChange.getType(),
                protocolParams.toArray(new ProtocolParams[protocolParams.size()])
            )
        );

        final ProfilePage profilePage = ProfilePage.from(profileManagementService.setAccessType(
            id,
            accessType,
            typeChange.getComment()
        ));
        profilePage.getPostUsers().forEach(userInfoPopulationService::populate);
        return profilePage;
    }

    /**
     * Edits company
     * @param id                 profile id attached to this company
     * @param companyInfoEdition info about edition
     * @param bindingResult      bindingResult
     * @return profile object
     * @throws ValidationException             validation errors
     * @throws ProfileNotFoundServiceException profile not found
     * @throws ServiceUnavailableException     service unavailable
     */
    @PostMapping(value = "/{id}/company")
    @ResponseStatus(HttpStatus.OK)
    public ProfilePage editCompany(
        @PathVariable long id,
        @Valid @NotNull @RequestBody CompanyInfoEdition companyInfoEdition,
        BindingResult bindingResult
    ) throws ValidationException, ProfileNotFoundServiceException, ServiceUnavailableException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        final ProfilePage profilePage = ProfilePage.from(companyManagementService.editCompany(id, new PortalCompanyEdition(
            companyInfoEdition.getCompany() == null ? null : companyInfoEdition.getCompany().toCompany(),
            companyInfoEdition.getComment(),
            companyInfoEdition.getServiceName(),
            ProfileClientType.of(companyInfoEdition.getClientType()),
            companyInfoEdition.getInternalComment()
        )));
        profilePage.getPostUsers().forEach(userInfoPopulationService::populate);
        return profilePage;
    }

    /**
     * Sends a request to send information related to specified profile
     * @param id profile id
     * @throws ProfileNotFoundServiceException profile with specified id not found
     * @throws ServiceUnavailableException     tracking service unavailable
     */
    @PostMapping(value = "/{id}/sendInfo")
    @ResponseStatus(HttpStatus.OK)
    public void sendInfo(@PathVariable long id) throws ProfileNotFoundServiceException, ServiceUnavailableException {
        profileManagementService.sendInfo(id);
    }

    /**
     * Returns all history for specified profile
     * @param id profile id
     * @return all history
     * @throws ProfileNotFoundServiceException profile with specified id not found
     * @throws ServiceUnavailableException     tracking service unavailable
     */
    @GetMapping(value = "/{id}/history")
    public List<HistoryEntry> getHistory(@PathVariable long id)
        throws ProfileNotFoundServiceException, ServiceUnavailableException {
        final List<PortalProfileHistoryEvent> history = profileHistoryService.getHistoryFor((int) id);
        final Set<PostUser> postUsersWithNoInfo = history.stream()
            .map(PortalProfileHistoryEvent::getUserHid)
            .filter(Objects::nonNull)
            .distinct()
            .map(PostUser::new)
            .collect(Collectors.toSet());
        postUsersWithNoInfo.forEach(userInfoPopulationService::populate);

        Map<String, ServiceUser> userToObject = new UserInfoCompletion<>(history, PortalProfileHistoryEvent::getAuthor).buildMap(userService);
        final Map<String, PostUser> userInfos = postUsersWithNoInfo.stream()
            .collect(Collectors.toMap(PostUser::getHid, Function.identity()));
        return history.stream()
            .map(e -> new HistoryEntry(
                    e.getDatetime(),
                    e.getUserHid() == null
                        ? constructAuthorInfo(
                        userToObject.getOrDefault(e.getAuthor(), new ServiceUser.Builder().setUsername(e.getAuthor()).build())
                    )
                        : constructAuthorInfo(userInfos.get(e.getUserHid())),
                    e.getDescription(),
                    e.getComment()
                )
            ).collect(Collectors.toList());
    }

    /**
     * Returns current counters state for profile with specified ID
     * @param id id
     * @return current counters state
     * @throws ProfileNotFoundServiceException profile not found
     * @throws ServiceUnavailableException     service is not available
     */
    @GetMapping(value = "/{id}/counters")
    public Counters getCounters(@PathVariable long id) throws ProfileNotFoundServiceException, ServiceUnavailableException {
        AdminPageProfileCounters counters = profileStatService.getCounters(id);
        return Counters.of(counters);
    }

    private AdminUser constructAuthorInfo(PostUser postUser) {
        if (postUser == null) {
            return new AdminUser("???", null, null, null, null);
        }
        return new AdminUser("", postUser.getFirstName(), postUser.getLastName(), postUser.getMiddleName(), postUser.getEmail());
    }

    private AdminUser constructAuthorInfo(ServiceUser serviceUser) {
        if (serviceUser == null) {
            return new AdminUser("???", null, null, null, null);
        }
        return new AdminUser(
            serviceUser.getUsername(),
            serviceUser.getName(),
            serviceUser.getSurname(),
            serviceUser.getPatronymic(),
            serviceUser.getEmail()
        );
    }

    /**
     * Sends a request to reset password of specified profile
     * @param id profile id
     * @throws ProfileNotFoundServiceException profile with specified id not found
     * @throws ServiceUnavailableException     tracking service unavailable
     */
    @PostMapping(value = "/{id}/resetPassword")
    @ResponseStatus(HttpStatus.OK)
    public void resetPassword(@PathVariable long id)
        throws ProfileNotFoundServiceException, ServiceUnavailableException {
        profileManagementService.resetPassword(id);
    }

    /**
     * ProfilePage not found
     */
    @ExceptionHandler({ProfileNotFoundServiceException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Profile not found")
    public void profileNotFound() {
    }

    /**
     * Bad access type
     */
    @ExceptionHandler({BadAccessTypeException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Unrecognized access type")
    public void unrecognizedAccessType() {
    }

    /**
     * Access type already set
     */
    @ExceptionHandler({AccessTypeAlreadySetException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Access type already set")
    public void accessTypeAlreadySet() {
    }
}
