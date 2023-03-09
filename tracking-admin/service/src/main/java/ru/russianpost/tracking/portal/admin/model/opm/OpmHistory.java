/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.opm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Online Payment Mark History class.
 * @author MKitchenko
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OpmHistory {
    private String onlinePaymentMarkId;
    private String shipmentId;
    private Integer value;
    private OnlinePaymentMarkStatus status;
    private Integer mailType;
    private Integer mailCtg;
    private Integer mass;
    private String f103Id;
    private Long operDate;
    private String inn;
    private String kpp;
    private String sndr;
    private String indexTo;
    private List<StatusInfoLite> history;
}
