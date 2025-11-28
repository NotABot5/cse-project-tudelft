package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import org.glassfish.jersey.client.ClientConfig;

import commons.Recipe;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;

import java.util.List;

public class ServerUtils {
    private static final String SERVER = "http://localhost:8080/";

    public static List<Recipe> getRecipes() {
        try (var client = ClientBuilder.newClient(new ClientConfig())){
                return client.target(SERVER)
                .path("api/recipes")
                .request(APPLICATION_JSON)
                .get(new GenericType<>() {
                });

        }
    }

    public static List<String> getRecipeNames() {
        try (var client = ClientBuilder.newClient(new ClientConfig())) {
            return client.target(SERVER).path("api/recipes/names")
                    .request(APPLICATION_JSON).get(new GenericType<List<String>>() {});
        }
    }
}
