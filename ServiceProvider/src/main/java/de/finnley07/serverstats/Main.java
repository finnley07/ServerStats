package de.finnley07.serverstats;

import de.finnley07.serverstats.api.APIServer;
import de.finnley07.serverstats.listener.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private APIServer apiServer;
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        apiServer = new APIServer(this);
        apiServer.start();

        registerListener();
        registerCommand();

        getLogger().info("ServerStats: ServiceProvider enabled");
    }

    @Override
    public void onDisable() {
        if (apiServer != null) {
            apiServer.stop();
        }
        getLogger().info("ServerStats: ServiceProvider disabled");
    }

    private void registerCommand() {

    }

    private void registerListener() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerListener(), this);
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

    public static Main getInstance() {
        return instance;
    }

}
