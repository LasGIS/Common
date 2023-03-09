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
import lombok.Setter;

/**
 * Customs
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Customs {
    @JsonProperty("DocumentInfo")
    private String documentInfo;
    @JsonProperty("DocumentType")
    private String documentType;
    @JsonProperty("DocumentExpireDate")
    private String documentExpireDate;
    @JsonProperty("DocumentIssueDate")
    private String documentIssueDate;
    @JsonProperty("DocumentHolderName")
    private String documentHolderName;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("MiddleName")
    private String middleName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("HolderBirthday")
    private String holderBirthday;
    @JsonProperty("TaxTotal")
    private String taxTotal;
    @JsonProperty("DeclarePriceTotal")
    private String declarePriceTotal;
}
