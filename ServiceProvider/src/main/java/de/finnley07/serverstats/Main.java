package de.finnley07.serverstats;

import de.finnley07.serverstats.api.APIServer;
import de.finnley07.serverstats.listener.PlayerListener;
import de.finnley07.serverstats.service.PlayerService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private APIServer apiServer;
    private PlayerService playerService;
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        // Single PlayerService instance shared across the whole plugin
        playerService = new PlayerService();

        apiServer = new APIServer(this, playerService);
        apiServer.start();

        registerListeners();
        registerCommands();

        getLogger().info("ServerStats: ServiceProvider enabled");
    }

    @Override
    public void onDisable() {
        if (apiServer != null) {
            apiServer.stop();
        }
        getLogger().info("ServerStats: ServiceProvider disabled");
    }

    private void registerCommands() {
        // register commands here
    }

    private void registerListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerListener(playerService), this);
    }

    public boolean isIPAllowed(String ip) {
        return getConfig().getStringList("allowed-ips").contains(ip);
    }

    public int getAPIPort() {
        return getConfig().getInt("api-port", 8080);
    }

    public boolean isCORSEnabled() {
        return getConfig().getBoolean("cors.enabled", true);
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public static Main getInstance() {
        return instance;
    }

}
