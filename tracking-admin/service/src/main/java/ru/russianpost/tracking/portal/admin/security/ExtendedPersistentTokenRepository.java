/*
 * Copyright 2020 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.security;

import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Date;

/**
 * ExtendedPersistentTokenRepository
 * @author MKitchenko
 * @version 1.0 01.10.2020
 */
public interface ExtendedPersistentTokenRepository extends PersistentTokenRepository {

    /**
     * Delete user token by series and token value
     * @param series     {@link org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken#series}
     * @param tokenValue {@link org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken#tokenValue}
     */
    @SuppressWarnings("JavadocReference")
    void deleteUserTokenBySeriesAndToken(String series, String tokenValue);

    /**
     * Clean old records by date
     * @param date instance of {@link Date}
     * @return amount of affected records
     */
    int cleanOldRecordsByDate(Date date);
}
