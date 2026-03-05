package de.finnley07.serverstats.listener;

import de.finnley07.serverstats.service.PlayerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private static PlayerService playerService = new PlayerService();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();
        playerService.addPlayer(playerName);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String playerName = event.getPlayer().getName();
        playerService.removePlayer(playerName);
    }

    public static PlayerService getPlayerService() {
        return playerService;
    }
}
