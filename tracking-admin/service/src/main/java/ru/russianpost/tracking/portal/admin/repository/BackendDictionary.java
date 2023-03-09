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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.ConfigHolder;
import ru.russianpost.tracking.portal.admin.model.operation.Country;
import ru.russianpost.tracking.portal.admin.model.operation.UkdInfo;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.CountryHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.IndexMapHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.LocalizedOperationAttributeMapHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.LocalizedTypeMapHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.PostMarkMapHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.PredefinedAccessTypesHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.RestDictionaryHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.currencies.CurrenciesDecimalPlacesHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.timezones.TimeZoneHolder;
import ru.russianpost.tracking.web.model.core.AccessType;
import ru.russianpost.tracking.web.model.info.BigIntegerLocalizedKey;
import ru.russianpost.tracking.web.model.info.IndexInfo;
import ru.russianpost.tracking.web.model.info.LocalizedKey;
import ru.russianpost.tracking.web.model.info.LocalizedOperationKey;

import java.math.BigInteger;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.joining;
import static ru.russianpost.tracking.portal.admin.model.operation.UkdInfo.EMPTY_UKD_INFO;

/**
 * @author MKitchenko
 * @version 1.0 (Feb 28, 2019)
 */
@Slf4j
@Service
@EnableScheduling
class BackendDictionary {

    private static final String LANG_CODE = "RUS";

    private final RestTemplate rt;
    private final String backendServiceRootUrl;
    private final String backendServiceVersionUrl;

    private final LocalizedTypeMapHolder operTypeMapHolder;
    private final LocalizedTypeMapHolder mailTypeMapHolder;
    private final LocalizedTypeMapHolder mailRankMapHolder;
    private final LocalizedTypeMapHolder sendCategoryMapHolder;
    private final LocalizedTypeMapHolder mailCategoryMapHolder;
    private final LocalizedTypeMapHolder customsStatusMapHolder;
    private final PostMarkMapHolder postMarkMapHolder;
    private final LocalizedTypeMapHolder postalOrderEventTypeMapHolder;
    private final LocalizedTypeMapHolder transTypeMapHolder;
    private final LocalizedOperationAttributeMapHolder operAttributeMapHolder;
    private final IndexMapHolder indexMapHolder;
    private final CountryHolder countryHolder;
    private final TimeZoneHolder timeZoneHolder;
    private final CurrenciesDecimalPlacesHolder currenciesDecimalPlacesHolder;
    private final PredefinedAccessTypesHolder predefinedAccessTypesHolder;
    private final LocalizedTypeMapHolder packageTypeMapHolder;
    private final LocalizedTypeMapHolder packageKindMapHolder;

    private final List<RestDictionaryHolder> backendServiceDictionaries;

    private final AtomicReference<String> backendVersion = new AtomicReference<>();

