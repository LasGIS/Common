/*
 * Copyright 2018 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * ThreadSafeRememberMeServices.
 * Solution for preventing "remember-me" bug. Some browsers sends preloading requests to server to speed-up
 * page loading. It may cause error when response of preload request not returned to client and second request
 * from client was send.
 * @author MKitchenko
 */
@Slf4j
@Service
public class ThreadSafeRememberMeServices extends PersistentTokenBasedRememberMeServices {

    private final ScheduledExecutorService scheduledExecutorService;
    private final ExtendedPersistentTokenRepository persistentTokenRepository;

    /**
     * Constructor.
     * @param userDetails               instance of {@link UserDetails}
     * @param rememberMeExecutor        instance of {@link ScheduledExecutorService}
     * @param tokenValiditySeconds      token validity seconds
     * @param secretKey                 secret key
     * @param persistentTokenRepository instance of {@link ExtendedPersistentTokenRepository}
     */
    public ThreadSafeRememberMeServices(
        final UserDetailsService userDetails,
        final ScheduledExecutorService rememberMeExecutor,
        @Value("${remember.me.token.validity.seconds}") final int tokenValiditySeconds,
        @Value("${remember.me.token.secret.key}") final String secretKey,
        final ExtendedPersistentTokenRepository persistentTokenRepository
    ) {
        super(secretKey, userDetails, persistentTokenRepository);
        super.setAlwaysRemember(true);
        super.setTokenValiditySeconds(tokenValiditySeconds);
        this.scheduledExecutorService = rememberMeExecutor;
        this.persistentTokenRepository = persistentTokenRepository;
    }

    /**
     * This method implementation copies the parent method except for the part where the token is updated. Here, the
     * token is not updated, but a completely new one is created. The old value is deleted after the specified time delay.
     */
    @Override
    protected UserDetails processAutoLoginCookie(
        String[] cookieTokens,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        try {
            if (cookieTokens.length != 2) {
                throw new InvalidCookieException("Cookie token did not contain " + 2
                    + " tokens, but contained '" + Arrays.asList(cookieTokens) + "'");
            }

            final String presentedSeries = cookieTokens[0];
            final String presentedToken = cookieTokens[1];

            PersistentRememberMeToken token = persistentTokenRepository
                .getTokenForSeries(presentedSeries);

            if (token == null) {
                // No series match, so we can't authenticate using this cookie
                throw new RememberMeAuthenticationException(
                    "No persistent token found for series id: " + presentedSeries);
            }

            // We have a match for this user/series combination
            if (!presentedToken.equals(token.getTokenValue())) {
                // Token doesn't match series value. Delete all logins for this user and throw
                // an exception to warn them.
                persistentTokenRepository.removeUserTokens(token.getUsername());

                throw new CookieTheftException(
                    messages.getMessage(
                        "PersistentTokenBasedRememberMeServices.cookieStolen",
                        "Invalid remember-me token (Series/token) mismatch. Implies previous cookie theft attack."));
            }

            if (token.getDate().getTime() + getTokenValiditySeconds() * 1000L < System
                .currentTimeMillis()) {
                throw new RememberMeAuthenticationException("Remember-me login has expired");
            }

            setNewToken(request, response, token);
            // Delete previous series-token record with delay. Delay is needed to solve race condition problems.
            scheduledExecutorService.schedule(
                () -> persistentTokenRepository.deleteUserTokenBySeriesAndToken(presentedSeries, presentedToken),
                15, SECONDS
            );

            return getUserDetailsService().loadUserByUsername(token.getUsername());
        } catch (CookieTheftException cte) {
            log.warn("Instead of throwing CookieTheftException, will convert it to RememberMeAuthenticationException", cte);
            throw new RememberMeAuthenticationException("RememberMeAuthenticationException occurred: " + cte.getMessage(), cte);
        }
    }

    private void setNewToken(HttpServletRequest request, HttpServletResponse response, PersistentRememberMeToken token) {
        try {
            PersistentRememberMeToken newToken = new PersistentRememberMeToken(
                token.getUsername(), generateSeriesData(), generateTokenData(), new Date()
            );
            persistentTokenRepository.createNewToken(newToken);
            setCookie(
                new String[]{newToken.getSeries(), newToken.getTokenValue()}, getTokenValiditySeconds(), request, response
            );
        } catch (Exception ex) {
            log.error("Failed to update token: ", ex);
            throw new RememberMeAuthenticationException(
                "Autologin failed due to data access problem");
        }
    }

    /**
     * Clean old records by cron task
     */
    @Scheduled(cron = "${remember.me.db.cleanup.task.cron}")
    private void cleanOldRecords() {
        try {
            final LocalDateTime localDateTime = LocalDateTime.now().minusSeconds(getTokenValiditySeconds());
            final Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            final int cleanedRows = persistentTokenRepository.cleanOldRecordsByDate(date);
            log.info("Clean old records by date {} in the amount of {} rows", date, cleanedRows);
        } catch (Exception e) {
            log.warn("Clean old records task was finished with error. {}", e.getMessage());
        }
    }
}
