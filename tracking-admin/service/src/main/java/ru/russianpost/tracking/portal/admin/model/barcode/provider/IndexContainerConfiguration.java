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
import ru.russianpost.tracking.portal.admin.model.barcode.provider.request.IndexMonthContainerConfiguration;
import ru.russianpost.tracking.portal.admin.validation.constraints.ValidContainerConfiguration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;

/**
 * IndexContainerConfiguration.
 *
 * @author KKiryakov
 */
@ValidContainerConfiguration
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IndexContainerConfiguration implements ContainerConfiguration {

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

    /** Notifications flag. */
    private final Boolean notificationEnabled;
    /** Compress attachment flag. */
    private final Boolean compressAttachment;
    /** List of emails for notifications. */
    private final List<String> emails;
    /** List of month configurations. */
    private final List<IndexMonthContainerConfiguration> months;

    /**
     * Constructor.
     * @param min min
     * @param max max
     * @param allocationSize allocation size
     * @param notificationEnabled notifications flag
     * @param compressAttachment compress attachment flag
     * @param emails list of emails
     * @param months months
     */
    @JsonCreator
    public IndexContainerConfiguration(
        @JsonProperty("min") Integer min,
        @JsonProperty("max") Integer max,
        @JsonProperty("allocationSize") Integer allocationSize,
        @JsonProperty("notificationEnabled") Boolean notificationEnabled,
        @JsonProperty("compressAttachment") Boolean compressAttachment,
        @JsonProperty("emails") List<String> emails,
        @JsonProperty("months") List<IndexMonthContainerConfiguration> months
    ) {
        this.min = min;
        this.max = max;
        this.allocationSize = allocationSize;
        this.notificationEnabled = notificationEnabled;
        this.compressAttachment = compressAttachment;
        this.emails = emails;
        this.months = months;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IndexContainerConfiguration)) {
            return false;
        }
        IndexContainerConfiguration that = (IndexContainerConfiguration) o;
        return Objects.equals(min, that.min)
            && Objects.equals(max, that.max)
            && Objects.equals(allocationSize, that.allocationSize)
            && Objects.equals(notificationEnabled, that.notificationEnabled)
            && Objects.equals(compressAttachment, that.compressAttachment)
            && Objects.equals(emails, that.emails)
            && Objects.equals(months, that.months);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max, allocationSize, notificationEnabled, compressAttachment, emails, months);
    }

    @Override
    public String toString() {
        return String.format(
            "IndexContainerConfiguration{min=%d, max=%d, allocationSize=%d, notificationEnabled=%s, compressAttachment=%s, emails=%s, months=%s}",
            min,
            max,
            allocationSize,
            notificationEnabled,
            compressAttachment,
            emails,
            months
        );
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

    public Boolean isNotificationEnabled() {
        return notificationEnabled;
    }

    public Boolean isCompressAttachment() {
        return compressAttachment;
    }

    public List<String> getEmails() {
        return emails;
    }

    public List<IndexMonthContainerConfiguration> getMonths() {
        return months;
    }
}

