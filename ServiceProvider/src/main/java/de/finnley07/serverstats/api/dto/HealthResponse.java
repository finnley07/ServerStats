package de.finnley07.serverstats.api.dto;

public class HealthResponse {
    public final String status;
    public final long timestamp;

    public HealthResponse(String status, long timestamp) {
        this.status = status;
        this.timestamp = timestamp;
    }
}

