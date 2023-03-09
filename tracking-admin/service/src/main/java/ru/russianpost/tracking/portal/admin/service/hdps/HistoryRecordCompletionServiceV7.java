/*
 * Copyright 2022 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.service.hdps;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.commons.hdps.dto.Person2;
import ru.russianpost.tracking.commons.hdps.dto.correction.Correction;
import ru.russianpost.tracking.commons.hdps.dto.espp.EsppRecord;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.RpoSummaryV7;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.ShelfLifeV7;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.admin.AdminEsppRecordV7;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.admin.AdminHistoryRecordV7;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.admin.AdminHistoryResponseV7;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.admin.AdminNotificationRecord;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.admin.AppliedCorrectionV7;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.admin.CorrectionsHistoryV7;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.admin.FullHistoryRecordV7;
import ru.russianpost.tracking.portal.admin.ConfigHolder;
import ru.russianpost.tracking.portal.admin.model.operation.CompletedHistoryRecord;
import ru.russianpost.tracking.portal.admin.model.operation.Country;
import ru.russianpost.tracking.portal.admin.model.operation.HistoryRecordId;
import ru.russianpost.tracking.portal.admin.model.operation.Money;
import ru.russianpost.tracking.portal.admin.model.operation.MpoDeclaration;
import ru.russianpost.tracking.portal.admin.model.operation.Notification;
import ru.russianpost.tracking.portal.admin.model.operation.OperationData;
import ru.russianpost.tracking.portal.admin.model.operation.PostalOrderEvent;
import ru.russianpost.tracking.portal.admin.model.operation.RegistrableMailInfoV7;
import ru.russianpost.tracking.portal.admin.model.operation.RpoSummary;
import ru.russianpost.tracking.portal.admin.model.operation.ShelfLife;
import ru.russianpost.tracking.portal.admin.model.operation.UkdInfo;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectedCompletedHistoryRecord;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectedCompletedHistoryRecordCorrection;
import ru.russianpost.tracking.portal.admin.model.operation.correction.CorrectionType;
import ru.russianpost.tracking.portal.admin.model.operation.correction.Corrections;
import ru.russianpost.tracking.portal.admin.model.ui.AdminUser;
import ru.russianpost.tracking.portal.admin.repository.Dictionary;
import ru.russianpost.tracking.portal.admin.service.mappers.CorrectionTypeMapper;
import ru.russianpost.tracking.portal.admin.service.users.CorrectionAuthorInfoService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.AD_VAL_TAX;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.AIR_RATE;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.COMPULSORY_PAYMENT;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.CUSTOM_DUTY;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.INSR_RATE;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.MASS_RATE;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.PAYMENT;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.RATE;
import static ru.russianpost.tracking.portal.admin.model.MoneyType.VALUE;
import static ru.russianpost.tracking.portal.admin.model.WeightType.DECLARED;
import static ru.russianpost.tracking.portal.admin.model.WeightType.VOLUME;
import static ru.russianpost.tracking.portal.admin.model.operation.MpoDeclaration.WITH_AN_INVENTORY_OF_THE_ATTACHMENT;
import static ru.russianpost.tracking.portal.admin.model.operation.MpoDeclaration.WITH_ELECTRONIC_CUSTOMS_DECLARATION;
import static ru.russianpost.tracking.portal.admin.model.operation.UkdInfo.EMPTY_UKD_INFO;

/**
 * HistoryRecordCompletionServiceV7
 *
 * @author MKitchenko
 * @version 1.0 (June 07, 2019)
 */
@Service
@RequiredArgsConstructor
public class HistoryRecordCompletionServiceV7 {

    private static final Integer RUSSIAN_COUNTRY_CODE = 643;
    private static final Pattern INTERNATIONAL_BARCODE_REGEXP = Pattern.compile(ConfigHolder
        .CONFIG.getString("regexp.international.barcode"));
    private static final List<Integer> MAIL_TYPES_4_SHOW_UKD_INFO = ConfigHolder.CONFIG.getIntList("relates-to-ukd-mailTypes");

    private final Dictionary dictionary;
    private final CorrectionAuthorInfoService correctionAuthorInfoService;
    private final CorrectionTypeMapper correctionTypeMapper;
    private final HistoryRecordFieldResolver fieldResolver;

