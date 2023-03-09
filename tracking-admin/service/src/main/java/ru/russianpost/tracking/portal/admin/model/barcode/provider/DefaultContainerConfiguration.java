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
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.russianpost.tracking.portal.admin.validation.constraints.ValidContainerConfiguration;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Objects;

/**
 * DefaultContainerConfiguration.
 *
 * @author KKiryakov
 */
@ValidContainerConfiguration
public class DefaultContainerConfiguration implements ContainerConfiguration {

    /** Container's minimum. */
    @Min(1)
    @Max(99999)
    private final int min;

    /** Container's maximum. */
    @Min(1)
    @Max(99999)
    private final int max;

    /** Allocation size. */
    @Min(1)
    @Max(99999)
    private final int allocationSize;

    /** Notifications flag. */
    private final boolean notificationEnabled;

    /** List of emails for notifications. */
    private final List<String> emails;

    /**
     * Constructor.
     *
     * @param min min
     * @param max max
     * @param allocationSize allocation size
     * @param notificationEnabled notifications flag
     * @param emails list of emails
     */
    @JsonCreator
    public DefaultContainerConfiguration(
        @JsonProperty(required = true, value = "min") int min,
        @JsonProperty(required = true, value = "max") int max,
        @JsonProperty(required = true, value = "allocationSize") int allocationSize,
        @JsonProperty(required = true, value = "notificationEnabled") boolean notificationEnabled,
        @JsonProperty(required = true, value = "emails") List<String> emails
    ) {
        this.min = min;
        this.max = max;
        this.allocationSize = allocationSize;
        this.notificationEnabled = notificationEnabled;
        this.emails = emails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultContainerConfiguration)) {
            return false;
        }
        DefaultContainerConfiguration that = (DefaultContainerConfiguration) o;
        return min == that.min
            && max == that.max
            && allocationSize == that.allocationSize
            && notificationEnabled == that.notificationEnabled
            && Objects.equals(emails, that.emails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max, allocationSize, notificationEnabled, emails);
    }

    @Override
    public String toString() {
        return String.format(
            "DefaultContainerConfiguration{min=%d, max=%d, allocationSize=%d, notificationEnabled=%s, emails=%s}",
            min,
            max,
            allocationSize,
            notificationEnabled,
            emails
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

    public List<String> getEmails() {
        return emails;
    }
}
