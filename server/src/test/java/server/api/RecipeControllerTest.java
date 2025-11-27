package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import commons.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.TestRecipeRepository; // <-- IMPORTANT: Import your new class

import java.util.ArrayList;
import java.util.List;

public class RecipeControllerTest {

    private TestRecipeRepository repo; // Use your concrete test repository class
    private RecipeController sut;      // "sut" stands for "System Under Test"

    @BeforeEach
    public void setup() {
        // Create a new instance of YOUR test repository before each test
        repo = new TestRecipeRepository();
        // Create the controller using your fake repository
        sut = new RecipeController(repo);
    }

    @Test
    public void testGetAll_WithEmptyRepository() {
        // 1. Perform: Call the method on an empty repository.
        List<Recipe> result = sut.getAll();

        // 2. Assert: Check that the controller returned an empty list.
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetAll_WithOneRecipe() {
        // 1. Prepare: Add a recipe directly to your test repository's list.
        Recipe recipe = new Recipe("Spaghetti", "en", new ArrayList<>());
        repo.save(recipe);

        // 2. Perform: Call the controller method.
        List<Recipe> result = sut.getAll();

        // 3. Assert: Check that the controller returned the recipe we added.
        assertEquals(1, result.size());
        assertEquals("Spaghetti", result.getFirst().name);
    }
}