package com.lasgis.eventmodel.websocket.hello;

import lombok.Getter;

@Getter
public class Greeting {
    private final String content;

    public Greeting(String content) {
        this.content = content;
    }
}