    /**
     * Build info.
     *
     * @param response history response
     * @return registrable mail info
     */
    public RegistrableMailInfoV7<CompletedHistoryRecord> buildInfo(AdminHistoryResponseV7 response) {
        final List<AdminHistoryRecordV7> responseHistory = response.getHistory();
        final List<CompletedHistoryRecord> completedHistory = responseHistory.stream()
            .map(r -> complete(r.getRecord(), r.isHidden(), r.getHideReason()))
            .collect(Collectors.toList());

        return new RegistrableMailInfoV7<>(
            completedHistory,
            resolveRpoSummary(response.getSummary(), resolveMpoDeclaration(response), responseHistory),
            response.isHasHiddenHistory(),
            resolvePostalOrderHistory(response.getEsppHistory()),
            resolveBoolean(response.getHasHiddenEsppHistory()),
            resolveNotifications(response.getNotifications()),
            resolveBoolean(response.getHasHiddenNotificationHistory())
        );
    }

    /**
     * Build full info
     *
     * @param response history response
     * @return registrable mail info
     */
    public RegistrableMailInfoV7<CorrectedCompletedHistoryRecord> buildFullInfo(AdminHistoryResponseV7 response) {
        final List<AdminHistoryRecordV7> responseHistory = response.getHistory();
        final Map<String, AdminUser> authorsMap = responseHistory.stream()
            .filter(r -> nonNull(r.getCorrectionsHistory()) && nonNull(r.getCorrectionsHistory().getAppliedCorrections()))
            .flatMap(r -> r.getCorrectionsHistory().getAppliedCorrections().stream().map(c -> c.getCorrection().author))
            .distinct()
            .filter(Objects::nonNull)
            .collect(toMap(Function.identity(), correctionAuthorInfoService::resolveAuthor));

        final List<CorrectedCompletedHistoryRecord> completedHistory = responseHistory.stream()
            .map(r -> complete(r, authorsMap))
            .collect(toList());

        return new RegistrableMailInfoV7<>(
            completedHistory,
            resolveRpoSummary(response.getSummary(), resolveMpoDeclaration(response), responseHistory),
            response.isHasHiddenHistory(),
            resolvePostalOrderHistory(response.getEsppHistory()),
            resolveBoolean(response.getHasHiddenEsppHistory()),
            resolveNotifications(response.getNotifications()),
            resolveBoolean(response.getHasHiddenNotificationHistory())
        );
    }

    private MpoDeclaration resolveMpoDeclaration(AdminHistoryResponseV7 response) {
        final boolean hasMpoDeclaration =
            ofNullable(response.getSummary()).map(RpoSummaryV7::getHasMpoDeclaration).orElse(FALSE);

        if (!hasMpoDeclaration) {
            return MpoDeclaration.EMPTY;
        }

        if (
            INTERNATIONAL_BARCODE_REGEXP.matcher(response.getBarcode()).matches()
                && response.getHistory().stream()
                .anyMatch(r -> !r.isHidden()
                    && r.getRecord().getMailDirect() != null
                    && !RUSSIAN_COUNTRY_CODE.equals(r.getRecord().getMailDirect())
                )
        ) {
            return WITH_ELECTRONIC_CUSTOMS_DECLARATION;
        } else {
            return WITH_AN_INVENTORY_OF_THE_ATTACHMENT;
        }
    }

    private List<Notification> resolveNotifications(List<AdminNotificationRecord> notifications) {
        return isNull(notifications) ? Collections.emptyList() : notifications.stream()
            .map(item -> {
                    final ru.russianpost.tracking.commons.hdps.dto.history.v7.admin.Notification notification =
                        item.getNotification();

                    return Notification.builder()
                        .shipmentId(notification.getShipmentId())
                        .operType(notification.getOperType())
                        .operDate(notification.getOperDate())
                        .zoneOffsetSeconds(notification.getZoneOffsetSeconds())
                        .indexOper(notification.getIndexOper())
                        .operAddress(fieldResolver.resolveAddress(
                            notification.getIndexOper(),
                            notification.getIndexOperDesc(),
                            notification.getCountryOper(),
                            notification.getDataProviderType()
                        ))
                        .operAttr(notification.getOperAttr())
                        .indexTo(notification.getIndexTo())
                        .destAddress(fieldResolver.resolveAddress(
                            notification.getIndexTo(),
                            notification.getIndexToDesc(),
                            notification.getMailDirect(),
                            notification.getDataProviderType()
                        ))
                        .hidden(item.isHidden())
                        .hideReasonDescription(dictionary.resolveHideReasonDescription(item.getHideReason()))
                        .build();
                }
            )
            .collect(Collectors.toList());
    }

