package de.finnley07.serverstats.api;

import de.finnley07.serverstats.Main;
import de.finnley07.serverstats.service.PlayerService;
import spark.Spark;

public class APIServer {
    private Main plugin;
    private PlayerService playerService;
    private APIRoutes apiRoutes;

    public APIServer(Main plugin) {
        this.plugin = plugin;
        this.playerService = new PlayerService();
        this.apiRoutes = new APIRoutes(playerService, plugin);
    }

    public void start() {
        Spark.port(plugin.getAPIPort());

        // IP Whitelist Filter
        Spark.before((request, response) -> {
            String clientIP = request.ip();

            if (!plugin.isIPAllowed(clientIP) && !plugin.isIPAllowed("0.0.0.0")) {
                Spark.halt(403, "IP " + clientIP + " not allowed");
            }

            // CORS wenn aktiviert
            if (plugin.isCORSEnabled()) {
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            }
        });

        apiRoutes.registerRoutes();

        plugin.getLogger().info("ServiceProvider started listening on port: " + plugin.getAPIPort());
    }

    public void stop() {
        Spark.stop();
    }
}
