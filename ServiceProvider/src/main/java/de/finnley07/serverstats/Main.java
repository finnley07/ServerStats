package de.finnley07.serverstats;

import de.finnley07.serverstats.api.APIServer;
import de.finnley07.serverstats.config.PluginConfig;
import de.finnley07.serverstats.listener.PlayerListener;
import de.finnley07.serverstats.service.ServiceRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    private PluginConfig pluginConfig;
    private ServiceRegistry serviceRegistry;
    private APIServer apiServer;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        pluginConfig = new PluginConfig(this);
        serviceRegistry = new ServiceRegistry();
        apiServer = new APIServer(pluginConfig, serviceRegistry);
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
        pluginManager.registerEvents(new PlayerListener(serviceRegistry.getPlayerService()), this);
    }

    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }

    public ServiceRegistry getServiceRegistry() {
        return serviceRegistry;
    }

    public static Main getInstance() {
        return instance;
    }
}


