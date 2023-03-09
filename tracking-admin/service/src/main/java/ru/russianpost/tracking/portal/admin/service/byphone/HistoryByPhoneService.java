/*
 * Copyright 2021 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.byphone;

import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.byphone.HistoryByPhoneRecord;

import java.util.List;

/**
 * History by phone Service.
 *
 * @author vlaskin
 * @since <pre>30.09.2021</pre>
 */
public interface HistoryByPhoneService {

    /**
     * Get list of history by phone records by given phone number.
     *
     * @param phone phone number
     * @param limit limitation returned operations
     * @return list of history by phone records
     * @throws ServiceUnavailableException if hdps is unavailable
     */
    List<HistoryByPhoneRecord> getHistoryByPhone(String phone, Integer limit) throws ServiceUnavailableException;
}
