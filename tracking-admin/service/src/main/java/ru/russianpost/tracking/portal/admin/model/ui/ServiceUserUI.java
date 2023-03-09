/*
 * Copyright 2016 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.model.ui;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.StringUtils;
import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;
import ru.russianpost.tracking.portal.admin.validation.constraints.ValidRoles;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;


/**
 * POJO representation of service user for UI layer.
 *
 * @author KKiryakov
 */
public class ServiceUserUI {

    @NotNull
    @Size(min = 1)
    private final String username;
    @ValidRoles
    private final String[] roles;
    private final String name;
    private final String surname;
    private final String patronymic;
    private final String email;
    private final String affiliate;

    /**
     * Constructor.
     *
     * @param username username
     * @param roles roles
     * @param name name
     * @param surname surname
     * @param patronymic patronymic
     * @param email email
     * @param affiliate affiliate
     */
    @JsonCreator
    public ServiceUserUI(
        @JsonProperty("username") String username,
        @JsonProperty("roles") String[] roles,
        @JsonProperty("name") String name,
        @JsonProperty("surname") String surname,
        @JsonProperty("patronymic") String patronymic,
        @JsonProperty("email") String email,
        @JsonProperty("affiliate") String affiliate
    ) {
        this.username = username;
        this.roles = Arrays.copyOf(roles, roles.length);
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.affiliate = affiliate;
    }

    /**
     * Constructor.
     * @param user user
     */
    public ServiceUserUI(ServiceUser user) {
        username = user.getUsername();
        roles = StringUtils.tokenizeToStringArray(user.getAuthorityString(), ",");
        name = user.getName();
        surname = user.getSurname();
        patronymic = user.getPatronymic();
        email = user.getEmail();
        affiliate = user.getAffiliate();
    }

    public String getUsername() {
        return username;
    }

    public String[] getRoles() {
        return Arrays.copyOf(roles, roles.length);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getEmail() {
        return email;
    }

    public String getAffiliate() {
        return affiliate;
    }

    @Override
    public String toString() {
        return String.format(
            "ServiceUserUI{username='%s', roles=%s, name='%s', surname='%s', patronymic='%s', email='%s', affiliate='%s'}",
            username,
            Arrays.toString(roles),
            name,
            surname,
            patronymic,
            email,
            affiliate
        );
    }
}
