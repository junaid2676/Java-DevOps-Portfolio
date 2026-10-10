package com.junaid.portfolio;

import com.junaid.portfolio.handler.PortfolioHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortfolioServer {

    private static final Logger LOGGER = Logger.getLogger(PortfolioServer.class.getName());
    private static final int DEFAULT_PORT = 8080;

    public static void main(String[] args) throws IOException {
        int port = DEFAULT_PORT;
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                LOGGER.warning("Invalid port argument: " + args[0] + ". Using default " + DEFAULT_PORT);
            }
        }

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new PortfolioHandler());
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();

        LOGGER.info("========================================");
        LOGGER.info("  Junaid's Portfolio Server Started");
        LOGGER.info("  Local:   http://localhost:" + port);
        LOGGER.info("  Press Ctrl+C to stop");
        LOGGER.info("========================================");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("Shutting down server...");
            server.stop(0);
        }));
    }
}
