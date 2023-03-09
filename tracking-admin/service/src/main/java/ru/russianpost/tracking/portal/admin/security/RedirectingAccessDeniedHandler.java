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

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;

/**
 * Handler for situations when authorized user tries to access a forbidden page.
 * In that case user will be redirected to home page.
 *
 * @author sslautin, rprokhorov
 * @version 1.0 06.10.2015
 */
public class RedirectingAccessDeniedHandler extends AccessDeniedHandlerImpl {

    private final String ajaxPrefix;
    private final String homePage;

    /**
     * RedirectingAccessDeniedHandler
     * @param ajaxPrefix prefix for API urls
     * @param homePage home page URL
     */
    public RedirectingAccessDeniedHandler(String ajaxPrefix, String homePage) {
        this.ajaxPrefix = ajaxPrefix;
        this.homePage = homePage;
    }

    @Override
    public void handle(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {

        boolean isAjax = request.getRequestURI().startsWith(ajaxPrefix);
        if (isAjax) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken)) {
            response.sendRedirect(homePage);
        }

        super.handle(request, response, accessDeniedException);
    }
}
