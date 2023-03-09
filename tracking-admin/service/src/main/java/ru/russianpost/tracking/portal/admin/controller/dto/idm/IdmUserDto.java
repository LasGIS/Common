/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller.dto.idm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Amosov Maxim
 * @since 01.09.2021 : 12:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Данные пользователя")
public class IdmUserDto {
    @ApiModelProperty("Логин")
    private String username;
    @ApiModelProperty("Имя")
    private String name;
    @ApiModelProperty("Фамилия")
    private String surname;
    @ApiModelProperty("Отчество")
    private String patronymic;
    @ApiModelProperty("Email")
    private String email;
    @ApiModelProperty("Список ролей")
    private List<IdmRoleDto> roles;
    @ApiModelProperty("Статус")
    private IdmUserStatus status;
}
