package com.lasgis.vertx.start;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.DeploymentOptionsConverter;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
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

        LOG.info("start MainVerticle {}", config().getString("name"));
        if (deployAll(startFuture)) {
            startFuture.complete();
        }
        eventBusGame();
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        final EventBus eb = vertx.eventBus();
        final DeliveryOptions option = new DeliveryOptions().addHeader("header", "stopFuture");
        eb.publish("news.message", "Timer -> Стоп-ка", option);
        vertx.cancelTimer(timerID[0]);
        LOG.info("stop MainVerticle");
    }

    /**
     * вычитываем из my-config Verticles и деплоим их.
     * @param startFuture a future which should be called when verticle start-up is complete.
     */
    private boolean deployAll(final Future<Void> startFuture) {
        final boolean[] isStartComplete = {true};
        final JsonArray deploy = config().getJsonArray("deploy");
        for (final Object vrtSet : deploy) {
            final String verticleName;
            final DeploymentOptions options = new DeploymentOptions();
            if (vrtSet instanceof JsonObject) {
                final JsonObject verticleSet = (JsonObject) vrtSet;
                verticleName = verticleSet.getString("name");
                final JsonObject optionSet = verticleSet.getJsonObject("option");
                if (optionSet != null) {
                    DeploymentOptionsConverter.fromJson(optionSet, options);
                }
            } else if (vrtSet instanceof String) {
                verticleName = (String) vrtSet;
            } else {
                verticleName = null;
            }
            if (verticleName != null) {
                vertx.deployVerticle(
                    verticleName, options, res -> {
                        LOG.info(
                            "deploy Verticle \"{}\" is {} id = {}", verticleName,
                            res.succeeded() ? "succeeded" : "filed :(", res.result()
                        );
                        if (!res.succeeded()) {
                            isStartComplete[0] = false;
                            startFuture.fail(res.cause());
                        }
                    }
                );
            }
        }
        return isStartComplete[0];
    }

    private void eventBusGame() {
        final EventBus eb = vertx.eventBus();
        final DeliveryOptions option = new DeliveryOptions().addHeader("header", "Timer");
        eb.consumer("news.message", message -> {
            LOG.info("received a message: header = {}; body = {}", message.headers().get("header"), message.body());
        });
        timerID[0] = vertx.setPeriodic(1000, hand -> {
            if (count[0]-- > 0) {
                eb.send("news.message", "Timer -> Кап[" + count[0] + "]", option, reply -> {
                    if (reply.succeeded()) {
                        final Message result = reply.result();
                        LOG.info("Таймеру ответили: header=\"{}\", address=\"{}\", body=\"{}\"",
                            result.headers().get("header"), result.address(), result.body());
                        result.reply("Stop", option);
                    }
                });
            } else {
                eb.publish("news.message", "Timer -> Стоп-ка", option);
                vertx.cancelTimer(timerID[0]);
                vertx.setTimer(10000, h -> vertx.close());
            }
        });
    }
}
