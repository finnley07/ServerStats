package de.finnley07.serverstats.api.dto;

public class StatusResponse {
    public final String status;
    public final int playerCount;

    public StatusResponse(String status, int playerCount) {
        this.status = status;
        this.playerCount = playerCount;
    }
}

