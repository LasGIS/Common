/*
 * @(#)AjaxController.java
 *
 * This file contains GLONASS Union intellectual property. It
 * may contain information about GLONASS Union processes that
 * are part of the Company's competitive advantage.
 *
 * Copyright (c) 2017, Non-profit Partnership for Development
 * and Use of Navigation Technologies. All Rights Reserved
 *
 * Данный Файл содержит информацию, являющуюся интеллектуальной
 * собственностью НП «ГЛОНАСС». Он также может содержать
 * информацию о процессах, представляющих конкурентное
 * преимущество компании.
 *
 * © 2017 Некоммерческое партнерство «Содействие развитию и
 * использованию навигационных технологий». Все права защищены.
 */

package lasgis.react.start.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lasgis.react.start.model.SimpleRequest;
import lasgis.react.start.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class AjaxController.
 * @author Vladimir Laskin
 * @version 1.0
 */
@RestController
@RequestMapping(value = "manager")
@Slf4j
public class AjaxController {

    @RequestMapping(value = "check.do", method = {RequestMethod.POST},
        headers = {
            "Accept=application/json",
            "Content-Type=application/json",
            "Access-Control-Request-Method=POST",
            "Access-Control-Allow-Origin=http://localhost:3001"
        }
    )
    @ResponseBody
    public SimpleRequest checkOnUnresolved(
        @RequestBody final User user,
        final HttpServletRequest request, final HttpServletResponse response
    ) throws Exception {
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        return SimpleRequest.of("state", user.toString());
    }

    @RequestMapping(value = "check1.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public SimpleRequest checkOnText(
        final HttpServletRequest request, final HttpServletResponse response
    ) throws Exception {
        response.addHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        log.info("check1.do with header:origin = {}", request.getHeader("origin"));
        return SimpleRequest.of("ok", "Вот эта строка должна показаться");
    }
}
