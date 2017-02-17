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
public class AjaxController {
    /*
Request URL:http://localhost:8099/react/manager/check.do
Request Method:GET
Status Code:200 OK
Remote Address:127.0.0.1:8099
Response Headers
view parsed
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 16 Feb 2017 13:31:45 GMT
Request Headers
view parsed
GET /react/manager/check.do HTTP/1.1
Host: localhost:8099
Connection: keep-alive
Cache-Control: max-age=0
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,* / *;q=0.8
Accept-Encoding: gzip, deflate, sdch, br
Accept-Language: ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4
Cookie: io=mjiV_mEaVlvoLLCOAAAN
*/

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

    @RequestMapping(value = "check1.do", method = {RequestMethod.GET, RequestMethod.POST}//,
/*
        headers = {
            //"Access-Control-Allow-Origin=http://localhost:*",
            "Content-Type=application/json;charset=UTF-8",
            //"Content-Type=text/html,application/xhtml+xml,application/xml;charset=UTF-8",
            "Accept=text/html,application/xhtml+xml,application/xml;charset=UTF-8"
        },
*/
//        produces = {"text/plain", "application/*"}
    )
    @ResponseBody
    public SimpleRequest checkOnText(
        final HttpServletRequest request, final HttpServletResponse response
    ) throws Exception {
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:3001");
        return SimpleRequest.of("state", "info");
    }
}
