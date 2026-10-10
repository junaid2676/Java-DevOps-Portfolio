package com.junaid.portfolio.handler;

import com.junaid.portfolio.model.ContactMessage;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortfolioHandler implements HttpHandler {

    private static final Logger LOGGER = Logger.getLogger(PortfolioHandler.class.getName());
    private static final String WEB_ROOT = "/web";
    private static final ConcurrentLinkedQueue<ContactMessage> messages = new ConcurrentLinkedQueue<>();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            if ("GET".equalsIgnoreCase(method)) {
                handleGet(exchange, path);
            } else if ("POST".equalsIgnoreCase(method) && "/api/contact".equals(path)) {
                handleContactPost(exchange);
            } else {
                sendResponse(exchange, 405, "Method Not Allowed", "text/plain");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error handling request", e);
            sendResponse(exchange, 500, "Internal Server Error", "text/plain");
        }
    }

    private void handleGet(HttpExchange exchange, String path) throws IOException {
        if ("/".equals(path) || path.isEmpty()) {
            path = "/index.html";
        }

        String resourcePath = WEB_ROOT + path;
        InputStream resource = getClass().getResourceAsStream(resourcePath);

        if (resource == null) {
            String notFound = "<!DOCTYPE html><html><head><title>404</title>"
                    + "<style>body{font-family:system-ui;display:flex;align-items:center;"
                    + "justify-content:center;height:100vh;margin:0;background:#0f172a;color:#e2e8f0;}"
                    + "div{text-align:center}h1{font-size:4rem;margin:0;color:#38bdf8}"
                    + "p{color:#94a3b8}a{color:#38bdf8}</style></head><body><div>"
                    + "<h1>404</h1><p>Page not found.</p>"
                    + "<a href=\"/\">Back to Home</a></div></body></html>";
            sendResponse(exchange, 404, notFound, "text/html");
            return;
        }

        byte[] content = resource.readAllBytes();
        String contentType = getContentType(path);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.getResponseHeaders().set("Cache-Control", "public, max-age=3600");
        exchange.sendResponseHeaders(200, content.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(content);
        }
    }

    private void handleContactPost(HttpExchange exchange) throws IOException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        Map<String, String> params = parseFormData(body);

        ContactMessage message = new ContactMessage(
                params.get("name"),
                params.get("email"),
                params.get("subject"),
                params.get("message")
        );

        if (!message.isValid()) {
            String json = "{\"success\":false,\"error\":\"Please provide a valid name, email, and message.\"}";
            sendResponse(exchange, 400, json, "application/json");
            return;
        }

        messages.add(message);
        LOGGER.info("New contact message received: " + message);

        String json = "{\"success\":true,\"message\":\"Thanks for reaching out! I'll get back to you soon.\"}";
        sendResponse(exchange, 200, json, "application/json");
    }

    private Map<String, String> parseFormData(String body) {
        Map<String, String> params = new HashMap<>();
        if (body == null || body.isEmpty()) {
            return params;
        }
        String[] pairs = body.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                params.put(key, value);
            }
        }
        return params;
    }

    private String getContentType(String path) {
        if (path.endsWith(".html")) return "text/html; charset=utf-8";
        if (path.endsWith(".css")) return "text/css; charset=utf-8";
        if (path.endsWith(".js")) return "application/javascript; charset=utf-8";
        if (path.endsWith(".json")) return "application/json; charset=utf-8";
        if (path.endsWith(".png")) return "image/png";
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
        if (path.endsWith(".svg")) return "image/svg+xml";
        if (path.endsWith(".ico")) return "image/x-icon";
        if (path.endsWith(".woff2")) return "font/woff2";
        return "application/octet-stream";
    }

    private void sendResponse(HttpExchange exchange, int status, String body, String contentType) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", contentType);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}
