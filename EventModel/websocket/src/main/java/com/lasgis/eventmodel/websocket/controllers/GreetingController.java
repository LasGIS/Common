package com.lasgis.eventmodel.websocket.controllers;

import com.lasgis.eventmodel.websocket.hello.Greeting;
import com.lasgis.eventmodel.websocket.hello.HelloMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.concurrent.TimeUnit;

import static java.util.Objects.nonNull;

@Controller
public class GreetingController {

    private final SimpMessagingTemplate template;

    private String name = null;

    public GreetingController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/hello")
    public void greeting(HelloMessage message) throws Exception {
        name = message.getName();
    }

    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.SECONDS)
    public void fireGreeting() {
        if (nonNull(name)) {
            this.template.convertAndSend("/topic/greetings", new Greeting("Hello, " + HtmlUtils.htmlEscape(name) + "!"));
        }
    }
}
