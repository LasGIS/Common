package com.lasgis.vertx.start.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
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
        final Router router = setupRouter();
        server.requestHandler(router::accept).listen(8080);
    }

    /**
     * Настройка сайта
     * @return Router
     */
    private Router setupRouter() {
        final Router router = Router.router(vertx);
        // We need cookies and sessions
        router.route().handler(CookieHandler.create());
        router.route().handler(BodyHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        router.route("/").handler(rc -> rc.response()
            .putHeader(HttpHeaders.CONTENT_TYPE, HttpHeaders.TEXT_HTML)
            .end("<html><h1>Hi! this is my 2nd try with Vert.x 3 :) </h1></html>")
        );
        router.route("/js/*").handler(StaticHandler.create("webroot/js"));
        router.route("/images/*").handler(StaticHandler.create("webroot/images"));
        router.route("/css/*").handler(StaticHandler.create("webroot/css"));
        router.routeWithRegex(".*\\.html").handler(StaticHandler.create("webroot/pages"));
/*
        router.get("/api/whiskies").handler(this::getAll);
        router.route("/api/whiskies*").handler(BodyHandler.create());

        router.post("/api/whiskies").handler(this::addOne);
        router.delete("/api/whiskies/:id").handler(this::deleteOne);
        router.get("/api/whiskies/:id").handler(this::getOne);
        router.put("/api/whiskies/:id").handler(this::updateOne);
*/
        return router;
    }

    @Override
    public void stop() throws Exception {
        LOG.info("stop Server");
    }
}
