package de.finnley07.serverstats.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Central registry for all API controllers.
 * Add new controllers here – that's the only place you need to touch
 * when introducing a new route group.
 */
public class ApiRouter {

    private final List<ApiController> controllers = new ArrayList<>();

    /**
     * Adds a controller to the registry.
     *
     * @param controller the controller to register
     * @return this instance (fluent API)
     */
    public ApiRouter register(ApiController controller) {
        controllers.add(controller);
        return this;
    }

    /**
     * Calls {@link ApiController#registerRoutes()} on every registered controller.
     * Must be called after Spark port / filters are configured.
     */
    public void registerAll() {
        controllers.forEach(ApiController::registerRoutes);
    }
}

