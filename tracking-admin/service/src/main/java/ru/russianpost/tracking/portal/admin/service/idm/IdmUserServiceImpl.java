/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.idm;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmCreateUserRequestDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmPageableData;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmRoleDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmUserDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmUserProperty;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmUserPropertyDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmUserPropertyValue;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.controller.exception.UnrecognizedIdmUserPropertyException;
import ru.russianpost.tracking.portal.admin.controller.exception.UnrecognizedRoleException;
import ru.russianpost.tracking.portal.admin.controller.exception.UserAlreadyExistException;
import ru.russianpost.tracking.portal.admin.controller.exception.UserNotFoundException;
import ru.russianpost.tracking.portal.admin.converter.IdmRoleConverter;
import ru.russianpost.tracking.portal.admin.converter.IdmUserConverter;
import ru.russianpost.tracking.portal.admin.converter.IdmUserPropertyConverter;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserHistoryEvent;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserHistoryEventType;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserInfo;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserInfoUpdatedEventArgs;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserRolesUpdatedEventArgs;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;
import ru.russianpost.tracking.portal.admin.repository.ServiceUserDao;
import ru.russianpost.tracking.portal.admin.security.Role;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.springframework.util.StringUtils.tokenizeToStringArray;
import static ru.russianpost.tracking.portal.admin.model.admin.history.UserHistoryEventType.USER_CREATED;

