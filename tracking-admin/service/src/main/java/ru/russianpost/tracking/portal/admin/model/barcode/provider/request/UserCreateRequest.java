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

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotEmpty;
import ru.russianpost.tracking.portal.admin.model.barcode.provider.BarcodeProviderUser;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * User creation request presentation.
 *
 * @author KKiryakov
 */
public class UserCreateRequest {

    /** Configuration. */
    @NotNull
    @Valid
    private final BarcodeProviderUser user;
    /** Comment for the change. */
    @NotEmpty
    private final String comment;
    /** List of email addresses to notify with login and password of new user. */
    @NotEmpty
    private final List<String> notificationEmails;
    /** Author of the change. */
    private String author;

    /**
     * Constructor.
     *
     * @param user changed user
     * @param notificationEmails email addresses to notify with login and password
     * @param comment comment
     */
    public UserCreateRequest(
        @JsonProperty(required = true, value = "user") BarcodeProviderUser user,
        @JsonProperty(required = true, value = "notificationEmails") List<String> notificationEmails,
        @JsonProperty(required = true, value = "comment") String comment
    ) {
        this.user = user;
        this.comment = comment;
        this.notificationEmails = notificationEmails;
    }

    public BarcodeProviderUser getUser() {
        return user;
    }

    public String getComment() {
        return comment;
    }

    public List<String> getNotificationEmails() {
        return notificationEmails;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "UserCreateRequest{user="
            + user
            + ", comment='"
            + comment
            + "', author='"
            + author
            + "', notificationEmails="
            + notificationEmails
            + '}';
    }
}
