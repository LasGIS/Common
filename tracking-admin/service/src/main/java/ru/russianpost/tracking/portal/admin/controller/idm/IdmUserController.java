/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller.idm;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.russianpost.tracking.portal.admin.config.aspect.Speed4J;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmCreateUserRequestDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmPageableData;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmRoleDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmUserDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmUserPropertyDto;
import ru.russianpost.tracking.portal.admin.controller.dto.idm.IdmUserPropertyValue;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.controller.exception.UnrecognizedRoleException;
import ru.russianpost.tracking.portal.admin.controller.exception.UserAlreadyExistException;
import ru.russianpost.tracking.portal.admin.controller.exception.UserNotFoundException;
import ru.russianpost.tracking.portal.admin.controller.swagger.ShowInSwagger;
import ru.russianpost.tracking.portal.admin.service.idm.IdmUserService;
import ru.russianpost.tracking.portal.admin.utils.ParamsChecker;

import java.util.List;

/**
 * @author Amosov Maxim
 * @since 31.08.2021 : 11:43
 */
@ShowInSwagger
@RestController
@RequiredArgsConstructor
@Api(tags = "Интеграция с IDM")
@RequestMapping("/api/v1/idm")
public class IdmUserController extends BaseIdmRestController {
    private final IdmUserService idmUserService;

    /**
     * @return all user roles
     */
    @Speed4J
    @GetMapping("/users/roles")
    @ApiOperation(value = "Списк всех возможных ролей", authorizations = @Authorization(value = "Basic"))
    public List<IdmRoleDto> getAllRoles() {
        return idmUserService.getAllRoles();
    }

    /**
     * @param name role name
     * @return roles by name
     * @throws BadRequestException if params are not correct
     */
    @Speed4J
    @GetMapping("/users/roles/search")
    @ApiOperation(value = "Like поиск ролей по имени", authorizations = @Authorization(value = "Basic"))
    public List<IdmRoleDto> getRolesByName(
        @ApiParam(value = "Имя роли", required = true) @RequestParam(value = "name", required = false) final String name
    ) throws BadRequestException {
        ParamsChecker.requireNonBlank(name, "name");
        return idmUserService.getRolesByName(name);
    }

    /**
     * @param name role name
     * @return role id by name
     * @throws BadRequestException if params are not correct
     */
    @Speed4J
    @GetMapping("/users/roles/by-name")
    @ApiOperation(value = "Получение идентификатора роли по её имени (точное совпадение)", authorizations = @Authorization(value = "Basic"))
    public String getRoleIdByName(
        @ApiParam(value = "Имя роли", required = true) @RequestParam(value = "name", required = false) final String name
    ) throws BadRequestException {
        ParamsChecker.requireNonBlank(name, "name");
        return idmUserService.getRoleIdByName(name);
    }

    /**
     * @param userName userName
     * @return user dto by userName
     * @throws BadRequestException if params are not correct
     */
    @Speed4J
    @GetMapping("/users/{userName}")
    @ApiOperation(value = "Получение пользователя по его username", authorizations = @Authorization(value = "Basic"))
    public ResponseEntity<IdmUserDto> getUser(
        @ApiParam(value = "Логин пользователя", required = true) @PathVariable("userName") final String userName
    ) throws BadRequestException {
        ParamsChecker.requireNonBlank(userName, "userName");
        final IdmUserDto user = idmUserService.getUserByUserName(userName);
        return user == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(user);
    }

    /**
     * @param createUserRequestDto user request dto
     * @return new user dto
     * @throws BadRequestException       if params not correct
     * @throws UserAlreadyExistException if user already exist
     */
    @Speed4J
    @PostMapping("/users")
    @ApiOperation(value = "Создание нового пользователя", authorizations = @Authorization(value = "Basic"))
    public IdmUserDto createUser(
        @ApiParam(value = "Данные для создание нового пользователя", required = true)
        @RequestBody(required = false) final IdmCreateUserRequestDto createUserRequestDto
    ) throws BadRequestException, UserAlreadyExistException {
        ParamsChecker.requireNonNull(createUserRequestDto, "createUserRequest");
        ParamsChecker.requireNonBlank(createUserRequestDto.getUsername(), "createUserRequest.username");
        return idmUserService.createUser(createUserRequestDto);
    }

    /**
     * @param page page number
     * @param size page size
     * @return pageable users
     * @throws BadRequestException if params not correct
     */
    @Speed4J
    @GetMapping("/users")
    @ApiOperation(value = "Получение pageable списка акитивных пользователей", authorizations = @Authorization(value = "Basic"))
    public IdmPageableData<String> getActiveUserNames(
        @ApiParam(value = "Номер страницы, начиная с 0", allowableValues = "range[0, infinity]", required = true)
        @RequestParam(value = "page", required = false) final Integer page,
        @ApiParam(value = "Размер страницы (от 0 до 2000)", allowableValues = "range[0, 2000]", required = true)
        @RequestParam(value = "size", required = false) final Integer size
    ) throws BadRequestException {
        ParamsChecker.checkPageSize(size, "size");
        ParamsChecker.checkPageNumber(page, "page");
        return idmUserService.getActiveUserNames(page, size);
    }

    /**
     * @param userName userName
     * @return user roles
     * @throws BadRequestException   if params not correct
     * @throws UserNotFoundException if user not found
     */
    @Speed4J
    @GetMapping("/users/{userName}/roles")
    @ApiOperation(value = "Получение списка ролей пользователя", authorizations = @Authorization(value = "Basic"))
    public List<IdmRoleDto> getUserRoles(
        @ApiParam(value = "Логин пользователя", required = true) @PathVariable("userName") final String userName
    ) throws BadRequestException, UserNotFoundException {
        ParamsChecker.requireNonBlank(userName, "userName");
        return idmUserService.getUserRoleByUserName(userName);
    }

