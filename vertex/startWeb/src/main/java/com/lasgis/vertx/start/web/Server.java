package com.lasgis.vertx.start.web;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server extends AbstractVerticle {

    static final Logger LOG = LoggerFactory.getLogger(Server.class);

    public Server() {
        LOG.info("constructor {}", this.getClass().getSimpleName());
    }

    @Override
    public void start() {
        LOG.info("start Server");
        //vertx.createHttpServer()
/*
        vertx.eventBus().registerHandler(
            "ping-address", new Handler<Message<String>>() {
                @Override
                public void handle(Message<String> message) {
                    message.reply("pong!");
                    container.logger().info("Sent back pong");
                }
            }
        );

        container.logger().info("PingVerticle started");

*/
    }

    @Override
    public void stop() throws Exception {
        LOG.info("stop Server");
    }
}
