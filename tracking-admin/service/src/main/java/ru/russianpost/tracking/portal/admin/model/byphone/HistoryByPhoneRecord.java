/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.byphone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

/**
 * History by phone record
 *
 * @author vlaskin
 * @since <pre>30.09.2021</pre>
 */
@Getter
@Builder
@ApiModel(description = "Операция над РПО по номеру телефона")
public class HistoryByPhoneRecord {
    @ApiModelProperty("Отношение данного номера телефона к операции")
    private final HistoryByPhoneClientType type;
    @ApiModelProperty("ШПИ")
    private final String shipmentId;
    @ApiModelProperty("Дата/время последней операции в миллисекундах")
    private final Long operDate;
    @ApiModelProperty("Код типа операции")
    private final Integer operType;
    @ApiModelProperty("Код атрибута операции")
    private final Integer operAttr;
    @ApiModelProperty("Индекс места проведения операции")
    private final String indexOper;
    @ApiModelProperty("Адрес места проведения операции")
    private final String operAddress;
}
