/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.admin;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.russianpost.tracking.portal.admin.model.admin.history.UserInfo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Update user info request.
 *
 * @author KKiryakov
 */
public class UpdateUserInfoRequest {

    @NotNull
    private UserInfo userInfo;
    @NotNull
    @Size(min = 1)
    private String comment;

    /**
     * Constructor.
     *
     * @param userInfo user info
     * @param comment comment
     */
    @JsonCreator
    public UpdateUserInfoRequest(
        @JsonProperty("userInfo") UserInfo userInfo,
        @JsonProperty("comment") String comment
    ) {
        this.userInfo = userInfo;
        this.comment = comment;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public String getComment() {
        return comment;
    }

    @Override
    public String toString() {
        return "UpdateUserInfoRequest{" + "userInfo=" + userInfo + ", comment='" + comment + '\'' + '}';
    }
}
