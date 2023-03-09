/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.operation;

import lombok.Getter;
import lombok.Setter;
import ru.russianpost.tracking.portal.admin.model.admin.history.CustomsInfo;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsInformation;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * RpoSummary
 * @author MKitchenko
 */
@Getter
@Setter
public class RpoSummary {
    private Set<String> linkedBarcodes;
    private List<String> postMarkList;
    private List<String> opmIds;
    private SmsInformation smsInformation;
    private String mailType;
    private String mailCategory;
    private String mailRank;
    private Integer deliveryMethod;
    private String transType;
    private String sndr;
    private String senderCategory;
    private String rcpn;
    private CustomsInfo customsInfo;
    private String mpoDeclaration;
    private String multiplaceBarcode;
    private Integer multiplaceDeliveryMethod;
    private String eorderNumber;
    private Long inn;
    private String acnt;
    private boolean opmServiceUnavailable;
    private HyperlocalDelivery hyperlocalDelivery;
    private ReturnParcelInfo returnParcelInfo;
    private Map<String, ExtensionProperty> operProperties;
}
