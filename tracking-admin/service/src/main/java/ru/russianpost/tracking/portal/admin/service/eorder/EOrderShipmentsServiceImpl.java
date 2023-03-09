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

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.commons.hdps.dto.eordershipments.v1.EOrderShipment;
import ru.russianpost.tracking.commons.hdps.dto.eordershipments.v1.EOrderShipmentsResponse;
import ru.russianpost.tracking.portal.admin.exception.ServiceUnavailableException;
import ru.russianpost.tracking.portal.admin.model.eorder.EOrderShipmentRecord;
import ru.russianpost.tracking.portal.admin.service.hdps.HdpsClient;

import java.util.List;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

/**
 * @author Amosov Maxim
 * @since 08.07.2021 : 12:41
 */
@Service
@RequiredArgsConstructor
public class EOrderShipmentsServiceImpl implements EOrderShipmentsService {
    private final HdpsClient hdpsClient;

    @Override
    public List<EOrderShipmentRecord> getEOrderShipments(final String eorder) throws ServiceUnavailableException {
        final EOrderShipmentsResponse response = hdpsClient.getEOrderShipments(eorder);
        return response.getShipments().stream().map(this::convertToRecord).collect(toList());
    }

    private EOrderShipmentRecord convertToRecord(final EOrderShipment shipment) {
        return EOrderShipmentRecord.builder()
            .eorder(shipment.getEorder())
            .inn(shipment.getInn())
            .acnt(shipment.getAcnt())
            .barcode(shipment.getBarcode())
            .returnBarcode(shipment.getReturnBarcode())
            .mailCategory(shipment.getMailCtg())
            .mailType(shipment.getMailType())
            .rcpn(nonNull(shipment.getRecipientPersonInfo()) ? shipment.getRecipientPersonInfo().getName() : null)
            .sndr(nonNull(shipment.getSenderPersonInfo()) ? shipment.getSenderPersonInfo().getName() : null)
            .build();
    }
}
