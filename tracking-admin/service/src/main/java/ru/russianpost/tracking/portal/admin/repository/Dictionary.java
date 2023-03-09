/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.repository;

import ru.russianpost.tracking.portal.admin.model.operation.Country;
import ru.russianpost.tracking.portal.admin.model.operation.UkdInfo;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsNotificationType;
import ru.russianpost.tracking.portal.admin.repository.holders.from.file.operations.OperationInfo;
import ru.russianpost.tracking.web.model.core.AccessType;

import java.math.BigInteger;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Nov 26, 2015)
 */
public interface Dictionary {

    /**
     * Gets operation types map.
     *
     * @return Map of operation type codes to its names
     */
    Map<Integer, String> getRtm02OperTypes();

    /**
     * Gets operation attributes map.
     *
     * @return Map where key is operation type code and value is a map of operation attribute codes to its names
     */
    Map<Integer, Map<Integer, String>> getRtm02OperAttrs();

    /**
     * Gets operation mail categories map.
     *
     * @return Map of operation mail categories codes to its names
     */
    Map<Integer, String> getRtm02MailCategories();

    /**
     * Gets operation mail types map.
     *
     * @return Map of operation mail types codes to its names
     */
    Map<Integer, String> getRtm02MailTypes();

    /**
     * Gets customs statuses map.
     *
     * @return Map of customs statuses codes to its names
     */
    Map<Integer, String> getRtm02CustomsStatuses();

    /**
     * Operation type name by operation type code
     *
     * @param operType operation type code
     * @return Operation type name
     */
    String getRtm02OperTypeName(Integer operType);

    /**
     * Operation attribute name by operation type code and operation attribute code
     *
     * @param operType operation type code
     * @param operAttr operation attribute code
     * @return Operation type name
     */
    String getRtm02OperAttrName(Integer operType, Integer operAttr);

    /**
     * Default operation attribute name by operation type code and operation attribute code
     *
     * @param operType operation type code
     * @param operAttr operation attribute code
     * @return default operation type name
     */
    String getRtm02DefaultOperAttrName(Integer operType, Integer operAttr);

    /**
     * Returns rtm02 mail type description by it's id.
     *
     * @param id rtm02 parameter id
     * @return found description or null if not found
     */
    String getRtm02MailTypeName(final Integer id);

    /**
     * Returns rtm02 mail ctg description by it's id.
     *
     * @param id rtm02 parameter id
     * @return found description or null if not found
     */
    String getRtm02MailCtgName(final Integer id);

    /**
     * Returns rtm02 send ctg description by it's id.
     *
     * @param id rtm02 parameter id
     * @return found description or null if not found
     */
    String getRtm02SendCtgName(final Integer id);

    /**
     * Returns rtm02 post mark description by it's id.
     *
     * @param id rtm02 parameter id
     * @return found description or null if not found
     */
    String getRtm02PostMarkName(final BigInteger id);

    /**
     * Returns rtm02 trans type description by it's id.
     *
     * @param id rtm02 parameter id
     * @return found description or null if not found
     */
    String getRtm02TransTypeName(Integer id);

    /**
     * Returns rtm02 mail rank description by it's id.
     *
     * @param id rtm02 parameter id
     * @return found description or null if not found
     */
    String getRtm02MailRankName(final Integer id);

    /**
     * Returns postal order event type map.
     *
     * @return postal order event type map.
     */
    Map<Integer, String> getPostalOrderEventTypes();

    /**
     * Returns postal order event type by it`s id.
     *
     * @param id postal order event id
     * @return postal order event type.
     */
    String getPostalOrderEventType(Integer id);

    /**
     * Address description by index
     *
     * @param index index
     * @return Address description
     */
    String getAddressDesc(String index);

    /**
     * Get Ukd Info by index
     *
     * @param index index
     * @return Ukd Info
     */
    UkdInfo getUkdInfo(String index);

    /**
     * Get aps name
     *
     * @param index index
     * @return aps name
     */
    String getApsName(String index);

    /**
     * Get UFPS name
     *
     * @param index index
     * @return UFPS name
     */
    String getUfpsName(final String index);

    /**
     * Returns all countries
     *
     * @return countries
     */
    List<Country> getCountries();

    /**
     * Returns country by id
     *
     * @param id country id
     * @return country by id
     */
    Country getCountryById(Integer id);

    /**
     * Returns time zone offset for the postal index, based on dictionary info.
     *
     * @param index postal index.
     * @return time zone offset.
     */
    ZoneOffset getZoneOffsetForIndex(final String index);

    /**
     * Returns currency decimal places map.
     *
     * @return currency decimal places map.
     */
    Map<String, Integer> getCurrencyDecimalPlaces();

    /**
     * Returns software name for the {@code softwareVesion}, based on dictionary info.
     *
     * @param softwareVersion software version.
     * @return software name or null if not found.
     */
    String getSoftwareNameByVersion(final String softwareVersion);


    /**
     * Returns list of predefined access type.
     *
     * @return list of predefined access type
     */
    List<AccessType> getPredefinedAccessTypes();

    /**
     * Returns sms notification type by id
     *
     * @param id sms notification type id
     * @return sms notification type by id
     */
    SmsNotificationType getSmsNotificationType(final Integer id);

    /**
     * Returns sms notification types map
     *
     * @return sms notification types map
     */
    Map<Integer, SmsNotificationType> getSmsNotificationMap();

    /**
     * Returns Sms type id to order map.
     *
     * @return Sms type id to order map.
     */
    Map<Integer, Integer> getSmsTypeOrderMap();

    /**
     * Returns human readable hide reason
     *
     * @param hideReason hideReason key
     * @return human readable hide reason
     */
    String resolveHideReasonDescription(String hideReason);

    /**
     * Get code of country by index.
     *
     * @param index of place of operation.
     * @return code of country for index.
     */
    Integer getCountry(final String index);

    /**
     * Returns operation info order list
     *
     * @return Operation info order list.
     */
    List<OperationInfo> getOperationOrderList();

    /**
     * Gets allowed to add operations.
     *
     * @return Map where key is operation type code and value is a map of operation attribute codes to its names
     */
    Map<Integer, List<Integer>> getAllowedToAddOperations();

    /**
     * Returns package types map
     *
     * @return package types map
     */
    Map<Integer, String> getPackageTypes();

    /**
     * Returns package kinds map
     *
     * @return package kinds map
     */
    Map<Integer, String> getPackageKinds();
}
