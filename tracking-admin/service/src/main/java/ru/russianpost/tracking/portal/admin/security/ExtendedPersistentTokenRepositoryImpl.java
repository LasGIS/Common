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

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import java.util.Date;

/**
 * ExtendedPersistentTokenRepositoryImpl
 * @author MKitchenko
 * @version 1.0 01.10.2020
 */
@Slf4j
public class ExtendedPersistentTokenRepositoryImpl extends JdbcTokenRepositoryImpl implements ExtendedPersistentTokenRepository {

    /** The default SQL used by <tt>removeUserTokenBySeriesAndToken</tt> */
    public static final String DEF_REMOVE_USER_TOKEN_BY_SERIES_AND_TOKEN_SQL = "delete from persistent_logins where " +
        "series = ? AND token = ?";

    /** The default SQL used by <tt>cleanOldRecordsByDate</tt> */
    public static final String DEF_CLEAN_OLD_RECORDS_BY_DATE = "delete from persistent_logins where last_used < ?";

    @Override
    public void deleteUserTokenBySeriesAndToken(String series, String token) {
        getJdbcTemplate().update(DEF_REMOVE_USER_TOKEN_BY_SERIES_AND_TOKEN_SQL, series, token);
    }

    @Override
    public int cleanOldRecordsByDate(Date date) {
        return getJdbcTemplate().update(DEF_CLEAN_OLD_RECORDS_BY_DATE, date);
    }
}
