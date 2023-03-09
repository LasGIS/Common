/*
 * Copyright 2015 Russian Post
 *
 * This source code is Russian Post Confidential Proprietary.
 * This software is protected by copyright. All rights and titles are reserved.
 * You shall not use, copy, distribute, modify, decompile, disassemble or reverse engineer the software.
 * Otherwise this violation would be treated by law and would be subject to legal prosecution.
 * Legal use of the software provides receipt of a license from the right holder only.
 */

package ru.russianpost.tracking.portal.admin;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.WebAppContext;

import java.util.Arrays;

/**
 * Application entry point.
 * @author sslautin
 * @version 1.0 08.10.2015
 */
@Slf4j
public final class Main {

    private static final String FRONTEND_DEBUG_FLAG = "--frontend-debug";

    private static final int PORT = ConfigHolder.CONFIG.getInt("http.port");
    private static final String CONTEXT_PATH = ConfigHolder.CONFIG.getString("http.root");
    private static final String FULL_URL = String.format("http://%s:%s/%s", "0.0.0.0", PORT, withoutSlash());

    /**
     * Hidden default constructor.
     */
    private Main() {
    }

    /**
     * Launches the Tracking Admin Console application.
     * @param args CLI args
     */
    public static void main(final String[] args) {
        log.info("Tracking Admin Console is {}", Main.class.getPackage().getImplementationVersion());

        final Server server = new Server(PORT);

        final WebAppContext contextHandler = new WebAppContext();
        contextHandler.setContextPath(CONTEXT_PATH);
        contextHandler.setDescriptor(Main.class.getResource("/WEB-INF/web.xml").toString());
        contextHandler.setResourceBase(Main.class.getResource("/static").toString());
        contextHandler.setThrowUnavailableOnStartupException(true);

        // To disable caching of frontend resources, run Main class with argument "--frontend-debug"
        if (Arrays.asList(args).contains(FRONTEND_DEBUG_FLAG)) {
            disableResourcesCaching(contextHandler);
        }

        contextHandler.setParentLoaderPriority(true);
        server.setHandler(contextHandler);

        try {
            server.start();
            log.info("Started server on {}", FULL_URL);
            Runtime.getRuntime().addShutdownHook(
                new Thread(
                    () -> {
                        log.info("Server is shutting down.");
                        try {
                            server.stop();
                        } catch (final Exception e) {
                            log.error("Cannot stop the server.", e);
                        }
                        log.info("Server is down.");
                    }
                )
            );
            /* Waiting for server shutdown. */
            server.join();
        } catch (final Exception e) {
            log.error("Cannot start the server.", e);
            System.exit(1);
        }
    }

    /**
     * Removes first char in path string, if this chas is slash ('/').
     * @return uri path part without preceding slash, or null if parameter is also null.
     */
    private static String withoutSlash() {
        if (CONTEXT_PATH != null) {
            if (CONTEXT_PATH.length() > 0 && CONTEXT_PATH.indexOf('/') == 0) {
                return CONTEXT_PATH.substring(1);
            } else {
                return CONTEXT_PATH;
            }
        }
        return null;
    }

    /**
     * This method should be used for debugging frontend.
     * <p>This methods provides ability to receive updated resources without restart.</p>
     * @param contextHandler WebAppContext
     */
    private static void disableResourcesCaching(WebAppContext contextHandler) {
        log.info("Frontend debug mode is enabled");
        try {
            contextHandler.setBaseResource(Resource.newResource("service/src/main/webapp/static"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
