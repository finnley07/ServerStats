package de.finnley07.serverstats.service;

import de.finnley07.serverstats.Main;
import org.bukkit.Server;

/**
 * Caches server data that is unsafe to read from non-main threads.
 */
public class ServerInfoCache {

    private volatile int onlinePlayers = 0;
    private volatile int maxPlayers = 0;
    private volatile String motd = "";

    /**
     * Must only be called from the Bukkit main thread.
     */
    public void refresh() {
        Server server = Main.getInstance().getServer();
        onlinePlayers = server.getOnlinePlayers().size();
        maxPlayers = server.getMaxPlayers();
        motd = server.getMotd();
    }

    public int getOnlinePlayers() {
        return onlinePlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getMotd() {
        return motd;
    }
}

