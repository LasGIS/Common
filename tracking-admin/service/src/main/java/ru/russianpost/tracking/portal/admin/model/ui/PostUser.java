/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin.model.ui;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * PostUser
 * @author Roman Prokhorov
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
public class PostUser {

    private long id;
    private String hid;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;

    /**
     * Create with only hid
     * @param hid hid
     */
    public PostUser(String hid) {
        this.hid = hid;
    }

    /**
     * Create by user info
     * @param pu user info
     */
    public PostUser(ru.russianpost.tracking.web.model.core.PostUser pu) {
        this.id = pu.getId();
        this.hid = pu.getHid();
        this.email = pu.getEmail();
    }

    @Override
    public int hashCode() {
        return 89 * 5 + Objects.hashCode(this.hid);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PostUser other = (PostUser) obj;
        return Objects.equals(this.hid, other.hid);
    }
}
