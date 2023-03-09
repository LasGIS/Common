/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.controller.dto.service.rtm4601.rtm4601;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

/**
 * Sender
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Parcel {
    @NonNull
    @JsonProperty("Weight")
    private String weight;
    @NonNull
    @JsonProperty("WeightUnit")
    private String weightUnit;
    @NonNull
    @JsonProperty("Price")
    private String price;
    @NonNull
    @JsonProperty("PriceCurrency")
    private String priceCurrency;
    @JsonProperty("Length")
    private String length;
    @JsonProperty("Width")
    private String width;
    @JsonProperty("Height")
    private String height;
    @JsonProperty("DimensionUnit")
    private String dimensionUnit;
    @NonNull
    @JsonProperty("Category")
    private String category;
    @JsonProperty("PostageFee")
    private String postageFee;
    @JsonProperty("FeeCurrency")
    private String feeCurrency;
    @NonNull
    @JsonProperty("GoodsList")
    private List<Goods> goodsList;
}