    private CorrectedCompletedHistoryRecord complete(
        final AdminHistoryRecordV7 record,
        final Map<String, AdminUser> authorNameToUser
    ) {
        final CorrectionsHistoryV7 correctionsHistory = record.getCorrectionsHistory();
        final List<AppliedCorrectionV7> corrections = nonNull(correctionsHistory) ? correctionsHistory.getAppliedCorrections() : emptyList();
        List<CorrectedCompletedHistoryRecordCorrection> changes;
        CompletedHistoryRecord finalRecord = complete(record.getRecord(), record.isHidden(), record.getHideReason());
        if (!corrections.isEmpty()) {
            FullHistoryRecordV7 originRecord = correctionsHistory.getOriginRecord();
            finalRecord = this.complete(
                record.getRecord(),
                new HistoryRecordId(
                    originRecord.getShipmentId(),
                    originRecord.getOperDate(),
                    originRecord.getOperType(),
                    originRecord.getOperAttr(),
                    originRecord.getIndexOper()
                ),
                record.isHidden(),
                record.getHideReason()
            );
            changes = corrections.stream().map(r -> {
                final Correction correction = r.getCorrection();
                return new CorrectedCompletedHistoryRecordCorrection(
                    correctionTypeMapper.by(correction.type),
                    correction.initiator,
                    authorNameToUser.get(correction.author),
                    correction.created,
                    correction.comment,
                    Corrections.from(correction),
                    complete(r.getCorrectedState(), record.isHidden(), record.getHideReason())
                );
            }).collect(Collectors.toList());
        } else {
            changes = new ArrayList<>();
        }
        if (corrections.stream()
            .noneMatch(c -> c.getCorrection().type == ru.russianpost.tracking.commons.hdps.dto.correction.CorrectionType.CREATE)) {
            if (isNull(correctionsHistory) || corrections.isEmpty()) {
                changes.add(0, buildFakeCreateCorrection(finalRecord));
            } else {
                changes.add(
                    0,
                    buildFakeCreateCorrection(
                        complete(
                            correctionsHistory.getOriginRecord(),
                            record.isHidden(),
                            record.getHideReason()
                        )
                    )
                );
            }
        }
        return new CorrectedCompletedHistoryRecord(finalRecord, changes);
    }

    private CorrectedCompletedHistoryRecordCorrection buildFakeCreateCorrection(CompletedHistoryRecord originHistoryRecord) {
        return new CorrectedCompletedHistoryRecordCorrection(
            CorrectionType.CREATE,
            null,
            null,
            originHistoryRecord.getIncomeDate(),
            null,
            null,
            originHistoryRecord
        );
    }

    /**
     * Completes history record with dictionary information
     *
     * @param historyRecord history record
     * @param isHidden      mark record as hidden
     * @param hideReason    hide reason
     * @return completed history record
     */
    private CompletedHistoryRecord complete(final FullHistoryRecordV7 historyRecord, boolean isHidden, String hideReason) {
        return complete(
            historyRecord,
            new HistoryRecordId(
                historyRecord.getShipmentId(),
                historyRecord.getOperDate(),
                historyRecord.getOperType(),
                historyRecord.getOperAttr(),
                historyRecord.getIndexOper()
            ),
            isHidden,
            hideReason
        );
    }

