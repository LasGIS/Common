/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.operation;

import com.ecyrd.speed4j.StopWatch;
import com.ecyrd.speed4j.StopWatchFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.api.protobuf.messages.PostalEvent;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.model.operation.CreatedHistoryRecord;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordCreation;
import ru.russianpost.tracking.portal.admin.service.kafka.KafkaService;
import ru.russianpost.tracking.portal.admin.service.protobuf.ProtobufConverter;

import java.math.BigInteger;
import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;


/**
 * Service for registering new operations.
 * @author KKiryakov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperationRegistrationServiceImpl implements OperationRegistrationService {

    private static final Charset DEFAULT_CHARSET = UTF_8;

    private final StopWatchFactory stopWatchFactory;
    private final ProtobufConverter protobufConverter;
    private final KafkaService kafkaService;

    @Override
    public CreatedHistoryRecord register(HistoryRecordCreation creation) {
        final StopWatch stopWatch = stopWatchFactory.getStopWatch("OperationRegistrationServiceImpl:register:" + creation);

        log.debug("Method OperationRegistrationServiceImpl.register started");
        final PostalEvent.Event event = protobufConverter.convertToPostalEvent(creation);
        log.debug("Converted to protobuf:\n{}", event);

        try {
            final byte[] key = creation.getShipmentId().getBytes(DEFAULT_CHARSET);
            final byte[] value = event.toByteArray();
            kafkaService.produceToInput(key, value);
            log.debug("Protobuf message has been sent to kafka.");
            return buildHistoryRecord(event);
        } catch (Exception e) {
            log.error("Error to produce message to kafka input topic. {}", e.getMessage());
            throw new InternalServerErrorException("Internal server error during registering new operation.", e);
        } finally {
            stopWatch.stop();
        }
    }

    private CreatedHistoryRecord buildHistoryRecord(PostalEvent.Event event) {
        return CreatedHistoryRecord.builder()
            .shipmentId(event.getShipmentId())
            .operType(event.getOperType())
            .operAttr(event.getOperAttr() == -1 ? null : event.getOperAttr())
            .operDate(event.getOperDate())
            .indexOper(event.getIndexOper())
            .zoneOffsetSeconds(event.getZoneOffsetSeconds())
            .mass(BigInteger.valueOf(event.getMass()))
            .softwareVersion(event.getSoftwareVersion())
            .dataProviderType(event.getDataProviderType())
            .loadDate(event.getTimestamp())
            .countryOper(event.getCountryOper())
            .build();
    }
}
