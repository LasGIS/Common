/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.opm;

import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.opm.NewOpsUser;
import ru.russianpost.tracking.portal.admin.model.opm.OpmHistory;
import ru.russianpost.tracking.portal.admin.model.opm.OpsIndex;

import java.util.List;

/**
 * OnlinePaymentMarkService interface.
 *
 * @author MKitchenko
 */
public interface OnlinePaymentMarkService {

    /**
     * Returns opm history by onlinePaymentMarkId.
     *
     * @param onlinePaymentMarkId the onlinePaymentMarkId.
     * @return history instance of {@link OpmHistory}
     * @throws ServiceUnavailableException if OnlinePaymentMarkService unavailable
     */
    OpmHistory getHistory(String onlinePaymentMarkId) throws ServiceUnavailableException;

    /**
     * Returns list of opmIds by barcode.
     *
     * @param barcode the barcode.
     * @return list of opmIds by barcode.
     * @throws ServiceUnavailableException if OnlinePaymentMarkService unavailable
     */
    List<String> getOpmIdsByBarcode(String barcode) throws ServiceUnavailableException;

    /**
     * @param opsIndices       opsIndices
     * @param overrideExisting override existing users
     * @return NewOpsUserOutput
     */
    List<NewOpsUser> generateOpsUsers(List<OpsIndex> opsIndices, boolean overrideExisting);
}
