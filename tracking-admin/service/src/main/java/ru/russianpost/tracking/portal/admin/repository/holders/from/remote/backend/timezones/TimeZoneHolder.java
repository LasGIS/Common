/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.timezones;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.RestMapHolder;
import ru.russianpost.tracking.portal.admin.repository.holders.from.remote.backend.timezones.model.TimeZoneInfo;

import java.time.ZoneOffset;
import java.util.Optional;

import static java.util.function.Function.identity;

/**
 * Holder of dictionary info of indexes to time offsets.
 * @author ssumrin
 * @version 2.0 05.07.2019
 */
public class TimeZoneHolder extends RestMapHolder<String, Object, ZoneOffset, TimeZoneInfo, Object> {

    private static final ZoneOffset DEFAULT_ZONE_OFFSET = ZoneOffset.ofHours(3);

    /**
     * TimeZoneHolder
     * @param uriBuilder   uriBuilder
     * @param restTemplate restTemplate
     */
    public TimeZoneHolder(
        final UriComponentsBuilder uriBuilder, final RestTemplate restTemplate
    ) {
        super(
            uriBuilder.build().toUriString(),
            restTemplate,
            item -> String.valueOf(item.getIndex()),
            item -> ZoneOffset.of(item.getTimeZone()),
            identity(),
            v -> null
        );
    }

    @Override
    protected Class<? extends DictionaryResponse<TimeZoneInfo, Object>> responseType() {
        return TimeZoneInfoResponse.class;
    }

    /**
     * CurrencyInfoResponse
     */
    private static class TimeZoneInfoResponse extends DictionaryResponse<TimeZoneInfo, Object> {
    }

    /**
     * Returns time zone offset for the postal index.
     * @param index postal index.
     * @return time zone offset.
     */
    public ZoneOffset getZoneOffsetForIndex(final String index) {
        return Optional.ofNullable(index).map(super::get).orElse(DEFAULT_ZONE_OFFSET);
    }
}
