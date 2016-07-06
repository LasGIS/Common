package com.lasgis.vertx.start;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

    static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

    public MainVerticle() {
        LOG.info("constructor {}", this.getClass().getName());
    }

    final int[] count = {100};
    final long[] timerID = {0};

    @Override
    public void start(Future<Void> startFuture) {

        LOG.info("start MainVerticle");
        vertx.deployVerticle("com.lasgis.vertx.start.web.Server", res -> {
          if (res.succeeded()) {
              LOG.info("deploy Verticle \"Server\" is succeeded id = {}", res.result());
              startFuture.complete();
          } else {
              LOG.info("deploy Verticle \"Server\" is filed :( id = {}", res.result());
              startFuture.fail(res.cause());
          }
        });

        final EventBus eb = vertx.eventBus();
        eb.consumer("news.message", message -> {
            LOG.info("received a message: header = {}; body = {}", message.headers().get("header"), message.body());
        });
        final DeliveryOptions option = new DeliveryOptions().addHeader("header", "Timer");
        timerID[0] = vertx.setPeriodic(1000, hand -> {
            if (count[0]-- > 0) {
                eb.send("news.message", "Timer -> Кап[" + count[0] + "]", option);
            } else {
                eb.publish("news.message", "Timer -> Стоп-ка", option);
                vertx.cancelTimer(timerID[0]);
            }
        });
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        final EventBus eb = vertx.eventBus();
        final DeliveryOptions option = new DeliveryOptions().addHeader("header", "stopFuture");
        eb.publish("news.message", "Timer -> Стоп-ка", option);
        vertx.cancelTimer(timerID[0]);
        LOG.info("stop MainVerticle");
    }
}
