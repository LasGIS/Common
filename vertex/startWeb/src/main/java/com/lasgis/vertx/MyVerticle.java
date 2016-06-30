package com.lasgis.vertx;

import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
This is a simple Java verticle which receives `ping` messages on the event bus and sends back `pong` replies
 */
public class MyVerticle extends AbstractVerticle {

    static final Logger LOG = LoggerFactory.getLogger(MyVerticle.class);

    public MyVerticle() {
        LOG.info("-conf myconf.json");
    }

    @Override
    public void start() {

        LOG.info("start MyVerticle");
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
        LOG.info("stop MyVerticle");
    }
}
