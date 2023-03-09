/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.russianpost.tracking.portal.admin.validation.constraints.ValidContainerConfiguration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * IndexMonthContainerConfiguration.
 *
 * @author KKiryakov
 */
@ValidContainerConfiguration
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IndexMonthContainerConfiguration implements ContainerConfiguration {

    /** Year. */
    private final Integer year;
    /** Month. */
    private final Integer month;
    /** Container's minimum. */
    @Min(1)
    @Max(99999)
    private final Integer min;
    /** Container's maximum. */
    @Min(1)
    @Max(99999)
    private final Integer max;
    /** Allocation size. */
    @Min(1)
    @Max(99999)
    private final Integer allocationSize;
    /** Is configuration should overwrite index configuration. */
    private final Boolean overwriteIndexConfig;

    /**
     * Constructor.
     *  @param year year
     * @param month month
     * @param min min
     * @param max max
     * @param allocationSize allocation size
     * @param overwriteIndexConfig Is configuration should overwrite index configuration
     */
    @JsonCreator
    public IndexMonthContainerConfiguration(
        @JsonProperty("year") Integer year,
        @JsonProperty("month") Integer month,
        @JsonProperty("min") Integer min,
        @JsonProperty("max") Integer max,
        @JsonProperty("allocationSize") Integer allocationSize,
        @JsonProperty("overwriteIndexConfig") Boolean overwriteIndexConfig
    ) {
        this.year = year;
        this.month = month;
        this.min = min;
        this.max = max;
        this.allocationSize = allocationSize;
        this.overwriteIndexConfig = overwriteIndexConfig;
    }

    @Override
    public String toString() {
        return String.format(
            "IndexMonthContainerConfiguration{year=%d, month=%d, min=%d, max=%d, allocationSize=%d, overwriteIndexConfig=%s}",
            year,
            month,
            min,
            max,
            allocationSize,
            overwriteIndexConfig
        );
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    @Override
    public Integer getMin() {
        return min;
    }

    @Override
    public Integer getMax() {
        return max;
    }

    @Override
    public Integer getAllocationSize() {
        return allocationSize;
    }

    public Boolean getOverwriteIndexConfig() {
        return overwriteIndexConfig;
    }
}
