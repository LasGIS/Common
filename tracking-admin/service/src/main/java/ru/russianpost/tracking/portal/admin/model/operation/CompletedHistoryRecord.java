/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.operation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsInformation;

/**
 * @author MKitchenko
 * @version 4.1 (dec 24, 2019)
 */
@Builder
@Getter
@AllArgsConstructor
public class CompletedHistoryRecord {
    private final HistoryRecordId id;
    @JsonIgnore
    private final OperationData operationData;
    private final Long mass;
    private final Long declaredWeight;
    private final Long volumeWeight;
    private final Money value;
    private final Money payment;
    private final Money compulsoryPayment;
    private final Money insrRate;
    private final Money totalMassRate;
    private final Money customDuty;
    private final String destIndex;
    private final String destAddress;
    private final String ukdIndex;
    private final String ukdName;
    private final String internum;
    private final Long incomeDate;
    private final Long loadDate;
    private final String softwareName;
    private final String softwareVersion;
    private final String dataProvider;
    private final String rcpn;
    private final String sndr;
    private final String phoneRecipient;
    private final String phoneSender;
    private final Integer mailCategory;
    private final Integer mailType;
    private final Money sendPrice;
    private final Money sendAirPrice;
    @JsonProperty("NDS")
    private final Money nds;
    private final Money additionalTariff;
    private final String transType;
    private final SmsInformation smsInformation;
    private final boolean hidden;
    private final String hideReason;
    private final String hideReasonDescription;
    private final ShelfLife shelfLife;
    private final Integer packageType;
    private final Integer packageKind;
    private final MultiplaceRpoInfo multiplaceRpoInfo;
    private final String apsName;
    private final DeliveryInfo delivery;
    private final Integer payType;

    /**
     * Creates a copy of CompletedHistoryRecord.
     *
     * @param rec id
     */
    protected CompletedHistoryRecord(CompletedHistoryRecord rec) {
        this(
            rec.id,
            rec.operationData,
            rec.mass,
            rec.declaredWeight,
            rec.volumeWeight,
            rec.value,
            rec.payment,
            rec.compulsoryPayment,
            rec.insrRate,
            rec.totalMassRate,
            rec.customDuty,
            rec.destIndex,
            rec.destAddress,
            rec.ukdIndex,
            rec.ukdName,
            rec.internum,
            rec.incomeDate,
            rec.loadDate,
            rec.softwareName,
            rec.softwareVersion,
            rec.dataProvider,
            rec.rcpn,
            rec.sndr,
            rec.phoneRecipient,
            rec.phoneSender,
            rec.mailCategory,
            rec.mailType,
            rec.sendPrice,
            rec.sendAirPrice,
            rec.nds,
            rec.additionalTariff,
            rec.transType,
            rec.smsInformation,
            rec.hidden,
            rec.hideReason,
            rec.hideReasonDescription,
            rec.shelfLife,
            rec.packageType,
            rec.packageKind,
            rec.multiplaceRpoInfo,
            rec.apsName,
            rec.delivery,
            rec.payType
        );
    }

    public Integer getOperType() {
        return operationData.getTypeId();
    }

    public Integer getOperAttr() {
        return operationData.getAttrId();
    }

    public String getOperIndex() {
        return operationData.getIndex();
    }

    public String getOperAddress() {
        return operationData.getAddress();
    }

    public Long getOperDate() {
        return operationData.getDate();
    }

    public Integer getOperDateOffset() {
        return operationData.getDateOffset();
    }
}
