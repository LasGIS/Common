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
import ru.russianpost.tracking.portal.admin.model.barcode.provider.BarcodeProviderUser;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * User update request presentation.
 *
 * @author KKiryakov
 */
public class UserUpdateRequest {

    /** Configuration. */
    @NotNull
    @Valid
    private final BarcodeProviderUser user;
    /** Comment for the change. */
    @NotEmpty
    private final String comment;
    /** Author of the change. */
    private String author;

    /**
     * Constructor.
     *
     * @param user changed user
     * @param comment comment
     */
    @JsonCreator
    public UserUpdateRequest(
        @JsonProperty(required = true, value = "user") BarcodeProviderUser user,
        @JsonProperty(required = true, value = "comment") String comment
    ) {
        this.user = user;
        this.comment = comment;
    }

    public BarcodeProviderUser getUser() {
        return user;
    }

    public String getComment() {
        return comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "UserUpdateRequest{user=" + user + ", comment='" + comment + "', author='" + author + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserUpdateRequest)) {
            return false;
        }
        UserUpdateRequest that = (UserUpdateRequest) o;
        return Objects.equals(user, that.user) && Objects.equals(comment, that.comment) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, comment, author);
    }
}
