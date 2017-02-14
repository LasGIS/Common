/*
 * @(#)CustomAuthenticationSuccessHandler.java
 *
 * This file contains GLONASS Union intellectual property. It
 * may contain information about GLONASS Union processes that
 * are part of the Company's competitive advantage.
 *
 * Copyright (c) 2016, Non-profit Partnership for Development
 * and Use of Navigation Technologies. All Rights Reserved
 *
 * Данный Файл содержит информацию, являющуюся интеллектуальной
 * собственностью НП «ГЛОНАСС». Он также может содержать
 * информацию о процессах, представляющих конкурентное
 * преимущество компании.
 *
 * © 2016 Некоммерческое партнерство «Содействие развитию и
 * использованию навигационных технологий». Все права защищены.
 */

package lasgis.react.start.security;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import static lasgis.react.start.core.IReqSessionParameterNames.APP_USER_PARAM;

/**
 * The Class CustomAuthenticationSuccessHandler.
 * @author Vladimir Laskin
 * @version 1.0
 */
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private ServletContext servletContext;

    @Override
    public void onAuthenticationSuccess(
        final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication
    ) throws IOException, ServletException {
        final Authentication authResult = SecurityContextHolder.getContext().getAuthentication();
        if (authResult != null) {
            final User user = (User) authResult.getPrincipal();
            request.getSession().setAttribute(APP_USER_PARAM, user);
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
