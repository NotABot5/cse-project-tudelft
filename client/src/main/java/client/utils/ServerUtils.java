package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import org.glassfish.jersey.client.ClientConfig;

import commons.Recipe;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;

import java.util.List;

public class ServerUtils {
    private static final String BASE_URL = "http://localhost:8080/api/";

    public static List<Recipe> getRecipes() {
        try (var client = ClientBuilder.newClient(new ClientConfig())){
                return client.target(BASE_URL)
                .path("api/recipes")
                .request(APPLICATION_JSON)
                .get(new GenericType<>() {
                });

        }
    }
}
