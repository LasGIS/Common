package com.lasgis.vertx.start.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server extends AbstractVerticle {

    static final Logger LOG = LoggerFactory.getLogger(Server.class);
    static final String APP_PREFIX_REG_INDEX = "/index.html|/";

    public Server() {
        LOG.info("constructor {}", this.getClass().getSimpleName());
    }

    @Override
    public void start() {
        LOG.info("start Server");
        final HttpServer server = vertx.createHttpServer();
        final Router router = setupRouter();
        server.requestHandler(router::accept).listen(8180);
    }

    /**
     * Настройка сайта
     * http://vlaskin.omsk.luxoft.com:8080/lasgis/index.html
     * @return Router
     */
    private Router setupRouter() {

        final Router router = Router.router(vertx);
        final FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();

        // We need cookies and sessions
        router.route().handler(CookieHandler.create());
        router.route().handler(BodyHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        // перенаправляем на index.html
        router.routeWithRegex(APP_PREFIX_REG_INDEX).handler(ctx -> index(ctx, engine));
/*
        router.route("/api/whiskies*").handler(BodyHandler.create());

        router.post("/api/whiskies").handler(this::addOne);
        router.delete("/api/whiskies/:id").handler(this::deleteOne);
        router.get("/api/whiskies/:id").handler(this::getOne);
        router.put("/api/whiskies/:id").handler(this::updateOne);
*/
        router.route("/*").handler(StaticHandler.create());
        return router;
    }

    /**
     *
     * @param ctx контекст запроса
     * @param engine Free Marker Template Engine
     */
    private void index(final RoutingContext ctx, final FreeMarkerTemplateEngine engine) {
        //ctx.put("users", new User[] {new User("Саша", 30), new User("Дима", 25)});
        //ctx.put("users", new JsonArray("[{\"name\":\"Саша\", \"age\":28},{\"name\":\"Дима Фишбух\", \"age\":27}]").getList());
        engine.render(ctx, "templates/main.json", jsonRes -> {
            if (jsonRes.succeeded()) {
                ctx.put("main", new JsonObject(jsonRes.result().toString()).getMap());
                engine.render(ctx, "templates/index.html", engineRes -> {
                    if (engineRes.succeeded()) {
                            ctx.response().end(engineRes.result());
                        } else {
                            final Throwable ex = engineRes.cause();
                            LOG.error(ex.getMessage(), ex);
                            ctx.fail(ex);
                        }
                    }
                );
            } else {
                final Throwable ex = jsonRes.cause();
                LOG.error(ex.getMessage(), ex);
                ctx.fail(ex);
            }
        });
    }

    @Override
    public void stop() throws Exception {
        LOG.info("stop Server");
    }
}
