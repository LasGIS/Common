/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.operation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import ru.russianpost.tracking.portal.admin.validation.constraints.ValidHistoryRecordId;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 02, 2015)
 */
@Value
@ValidHistoryRecordId
public class HistoryRecordId {

    /** Default value for null */
    public static final Integer NULL_OPER_ATTR_VALUE = -1;

    /** Default value for null */
    public static final String NULL_INDEX_OPER_VALUE = "";

    @NotNull
    @Size(max = 15)
    private String shipmentId;
    @NotNull
    @Min(0L)
    private Long operDate;
    @NotNull
    private Integer operType;
    @NotNull
    private Integer operAttr;
    @NotNull
    private String indexOper;

    /**
     * @param shipmentId shipmentId
     * @param operDate   operDate
     * @param operType   operType
     * @param operAttr   operAttr, if is null then it will be replaced by {@link #NULL_OPER_ATTR_VALUE}
     * @param indexOper  indexOper, if is null then it will be replaced by {@link #NULL_INDEX_OPER_VALUE}
     * @return new {@link HistoryRecordId} instance
     */
    @JsonCreator
    public static HistoryRecordId safeCreate(
        @JsonProperty("shipmentId") String shipmentId,
        @JsonProperty("operDate") Long operDate,
        @JsonProperty("operType") Integer operType,
        @JsonProperty("operAttr") Integer operAttr,
        @JsonProperty("indexOper") String indexOper
    ) {
        return new HistoryRecordId(
            shipmentId,
            operDate,
            operType,
            Optional.ofNullable(operAttr).orElse(NULL_OPER_ATTR_VALUE),
            Optional.ofNullable(indexOper).orElse(NULL_INDEX_OPER_VALUE)
        );
    }
}
