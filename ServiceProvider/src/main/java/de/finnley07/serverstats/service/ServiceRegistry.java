package de.finnley07.serverstats.service;

/**
 * Central registry that holds all service instances.
 * Avoids passing individual services as constructor parameters everywhere.
 * Add a new service here and access it via ServiceRegistry.
 */
public class ServiceRegistry {

    private final PlayerService playerService;

    public ServiceRegistry() {
        this.playerService = new PlayerService();
    }

    public PlayerService getPlayerService() {
        return playerService;
    }
}

