package de.finnley07.serverstats.config;

import de.finnley07.serverstats.Main;

import java.util.List;

/**
 * Wraps all config.yml access in one place.
 * Add new config keys here – nothing else needs to touch getConfig() directly.
 */
public class PluginConfig {

    private final Main plugin;

    public PluginConfig(Main plugin) {
        this.plugin = plugin;
    }

    public int getApiPort() {
        return plugin.getConfig().getInt("api-port", 8080);
    }

    public boolean isCorsEnabled() {
        return plugin.getConfig().getBoolean("cors.enabled", true);
    }

    public List<String> getAllowedIps() {
        return plugin.getConfig().getStringList("allowed-ips");
    }

    public boolean isIpAllowed(String ip) {
        List<String> allowed = getAllowedIps();
        return allowed.contains(ip) || allowed.contains("0.0.0.0");
    }
}

