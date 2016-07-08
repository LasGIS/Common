package com.lasgis.vertx.start.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Leaf extends AbstractVerticle {

    static final Logger LOG = LoggerFactory.getLogger(Leaf.class);

    public Leaf() {
        LOG.info("constructor {}", this.getClass().getSimpleName());
    }

    @Override
    public void start() {
        LOG.info("start Leaf");
        final EventBus eb = vertx.eventBus();
        final MessageConsumer<String> consumer = eb.consumer("news.message");
        consumer.handler(message -> {
            LOG.info("received a message: header = {}; body = {}", message.headers().get("header"), message.body());
            message.reply(
                "It is very interesting!",
                new DeliveryOptions().addHeader("header", "Leaf"),
                replayOnReplay -> {
                    LOG.info("Ответ на ответ: header=\"{}\", address=\"{}\", body=\"{}\"",
                        message.headers().get("header"), message.address(), message.body()
                    );
                }
            );
        });
        consumer.completionHandler(res -> {
            if (res.succeeded()) {
                eb.send("news.message", "consumer.completionHandler = success",
                    new DeliveryOptions().addHeader("header", "Leaf")
                );
            } else {
                eb.send("news.message", "consumer.completionHandler = failed");
            }
        });
    }

    @Override
    public void stop() throws Exception {
        LOG.info("stop Leaf");
    }
}