    public BackendDictionary(
        @Qualifier("restTemplate") final RestTemplate rt,
        @Value("${ru.russianpost.tracking.portal.backend.service.url}") final String backendServiceRootUrl
    ) {
        this.rt = rt;
        this.backendServiceRootUrl = backendServiceRootUrl;
        this.backendServiceVersionUrl = UriComponentsBuilder.fromUriString(backendServiceRootUrl)
            .path("/monitoring/version").build().toUriString();

        try {
            this.operTypeMapHolder = new LocalizedTypeMapHolder(buildDictionaryUriString("operTypes"), rt);
            this.mailTypeMapHolder = new LocalizedTypeMapHolder(buildDictionaryUriString("mailTypes"), rt);
            this.mailRankMapHolder = new LocalizedTypeMapHolder(buildDictionaryUriString("mailRanks"), rt);
            this.sendCategoryMapHolder = new LocalizedTypeMapHolder(buildDictionaryUriString("sendCategories"), rt);
            this.mailCategoryMapHolder = new LocalizedTypeMapHolder(buildDictionaryUriString("mailCategories"), rt);
            this.customsStatusMapHolder = new LocalizedTypeMapHolder(buildDictionaryUriString("customsStatuses"), rt);
            this.postMarkMapHolder = new PostMarkMapHolder(buildDictionaryUriString("postMarks"), rt);
            this.postalOrderEventTypeMapHolder = new LocalizedTypeMapHolder(buildDictionaryUriString("postalOrderEventTypes"), rt);
            this.transTypeMapHolder = new LocalizedTypeMapHolder(buildDictionaryUriString("transTypes"), rt);
            this.operAttributeMapHolder = new LocalizedOperationAttributeMapHolder(buildDictionaryUriString("operAttributes"), rt);
            this.indexMapHolder = new IndexMapHolder(createUriBuilder("indexes"), rt);
            this.countryHolder = new CountryHolder(buildDictionaryUriString("countries"), rt);
            this.timeZoneHolder = new TimeZoneHolder(createUriBuilder("timeZones"), rt);
            this.currenciesDecimalPlacesHolder = new CurrenciesDecimalPlacesHolder(createUriBuilder("currencies"), rt, ConfigHolder.CONFIG);
            this.predefinedAccessTypesHolder = new PredefinedAccessTypesHolder(buildDictionaryUriString("predefinedAccessTypes"), rt);
            this.packageTypeMapHolder = new LocalizedTypeMapHolder(buildDictionaryUriString("packageTypes"), rt);
            this.packageKindMapHolder = new LocalizedTypeMapHolder(buildDictionaryUriString("packageKinds"), rt);

            this.backendServiceDictionaries = Arrays.asList(
                operTypeMapHolder,
                mailTypeMapHolder,
                mailRankMapHolder,
                sendCategoryMapHolder,
                mailCategoryMapHolder,
                customsStatusMapHolder,
                postMarkMapHolder,
                postalOrderEventTypeMapHolder,
                transTypeMapHolder,
                operAttributeMapHolder,
                indexMapHolder,
                countryHolder,
                timeZoneHolder,
                currenciesDecimalPlacesHolder,
                predefinedAccessTypesHolder,
                packageTypeMapHolder,
                packageKindMapHolder
            );

            actualize();

            log.info("Backend service version is '{}'", this.backendVersion.get());
        } catch (Exception e) {
            log.error("Initialization Backend service dictionaries failed.");
            throw e;
        }
    }

    Map<Integer, String> getOperTypes() {
        return this.operTypeMapHolder.getMapByLocale(LANG_CODE);
    }

    Map<Integer, Map<Integer, String>> getOperAttrs() {
        return operAttributeMapHolder.getMapByLocale(LANG_CODE);
    }

    Map<Integer, String> getMailCategories() {
        return this.mailCategoryMapHolder.getMapByLocale(LANG_CODE);
    }

    Map<Integer, String> getMailTypes() {
        return this.mailTypeMapHolder.getMapByLocale(LANG_CODE);
    }

    Map<Integer, String> getCustomsStatuses() {
        return this.customsStatusMapHolder.getMapByLocale(LANG_CODE);
    }

    String getOperTypeName(Integer operType) {
        return this.operTypeMapHolder.get(new LocalizedKey(LANG_CODE, operType));
    }

    String getOperAttrName(Integer operType, Integer operAttr) {
        return this.operAttributeMapHolder.get(new LocalizedOperationKey(LANG_CODE, normalized(operType), normalized(operAttr)));
    }

    String getDefaultOperAttrName(Integer operType, Integer operAttr) {
        return this.operAttributeMapHolder.getDefaultValue(new LocalizedOperationKey(LANG_CODE, normalized(operType), normalized(operAttr)));
    }

    String getMailTypeName(Integer id) {
        return this.mailTypeMapHolder.get(new LocalizedKey(LANG_CODE, normalized(id)));
    }

    String getMailCtgName(Integer id) {
        return this.mailCategoryMapHolder.get(new LocalizedKey(LANG_CODE, normalized(id)));
    }

    String getSendCtgName(Integer id) {
        return this.sendCategoryMapHolder.get(new LocalizedKey(LANG_CODE, normalized(id)));
    }

    String getPostMarkName(BigInteger id) {
        return this.postMarkMapHolder.get(new BigIntegerLocalizedKey(LANG_CODE, normalized(id)));
    }

    String getTransTypeName(Integer id) {
        return this.transTypeMapHolder.get(new LocalizedKey(LANG_CODE, normalized(id)));
    }

