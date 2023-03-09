/*
 * Copyright 2019 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.currencies;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.RestMapHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.currencies.model.CurrencyInfo;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;

/**
 * Holder of dictionary info of currencies.
 * @author MKitchenko
 * @version 1.0 23.01.2019
 */
public class CurrenciesDecimalPlacesHolder extends RestMapHolder<String, Object, Integer, CurrencyInfo, Object> {

    private Map<String, Integer> exclusions;

    /**
     * CurrenciesDecimalPlacesHolder
     * @param uriBuilder   uriBuilder
     * @param restTemplate restTemplate
     * @param config       config
     */
    public CurrenciesDecimalPlacesHolder(
        final UriComponentsBuilder uriBuilder,
        final RestTemplate restTemplate,
        final Config config
    ) {
        super(
            uriBuilder.build().toUriString(),
            restTemplate,
            CurrencyInfo::getCodeCurrency,
            CurrencyInfo::getCurrencyDecimalPlaces,
            identity(),
            v -> null
        );
        init(config);
    }

    private void init(Config config) {
        this.exclusions = config.getObjectList("currencies.exclusions").stream()
            .map(ConfigObject::toConfig)
            .collect(Collectors.toMap(item -> item.getString("currencyCode"), item -> item.getInt("currencyDecimalPlaces")));
    }

    @Override
    public void load() {
        final String uri = uri();
        log().info("Loading dictionary {}...", uri);

        try {
            final DictionaryResponse<CurrencyInfo, Object> response = restTemplate().getForObject(uri, responseType());

            final Stream<CurrencyInfo> exclusionsStream = exclusions.entrySet().stream()
                .map(entry -> new CurrencyInfo(entry.getKey(), entry.getValue()));

            final List<CurrencyInfo> merged = Stream.concat(response.getItems().stream(), exclusionsStream)
                .filter(item -> Objects.nonNull(item.getCodeCurrency()))
                .sorted(Comparator.comparing(CurrencyInfo::getCodeCurrency))
                .filter(distinctByKey(CurrencyInfo::getCodeCurrency))
                .peek(item -> {
                    if (Objects.isNull(item.getCurrencyDecimalPlaces())) {
                        item.setCurrencyDecimalPlaces(0);
                    }
                })
                .collect(Collectors.toList());

            response.setItems(merged);

            processResponse(uri, response);
        } catch (Exception e) {
            log().error("Error to load dictionary '{}'. Error message: {}", uri, e.getMessage());
            throw e;
        }
    }

    /**
     * Gets map.
     * @return the map
     */
    public Map<String, Integer> map() {
        return getMap();
    }

    @Override
    protected Class<? extends DictionaryResponse<CurrencyInfo, Object>> responseType() {
        return CurrencyInfoResponse.class;
    }

    /**
     * CurrencyInfoResponse
     */
    private static class CurrencyInfoResponse extends DictionaryResponse<CurrencyInfo, Object> {
    }

    private Predicate<CurrencyInfo> distinctByKey(Function<CurrencyInfo, String> keyExtractor) {
        final Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> {
            final String key = keyExtractor.apply(t);
            final boolean isUniqueKey = seen.add(key);
            if (!isUniqueKey) {
                log().warn("Duplicate key found: key = {}, value = {}", key, t.getCurrencyDecimalPlaces());
            }
            return isUniqueKey;
        };
    }
}
