package de.finnley07.serverstats.api.middleware;

import de.finnley07.serverstats.config.PluginConfig;
import spark.Spark;

/**
 * Global Spark before-filters (IP whitelist, CORS).
 * Add new cross-cutting concerns here without touching APIServer.
 */
public class RequestMiddleware {

    private final PluginConfig config;

    public RequestMiddleware(PluginConfig config) {
        this.config = config;
    }

    public void register() {
        registerIpWhitelist();
        registerCors();
    }

    private void registerIpWhitelist() {
        Spark.before((request, response) -> {
            if (!config.isIpAllowed(request.ip())) {
                Spark.halt(403, "{\"error\":\"IP " + request.ip() + " not allowed\"}");
            }
        });
    }

    private void registerCors() {
        if (!config.isCorsEnabled()) return;

        Spark.before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });

        // Handle pre-flight OPTIONS requests
        Spark.options("/*", (request, response) -> {
            response.status(200);
            return "";
        });
    }
}

