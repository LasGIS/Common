/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.eorder;

import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.eorder.EOrderShipmentRecord;

import java.util.List;

/**
 * @author Amosov Maxim
 * @since 08.07.2021 : 12:41
 */
public interface EOrderShipmentsService {
    /**
     * @param eorder eorder
     * @return list of eorder shipmnet records
     * @throws ServiceUnavailableException if hdps is unavailable
     */
    List<EOrderShipmentRecord> getEOrderShipments(String eorder) throws ServiceUnavailableException;
}
