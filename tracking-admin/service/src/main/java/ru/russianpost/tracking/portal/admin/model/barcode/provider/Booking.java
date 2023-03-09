/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Booking.
 * @author KKiryakov
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    /**
     * Booking id.
     */
    private Long id;
    /**
     * The Company name.
     */
    private String companyName;
    /**
     * INN.
     */
    private String inn;
    /**
     * Start of booking.
     */
    private int start;
    /**
     * End of booking.
     */
    private int end;
    /**
     * Current position.
     */
    private int current;
    /**
     * Booking type.
     */
    private BookingType type;
    /**
     * DateTime.
     */
    private String dateTime;
}
