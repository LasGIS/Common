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
 * Address
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @NonNull
    @JsonProperty("ZipCode")
    private String zipCode;
    @NonNull
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Province")
    private String province;
    @NonNull
    @JsonProperty("City")
    private String city;
    @JsonProperty("District")
    private String district;
    @NonNull
    @JsonProperty("Street")
    private String street;
    @NonNull
    @JsonProperty("DetailAddress")
    private String detailAddress;
}
