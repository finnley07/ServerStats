package de.finnley07.serverstats.api;

import com.google.gson.Gson;
import de.finnley07.serverstats.Main;
import de.finnley07.serverstats.api.controller.PlayerController;
import de.finnley07.serverstats.api.controller.ServerController;
import de.finnley07.serverstats.service.PlayerService;
import spark.Spark;

public class APIServer {

    private final Main plugin;
    private final PlayerService playerService;
    private final Gson gson = new Gson();

    public APIServer(Main plugin, PlayerService playerService) {
        this.plugin = plugin;
        this.playerService = playerService;
    }

    public void start() {
        Spark.port(plugin.getAPIPort());

        // ── Global before-filter: IP whitelist + CORS ────────────────────────
        Spark.before((request, response) -> {
            String clientIP = request.ip();

            if (!plugin.isIPAllowed(clientIP) && !plugin.isIPAllowed("0.0.0.0")) {
                Spark.halt(403, "IP " + clientIP + " not allowed");
            }

            if (plugin.isCORSEnabled()) {
                response.header("Access-Control-Allow-Origin", "*");
                response.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                response.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
            }
        });

        // ── Register controllers ─────────────────────────────────────────────
        new ApiRouter()
                .register(new ServerController(playerService, plugin, gson))
                .register(new PlayerController(playerService, plugin, gson))
                .registerAll();

        plugin.getLogger().info("ServiceProvider started listening on port: " + plugin.getAPIPort());
    }

    public void stop() {
        Spark.stop();
    }
}


