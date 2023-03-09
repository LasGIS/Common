/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.barcode.automatization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * SetUserRequest.
 * @author MKitchenko
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UFPSUser {
    @NotEmpty
    private String login;
    @NotNull
    @JsonProperty("ufps")
    private List<UFPS> ufpsList;

}
