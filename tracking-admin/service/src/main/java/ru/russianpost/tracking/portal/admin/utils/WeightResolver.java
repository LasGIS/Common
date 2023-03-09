/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.utils;

import lombok.experimental.UtilityClass;
import ru.russianpost.tracking.commons.hdps.dto.WeightInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static java.math.BigDecimal.valueOf;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * WeightResolver
 * @author MKitchenko
 * @version 1.0 27.11.2020
 */
@UtilityClass
public class WeightResolver {

    /** LB_TO_GRAMS_RATIO */
    public static final BigDecimal LB_TO_GRAMS_RATIO = valueOf(453.5923);
    /** OZ_TO_GRAMS_RATIO */
    public static final BigDecimal OZ_TO_GRAMS_RATIO = valueOf(28.3495);

    /**
     * Converts weightInfo to grams
     * @param weightInfo instance of {@link WeightInfo}
     * @return value if {@link WeightInfo#getValue()} not null otherwise {@link Optional#empty()}
     */
    public Optional<Long> convertToGrams(WeightInfo weightInfo) {
        if (weightInfo == null || weightInfo.getValue() == null) {
            return empty();
        }

        final Double value = weightInfo.getValue();
        final String measurement = weightInfo.getMeasurement() == null ? "g" : weightInfo.getMeasurement().toLowerCase();

        switch (measurement) {
            case "mg":
                return of(valueOf(value).divide(valueOf(1000), 0, RoundingMode.HALF_UP).longValue());
            case "kg":
                return of(valueOf(value).multiply(valueOf(1000)).longValue());
            case "lb":
                return of(valueOf(value).multiply(LB_TO_GRAMS_RATIO).longValue());
            case "oz":
                return of(valueOf(value).multiply(OZ_TO_GRAMS_RATIO).longValue());
            case "g":
            default:
                return of(value.longValue());
        }
    }
}
