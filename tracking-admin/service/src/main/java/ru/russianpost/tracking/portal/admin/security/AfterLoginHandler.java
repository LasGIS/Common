/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */
package ru.russianpost.tracking.portal.admin.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 09, 2015)
 */
public class AfterLoginHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AfterLoginHandler.class);

    private final String availablePagesCookieName;
    private final Map<Role, String> roleToPageName;

    /**
     * AfterLoginHandler
     * @param defaultTargetUrl         the URL to which the user should be redirected on
     * @param availablePagesCookieName availablePagesCookieName
     * @param roleToPageName           roleToPageName
     */
    AfterLoginHandler(
        final String defaultTargetUrl,
        final String availablePagesCookieName,
        final Map<Role, String> roleToPageName
    ) {
        super(defaultTargetUrl);
        this.availablePagesCookieName = availablePagesCookieName;
        this.roleToPageName = roleToPageName;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
        throws ServletException, IOException {
        if (authentication != null && authentication.isAuthenticated()) {
            LOG.info("User '{}' successfully logged in", authentication.getName());
            String availableViews = authentication.getAuthorities().stream()
                .map(a -> roleToPageName.get(Role.by(a.getAuthority())))
                .filter(Objects::nonNull)
                .collect(Collectors.joining(","));
            Cookie cookie = new Cookie(availablePagesCookieName, availableViews);
            response.addCookie(cookie);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
