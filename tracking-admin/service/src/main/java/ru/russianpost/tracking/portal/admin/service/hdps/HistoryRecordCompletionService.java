/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.hdps;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.portal.admin.model.operation.CompletedHistoryRecord;
import ru.russianpost.tracking.portal.admin.model.operation.CreatedHistoryRecord;
import ru.russianpost.tracking.portal.admin.model.operation.HistoryRecordId;
import ru.russianpost.tracking.portal.admin.model.operation.OperationData;
import ru.russianpost.tracking.portal.admin.repository.Dictionary;

import static ru.russianpost.tracking.portal.admin.service.hdps.HistoryRecordFieldResolver.longOf;

/**
 * HistoryRecordCompletionService
 *
 * @author Roman Prokhorov
 * @version 1.0 (Nov 26, 2015)
 */
@Service
@RequiredArgsConstructor
public class HistoryRecordCompletionService {

    private final Dictionary dictionary;
    private final HistoryRecordFieldResolver fieldResolver;

    /**
     * Completes history record with dictionary information
     *
     * @param historyRecord instance of {@link CreatedHistoryRecord}
     * @param hidden        mark record as hidden
     * @return completed history record
     * @see #complete(CreatedHistoryRecord, HistoryRecordId, boolean)
     */
    public CompletedHistoryRecord complete(final CreatedHistoryRecord historyRecord, boolean hidden) {
        return complete(
            historyRecord,
            new HistoryRecordId(
                historyRecord.getShipmentId(),
                historyRecord.getOperDate(),
                historyRecord.getOperType(),
                historyRecord.getOperAttr(),
                historyRecord.getIndexOper()
            ),
            hidden
        );
    }

    /**
     * Completes history record with dictionary information and given id.
     * Used for prevent change id from afterSet if id property was changed
     *
     * @param historyRecord instance of {@link CreatedHistoryRecord}
     * @param id            id of history record
     * @param hidden        mark record as hidden
     * @return completed history record
     */
    private CompletedHistoryRecord complete(final CreatedHistoryRecord historyRecord, final HistoryRecordId id, boolean hidden) {
        final OperationData operationData = OperationData.builder()
            .typeId(historyRecord.getOperType())
            .attrId(historyRecord.getOperAttr())
            .index(historyRecord.getIndexOper())
            .address(fieldResolver.resolveAddress(
                historyRecord.getIndexOper(), null, historyRecord.getCountryOper(), historyRecord.getDataProviderType()
            ))
            .date(historyRecord.getOperDate())
            .dateOffset(historyRecord.getZoneOffsetSeconds())
            .build();

        return CompletedHistoryRecord.builder()
            .id(id)
            .operationData(operationData)
            .loadDate(historyRecord.getLoadDate())
            .softwareName(this.dictionary.getSoftwareNameByVersion(historyRecord.getSoftwareVersion()))
            .softwareVersion(historyRecord.getSoftwareVersion())
            .dataProvider(historyRecord.getDataProviderType())
            .mass(longOf(historyRecord.getMass()))
            .hidden(hidden)
            .build();
    }
}
