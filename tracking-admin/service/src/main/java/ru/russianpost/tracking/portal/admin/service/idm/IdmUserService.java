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

import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmCreateUserRequestDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmPageableData;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmRoleDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmUserDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmUserPropertyDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmUserPropertyValue;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.controller.exception.UnrecognizedIdmUserPropertyException;
import ru.russianpost.tracking.portal.admin.controller.exception.UnrecognizedRoleException;
import ru.russianpost.tracking.portal.admin.controller.exception.UserAlreadyExistException;
import ru.russianpost.tracking.portal.admin.controller.exception.UserNotFoundException;

import java.util.List;

/**
 * @author Amosov Maxim
 * @since 31.08.2021 : 12:03
 */
public interface IdmUserService {
    /**
     * @return all existing roles
     */
    List<IdmRoleDto> getAllRoles();

    /**
     * @param name role name
     * @return roles by name
     */
    List<IdmRoleDto> getRolesByName(String name);

    /**
     * @param name role name
     * @return role id by name
     * @throws BadRequestException if no roles found
     */
    String getRoleIdByName(String name) throws BadRequestException;

    /**
     * @param page page number
     * @param size page size
     * @return pageable user names
     */
    IdmPageableData<String> getActiveUserNames(Integer page, Integer size);

    /**
     * @param userName userName
     * @return userDto by userName
     */
    IdmUserDto getUserByUserName(String userName);

    /**
     * @param createUserRequestDto user request dto
     * @return new user dto
     * @throws UserAlreadyExistException if user already exist
     */
    IdmUserDto createUser(IdmCreateUserRequestDto createUserRequestDto) throws UserAlreadyExistException;

    /**
     * @param userName userName
     * @return user roles
     * @throws UserNotFoundException if user not found
     */
    List<IdmRoleDto> getUserRoleByUserName(String userName) throws UserNotFoundException;

    /**
     * Adds roles to user
     *
     * @param userName userName
     * @param rolesIds rolesIds
     * @throws UserNotFoundException     if user not found
     * @throws UnrecognizedRoleException if role is not recognized
     */
    void addUserRoles(String userName, List<String> rolesIds) throws UserNotFoundException, UnrecognizedRoleException;

    /**
     * Delete roles from user
     *
     * @param userName userName
     * @param rolesIds rolesIds
     * @throws UserNotFoundException     if user not found
     * @throws UnrecognizedRoleException if role is not recognized
     */
    void deleteUserRoles(String userName, List<String> rolesIds) throws UserNotFoundException, UnrecognizedRoleException;

    /**
     * @param userName userName
     * @throws UserNotFoundException if user not found
     */
    void lockUser(String userName) throws UserNotFoundException;

    /**
     * @param userName userName
     * @throws UserNotFoundException if user not found
     */
    void unlockUser(String userName) throws UserNotFoundException;

    /**
     * @return all user properties
     */
    List<IdmUserPropertyDto> getAllUserProperties();

    /**
     * @param userName    userName
     * @param propertyIds propertyIds
     * @return user property values
     * @throws UserNotFoundException                if user not found
     * @throws UnrecognizedIdmUserPropertyException if property can't be resolved
     */
    List<IdmUserPropertyValue> getUserProperties(String userName, List<String> propertyIds)
        throws UserNotFoundException, UnrecognizedIdmUserPropertyException;

    /**
     * Sets user properties
     *
     * @param userName       userName
     * @param propertyValues propertyValues
     * @throws UserNotFoundException                if user not found
     * @throws UnrecognizedIdmUserPropertyException if property can't be resolved
     */
    void setUserProperties(String userName, List<IdmUserPropertyValue> propertyValues)
        throws UserNotFoundException, UnrecognizedIdmUserPropertyException;
}
