package de.finnley07.serverstats.service;

import java.util.ArrayList;
import java.util.List;

public class PlayerService {
    private static List<String> onlinePlayers = new ArrayList<>();

    public void addPlayer(String playerName) {
        if (!onlinePlayers.contains(playerName)) {
            onlinePlayers.add(playerName);
        }
    }

    public void removePlayer(String playerName) {
        onlinePlayers.remove(playerName);
    }

    public List<String> getOnlinePlayers() {
        return new ArrayList<>(onlinePlayers);
    }

    public int getOnlinePlayerCount() {
        return onlinePlayers.size();
    }

    public boolean isPlayerOnline(String playerName) {
        return onlinePlayers.contains(playerName);
    }

    public void clearAllPlayers() {
        onlinePlayers.clear();
    }
}