    String getMailRankName(Integer id) {
        return this.mailRankMapHolder.get(new LocalizedKey(LANG_CODE, normalized(id)));
    }

    Map<Integer, String> getPostalOrderEventTypes() {
        return this.postalOrderEventTypeMapHolder.getMapByLocale(LANG_CODE);
    }

    String getPostalOrderEventType(Integer id) {
        return this.postalOrderEventTypeMapHolder.get(new LocalizedKey(LANG_CODE, normalized(id)));
    }

    String getAddressDesc(String index) {
        final IndexInfo info = this.indexMapHolder.get(index);
        return nonNull(info) ?
            Stream.of(info.getRegion(), info.getAutonom(), info.getArea(), info.getOpsName())
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(item -> !item.isEmpty())
                .collect(joining(", ")) : null;
    }

    UkdInfo getUkdInfo(final String index) {
        final IndexInfo info = indexMapHolder.get(index);
        return nonNull(info) ? UkdInfo.of(info.getUkdIndex(), info.getUkdName()) : EMPTY_UKD_INFO;
    }

    String getApsName(final String index) {
        final IndexInfo info = indexMapHolder.get(index);
        return isNull(info) ? null : info.getPartnerName();
    }

    String getUfpsName(final String index) {
        IndexInfo indexInfo;
        String ufpsIndex = index;
        int depth = 10;
        do {
            indexInfo = indexMapHolder.get(ufpsIndex);
            if (indexInfo != null) {
                ufpsIndex = indexInfo.getOpsSubm();
            }
            depth--;
        } while ((indexInfo != null && StringUtils.isNotBlank(ufpsIndex)) || depth == 0);
        return isNull(indexInfo) ? null : indexInfo.getOpsName();
    }

    List<Country> getCountries() {
        return this.countryHolder.getCountries();
    }

    Country getCountryById(Integer id) {
        return this.countryHolder.getCountryById(id);
    }

    ZoneOffset getZoneOffsetForIndex(String index) {
        return this.timeZoneHolder.getZoneOffsetForIndex(index);
    }

    Map<String, Integer> getCurrencyDecimalPlaces() {
        return this.currenciesDecimalPlacesHolder.map();
    }

    List<AccessType> getPredefinedAccessTypes() {
        return predefinedAccessTypesHolder.get();
    }

    Map<Integer, String> getPackageTypes() {
        return this.packageTypeMapHolder.getMapByLocale(LANG_CODE);
    }

    Map<Integer, String> getPackageKinds() {
        return this.packageKindMapHolder.getMapByLocale(LANG_CODE);
    }

    private UriComponentsBuilder createUriBuilder(String dictionaryName) {
        return UriComponentsBuilder.fromUriString(backendServiceRootUrl).path("/api/v2.0/dictionary/" + dictionaryName);
    }

    private String buildDictionaryUriString(String dictionaryName) {
        return createUriBuilder(dictionaryName).build().toUriString();
    }

    private String getBackendVersion() {
        return rt.getForObject(backendServiceVersionUrl, String.class);
    }

    private Integer normalized(final Integer key) {
        return key == null ? Integer.valueOf(-1) : key;
    }

    private BigInteger normalized(final BigInteger key) {
        return key == null ? BigInteger.valueOf(-1L) : key;
    }

    /**
     * Actualize cache of backend service dictionaries, if the version of backend service has been changed.
     * <p>Check for version delay is set to 10 minutes.</p>
     */
    @Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 10 * 60 * 1000)
    private void actualizeBackendServiceDictionaries() {
        final String oldVersion = this.backendVersion.get();
        try {
            actualize();
            final String newVersion = this.backendVersion.get();
            if (!Objects.equals(oldVersion, newVersion)) {
                log.info("Backend service version has been changed from '{}' to '{}'", oldVersion, newVersion);
            }
        } catch (Exception e) {
            log.warn("Actualizing Backend service dictionaries failed. {}", e.getMessage());
        }
    }

    private void actualize() {
        final String newVersion = getBackendVersion();
        final String currentBackendVersion = this.backendVersion.get();

        if (!Objects.equals(currentBackendVersion, newVersion)) {
            this.backendServiceDictionaries.forEach(RestDictionaryHolder::load);
            this.backendVersion.set(newVersion);
        }
    }
}
