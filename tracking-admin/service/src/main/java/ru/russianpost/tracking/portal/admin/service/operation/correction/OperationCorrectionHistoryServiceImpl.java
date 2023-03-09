/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.operation.correction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.commons.hdps.dto.correction.Correction2;
import ru.russianpost.tracking.commons.hdps.dto.correction.HistoryRecordCorrectionSearchResult;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrection;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrectionSourceSystem;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrectionType;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.operation.Money;
import ru.russianpost.tracking.portal.admin.service.hdps.CorrectionsJournal;
import ru.russianpost.tracking.portal.admin.service.hdps.HdpsClient;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author MKitchenko
 * @version 2.0 (May 18, 2020)
 */
@Service
public class OperationCorrectionHistoryServiceImpl implements OperationCorrectionHistoryService {

    private final HdpsClient hdpsClient;
    private final int batchSize;

    /**
     * @param hdpsClient hdps client
     * @param batchSize  batch size
     */
    public OperationCorrectionHistoryServiceImpl(
        HdpsClient hdpsClient,
        @Value("${ru.russianpost.hdps.corrections.batch.size}") int batchSize
    ) {
        this.hdpsClient = hdpsClient;
        this.batchSize = batchSize;
    }

    @Override
    public CorrectionsJournal getCorrections(
        long from,
        long to,
        List<HistoryRecordCorrectionType> correctionTypes,
        HistoryRecordCorrectionSourceSystem sourceSystem
    ) throws ServiceUnavailableException {
        final HistoryRecordCorrectionSearchResult searchResult = hdpsClient.getCorrections(
            from, to, correctionTypes, sourceSystem, batchSize
        );
        return new CorrectionsJournal(fromDtoToModel(searchResult.getCorrections()), searchResult.getNextPageTimestamp());
    }

    private List<HistoryRecordCorrection> fromDtoToModel(List<Correction2> corrections) {
        return corrections.stream()
            .map(corr -> {
                    final Money payment = resolveMoney(corr.getPayment());
                    final Money value = resolveMoney(corr.getValue());
                    return HistoryRecordCorrection.builder()
                        .originShipmentId(corr.getOriginShipmentId())
                        .originOperDate(corr.getOriginOperDate())
                        .originOperType(corr.getOriginOperType())
                        .originOperAttr(corr.getOriginOperAttr())
                        .originIndexOper(corr.getOriginIndexOper())
                        .sourceSystem(HistoryRecordCorrectionSourceSystem.valueOf(corr.getSourceSystem().name()))
                        .operDate(corr.getOperDate())
                        .indexOper(corr.getIndexOper())
                        .mass(corr.getMass())
                        .indexTo(corr.getIndexTo())
                        .created(corr.getCreated())
                        .author(corr.getAuthor())
                        .initiator(corr.getInitiator())
                        .comment(corr.getComment())
                        .rcpn(corr.getRcpn())
                        .sndr(corr.getSndr())
                        .payment(payment.getValue())
                        .paymentCurrency(payment.getCurrency())
                        .value(value.getValue())
                        .valueCurrency(value.getCurrency())
                        .mailType(corr.getMailType())
                        .mailCategory(corr.getMailCategory())
                        .type(HistoryRecordCorrectionType.valueOf(corr.getType().name()))
                        .build();
                }
            )
            .collect(toList());
    }

    private Money resolveMoney(ru.russianpost.tracking.commons.hdps.dto.Money money) {
        if (money == null) {
            return new Money(null, null);
        }
        return new Money(money.getValue(), money.getCurrency());
    }
}
