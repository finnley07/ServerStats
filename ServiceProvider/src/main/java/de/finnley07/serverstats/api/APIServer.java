package de.finnley07.serverstats.api;

import com.google.gson.Gson;
import de.finnley07.serverstats.api.controller.PlayerController;
import de.finnley07.serverstats.api.controller.ServerController;
import de.finnley07.serverstats.api.middleware.RequestMiddleware;
import de.finnley07.serverstats.config.PluginConfig;
import de.finnley07.serverstats.service.ServiceRegistry;
import spark.Spark;

public class APIServer {

    private final PluginConfig config;
    private final ServiceRegistry services;
    private final Gson gson = new Gson();

    public APIServer(PluginConfig config, ServiceRegistry services) {
        this.config = config;
        this.services = services;
    }

    public void start() {
        Spark.port(config.getApiPort());

        new RequestMiddleware(config).register();

        new ApiRouter()
                .register(new ServerController(services, config, gson))
                .register(new PlayerController(services, gson))
                .registerAll();

        Spark.awaitInitialization();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}