    /**
     * Completes history record with dictionary information and given id.
     * Used for prevent change id from afterSet if id property was changed
     *
     * @param historyRecord history record
     * @param id            id of history record
     * @param hidden        mark record as hidden
     * @param hideReason    hide reason
     * @return completed history record
     */
    private CompletedHistoryRecord complete(final FullHistoryRecordV7 historyRecord, final HistoryRecordId id, boolean hidden, String hideReason) {
        final OperationData operationData = OperationData.builder()
            .typeId(historyRecord.getOperType())
            .attrId(historyRecord.getOperAttr())
            .index(historyRecord.getIndexOper())
            .address(fieldResolver.resolveAddress(
                historyRecord.getIndexOper(), historyRecord.getIndexOperDesc(), historyRecord.getCountryOper(), historyRecord.getDataProviderType()
            ))
            .date(historyRecord.getOperDate())
            .dateOffset(historyRecord.getZoneOffsetSeconds())
            .build();

        final Money insrRate = fieldResolver.resolveMoneyByType(historyRecord.getMoneyInfo(), INSR_RATE);
        final Money sendPrice = fieldResolver.resolveMoneyByType(historyRecord.getMoneyInfo(), MASS_RATE);
        final Money totalMassRate = fieldResolver.resolveTotalMassRate(insrRate, sendPrice);
        final UkdInfo ukdInfo = MAIL_TYPES_4_SHOW_UKD_INFO.contains(historyRecord.getMailType()) ?
            dictionary.getUkdInfo(historyRecord.getIndexTo()) : EMPTY_UKD_INFO;

        return CompletedHistoryRecord.builder()
            .id(id)
            .operationData(operationData)
            .destAddress(fieldResolver.resolveAddress(
                historyRecord.getIndexTo(), historyRecord.getIndexToDesc(), historyRecord.getMailDirect(), historyRecord.getDataProviderType()
            ))
            .destIndex(historyRecord.getIndexTo())
            .apsName(dictionary.getApsName(historyRecord.getIndexTo()))
            .ukdIndex(ukdInfo.getIndex())
            .ukdName(ukdInfo.getName())
            .incomeDate(historyRecord.getIncomeDate())
            .loadDate(historyRecord.getLoadDate())
            .softwareName(dictionary.getSoftwareNameByVersion(historyRecord.getSoftwareVersion()))
            .softwareVersion(historyRecord.getSoftwareVersion())
            .dataProvider(historyRecord.getDataProviderType())
            .rcpn(fieldResolver.resolvePersonName(historyRecord.getRecipientPersonInfo()))
            .sndr(fieldResolver.resolvePersonName(historyRecord.getSenderPersonInfo()))
            .phoneRecipient(historyRecord.getPhoneRecipient())
            .phoneSender(historyRecord.getPhoneSender())
            .mailCategory(historyRecord.getMailCtg())
            .mailType(historyRecord.getMailType())
            .mass(fieldResolver.resolveMass(historyRecord.getWeightInfo()))
            .declaredWeight(fieldResolver.resolveMassByType(historyRecord.getWeightInfo(), DECLARED))
            .volumeWeight(fieldResolver.resolveMassByType(historyRecord.getWeightInfo(), VOLUME))
            .value(fieldResolver.resolveMoneyByType(historyRecord.getMoneyInfo(), VALUE))
            .payment(fieldResolver.resolveMoneyByType(historyRecord.getMoneyInfo(), PAYMENT))
            .compulsoryPayment(fieldResolver.resolveMoneyByType(historyRecord.getMoneyInfo(), COMPULSORY_PAYMENT))
            .insrRate(insrRate)
            .totalMassRate(totalMassRate)
            .customDuty(fieldResolver.resolveMoneyByType(historyRecord.getMoneyInfo(), CUSTOM_DUTY))
            .sendPrice(sendPrice)
            .sendAirPrice(fieldResolver.resolveMoneyByType(historyRecord.getMoneyInfo(), AIR_RATE))
            .nds(fieldResolver.resolveMoneyByType(historyRecord.getMoneyInfo(), AD_VAL_TAX))
            .additionalTariff(fieldResolver.resolveMoneyByType(historyRecord.getMoneyInfo(), RATE))
            .transType(dictionary.getRtm02TransTypeName(historyRecord.getTransType()))
            .smsInformation(fieldResolver.resolveSmsInformation(historyRecord.getSmsInformation()))
            .hidden(hidden)
            .hideReason(hideReason)
            .hideReasonDescription(dictionary.resolveHideReasonDescription(hideReason))
            .internum(historyRecord.getInternum())
            .shelfLife(resolveShelfLife(historyRecord.getShelfLife()))
            .packageType(historyRecord.getPackageType())
            .packageKind(historyRecord.getPackageKind())
            .multiplaceRpoInfo(fieldResolver.resolveMultiplaceRpoInfo(historyRecord.getMultiplaceRpoInfo()))
            .delivery(fieldResolver.resolveDelivery(historyRecord.getDelivery()))
            .payType(historyRecord.getPayType())
            .build();
    }

    private ShelfLife resolveShelfLife(final ShelfLifeV7 shelfLife) {
        return ofNullable(shelfLife)
            .map(sl -> ShelfLife.of(sl.getRetentionEndDate(), sl.getRetentionPeriod()))
            .orElse(null);
    }

