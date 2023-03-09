/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.elastic.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/****
 * TrackingApiStatisticsInfo
 *
 * @author VVorobeva
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingApiStatisticsInfo {

    private Integer numberOfRequests;
    private Integer countOfUniqueClients;
    private Integer countBarcodesPerTicket;

    /**
     * Constructor
     * @param numberOfRequests number of requests
     * @param countOfUniqueClients count of unique clients
     */
    public TrackingApiStatisticsInfo(Integer numberOfRequests, Integer countOfUniqueClients) {
        this.numberOfRequests = numberOfRequests;
        this.countOfUniqueClients = countOfUniqueClients;
    }
}
