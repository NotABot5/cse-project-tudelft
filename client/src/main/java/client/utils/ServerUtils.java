package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.Ingredient;
import org.glassfish.jersey.client.ClientConfig;

import commons.Recipe;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;

import java.util.List;

public class ServerUtils {
    private static final String SERVER = "http://localhost:8080/";

    /**
     * Fetches list of recipes from server
     * @return list of recipes stored in server
     */
    public static List<Recipe> getRecipes() {
        try (var client = ClientBuilder.newClient(new ClientConfig())){
            return client.target(SERVER)
                    .path("api/recipes")
                    .request(APPLICATION_JSON)
                    .get(new GenericType<>() {
                    });
        }
    }

    /**
     * Fetches list of recipe names from server
     * @return list of recipe names stored in server
     */
    public static List<String> getRecipeNames() {
        try (var client = ClientBuilder.newClient(new ClientConfig())) {
            return client.target(SERVER).path("api/recipes/names")
                    .request(APPLICATION_JSON).get(new GenericType<>() {
                    });
        }
    }

    /**
     * Fetches list of ingredients from server
     * @return list of ingredients stored in server
     */
    public static List<Ingredient> getIngredients() {
        //Pathname used in IngredientController: /api/ingredients
        try (var client = ClientBuilder.newClient(new ClientConfig())) {
            return client.target(SERVER)
                    .path("api/ingredients")
                    .request(APPLICATION_JSON)
                    .get(new GenericType<>() {
                    });
        }
    }
}
