package de.finnley07.serverstats.api.dto;

public class ServerInfoResponse {
    public final int onlinePlayers;
    public final int maxPlayers;
    public final String motd;

    public ServerInfoResponse(int onlinePlayers, int maxPlayers, String motd) {
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
        this.motd = motd;
    }
}

