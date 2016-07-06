package com.lasgis.vertx.start.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
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
        final EventBus eb = vertx.eventBus();
        final MessageConsumer<String> consumer = eb.consumer("news.message");
        consumer.handler(message -> {
            LOG.info("received a message: header = {}; body = {}", message.headers().get("header"), message.body());
        });
        consumer.completionHandler(res -> {
            if (res.succeeded()) {
                eb.publish("news.message", "consumer.completionHandler = success",
                    new DeliveryOptions().addHeader("header", "Header")
                );
            } else {
                eb.send("news.message", "consumer.completionHandler = failed");
            }
        });
    }

    @Override
    public void stop() throws Exception {
        LOG.info("stop Server");
    }
}
