package de.finnley07.serverstats.api;

/**
 * Interface that every API controller must implement.
 */
public interface ApiController {

    /**
     * Register all Spark routes belonging to this controller.
     */
    void registerRoutes();
}

