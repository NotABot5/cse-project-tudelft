package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import commons.Ingredient;
import commons.IngredientUsage;
import jakarta.ws.rs.client.Entity;
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
        //Pathname used in IngredientController: /api/ingredients/sorted
        try (var client = ClientBuilder.newClient(new ClientConfig())) {
            return client.target(SERVER)
                    .path("api/ingredients/sorted")
                    .request(APPLICATION_JSON)
                    .get(new GenericType<>() {
                    });
        }
    }

    /**
     * For adding a recipe to the server
     * @param recipe The ingredient object to be added
     */
    public static Recipe addRecipe(Recipe recipe) {
        try (var client = ClientBuilder.newClient(new ClientConfig())) {
            return client.target(SERVER).path("api/recipes")
                    .request(APPLICATION_JSON)
                    .post(Entity.entity(recipe, APPLICATION_JSON), Recipe.class);
        }
    }

    /**
     * Deleting recipes using their id
     * @param id The id of the recipe to be deleted
     */
    public static void deleteRecipe(long id) {
        try (var client = ClientBuilder.newClient(new ClientConfig())) {
            client.target(SERVER).path("api/recipes/" + id)
                    .request(APPLICATION_JSON)
                    .delete();
        }
    }

    /**
     * For adding an ingredient to the server
     * @param ingredient The ingredient object to be added
     */
    public static Ingredient addIngredient(Ingredient ingredient) {
        try (var client = ClientBuilder.newClient(new ClientConfig())) {
            return client.target(SERVER).path("api/ingredients")
                    .request(APPLICATION_JSON)
                    .post(Entity.entity(ingredient, APPLICATION_JSON), Ingredient.class);
        }
    }

    /**
     * Deleting ingredients using their id
     * @param id The id of the ingredient to be deleted
     */
    public static void deleteIngredient(long id) {
        try (var client = ClientBuilder.newClient(new ClientConfig())) {
            client.target(SERVER).path("api/ingredients/" + id)
                    .request(APPLICATION_JSON)
                    .delete();
        }
    }

    /**
     * Fetches all ingredient used in a recipe
     * @param id id of recipe to fetch ingredients from
     * @return list of ingredient usages in recipe
     */
    public static List<IngredientUsage> fetchAllIngredientsInRecipe(long id) {
        try (var client = ClientBuilder.newClient(new ClientConfig())) {
            return client.target(SERVER)
                    .path("api/ingredientUsage/" + id)
                    .request(APPLICATION_JSON)
                    .get(new GenericType<>() {});
        }
    }

    /**
     * Fetches the count of recipes corresponding to a given ingredient
     * @param id id of ingredient to check
     * @return recipe count of that ingredient
     */
    public static long fetchRecipeCount(long id) {
        try (var client = ClientBuilder.newClient(new ClientConfig())) {
            return client.target(SERVER)
                    .path("api/ingredientUsage/recipeCount/" + id)
                    .request(APPLICATION_JSON)
                    .get(new GenericType<>() {});
        }
    }

    /**
     * Requests ingredient usage to be added on the server
     * @param ingredientUsage ingredient usage to be added
     * @return the added ingredient usage
     */
    public static IngredientUsage addIngredientUsage(IngredientUsage ingredientUsage) {
        try (var client = ClientBuilder.newClient(new ClientConfig())) {
            return client.target(SERVER).path("api/ingredientUsage")
                    .request(APPLICATION_JSON)
                    .post(Entity.entity(ingredientUsage, APPLICATION_JSON), IngredientUsage.class);
        }
    }

    /**
     * Requests ingredient usage to be deleted from the server
     * @param id id of ingredient usage to be deleted
     */
    public static void deleteIngredientUsage(long id) {
        try (var client = ClientBuilder.newClient(new ClientConfig())) {
            client.target(SERVER).path("api/ingredientUsage/" + id)
                    .request(APPLICATION_JSON)
                    .delete();
        }
    }
}
