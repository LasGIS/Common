/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package com.lasgis.springBoot.springinitializrdemo.controller.error;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Error delivery dto
 *
 * @author VLaskin
 * @since <pre>29.06.2020</pre>
 */
@Data
@Builder
public class ErrorDto {

    /** Уникальный код ошибки */
    @ApiModelProperty("Уникальный код ошибки")
    private final Integer code;

    /** Сообщение для GUI */
    @ApiModelProperty("Сообщение для клиента (для GUI)")
    private final String message;

    /** Детальная причина ошибки */
    @ApiModelProperty("Детальная причина ошибки")
    private final String detail;

    /** Список дополнительных параметров */
    @ApiModelProperty("Список дополнительных параметров")
    private final List<String> parameters;
}
