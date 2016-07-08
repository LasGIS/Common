package com.lasgis.vertx.start.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
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
        final HttpServer server = vertx.createHttpServer();
        final Router router = Router.router(vertx);

        // We need cookies and sessions
        router.route().handler(CookieHandler.create());
        router.route().handler(BodyHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        router.route().handler(routingContext -> {

          // This handler will be called for every request
          HttpServerResponse response = routingContext.response();
          response.putHeader("content-type", "text/plain");

          // Write to the response and end it
          response.end("Hello World from Vert.x-Web!");
        });

        server.requestHandler(router::accept).listen(8080);
    }

    @Override
    public void stop() throws Exception {
        LOG.info("stop Server");
    }
}
