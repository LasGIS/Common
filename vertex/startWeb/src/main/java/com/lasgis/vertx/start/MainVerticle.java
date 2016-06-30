package com.lasgis.vertx.start;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainVerticle extends AbstractVerticle {

    static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

    public MainVerticle() {
        LOG.info("constructor {}", this.getClass().getName());
    }

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
    }

    @Override
    public void stop(Future<Void> stopFuture) throws Exception {
        LOG.info("stop MainVerticle");
    }
}
