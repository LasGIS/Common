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
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Amosov Maxim
 * @since 05.09.2021 : 18:44
 */
@Getter
@RequiredArgsConstructor
@ApiModel(description = "Ответ при возникновении ошибки")
public class IdmErrorResponse {
    @ApiModelProperty("Статус")
    private final int status;
    @ApiModelProperty("Описание ошибки")
    private final String description;
}
