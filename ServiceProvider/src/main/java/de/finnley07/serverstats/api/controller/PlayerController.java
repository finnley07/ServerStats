package de.finnley07.serverstats.api.controller;

import com.google.gson.Gson;
import de.finnley07.serverstats.api.ApiController;
import de.finnley07.serverstats.api.dto.PlayerCountResponse;
import de.finnley07.serverstats.api.dto.PlayersResponse;
import de.finnley07.serverstats.service.ServiceRegistry;
import spark.Spark;

/**
 * Handles all routes related to the player list.
 * Base path: /api/players
 */
public class PlayerController implements ApiController {

    private final ServiceRegistry services;
    private final Gson gson;

    public PlayerController(ServiceRegistry services, Gson gson) {
        this.services = services;
        this.gson = gson;
    }

    @Override
    public void registerRoutes() {
        Spark.get("/api/players", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new PlayersResponse(services.getPlayerService().getOnlinePlayers()));
        });

        Spark.get("/api/playercount", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new PlayerCountResponse(
                    services.getPlayerService().getOnlinePlayerCount(),
                    services.getServerInfoCache().getMaxPlayers()
            ));
        });
    }
}