    /**
     * Add roles to user
     *
     * @param userName userName
     * @param rolesIds roles to add
     * @throws BadRequestException       if params not correct
     * @throws UserNotFoundException     if user not found
     * @throws UnrecognizedRoleException if role is not recognized
     */
    @Speed4J
    @PatchMapping("/users/{userName}/roles")
    @ApiOperation(value = "Добавление ролей пользователю", authorizations = @Authorization(value = "Basic"))
    public void addUserRoles(
        @ApiParam(value = "Логин пользователя", required = true) @PathVariable("userName") final String userName,
        @ApiParam(value = "Список идентификаторов ролей для добавления", required = true) @RequestBody(required = false) final List<String> rolesIds
    ) throws BadRequestException, UserNotFoundException, UnrecognizedRoleException {
        ParamsChecker.requireNonBlank(userName, "userName");
        ParamsChecker.requireNonEmpty(rolesIds, "rolesIds");
        idmUserService.addUserRoles(userName, rolesIds);
    }

    /**
     * Delete user roles from user
     *
     * @param userName userName
     * @param rolesIds roles to delete
     * @throws BadRequestException       if params not correct
     * @throws UserNotFoundException     if user not found
     * @throws UnrecognizedRoleException if role is not recognized
     */
    @Speed4J
    @DeleteMapping("/users/{userName}/roles")
    @ApiOperation(value = "Удаление ролей у пользователя", authorizations = @Authorization(value = "Basic"))
    public void deleteUserRoles(
        @ApiParam(value = "Логин пользователя", required = true) @PathVariable("userName") final String userName,
        @ApiParam(value = "Список идентификаторов ролей для удаления", required = true) @RequestBody(required = false) final List<String> rolesIds
    ) throws BadRequestException, UserNotFoundException, UnrecognizedRoleException {
        ParamsChecker.requireNonBlank(userName, "userName");
        ParamsChecker.requireNonEmpty(rolesIds, "rolesIds");
        idmUserService.deleteUserRoles(userName, rolesIds);
    }

    /**
     * @param userName userName
     * @throws BadRequestException   if params not correct
     * @throws UserNotFoundException if user not found
     */
    @Speed4J
    @PatchMapping("/users/{userName}/lock")
    @ApiOperation(value = "Блокировка пользователя", authorizations = @Authorization(value = "Basic"))
    public void lockUser(
        @ApiParam(value = "Логин пользователя", required = true) @PathVariable("userName") final String userName
    ) throws BadRequestException, UserNotFoundException {
        ParamsChecker.requireNonBlank(userName, "userName");
        idmUserService.lockUser(userName);
    }

    /**
     * @param userName userName
     * @throws BadRequestException   if params not correct
     * @throws UserNotFoundException if user not found
     */
    @Speed4J
    @PatchMapping("/users/{userName}/unlock")
    @ApiOperation(value = "Разблокировка пользователя", authorizations = @Authorization(value = "Basic"))
    public void unlockUser(
        @ApiParam(value = "Логин пользователя", required = true) @PathVariable("userName") final String userName
    ) throws BadRequestException, UserNotFoundException {
        ParamsChecker.requireNonBlank(userName, "userName");
        idmUserService.unlockUser(userName);
    }

    /**
     * @return all available user properties
     */
    @Speed4J
    @GetMapping("/users/properties")
    @ApiOperation(value = "Списк всех возможных свойств пользователя", authorizations = @Authorization(value = "Basic"))
    public List<IdmUserPropertyDto> getAllUserProperties() {
        return idmUserService.getAllUserProperties();
    }

    /**
     * @param userName    userName
     * @param propertyIds propertyIds
     * @return user properties
     * @throws BadRequestException   if params not correct
     * @throws UserNotFoundException if user not found
     */
    @Speed4J
    @GetMapping("/users/{userName}/properties")
    @ApiOperation(value = "Получение списка свойств пользователя", authorizations = @Authorization(value = "Basic"))
    public List<IdmUserPropertyValue> getUserProperties(
        @ApiParam(value = "Логин пользователя", required = true)
        @PathVariable("userName") final String userName,
        @ApiParam(value = "Список идентификаторов свойств прользователя", required = true)
        @RequestParam(value = "propertyIds", required = false) final List<String> propertyIds
    ) throws BadRequestException, UserNotFoundException {
        ParamsChecker.requireNonBlank(userName, "userName");
        ParamsChecker.requireNonEmpty(propertyIds, "propertyIds");
        return idmUserService.getUserProperties(userName, propertyIds);
    }

    /**
     * @param userName       userName
     * @param propertyValues property values to set
     * @throws BadRequestException   if params not correct
     * @throws UserNotFoundException if user not found
     */
    @Speed4J
    @PatchMapping("/users/{userName}/properties")
    @ApiOperation(value = "Сохранение свойств пользователя", authorizations = @Authorization(value = "Basic"))
    public void setUserProperties(
        @ApiParam(value = "Логин пользователя", required = true)
        @PathVariable("userName") final String userName,
        @ApiParam(value = "Список значений свойств прользователя", required = true)
        @RequestBody(required = false) final List<IdmUserPropertyValue> propertyValues
    ) throws BadRequestException, UserNotFoundException {
        ParamsChecker.requireNonBlank(userName, "userName");
        ParamsChecker.requireNonEmpty(propertyValues, "propertyValues");
        idmUserService.setUserProperties(userName, propertyValues);
    }
}
