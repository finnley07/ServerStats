package de.finnley07.serverstats.api.dto;

public class PlayerCountResponse {
    public final int online;
    public final int max;

    public PlayerCountResponse(int online, int max) {
        this.online = online;
        this.max = max;
    }
}

