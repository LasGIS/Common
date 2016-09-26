package com.lasgis.vertx.start.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
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
        //LOG.info("JSON code: {}", json());
        final HttpServer server = vertx.createHttpServer();
        final Router router = setupRouter();
        server.requestHandler(router::accept).listen(8180);
    }

    private String json() {
        return Json.encodePrettily(new Sample(EnumField.VAL2));
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
        router.route("/stat/*").handler((ctx) -> stat(ctx, engine));
        router.routeWithRegex("/doc/.*\\.json").handler(ctx -> routeJson(ctx, engine));
        router.routeWithRegex("/doc/.*\\.html").handler(ctx -> routeHtml(ctx, engine));
        router.routeWithRegex("/maps/.*\\.json").handler(ctx -> routeJson(ctx, engine));
        router.routeWithRegex("/maps/.*\\.html").handler(ctx -> routeHtml(ctx, engine));
        router.route("/*").handler(StaticHandler.create());
        return router;
    }

    private void stat(final RoutingContext ctx, final FreeMarkerTemplateEngine engine) {
        final String path = ctx.request().path();
        vertx.fileSystem().readFile("webroot" + path, result -> {
            if (result.succeeded()) {
                ctx.response().end(result.result());
            } else {
                vertx.fileSystem().readFile("webroot/stat/error.html", err -> {
                    if (err.succeeded()) {
                        ctx.response().end(err.result());
                    } else {
                        LOG.error("Oh oh ...", result.cause());
                    }
                });
            }
        });
    }

    private void routeJson(final RoutingContext ctx, final FreeMarkerTemplateEngine engine) {
        final String mainJson = ctx.request().path();
        callIndex(ctx, engine, mainJson, null);
    }

    private void routeHtml(final RoutingContext ctx, final FreeMarkerTemplateEngine engine) {
        final String rightContent = ctx.request().path();
        callIndex(ctx, engine, null, rightContent);
    }

    /**
     *
     * @param ctx контекст запроса
     * @param engine Free Marker Template Engine
     */
    private void index(final RoutingContext ctx, final FreeMarkerTemplateEngine engine) {
        //ctx.put("users", new User[] {new User("Саша", 30), new User("Дима", 25)});
        //ctx.put("users", new JsonArray("[{\"name\":\"Саша\", \"age\":28},{\"name\":\"Дима Фишбух\", \"age\":27}]").getList());
        callIndex(ctx, engine, "/doc/main.json", "/doc/front/main.html");
    }

    private void callIndex(
        final RoutingContext ctx, final FreeMarkerTemplateEngine engine,
        final String mainJson, final String rightContent
    ) {
        final String mainJsonCalc;
        if (mainJson != null) {
            mainJsonCalc = "/webroot" + mainJson;
            ctx.session().put("mainJson", mainJsonCalc);
        } else {
            final String mainJsonSession = ctx.session().get("mainJson");
            if (mainJsonSession == null) {
                mainJsonCalc = "/webroot/doc/main.json";
                ctx.session().put("mainJson", mainJsonCalc);
            } else {
                mainJsonCalc = mainJsonSession;
            }
        }
        engine.render(ctx, mainJsonCalc, jsonRes -> {
            if (jsonRes.succeeded()) {
                final JsonObject main = new JsonObject(jsonRes.result().toString());
                if (rightContent != null) {
                    main.put("documentName", "/webroot" + rightContent + ".ftl");
                }
                ctx.put("main", main.getMap());
                engine.render(ctx, "webroot/doc/index.html", engineRes -> {
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

    private static class Sample {
        private EnumField enumField;

        public Sample(final EnumField enumField) {
            this.enumField = enumField;
        }

        public EnumField getEnumField() {
            return enumField;
        }

        public void setEnumField(EnumField enumField) {
            this.enumField = enumField;
        }
    }

    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private enum EnumField {
        VAL1(1, "DESC1"), VAL2(2, "DESC2");
        private final int code;
        private final String desc;

        EnumField(final int code, final String desc) {
            this.code = code;
            this.desc = desc;
        }

        @JsonValue
        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
}
