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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class AjaxController.
 * @author Vladimir Laskin
 * @version 1.0
 */
@Controller
@RequestMapping(value = "manager")
@Slf4j
public class AjaxController {

    @RequestMapping(value = "check.do", method = {RequestMethod.GET, RequestMethod.POST},
        headers = {
            "Access-Control-Allow-Origin=http://localhost:3001",
            //"Accept=application/json;charset=UTF-8",
            //"Content-Type=application/x-www-form-urlencoded; charset=UTF-8",
            "Accept=text/html;charset=UTF-8",
            "Content-Type=application/json;charset=UTF-8",
        }
    )
    @ResponseBody
    public SimpleRequest checkOnUnresolved(
        final HttpServletRequest request, final HttpServletResponse response
    ) throws Exception {
        return SimpleRequest.of("state", "info");
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
