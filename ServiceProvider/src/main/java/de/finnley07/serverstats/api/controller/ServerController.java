package de.finnley07.serverstats.api.controller;

import com.google.gson.Gson;
import de.finnley07.serverstats.Main;
import de.finnley07.serverstats.api.ApiController;
import de.finnley07.serverstats.api.dto.HealthResponse;
import de.finnley07.serverstats.api.dto.ServerInfoResponse;
import de.finnley07.serverstats.api.dto.StatusResponse;
import de.finnley07.serverstats.config.PluginConfig;
import de.finnley07.serverstats.service.ServiceRegistry;
import spark.Spark;

/**
 * Handles all routes related to server status / info.
 * Base paths: /api/status, /api/info, /api/health, /
 */
public class ServerController implements ApiController {

    private final ServiceRegistry services;
    private final PluginConfig config;
    private final Gson gson;

    public ServerController(ServiceRegistry services, PluginConfig config, Gson gson) {
        this.services = services;
        this.config = config;
        this.gson = gson;
    }

    @Override
    public void registerRoutes() {
        Spark.get("/", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new StatusResponse("API is running", services.getPlayerService().getOnlinePlayerCount()));
        });

        Spark.get("/api/status", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new StatusResponse("online", services.getPlayerService().getOnlinePlayerCount()));
        });

        Spark.get("/api/info", (req, res) -> {
            res.type("application/json");
            Main plugin = Main.getInstance();
            return gson.toJson(new ServerInfoResponse(
                    plugin.getServer().getOnlinePlayers().size(),
                    plugin.getServer().getMaxPlayers(),
                    plugin.getServer().getMotd()
            ));
        });

        Spark.get("/api/health", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new HealthResponse("healthy", System.currentTimeMillis()));
        });
    }
}