    private RpoSummary resolveRpoSummary(
        final RpoSummaryV7 summary,
        final MpoDeclaration mpoDeclaration,
        final List<AdminHistoryRecordV7> responseHistory
    ) {
        final RpoSummary result = new RpoSummary();

        if (nonNull(summary)) {
            result.setLinkedBarcodes(ofNullable(summary.getLinkedBarcodes()).orElseGet(Collections::emptySet));

            result.setMailType(resolveSummaryFieldValue(summary.getMailType(), dictionary::getRtm02MailTypeName));
            result.setMailCategory(resolveSummaryFieldValue(summary.getMailCtg(), dictionary::getRtm02MailCtgName));
            result.setMailRank(resolveSummaryFieldValue(summary.getMailRank(), dictionary::getRtm02MailRankName));
            result.setTransType(resolveSummaryFieldValue(summary.getTransType(), dictionary::getRtm02TransTypeName));
            result.setSenderCategory(resolveSummaryFieldValue(summary.getSenderCategory(), dictionary::getRtm02SendCtgName));

            result.setSndr(ofNullable(summary.getSenderPersonInfo()).map(Person2::getName).orElse(EMPTY));
            result.setRcpn(ofNullable(summary.getRecipientPersonInfo()).map(Person2::getName).orElse(EMPTY));

            result.setCustomsInfo(fieldResolver.resolveCustomsInfo(summary.getCustomsInfo()));
            result.setPostMarkList(fieldResolver.resolvePostmarks(summary.getPostmarks()));
            result.setSmsInformation(fieldResolver.resolveSmsInformation(summary.getSmsInformation()));
            result.setMpoDeclaration(mpoDeclaration.getDescription());
            result.setMultiplaceBarcode(summary.getMultiplaceBarcode());
            result.setMultiplaceDeliveryMethod(summary.getMultiplaceDeliveryMethod());
            result.setDeliveryMethod(summary.getDeliveryMethod());
            result.setEorderNumber(resolveEorderNumber(responseHistory));
            result.setInn(summary.getInn());
            result.setAcnt(summary.getAcnt());
            result.setHyperlocalDelivery(fieldResolver.resolveHyperlocalDelivery(summary.getHyperlocalDelivery()));
            result.setReturnParcelInfo(fieldResolver.resolveReturnParcelInfo(summary.getReturnParcelInfo()));
            result.setOperProperties(fieldResolver.resolveOperProperties(summary.getOperProperties()));
        }

        return result;
    }

    private String resolveSummaryFieldValue(Integer field, IntFunction<String> function) {
        return ofNullable(field).map(function::apply).orElse(EMPTY);
    }

    @Nullable
    private String resolveEorderNumber(List<AdminHistoryRecordV7> responseHistory) {
        return responseHistory.stream()
            .filter(record -> !record.isHidden() && record.getRecord().getEorder() != null)
            .map(record -> record.getRecord().getEorder().getNumber())
            .filter(Objects::nonNull)
            .findFirst().orElse(null);
    }

    private List<PostalOrderEvent> resolvePostalOrderHistory(List<AdminEsppRecordV7> esppHistory) {
        return ofNullable(esppHistory)
            .map(history -> history.stream()
                .map(this::resolveEsppRecord)
                .collect(toList()))
            .orElseGet(Collections::emptyList);
    }

    private PostalOrderEvent resolveEsppRecord(AdminEsppRecordV7 record) {
        final EsppRecord esppRecord = record.getRecord();
        final Integer countryEventCode = resolveCountryCodeByCodeA2(esppRecord.getCountryEventCode());
        final Integer countryToCode = resolveCountryCodeByCodeA2(esppRecord.getCountryToCode());
        final String indexEvent = Integer.toString(esppRecord.getIndexEvent());
        final String indexTo = Integer.toString(esppRecord.getIndexTo());

        return PostalOrderEvent.builder()
            .number(esppRecord.getNumber())
            .barcode(esppRecord.getBarcode())
            .eventType(dictionary.getPostalOrderEventType(esppRecord.getEventType()))
            .eventDateTime(esppRecord.getEventDateTime())
            .timeZoneOffsetInSeconds(esppRecord.getTimeZoneOffsetInSeconds())
            .indexEvent(indexEvent)
            .eventAddress(fieldResolver.resolveAddress(indexEvent, null, countryEventCode, null))
            .indexTo(indexTo)
            .destAddress(fieldResolver.resolveAddress(indexTo, null, countryToCode, null))
            .sumPaymentForward(esppRecord.getSumPaymentForward())
            .hidden(record.isHidden())
            .hideReasonDescription(dictionary.resolveHideReasonDescription(record.getHideReason()))
            .build();
    }

    @Nullable
    private Integer resolveCountryCodeByCodeA2(final String code2A) {
        if (isNull(code2A) || code2A.length() != 2) {
            return null;
        }
        return dictionary.getCountries().stream()
            .filter(item -> Objects.equals(code2A, item.getCodeA2()))
            .findFirst()
            .map(Country::getId)
            .orElse(null);
    }

    private boolean resolveBoolean(Boolean value) {
        return !isNull(value) && value;
    }
}
