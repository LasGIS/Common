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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * OrderView
 * @author MKitchenko
 * @version 1.0 27.08.2019
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rtm4601 {
    @JsonProperty("BizType")
    private String bizType;
    @JsonProperty("TradeID")
    private String tradeId;
    @JsonProperty("OrderID")
    private String orderId;
    @NonNull
    @JsonProperty("TrackingNumber")
    private String trackingNumber;
    @NonNull
    @JsonProperty("LogisticsOrderCode")
    private String logisticsOrderCode;
    @NonNull
    @JsonProperty("OrderDeliveryMethod")
    private String orderDeliveryMethod;
    @NonNull
    @JsonProperty("OrderMade")
    private String orderMade;
    @NonNull
    @JsonProperty("DeliveryType")
    private String deliveryType;
    @NonNull
    @JsonProperty("Sender")
    private Sender sender;
    @NonNull
    @JsonProperty("Recipient")
    private Recipient recipient;
    @JsonProperty("DropoffPickupStation")
    private DropoffPickupStation dropoffPickupStation;
    @NonNull
    @JsonProperty("Parcel")
    private Parcel parcel;
    @NonNull
    @JsonProperty("Customs")
    private Customs customs;
    @JsonProperty("ReturnParcel")
    private ReturnParcel returnParcel;
    @JsonProperty("CarrierCode")
    private String carrierCode;
    @JsonProperty("LogisticsOrderCreateTime")
    private String logisticsOrderCreateTime;
}


