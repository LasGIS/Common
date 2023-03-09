/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.portal.admin.model.operation.Country;
import ru.russianpost.tracking.portal.admin.model.operation.UkdInfo;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsNotificationType;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.AllowedToAddOperationsHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.HideReasonDescriptionsHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.IndexCountryHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SmsNotificationTypesHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SmsTypeOrderHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.SoftwareHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.operations.OperationInfo;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.operations.OperationOrderHolder;
import ru.russianpost.tracking.web.model.core.AccessType;

import java.math.BigInteger;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

/**
 * @author MKitchenko
 * @version 1.0 (Feb 28, 2019)
 */
@Service
@RequiredArgsConstructor
public class DictionaryService implements Dictionary {

    private final BackendDictionary backendDictionary;
    private final IndexCountryHolder indexCountryHolder;
    private final SmsNotificationTypesHolder smsNotificationTypesHolder;
    private final SmsTypeOrderHolder smsTypeOrderHolder;
    private final HideReasonDescriptionsHolder hideReasonDescriptionsHolder;
    private final AllowedToAddOperationsHolder allowedToAddOperationsHolder;
    private final OperationOrderHolder operationOrderHolder;
    private final SoftwareHolder softwareHolder;

    @Override
    public Map<Integer, String> getRtm02OperTypes() {
        return backendDictionary.getOperTypes();
    }

    @Override
    public Map<Integer, Map<Integer, String>> getRtm02OperAttrs() {
        return backendDictionary.getOperAttrs();
    }

    @Override
    public Map<Integer, String> getRtm02MailCategories() {
        return backendDictionary.getMailCategories();
    }

    @Override
    public Map<Integer, String> getRtm02MailTypes() {
        return backendDictionary.getMailTypes();
    }

    @Override
    public Map<Integer, String> getRtm02CustomsStatuses() {
        return backendDictionary.getCustomsStatuses();
    }

    @Override
    public String getRtm02OperTypeName(Integer operType) {
        return backendDictionary.getOperTypeName(operType);
    }

    @Override
    public String getRtm02OperAttrName(Integer operType, Integer operAttr) {
        return backendDictionary.getOperAttrName(operType, operAttr);
    }

    @Override
    public String getRtm02DefaultOperAttrName(Integer operType, Integer operAttr) {
        return backendDictionary.getDefaultOperAttrName(operType, operAttr);
    }

    @Override
    public String getRtm02MailTypeName(Integer id) {
        return backendDictionary.getMailTypeName(id);
    }

    @Override
    public String getRtm02MailCtgName(Integer id) {
        return backendDictionary.getMailCtgName(id);
    }

    @Override
    public String getRtm02SendCtgName(Integer id) {
        return backendDictionary.getSendCtgName(id);
    }

    @Override
    public String getRtm02PostMarkName(BigInteger id) {
        return backendDictionary.getPostMarkName(id);
    }

    @Override
    public String getRtm02TransTypeName(Integer id) {
        return backendDictionary.getTransTypeName(id);
    }

    @Override
    public String getRtm02MailRankName(Integer id) {
        return backendDictionary.getMailRankName(id);
    }

    @Override
    public Map<Integer, String> getPostalOrderEventTypes() {
        return backendDictionary.getPostalOrderEventTypes();
    }

    @Override
    public String getPostalOrderEventType(Integer id) {
        return backendDictionary.getPostalOrderEventType(id);
    }

    @Override
    public String getAddressDesc(String index) {
        return backendDictionary.getAddressDesc(index);
    }

    @Override
    public UkdInfo getUkdInfo(final String index) {
        return backendDictionary.getUkdInfo(index);
    }

    @Override
    public String getApsName(final String index) {
        return backendDictionary.getApsName(index);
    }

    @Override
    public String getUfpsName(final String index) {
        return backendDictionary.getUfpsName(index);
    }

    @Override
    public List<Country> getCountries() {
        return backendDictionary.getCountries();
    }

    @Override
    public Country getCountryById(Integer id) {
        return backendDictionary.getCountryById(id);
    }

    @Override
    public ZoneOffset getZoneOffsetForIndex(String index) {
        return backendDictionary.getZoneOffsetForIndex(index);
    }

    @Override
    public Map<String, Integer> getCurrencyDecimalPlaces() {
        return backendDictionary.getCurrencyDecimalPlaces();
    }

    @Override
    public String getSoftwareNameByVersion(String softwareVersion) {
        return softwareHolder.getSoftwareNameByVersion(softwareVersion);
    }

    @Override
    public List<AccessType> getPredefinedAccessTypes() {
        return backendDictionary.getPredefinedAccessTypes();
    }

    @Override
    public SmsNotificationType getSmsNotificationType(Integer id) {
        return smsNotificationTypesHolder.getSmsNotificationType(id);
    }

    @Override
    public Map<Integer, SmsNotificationType> getSmsNotificationMap() {
        return smsNotificationTypesHolder.getMap();
    }

    @Override
    public Map<Integer, Integer> getSmsTypeOrderMap() {
        return smsTypeOrderHolder.getSmsTypeOrderMap();
    }

    @Override
    public String resolveHideReasonDescription(String hideReason) {
        return hideReasonDescriptionsHolder.resolve(hideReason);
    }

    @Override
    public Integer getCountry(String index) {
        return indexCountryHolder.getCountry(index);
    }

    @Override
    public List<OperationInfo> getOperationOrderList() {
        return operationOrderHolder.getOperationOrderList();
    }

    @Override
    public Map<Integer, List<Integer>> getAllowedToAddOperations() {
        return allowedToAddOperationsHolder.getAllowedToAddOperations();
    }

    @Override
    public Map<Integer, String> getPackageTypes() {
        return backendDictionary.getPackageTypes();
    }

    @Override
    public Map<Integer, String> getPackageKinds() {
        return backendDictionary.getPackageKinds();
    }
}
