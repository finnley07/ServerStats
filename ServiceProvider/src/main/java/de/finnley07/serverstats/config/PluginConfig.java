package de.finnley07.serverstats.config;

import de.finnley07.serverstats.Main;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Wraps all config.yml access in one place.
 */
public class PluginConfig {

    private final Main plugin;

    // Cached at startup – no YAML read on every request
    private final int apiPort;
    private final boolean corsEnabled;
    private final Set<String> allowedIps;

    public PluginConfig(Main plugin) {
        this.plugin = plugin;
        this.apiPort = plugin.getConfig().getInt("api-port", 8080);
        this.corsEnabled = plugin.getConfig().getBoolean("cors.enabled", true);
        this.allowedIps = new HashSet<>(plugin.getConfig().getStringList("allowed-ips"));
    }

    public int getApiPort() {
        return apiPort;
    }

    public boolean isCorsEnabled() {
        return corsEnabled;
    }

    public List<String> getAllowedIps() {
        return List.copyOf(allowedIps);
    }

    public boolean isIpAllowed(String ip) {
        return allowedIps.contains(ip) || allowedIps.contains("0.0.0.0");
    }
}


