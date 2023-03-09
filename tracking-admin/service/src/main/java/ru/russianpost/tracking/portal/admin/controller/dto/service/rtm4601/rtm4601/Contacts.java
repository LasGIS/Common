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
 * @author MKitchenko
 * @version 1.0 27.08.19.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
class Contacts {
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("Mobile")
    private String mobile;
    @JsonProperty("Email")
    private String email;
}
