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

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.russianpost.tracking.commons.hdps.dto.Delivery;
import ru.russianpost.tracking.commons.hdps.dto.MoneyInfo;
import ru.russianpost.tracking.commons.hdps.dto.Person2;
import ru.russianpost.tracking.commons.hdps.dto.WeightInfo;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.CustomsInfoV7;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.MultiplaceRpoInfoV7;
import ru.russianpost.tracking.commons.hdps.dto.history.v7.ReturnParcelInfoV7;
import ru.russianpost.tracking.commons.hdps.dto.sms.SmsInfo2;
import ru.russianpost.tracking.commons.utils.PostmarksUtils;
import ru.russianpost.tracking.portal.admin.model.MoneyType;
import ru.russianpost.tracking.portal.admin.model.WeightType;
import ru.russianpost.tracking.portal.admin.model.admin.history.CustomsInfo;
import ru.russianpost.tracking.portal.admin.model.operation.Country;
import ru.russianpost.tracking.portal.admin.model.operation.DeliveryInfo;
import ru.russianpost.tracking.portal.admin.model.operation.ExtensionObject;
import ru.russianpost.tracking.portal.admin.model.operation.ExtensionProperty;
import ru.russianpost.tracking.portal.admin.model.operation.HyperlocalDelivery;
import ru.russianpost.tracking.portal.admin.model.operation.Money;
import ru.russianpost.tracking.portal.admin.model.operation.MultiplaceRpoInfo;
import ru.russianpost.tracking.portal.admin.model.operation.ReturnParcelInfo;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsInformation;
import ru.russianpost.tracking.portal.admin.model.operation.sms.SmsNotification;
import ru.russianpost.tracking.portal.admin.repository.Dictionary;
import ru.russianpost.tracking.portal.admin.utils.WeightResolver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Boolean.FALSE;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static ru.russianpost.tracking.portal.admin.model.WeightType.ACTUAL;

/**
 * @author MKitchenko
 * @version 1.0 (June 17, 2019)
 */
@Service
public class HistoryRecordFieldResolver {

    private static final Integer RUSSIA_COUNTRY_CODE = 643;
    private static final String INTERNATIONAL_DATA_PROVIDER = "upu";
    private static final Pattern INTERNATIONAL_DATA_PROVIDER_PATTERN = Pattern.compile("^" + INTERNATIONAL_DATA_PROVIDER + "$");
    private static final Pattern RUSSIAN_INDEX_PATTERN = Pattern.compile("^[0-9]{6}$");

    private final Dictionary dictionary;

