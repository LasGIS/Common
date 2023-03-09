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

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.commons.hdps.dto.history.v9.HdpsHistoryRecordV9;
import ru.russianpost.tracking.commons.hdps.dto.historybyphone.v1.HistoryByPhoneResponse;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.byphone.HistoryByPhoneRecord;
import ru.russianpost.tracking.portal.admin.model.byphone.HistoryByPhoneClientType;
import ru.russianpost.tracking.portal.admin.service.hdps.HdpsClient;
import ru.russianpost.tracking.portal.admin.service.hdps.HistoryRecordFieldResolver;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @author VLaskin
 * @since <pre>30.09.2021</pre>
 */
@Service
@RequiredArgsConstructor
public class HistoryByPhoneServiceImpl implements HistoryByPhoneService {

    private final HdpsClient hdpsClient;
    private final HistoryRecordFieldResolver fieldResolver;

    @Override
    public List<HistoryByPhoneRecord> getHistoryByPhone(final String phone, final Integer limit) throws ServiceUnavailableException {
        final HistoryByPhoneResponse response = hdpsClient.getHistoryByPhone(phone, limit);
        return Stream.of(
            response.getRecipientHistory().stream().map(record -> convertToRecord(record, HistoryByPhoneClientType.RECIPIENT)),
            response.getSenderHistory().stream().map(record -> convertToRecord(record, HistoryByPhoneClientType.SENDER)),
            response.getThirdPartyHistory().stream().map(record -> convertToRecord(record, HistoryByPhoneClientType.THIRD_PARTY))
        ).flatMap(rec -> rec).collect(toList());
    }

    private HistoryByPhoneRecord convertToRecord(final HdpsHistoryRecordV9 record, final HistoryByPhoneClientType type) {
        return HistoryByPhoneRecord.builder()
            .type(type)
            .shipmentId(record.getShipmentId())
            .operDate(record.getOperDate())
            .operType(record.getOperType())
            .operAttr(record.getOperAttr())
            .indexOper(record.getIndexOper())
            .operAddress(fieldResolver.resolveAddress(
                record.getIndexOper(),
                record.getIndexOperDesc(),
                record.getCountryOper(),
                record.getDataProviderType()
            ))
            .build();
    }
}
