package de.finnley07.serverstats.api;

import com.google.gson.Gson;
import de.finnley07.serverstats.Main;
import de.finnley07.serverstats.service.PlayerService;
import spark.Spark;

import java.util.List;

public class APIRoutes {
    private PlayerService playerService;
    private Main plugin;
    private Gson gson = new Gson();

    public APIRoutes(PlayerService playerService, Main plugin) {
        this.playerService = playerService;
        this.plugin = plugin;
    }

    public void registerRoutes() {
        Spark.get("/api/status", (req, res) -> {
            res.type("application/json");
            StatusResponse response = new StatusResponse("online", playerService.getOnlinePlayerCount());
            return gson.toJson(response);
        });

        Spark.get("/api/players", (req, res) -> {
            res.type("application/json");
            PlayersResponse response = new PlayersResponse(playerService.getOnlinePlayers());
            return gson.toJson(response);
        });

        Spark.get("/api/info", (req, res) -> {
            res.type("application/json");
            ServerInfoResponse response = new ServerInfoResponse(
                    plugin.getServer().getOnlinePlayers().size(),
                    plugin.getServer().getMaxPlayers(),
                    plugin.getServer().getMotd()
            );
            return gson.toJson(response);
        });

        Spark.get("/api/health", (req, res) -> {
            res.type("application/json");
            HealthResponse response = new HealthResponse("healthy", System.currentTimeMillis());
            return gson.toJson(response);
        });

        Spark.get("/api/playercount", (req, res) -> {
            res.type("application/json");
            PlayerCountResponse response = new PlayerCountResponse(
                    playerService.getOnlinePlayerCount(),
                    plugin.getServer().getMaxPlayers()
            );
            return gson.toJson(response);
        });

        Spark.get("/", (req, res) -> {
            res.type("application/json");
            return gson.toJson(new StatusResponse("API is running", playerService.getOnlinePlayerCount()));
        });
    }

    private static class StatusResponse {
        String status;
        int playerCount;

        StatusResponse(String status, int playerCount) {
            this.status = status;
            this.playerCount = playerCount;
        }
    }

    private static class PlayersResponse {
        List<String> players;

        PlayersResponse(List<String> players) {
            this.players = players;
        }
    }

    private static class ServerInfoResponse {
        int onlinePlayers;
        int maxPlayers;
        String motd;

        ServerInfoResponse(int onlinePlayers, int maxPlayers, String motd) {
            this.onlinePlayers = onlinePlayers;
            this.maxPlayers = maxPlayers;
            this.motd = motd;
        }
    }

    private static class HealthResponse {
        String status;
        long timestamp;

        HealthResponse(String status, long timestamp) {
            this.status = status;
            this.timestamp = timestamp;
        }
    }

    private static class PlayerCountResponse {
        int online;
        int max;

        PlayerCountResponse(int online, int max) {
            this.online = online;
            this.max = max;
        }
    }
}
