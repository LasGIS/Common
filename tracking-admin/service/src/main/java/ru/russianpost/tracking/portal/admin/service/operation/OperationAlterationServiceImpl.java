/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.operation;

import com.google.protobuf.GeneratedMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.api.protobuf.messages.RecordAlteration;
import ru.russianpost.tracking.commons.model.HistoryRecordCorrection;
import ru.russianpost.tracking.portal.admin.controller.exception.BadRequestException;
import ru.russianpost.tracking.portal.admin.exception.InternalServerErrorException;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectionId;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectionType;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordCreation;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordDeletion;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordEdition;
import ru.russianpost.tracking.portal.admin.model.operation.correction.HistoryRecordRestoration;
import ru.russianpost.tracking.portal.admin.service.kafka.KafkaService;
import ru.russianpost.tracking.portal.admin.service.protobuf.ProtobufConverter;

import java.nio.charset.Charset;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 04, 2015)
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OperationAlterationServiceImpl implements OperationAlterationService {

    private static final Charset DEFAULT_CHARSET = UTF_8;
    private static final String INTERNAL_SERVER_ERROR_DURING_REGISTERING_RECORD_ALTERATION = "Internal server error " +
        "during registering record alteration.";

    private final KafkaService kafkaService;
    private final ProtobufConverter protobufConverter;

    @Override
    public CorrectionId edit(String author, HistoryRecordEdition edition) throws BadRequestException {
        if (!edition.containsChanges()) {
            log.info("Skipped operation alteration request: {}", edition);
            throw new BadRequestException("Correction doesn't contain any changes");
        }
        try {
            byte[] key = edition.getId().getShipmentId().getBytes(DEFAULT_CHARSET);
            RecordAlteration.HistoryRecordEdition protobufAlteration = protobufConverter.convertToRecordAlteration(author, edition);
            kafkaService.produceToAlteration(
                key,
                wrap(
                    RecordAlteration.AlterationType.UPDATE,
                    RecordAlteration.HistoryRecordEdition.body,
                    protobufAlteration
                ).toByteArray()
            );
            return new CorrectionId(CorrectionType.CHANGE, protobufAlteration.getCreated(), edition.getId());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new InternalServerErrorException(INTERNAL_SERVER_ERROR_DURING_REGISTERING_RECORD_ALTERATION, ex);
        }
    }

    @Override
    public CorrectionId delete(String author, HistoryRecordDeletion deletion) {
        try {
            byte[] key = deletion.getId().getShipmentId().getBytes(DEFAULT_CHARSET);
            RecordAlteration.HistoryRecordDeletion protobufAlteration = protobufConverter.convertToRecordAlteration(author, deletion);
            kafkaService.produceToAlteration(
                key,
                wrap(
                    RecordAlteration.AlterationType.DELETE,
                    RecordAlteration.HistoryRecordDeletion.body,
                    protobufAlteration
                ).toByteArray()
            );
            return new CorrectionId(CorrectionType.DELETE, protobufAlteration.getCreated(), deletion.getId());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new InternalServerErrorException(INTERNAL_SERVER_ERROR_DURING_REGISTERING_RECORD_ALTERATION, ex);
        }
    }

    @Override
    public CorrectionId restore(String author, HistoryRecordRestoration restoration) {
        try {
            byte[] key = restoration.getId().getShipmentId().getBytes(DEFAULT_CHARSET);
            RecordAlteration.HistoryRecordRestoration protobufAlteration = protobufConverter.convertToRecordAlteration(author, restoration);
            kafkaService.produceToAlteration(
                key,
                wrap(
                    RecordAlteration.AlterationType.RESTORE,
                    RecordAlteration.HistoryRecordRestoration.body,
                    protobufAlteration
                ).toByteArray()
            );
            return new CorrectionId(CorrectionType.RESTORE, protobufAlteration.getCreated(), restoration.getId());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new InternalServerErrorException(INTERNAL_SERVER_ERROR_DURING_REGISTERING_RECORD_ALTERATION, ex);
        }
    }

    @Override
    public HistoryRecordCorrection create(String author, HistoryRecordCreation creation) {
        try {
            byte[] key = creation.getShipmentId().getBytes(DEFAULT_CHARSET);
            final RecordAlteration.HistoryRecordCreation alteration = protobufConverter.convertToRecordAlteration(author, creation);
            kafkaService.produceToAlteration(
                key,
                wrap(
                    RecordAlteration.AlterationType.CREATE,
                    RecordAlteration.HistoryRecordCreation.body, alteration
                ).toByteArray()
            );
            return buildCorrection(alteration);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new InternalServerErrorException(INTERNAL_SERVER_ERROR_DURING_REGISTERING_RECORD_ALTERATION, ex);
        }
    }

    private HistoryRecordCorrection buildCorrection(RecordAlteration.HistoryRecordCreation alteration) {
        return HistoryRecordCorrection.create()
            .author(alteration.getAuthor())
            .originShipmentId(alteration.getTargetShipmentId())
            .originIndexOper(alteration.getTargetIndexOper())
            .originOperDate(alteration.getTargetOperDate())
            .originOperType(alteration.getTargetOperType())
            .originOperAttr(alteration.getTargetOperAttr() == -1 ? null : alteration.getTargetOperAttr())
            .initiator(alteration.getInitiator())
            .comment(alteration.getComment())
            .created(alteration.getCreated()).build();
    }

    private static <T> RecordAlteration.AlterationRequest wrap(
        RecordAlteration.AlterationType type,
        GeneratedMessage.GeneratedExtension<RecordAlteration.AlterationRequest, T> extension,
        T protobufObject
    ) {
        return RecordAlteration.AlterationRequest.newBuilder().setBody(type).setExtension(extension, protobufObject).build();
    }
}
