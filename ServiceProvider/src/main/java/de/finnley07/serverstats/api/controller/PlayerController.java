package de.finnley07.serverstats.api.controller;

import com.google.gson.Gson;
import de.finnley07.serverstats.Main;
import de.finnley07.serverstats.api.ApiController;
import de.finnley07.serverstats.api.dto.PlayerCountResponse;
import de.finnley07.serverstats.api.dto.PlayersResponse;
import de.finnley07.serverstats.service.PlayerService;
import spark.Spark;

/**
 * Handles all routes related to the player list.
 * Base path: /api/players
 */
public class PlayerController implements ApiController {

    private final PlayerService playerService;
    private final Main plugin;
    private final Gson gson;

    public PlayerController(PlayerService playerService, Main plugin, Gson gson) {
        this.playerService = playerService;
        this.plugin = plugin;
        this.gson = gson;
    }

    @Override
    public void registerRoutes() {
        // GET /api/players – list of online players
        Spark.get("/api/players", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new PlayersResponse(playerService.getOnlinePlayers()));
        });

        // GET /api/playercount – current online / max count
        Spark.get("/api/playercount", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new PlayerCountResponse(
                    playerService.getOnlinePlayerCount(),
                    plugin.getServer().getMaxPlayers()
            ));
        });
    }
}




