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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Amosov Maxim
 * @since 09.09.2021 : 12:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Данные для создания пользователя")
public class IdmCreateUserRequestDto {
    @ApiModelProperty(value = "Логин", required = true)
    private String username;
    @ApiModelProperty("Пароль")
    private String password;
    @ApiModelProperty("Список свойств")
    private List<IdmUserPropertyValue> properties = new ArrayList<>();
}
