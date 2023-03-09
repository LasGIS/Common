/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.barcode.automatization;

import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.BarcodeAllocateResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.BarcodesAllocateRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.S10Range;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.SetUserRequest;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.SuitableRangeResponse;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.UFPS;
import ru.russianpost.tracking.portal.admin.model.barcode.automatization.UFPSUser;

import java.util.List;

/**
 * BarcodeAutomatizationService interface.
 *
 * @author MKitchenko
 */
public interface BarcodeAutomatizationService {

    /**
     * Set ufps user
     *
     * @param setUserRequest set user request from ui
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    void setUfpsUser(SetUserRequest setUserRequest) throws ServiceUnavailableException;

    /**
     * Returns ufps user by user login.
     *
     * @param userLogin user login
     * @return ufps user
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    UFPSUser getUfpsUser(String userLogin) throws ServiceUnavailableException;

    /**
     * Returns ufps list.
     *
     * @return ufps list
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    List<UFPS> getUfpsList() throws ServiceUnavailableException;

    /**
     * Returns allocation results.
     *
     * @param ufpsId ufps id
     * @param from   date from
     * @param to     date to
     * @return allocation results
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    List<S10Range> getAllocationResults(
        int ufpsId,
        String from,
        String to
    ) throws ServiceUnavailableException;

    /**
     * Returns instance of {@link BarcodeAllocateResponse}.
     *
     * @param request instance of {@link BarcodesAllocateRequest}
     * @return instance of {@link BarcodeAllocateResponse}
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    BarcodeAllocateResponse allocate(BarcodesAllocateRequest request) throws ServiceUnavailableException;

    /**
     * Get free S10 range suitable for specified criteria.
     *
     * @param ufpsId        ufps Id
     * @param containerType container type
     * @param quantity      number of barcodes to allocate
     * @return instance of {@link BarcodeAllocateResponse}
     * @throws ServiceUnavailableException on error during barcode provider service call
     */
    SuitableRangeResponse getSuitableRange(int ufpsId, String containerType, Integer quantity) throws ServiceUnavailableException;
}