    /**
     * Constructor
     *
     * @param dictionary instance of {@link Dictionary}
     */
    public HistoryRecordFieldResolver(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    /**
     * Resolves money by its type
     *
     * @param moneyInfoList money info list
     * @param type          money type
     * @return {@link Money} object
     */
    @Nullable
    Money resolveMoneyByType(final List<MoneyInfo> moneyInfoList, final MoneyType type) {
        return moneyInfoList.stream()
            .filter(m -> Objects.equals(type.getType(), m.getType()))
            .findFirst()
            .map(item -> new Money(BigInteger.valueOf(item.getValue()), item.getMeasurement()))
            .orElse(null);
    }

    /**
     * Resolve total massRate.
     * We do not take into account the currency. We assume that the values are in kopecks!!!
     *
     * @param insrRate  insrRate {@link Long} value
     * @param sendPrice sendPrice {@link Long} value
     * @return total massRate
     */
    @Nullable
    Money resolveTotalMassRate(final Money insrRate, final Money sendPrice) {
        if (isNull(insrRate)) {
            return sendPrice;
        } else if (isNull(sendPrice)) {
            return insrRate;
        } else if (insrRate.getCurrency().equals(sendPrice.getCurrency())) {
            return new Money(insrRate.getValue().add(sendPrice.getValue()), insrRate.getCurrency());
        } else {
            return null;
        }
    }

    /**
     * Resolves mass
     *
     * @param weightInfoList weight info list
     * @return mass
     */
    @Nullable
    Long resolveMass(final List<WeightInfo> weightInfoList) {
        return resolveMassByType(weightInfoList, ACTUAL);
    }

    /**
     * Resolves mass by type
     *
     * @param weightInfoList wight info list
     * @param type           mass type
     * @return mass
     */
    @Nullable
    Long resolveMassByType(List<WeightInfo> weightInfoList, WeightType type) {
        return weightInfoList.stream()
            .filter(w -> Objects.equals(type.getType(), w.getType()) && nonNull(w.getValue()))
            .findFirst()
            .flatMap(WeightResolver::convertToGrams)
            .orElse(null);
    }

    /**
     * Resolves person name
     *
     * @param person person
     * @return person name
     */
    @Nullable
    String resolvePersonName(Person2 person) {
        if (person == null) {
            return null;
        }
        return person.getName();
    }

    /**
     * Resolves address
     *
     * @param index            index
     * @param indexDesc        indexDesc
     * @param countryCode      countryCode
     * @param dataProviderType dataProviderType
     * @return address
     */
    @Nullable
    public String resolveAddress(
        String index,
        String indexDesc,
        Integer countryCode,
        String dataProviderType
    ) {
        if ((isNull(index) || index.isEmpty()) && (isNull(indexDesc) || indexDesc.isEmpty())) {
            return ofNullable(this.dictionary.getCountryById(countryCode)).map(Country::getNameRu).orElse(null);
        }
        final Integer resolvedCountryCode = isRuPostExternalOffice(index, countryCode) ||
            (isNull(countryCode) && isItLooksLikeRussianIndex(index) && notInternationalDataProvider(dataProviderType))
            ? RUSSIA_COUNTRY_CODE : countryCode;

        if (nonNull(resolvedCountryCode)) {
            if (Objects.equals(resolvedCountryCode, RUSSIA_COUNTRY_CODE)) {
                String desc = this.dictionary.getAddressDesc(index);
                if (isNull(desc) || desc.isEmpty()) {
                    desc = indexDesc;
                }
                return desc;
            } else {
                return ofNullable(this.dictionary.getCountryById(resolvedCountryCode))
                    .map(item -> item.getNameRu() + " " + index)
                    .orElse(null);
            }
        }
        return null;
    }

    /**
     * Check on russianpost specific postaloffices into the other counties to fix the countryOper to the Mother Russia
     *
     * @param index       index
     * @param countryCode country code
     * @return {@code true} if the place belongs to the russianpost
     */
    private boolean isRuPostExternalOffice(String index, Integer countryCode) {
        final Integer countryFromMap = this.dictionary.getCountry(index);
        return nonNull(countryFromMap) && Objects.equals(countryFromMap, countryCode);
    }

    private boolean notInternationalDataProvider(String dataProvider) {
        return !(nonNull(dataProvider) && INTERNATIONAL_DATA_PROVIDER_PATTERN.matcher(dataProvider).matches());
    }

    private boolean isItLooksLikeRussianIndex(final String index) {
        return Optional.ofNullable(index).map(i -> RUSSIAN_INDEX_PATTERN.matcher(i).matches()).orElse(FALSE);
    }

    /**
     * Resolves sms information
     *
     * @param smsInfo {@link SmsInfo2} object
     * @return {@link SmsInformation} object
     */
    @Nullable
    SmsInformation resolveSmsInformation(SmsInfo2 smsInfo) {
        if (isNull(smsInfo)) {
            return null;
        }
        return new SmsInformation(
            smsInfo.getSmsNotifications().stream()
                .map(n -> new SmsNotification(
                    n.getSmsTel(), n.getSmsTypes().stream().map(dictionary::getSmsNotificationType).collect(toList())
                ))
                .collect(Collectors.toList()));
    }

    /**
     * Resolves customs info
     *
     * @param customsInfoV7 {@link CustomsInfoV7} object
     * @return {@link CustomsInfo} object
     */
    @Nullable
    CustomsInfo resolveCustomsInfo(CustomsInfoV7 customsInfoV7) {
        return ofNullable(customsInfoV7)
            .map(info -> CustomsInfo.builder()
                .customsPayment(customsInfoV7.getCustomsPayment())
                .customsStatus(customsInfoV7.getCustomsStatus())
                .amountOfUnpaid(customsInfoV7.getAmountOfUnpaid())
                .build())
            .orElse(null);
    }

    /**
     * Resolves postmarks
     *
     * @param postmarks postmarks
     * @return resolved posmarks as list
     */
    List<String> resolvePostmarks(List<Integer> postmarks) {
        return ofNullable(postmarks)
            .flatMap(PostmarksUtils::combinePostmark)
            .map(postmark -> {
                if (Objects.equals(BigInteger.ZERO, postmark)) {
                    return Collections.singletonList(dictionary.getRtm02PostMarkName(BigInteger.ZERO));
                }

                final int numberOfBits = postmark.bitLength();

                return IntStream.range(0, numberOfBits)
                    .map(item -> numberOfBits - item - 1)
                    .filter(postmark::testBit)
                    .boxed()
                    .map(item -> dictionary.getRtm02PostMarkName(BigDecimal.valueOf(Math.pow(2, item)).toBigInteger()))
                    .collect(toList());
            })
            .orElseGet(Collections::emptyList);
    }

    /**
     * It resolves incoming hdps dto to admin portal model of hyperlocal delivery
     *
     * @param hyperlocalDelivery instance of {@link ru.russianpost.tracking.commons.hdps.dto.HyperlocalDelivery}
     * @return instance of {@link HyperlocalDelivery}
     */
    @Nullable
    public HyperlocalDelivery resolveHyperlocalDelivery(ru.russianpost.tracking.commons.hdps.dto.HyperlocalDelivery hyperlocalDelivery) {
        if (hyperlocalDelivery == null) {
            return null;
        }
        return new HyperlocalDelivery(hyperlocalDelivery.getOriginalBarcode(), hyperlocalDelivery.getHyperlocalDeliveryBarcode());
    }

    /**
     * Resolves incoming hdps dto to admin portal model of MultiplaceRpoInfo
     *
     * @param multiplaceRpoInfo multiplaceRpoInfo
     * @return instance of {@link MultiplaceRpoInfo}
     */
    @Nullable
    public MultiplaceRpoInfo resolveMultiplaceRpoInfo(final MultiplaceRpoInfoV7 multiplaceRpoInfo) {
        return ofNullable(multiplaceRpoInfo)
            .map(info -> MultiplaceRpoInfo.of(info.getMultiplaceBarcode(), info.getDeliveryMethod(), info.getRpoNum()))
            .orElse(null);
    }

    /**
     * Resolves incoming hdps dto to admin portal model of DeliveryInfo
     *
     * @param delivery delivery
     * @return instance of {@link DeliveryInfo}
     */
    @Nullable
    public DeliveryInfo resolveDelivery(final Delivery delivery) {
        return ofNullable(delivery)
            .map(info -> DeliveryInfo.of(info.getDateTime(), info.getMethod()))
            .orElse(null);
    }

    /**
     * Resolves incoming hdps dto to admin portal model of ExtensionProperty
     *
     * @param properties properties
     * @return instance of {@link Set<ExtensionProperty>}
     */
    public Map<String, ExtensionProperty> resolveOperProperties(final Set<ru.russianpost.tracking.commons.hdps.dto.ExtensionProperty> properties) {
        if (properties == null) {
            return emptyMap();
        }
        return properties.stream().map(extensionProperty -> new ExtensionProperty(
            extensionProperty.getName(),
            extensionProperty.getValue(),
            extensionProperty.getObjectList() != null
                ? extensionProperty.getObjectList().stream().map(eo -> new ExtensionObject(eo.getName(), eo.getAttributes())).collect(toList())
                : emptyList()
        )).collect(Collectors.toMap(ExtensionProperty::getName, p -> p));
    }

    /**
     * It resolves incoming hdps dto to admin portal model of Return Parcel Info
     *
     * @param returnParcelInfo instance of {@link ReturnParcelInfoV7}
     * @return instance of {@link ReturnParcelInfo}
     */
    @Nullable
    public ReturnParcelInfo resolveReturnParcelInfo(final ReturnParcelInfoV7 returnParcelInfo) {
        return ofNullable(returnParcelInfo)
            .map(parcelInfoV7 -> ReturnParcelInfo.of(parcelInfoV7.getOriginalBarcode(), parcelInfoV7.getReturnedParcelBarcode()))
            .orElse(null);
    }

    /**
     * Extract long value or null from BigInteger object
     *
     * @param value BigInteger value or null
     * @return long value or null
     */
    @Nullable
    public static Long longOf(final BigInteger value) {
        return isNull(value) ? null : value.longValueExact();
    }
}
