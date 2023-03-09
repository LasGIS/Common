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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Roman Prokhorov
 * @version 1.0 (Dec 09, 2015)
 */
@Slf4j
@RequiredArgsConstructor
public class RootRedirectionFilter extends GenericFilterBean {

    private final String applicationBaseUrl;
    private final String url;
    private final String loginUrl;
    private final String noRolesUrl;
    private final Map<Role, String> roleToStartPageUrl;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("Accessing application root page.");
        if (!url.equals(((HttpServletRequest) request).getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        final String target;
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            List<Role> roles = authentication.getAuthorities()
                .stream()
                .map(a -> Role.by(a.getAuthority()))
                .distinct()
                .collect(Collectors.toList());

            boolean atLeastOneRoleAvailable = roles.stream()
                .anyMatch(roleToStartPageUrl::containsKey);

            if (atLeastOneRoleAvailable) {
                target = roles.stream()
                    .map(roleToStartPageUrl::get)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(loginUrl);
            } else {
                target = noRolesUrl;
            }
            log.debug("Redirect to {} page.", target);
        } else {
            log.debug("Not authorized. Redirect to sign-in page.");
            target = loginUrl;
        }
        ((HttpServletResponse) response).sendRedirect(applicationBaseUrl + target);
    }

}
