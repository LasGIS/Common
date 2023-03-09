/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.eorder;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;

/**
 * @author Amosov Maxim
 * @since 08.07.2021 : 12:25
 */
@Getter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class EOrderShipmentRecord {
    private final String eorder;
    private final BigInteger inn;
    private final String acnt;
    private final String barcode;
    private final String returnBarcode;
    private final Integer mailCategory;
    private final Integer mailType;
    private final String rcpn;
    private final String sndr;
}
