/*
 * Copyright 2017 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.barcode.provider.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import ru.russianpost.tracking.portal.admin.validation.validators.Regexp;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Request for creation internal booking.
 *
 * @author KKiryakov
 */
public class BookingInternalCreateRequest {
    @NotNull @Pattern(regexp = Regexp.INDEX)
    private final String index;
    @NotEmpty
    private final String inn;
    private final String companyName;
    @NotNull @Min(1) @Max(99999)
    private final Integer startNumber;
    @NotNull @Min(1) @Max(99999)
    private final Integer count;
    @NotNull @Min(1)
    private final Integer numMonth;
    private final List<String> notificationEmails;
    @NotEmpty
    private final String comment;
    private String author;

    /**
     * Constructor.
     *
     * @param index index
     * @param inn inn
     * @param companyName company name
     * @param startNumber start number
     * @param count count
     * @param numMonth num month
     * @param notificationEmails notification emails
     * @param author author
     * @param comment comment
     */
    @JsonCreator
    public BookingInternalCreateRequest(
        @JsonProperty("index") String index,
        @JsonProperty("inn") String inn,
        @JsonProperty("companyName") String companyName,
        @JsonProperty("startNumber") Integer startNumber,
        @JsonProperty("count") Integer count,
        @JsonProperty("numMonth") Integer numMonth,
        @JsonProperty("notificationEmails") List<String> notificationEmails,
        @JsonProperty("author") String author,
        @JsonProperty("comment") String comment
    ) {
        this.index = index;
        this.inn = inn;
        this.companyName = companyName;
        this.startNumber = startNumber;
        this.count = count;
        this.numMonth = numMonth;
        this.notificationEmails = notificationEmails;
        this.author = author;
        this.comment = comment;
    }

    public String getIndex() {
        return index;
    }

    public String getInn() {
        return inn;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getStartNumber() {
        return startNumber;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getNumMonth() {
        return numMonth;
    }

    public List<String> getNotificationEmails() {
        return notificationEmails;
    }

    public String getComment() {
        return comment;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}
