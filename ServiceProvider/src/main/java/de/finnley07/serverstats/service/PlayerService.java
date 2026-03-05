package de.finnley07.serverstats.service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Thread-safe player service.
 */
public class PlayerService {

    private final CopyOnWriteArrayList<String> onlinePlayers = new CopyOnWriteArrayList<>();

    public void addPlayer(String playerName) {
        onlinePlayers.addIfAbsent(playerName);
    }

    public void removePlayer(String playerName) {
        onlinePlayers.remove(playerName);
    }

    public List<String> getOnlinePlayers() {
        return List.copyOf(onlinePlayers);
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

