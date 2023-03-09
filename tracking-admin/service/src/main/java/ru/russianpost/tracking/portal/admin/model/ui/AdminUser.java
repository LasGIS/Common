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

import ru.russianpost.tracking.portal.admin.model.security.ServiceUser;

/**
 *
 * @author Roman Prokhorov
 * @version 1.0 (Jan 26, 2016)
 */
public class AdminUser {

    private final String username;
    private final String name;
    private final String surname;
    private final String patronymic;
    private final String email;

    /**
     * AdminUser.
     * @param username username
     */
    public AdminUser(String username) {
        this.username = username;
        this.name = null;
        this.surname = null;
        this.patronymic = null;
        this.email = null;
    }

    /**
     * AdminUser
     *
     * @param username username
     * @param name name
     * @param surname surname
     * @param patronymic patronymic
     * @param email email
     */
    public AdminUser(String username, String name, String surname, String patronymic, String email) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
    }

    /**
     * AdminUser
     *
     * @param serviceUser serviceUser
     */
    public AdminUser(ServiceUser serviceUser) {
        this.username = serviceUser.getUsername();
        this.name = serviceUser.getName();
        this.surname = serviceUser.getSurname();
        this.patronymic = serviceUser.getPatronymic();
        this.email = serviceUser.getEmail();
    }

    public String getUsername() {
        return username;
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
}
