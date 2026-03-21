package de.finnley07.serverstats.api.dto;

import java.util.List;

public class PlayersResponse {
    public final List<String> players;

    public PlayersResponse(List<String> players) {
        this.players = players;
    }
}

