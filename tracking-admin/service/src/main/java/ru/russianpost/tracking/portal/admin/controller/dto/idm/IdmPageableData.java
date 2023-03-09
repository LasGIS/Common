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

import java.util.Collection;

/**
 * @param <T> type of pageable data
 * @author Amosov Maxim
 * @since 03.09.2021 : 14:14
 */
@Getter
@RequiredArgsConstructor
@ApiModel(description = "Обёртка над страничными данными")
public class IdmPageableData<T> {
    @ApiModelProperty("Номер страницы")
    private final Integer page;
    @ApiModelProperty("Размер страницы")
    private final Integer size;
    @ApiModelProperty("Общее кол-во элементов в БД")
    private final Long totalSize;
    @ApiModelProperty("Список данных на запрашиваемой странице")
    private final Collection<T> data;

    /**
     * @param data      data
     * @param page      page number
     * @param totalSize total size of data in db
     * @param <T>       type of pageable data
     * @return pageable data
     */
    public static <T> IdmPageableData<T> of(
        final Collection<T> data,
        final Integer page,
        final Long totalSize
    ) {
        return new IdmPageableData<>(page, data.size(), totalSize, data);
    }
}
