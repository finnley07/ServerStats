package de.finnley07.serverstats.service;

import de.finnley07.serverstats.Main;
import org.bukkit.Bukkit;
/**
 * Central registry that holds all service instances.
 */
public class ServiceRegistry {

    private final PlayerService playerService;
    private final ServerInfoCache serverInfoCache;

    public ServiceRegistry() {
        this.playerService = new PlayerService();
        this.serverInfoCache = new ServerInfoCache();
    }

    public void startCacheRefresh(Main plugin) {
        // Runs on the Bukkit main thread every 20 ticks (1 second)
        Bukkit.getScheduler().runTaskTimer(plugin, serverInfoCache::refresh, 0L, 20L);
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public ServerInfoCache getServerInfoCache() {
        return serverInfoCache;
    }
}
