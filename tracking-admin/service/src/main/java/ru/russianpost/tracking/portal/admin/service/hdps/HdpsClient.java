/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.hdps;

import ru.russianpost.tracking.commons.hdps.dto.correction.HistoryRecordCorrectionSearchResult;
import ru.russianpost.tracking.commons.hdps.dto.eordershipments.v1.EOrderShipmentsResponse;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.admin.AdminHistoryResponseV7;
import ru.russianpost.tracking.commons.hdps.dto.historybyphone.v1.HistoryByPhoneResponse;
import ru.russianpost.tracking.commons.hdps.dto.multiplace.v1.MultiplaceRpoResponse;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrectionSourceSystem;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrectionType;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.customs.declaration.CustomsDeclaration;
import ru.russianpost.tracking.portal.admin.model.operation.HdpsScope;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsHistoryRecord;

import java.util.EnumSet;
import java.util.List;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Nov 25, 2015)
 */
public interface HdpsClient {

    /**
     * Extracts history by barcode
     *
     * @param barcode barcode
     * @param scope   scope
     * @return history
     * @throws ServiceUnavailableException HDPS service unavailable
     */
    AdminHistoryResponseV7 getHistory(
        String barcode,
        EnumSet<HdpsScope> scope
    ) throws ServiceUnavailableException;

    /**
     * Extracts corrections by time interval
     *
     * @param fromUtc         interval starting timestamp (UTC)
     * @param toUtc           interval ending timestamp (UTC)
     * @param correctionTypes correction types to include
     * @param sourceSystem    source system
     * @param count           requested number of corrections
     * @return list of corrections
     * @throws ServiceUnavailableException HDPS service unavailable
     */
    HistoryRecordCorrectionSearchResult getCorrections(
        long fromUtc,
        long toUtc,
        List<HistoryRecordCorrectionType> correctionTypes,
        HistoryRecordCorrectionSourceSystem sourceSystem,
        int count
    ) throws ServiceUnavailableException;

    /**
     * Extracts sms history by barcode
     *
     * @param barcode barcode
     * @return history
     * @throws ServiceUnavailableException HDPS service unavailable
     */
    List<SmsHistoryRecord> getSmsHistory(String barcode) throws ServiceUnavailableException;

    /**
     * Mmo info by multiplace barcode
     *
     * @param multiplaceBarcode multiplace barcode
     * @return instance of {@link MultiplaceRpoResponse}
     * @throws ServiceUnavailableException if hdps is unavailable
     */
    MultiplaceRpoResponse getMmoInfoByMmoId(String multiplaceBarcode) throws ServiceUnavailableException;

    /**
     * @param eorder eorder
     * @return eorder shipmnets response
     * @throws ServiceUnavailableException if hdps is unavailable
     */
    EOrderShipmentsResponse getEOrderShipments(String eorder) throws ServiceUnavailableException;

    /**
     * Get All Customs Declarations for shipmentId
     *
     * @param shipmentId shipment ID
     * @return list of Customs Declarations for given shipmentId
     * @throws ServiceUnavailableException if hdps is unavailable
     */
    List<CustomsDeclaration> getAllCustomsDeclarations(String shipmentId) throws ServiceUnavailableException;

    /**
     * Get history by phone response for given phone number
     *
     * @param phone phone number
     * @param limit limitation returned operations
     * @return history by phone response
     * @throws ServiceUnavailableException if hdps is unavailable
     */
    HistoryByPhoneResponse getHistoryByPhone(String phone, Integer limit) throws ServiceUnavailableException;
}
