package de.finnley07.serverstats.api.controller;

import com.google.gson.Gson;
import de.finnley07.serverstats.Main;
import de.finnley07.serverstats.api.ApiController;
import de.finnley07.serverstats.api.dto.HealthResponse;
import de.finnley07.serverstats.api.dto.ServerInfoResponse;
import de.finnley07.serverstats.api.dto.StatusResponse;
import de.finnley07.serverstats.service.PlayerService;
import spark.Spark;

/**
 * Handles all routes related to server status / info.
 * Base paths: /api/status, /api/info, /api/health, /
 */
public class ServerController implements ApiController {

    private final PlayerService playerService;
    private final Main plugin;
    private final Gson gson;

    public ServerController(PlayerService playerService, Main plugin, Gson gson) {
        this.playerService = playerService;
        this.plugin = plugin;
        this.gson = gson;
    }

    @Override
    public void registerRoutes() {
        // GET / – root info
        Spark.get("/", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new StatusResponse("API is running", playerService.getOnlinePlayerCount()));
        });

        // GET /api/status – quick status check
        Spark.get("/api/status", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new StatusResponse("online", playerService.getOnlinePlayerCount()));
        });

        // GET /api/info – detailed server info
        Spark.get("/api/info", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new ServerInfoResponse(
                    plugin.getServer().getOnlinePlayers().size(),
                    plugin.getServer().getMaxPlayers(),
                    plugin.getServer().getMotd()
            ));
        });

        // GET /api/health – health-check endpoint
        Spark.get("/api/health", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new HealthResponse("healthy", System.currentTimeMillis()));
        });
    }
}