/**
 * @author Amosov Maxim
 * @since 31.08.2021 : 12:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IdmUserServiceImpl implements IdmUserService {
    private static final Gson GSON = new Gson();
    private static final String LOG_EVENT_AUTHOR = "idm";

    private final ServiceUserDao userDao;
    private final IdmRoleConverter roleConverter;
    private final IdmUserConverter userConverter;
    private final IdmUserPropertyConverter userPropertyConverter;

    @Override
    public List<IdmRoleDto> getAllRoles() {
        return Arrays.stream(Role.values()).filter(r -> r != Role.UNKNOWN)
            .map(roleConverter::convert)
            .collect(toList());
    }

    @Override
    public List<IdmRoleDto> getRolesByName(@NotNull final String name) {
        return Arrays.stream(Role.values()).filter(r -> r != Role.UNKNOWN)
            .filter(r -> containsIgnoreCase(r.getFullName(), name))
            .map(roleConverter::convert)
            .collect(toList());
    }

    @Override
    public String getRoleIdByName(@NotNull final String name) throws BadRequestException {
        return Arrays.stream(Role.values()).filter(r -> r != Role.UNKNOWN)
            .filter(r -> r.getFullName().equalsIgnoreCase(name))
            .map(Enum::name)
            .findAny()
            .orElseThrow(() -> new BadRequestException("No roles found matching name = " + name));
    }

    @Override
    public IdmPageableData<String> getActiveUserNames(@NotNull final Integer page, @NotNull final Integer size) {
        final Long totalSize = userDao.getSizeOfActiveUsers();
        final List<String> activeUserNames = userDao.getActiveUserNames(page, size);
        return IdmPageableData.of(activeUserNames, page, totalSize);
    }

    @Override
    public IdmUserDto getUserByUserName(@NotNull final String userName) {
        return ofNullable(userDao.getServiceUser(userName))
            .map(userConverter::convert)
            .orElse(null);
    }

    @Override
    public IdmUserDto createUser(@NotNull final IdmCreateUserRequestDto requestDto) throws UserAlreadyExistException {
        final ServiceUser user = userDao.getServiceUser(requestDto.getUsername());
        if (nonNull(user)) {
            throw new UserAlreadyExistException(user.getUsername());
        }

        final UserInfo userInfo = new UserInfo();
        requestDto.getProperties().stream().map(p -> Pair.of(IdmUserProperty.byNameOrThrow(p.getId()), p.getValue()))
            .forEach(p -> p.getKey().getUserEditor().accept(userInfo, p.getValue()));

        final ServiceUser newUser = ServiceUser.builder()
            .setUsername(requestDto.getUsername().toLowerCase())
            .setPassword(StringUtils.defaultString(requestDto.getPassword()))
            .setName(userInfo.getName())
            .setSurname(userInfo.getSurname())
            .setPatronymic(userInfo.getPatronymic())
            .setEmail(userInfo.getEmail())
            .build();

        userDao.addUser(newUser);
        userDao.logEvent(new UserHistoryEvent(newUser.getUsername(), LOG_EVENT_AUTHOR, USER_CREATED, GSON.toJson(newUser)));
        return userConverter.convert(newUser);
    }

    @Override
    public List<IdmRoleDto> getUserRoleByUserName(@NotNull final String userName) throws UserNotFoundException, UnrecognizedRoleException {
        return ofNullable(userDao.getServiceUser(userName))
            .map(u -> tokenizeToStringArray(u.getAuthorityString(), ","))
            .map(Arrays::stream)
            .map(roles -> roles.map(Role::by).collect(toList()))
            .map(roleConverter::convertAll)
            .orElseThrow(() -> new UserNotFoundException(userName));
    }

    @Override
    public void addUserRoles(
        @NotNull final String userName,
        @NotNull final List<String> rolesIds
    ) throws UserNotFoundException, UnrecognizedRoleException {
        final Set<Role> rolesToAdd = rolesIds.stream().map(Role::byNameOrThrow).collect(Collectors.toSet());
        final ServiceUser user = getUserOrThrow(userName);
        final Set<Role> existingRoles = Arrays.stream(tokenizeToStringArray(user.getAuthorityString(), ","))
            .map(Role::by).collect(Collectors.toSet());

        final Set<Role> rolesToSave = SetUtils.union(existingRoles, rolesToAdd).toSet();
        if (rolesToSave.equals(existingRoles)) {
            log.info("No need to update roles for user = {}", userName);
            return;
        }

        final String resultAuthority = rolesToSave.stream().map(Enum::name).collect(joining(","));
        userDao.updateAuthorityString(userName, resultAuthority);

        final List<String> newRoles = SetUtils.difference(rolesToAdd, existingRoles).stream().map(Enum::name).collect(toList());
        userDao.logEvent(new UserHistoryEvent(
            userName,
            LOG_EVENT_AUTHOR,
            UserHistoryEventType.USER_ROLES_UPDATED,
            GSON.toJson(new UserRolesUpdatedEventArgs(newRoles, emptyList()))
        ));
    }

    @Override
    public void deleteUserRoles(
        @NotNull final String userName,
        @NotNull final List<String> rolesIds
    ) throws UserNotFoundException {
        final Set<Role> rolesToDelete = rolesIds.stream().map(Role::byNameOrThrow).collect(Collectors.toSet());
        final ServiceUser user = getUserOrThrow(userName);
        final Set<Role> existingRoles = Arrays.stream(tokenizeToStringArray(user.getAuthorityString(), ","))
            .map(Role::by).collect(Collectors.toSet());

        final Set<Role> rolesToSave = SetUtils.difference(existingRoles, rolesToDelete).toSet();
        if (rolesToSave.equals(existingRoles)) {
            log.info("No need to update roles for user = {}", userName);
            return;
        }

        final String resultAuthority = rolesToSave.stream().map(Enum::name).collect(joining(","));
        userDao.updateAuthorityString(userName, resultAuthority);

        final List<String> deletedRoles = SetUtils.intersection(existingRoles, rolesToDelete).stream().map(Enum::name).collect(toList());
        userDao.logEvent(new UserHistoryEvent(
            userName,
            LOG_EVENT_AUTHOR,
            UserHistoryEventType.USER_ROLES_UPDATED,
            GSON.toJson(new UserRolesUpdatedEventArgs(emptyList(), deletedRoles))
        ));
    }

    @Override
    public void lockUser(final String userName) throws UserNotFoundException {
        final ServiceUser serviceUser = getUserOrThrow(userName);
        userDao.lockUser(serviceUser.getUsername());
    }

    @Override
    public void unlockUser(final String userName) throws UserNotFoundException {
        final ServiceUser serviceUser = getUserOrThrow(userName);
        userDao.unlockUser(serviceUser.getUsername());
    }

    @Override
    public List<IdmUserPropertyDto> getAllUserProperties() {
        return Arrays.stream(IdmUserProperty.values())
            .map(userPropertyConverter::convert)
            .collect(toList());
    }

    @Override
    public List<IdmUserPropertyValue> getUserProperties(
        @NotNull final String userName,
        @NotNull final List<String> propertyIds
    ) throws UserNotFoundException, UnrecognizedIdmUserPropertyException {
        final UserInfo userInfo = new UserInfo(getUserOrThrow(userName));
        return propertyIds.stream().map(IdmUserProperty::byNameOrThrow)
            .map(p -> new IdmUserPropertyValue(p.name(), p.getUserExtractor().apply(userInfo)))
            .collect(toList());
    }

    @Override
    public void setUserProperties(
        @NotNull final String userName,
        @NotNull final List<IdmUserPropertyValue> propertyValues
    ) throws UserNotFoundException, UnrecognizedIdmUserPropertyException {
        final ServiceUser serviceUser = getUserOrThrow(userName);
        final UserInfo currentUserInfo = new UserInfo(serviceUser);

        final UserInfo newUserInfo = new UserInfo(serviceUser);
        propertyValues.stream().map(p -> Pair.of(IdmUserProperty.byNameOrThrow(p.getId()), p.getValue()))
            .forEach(p -> p.getKey().getUserEditor().accept(newUserInfo, p.getValue()));

        if (currentUserInfo.equals(newUserInfo)) {
            log.info("Nothing to update for user = {}", userName);
        } else {
            userDao.updateUserInfo(
                userName,
                newUserInfo.getName(),
                newUserInfo.getSurname(),
                newUserInfo.getPatronymic(),
                newUserInfo.getEmail()
            );
            userDao.logEvent(new UserHistoryEvent(
                userName,
                LOG_EVENT_AUTHOR,
                UserHistoryEventType.USER_INFORMATION_UPDATED,
                GSON.toJson(new UserInfoUpdatedEventArgs(currentUserInfo, newUserInfo))
            ));
        }
    }

    private ServiceUser getUserOrThrow(final String userName) throws UserNotFoundException {
        return ofNullable(userDao.getServiceUser(userName))
            .orElseThrow(() -> new UserNotFoundException(userName));
    }
}
