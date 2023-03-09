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

/**
 * Goods
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Goods {
    @JsonProperty("ProductID")
    private String productId;
    @NonNull
    @JsonProperty("Name")
    private String name;
    @NonNull
    @JsonProperty("CategoryFeature")
    private String categoryFeature;
    @NonNull
    @JsonProperty("Price")
    private String price;
    @NonNull
    @JsonProperty("PriceCurrency")
    private String priceCurrency;
    @NonNull
    @JsonProperty("DeclarePrice")
    private String declarePrice;
    @NonNull
    @JsonProperty("Quantity")
    private String quantity;
    @NonNull
    @JsonProperty("URL")
    private String url;
    @JsonProperty("HSCode")
    private String hsCode;
    @JsonProperty("Tax")
    private String tax;
    @NonNull
    @JsonProperty("Weight")
    private String weight;
    @NonNull
    @JsonProperty("WeightUnit")
    private String weightUnit;
    @JsonProperty("TradeMark")
    private String tradeMark;
}
