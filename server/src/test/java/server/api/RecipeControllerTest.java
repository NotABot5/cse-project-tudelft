package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import commons.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
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

    @Test
    public void testClone() {
        // 1. Prepare: Add a recipe to the repository.
        Recipe original = new Recipe("Spaghetti", "en", List.of("Boil water", "Add pasta"));
        repo.save(original);

        // 2. Perform: Call the clone method.
        String newName = "Cloned Spaghetti";
        ResponseEntity<Recipe> response = sut.clone(original.id, newName);

        // 3. Assert: Check that the response is correct.
        assertEquals(OK, response.getStatusCode());
        Recipe cloned = response.getBody();
        assertEquals(newName, cloned.name);
        assertEquals(original.lang, cloned.lang);
        assertEquals(original.preparation, cloned.preparation);

        // 4. Assert: Check that the repository now contains two recipes.
        assertEquals(2, repo.count());
    }

    @Test
    public void testCloneNonExistent() {
        // 1. Prepare: Don't add any recipes to the repository.

        // 2. Perform: Call the clone method with a non-existent ID.
        String newName = "Cloned Spaghetti";
        ResponseEntity<Recipe> response = sut.clone(123L, newName);

        // 3. Assert: Check that the response is a bad request.
        assertEquals(BAD_REQUEST, response.getStatusCode());

        // 4. Assert: Check that the repository is still empty.
        assertEquals(0, repo.count());
    }

    @Test
    public void testCloneWithEmptyName() {
        // 1. Prepare: Add a recipe to the repository.
        Recipe original = new Recipe("Spaghetti", "en", List.of("Boil water", "Add pasta"));
        repo.save(original);

        // 2. Perform: Call the clone method with an empty name.
        ResponseEntity<Recipe> response = sut.clone(original.id, "");

        // 3. Assert: Check that the response is a bad request.
        assertEquals(BAD_REQUEST, response.getStatusCode());

        // 4. Assert: Check that the repository still contains only one recipe.
        assertEquals(1, repo.count());
    }

    @Test
    public void testOriginalRecipeUnchangedAfterClone() {
        // 1. Prepare: Add a recipe to the repository.
        String originalName = "Spaghetti";
        String originalLang = "en";
        List<String> originalPreparation = List.of("Boil water", "Add pasta");
        Recipe original = new Recipe(originalName, originalLang, originalPreparation);
        repo.save(original);
        long originalId = original.id;

        // 2. Perform: Call the clone method.
        String newName = "Cloned Spaghetti";
        ResponseEntity<Recipe> response = sut.clone(originalId, newName);

        // 3. Assert: Check that the clone was successful.
        assertEquals(OK, response.getStatusCode());
        Recipe cloned = response.getBody();
        assertNotNull(cloned);
        assertNotEquals(originalId, cloned.id);

        // 4. Assert: Check that the original recipe is unchanged in the repository.
        Recipe retrievedOriginal = repo.findById(originalId).orElse(null);
        assertNotNull(retrievedOriginal);
        assertEquals(originalName, retrievedOriginal.getName());
        assertEquals(originalLang, retrievedOriginal.getLang());
        assertEquals(originalPreparation, retrievedOriginal.getPreparation());
    }

    @Test
    public void testCloneAndVerifyNewIdGeneration() {
        // This test ensures that cloning a recipe results in a new, unique ID.
        // 1. Prepare: Create and save the original recipe.
        Recipe originalRecipe = new Recipe("Original Lasagna", "it", List.of("Layer pasta", "Add sauce", "Add cheese"));
        repo.save(originalRecipe);
        long originalId = originalRecipe.id;
        assertTrue(originalId >= 0, "Original ID should be non-negative.");

        // 2. Perform: Clone the recipe with a new name.
        String clonedName = "My Lasagna";
        ResponseEntity<Recipe> cloneResponse = sut.clone(originalId, clonedName);

        // 3. Assert: Verify the response and the new recipe's properties.
        assertEquals(OK, cloneResponse.getStatusCode(), "Cloning should return a 200 OK status.");
        Recipe clonedRecipe = cloneResponse.getBody();
        assertNotNull(clonedRecipe, "The cloned recipe should not be null.");
        assertEquals(clonedName, clonedRecipe.getName(), "The cloned recipe should have the new name.");

        // 4. Assert: Explicitly check that the new ID is different from the original.
        long clonedId = clonedRecipe.id;
        assertNotEquals(originalId, clonedId, "The cloned recipe must have a new, unique ID.");
        assertTrue(clonedId > originalId, "The new ID should be greater than the original, assuming sequential generation.");

        // 5. Assert: Ensure the repository now contains two distinct recipes.
        assertEquals(2, repo.count(), "The repository should now contain two recipes.");
    }

    @Test
    public void testCloneWithComplexPreparationSteps() {
        // This test verifies that a recipe with a detailed preparation list is cloned accurately.
        // 1. Prepare: Create a recipe with a multi-step preparation.
        List<String> complexPreparation = new ArrayList<>();
        complexPreparation.add("Step 1: Finely chop onions and garlic.");
        complexPreparation.add("Step 2: Sauté the onions and garlic in olive oil until translucent.");
        complexPreparation.add("Step 3: Add ground beef and cook until browned.");
        complexPreparation.add("Step 4: Stir in tomato paste, crushed tomatoes, and herbs.");
        complexPreparation.add("Step 5: Simmer for at least 1 hour, stirring occasionally.");
        Recipe originalRecipe = new Recipe("Bolognese Sauce", "it", complexPreparation);
        repo.save(originalRecipe);
        long originalId = originalRecipe.id;

        // 2. Perform: Clone the recipe.
        String clonedName = "My Bolognese";
        ResponseEntity<Recipe> cloneResponse = sut.clone(originalId, clonedName);

        // 3. Assert: Verify the response and basic properties of the clone.
        assertEquals(OK, cloneResponse.getStatusCode(), "Cloning should be successful.");
        Recipe clonedRecipe = cloneResponse.getBody();
        assertNotNull(clonedRecipe, "The cloned recipe must not be null.");
        assertEquals(clonedName, clonedRecipe.getName(), "The name of the clone should be updated.");

        // 4. Assert: Verify that the preparation steps are identical.
        List<String> clonedPreparation = clonedRecipe.getPreparation();
        assertEquals(complexPreparation.size(), clonedPreparation.size(), "The number of preparation steps should match.");
        for (int i = 0; i < complexPreparation.size(); i++) {
            assertEquals(complexPreparation.get(i), clonedPreparation.get(i), "Each preparation step must be identical.");
        }
    }

    @Test
    public void testSuccessiveCloning() {
        // This test ensures that a recipe can be cloned, and then its clone can also be cloned.
        // 1. Prepare: Create and save the initial recipe.
        Recipe firstRecipe = new Recipe("First Gen Cake", "en", List.of("Mix ingredients", "Bake at 180C"));
        repo.save(firstRecipe);
        long firstId = firstRecipe.id;

        // 2. Perform: Clone the first recipe to create a second one.
        String secondName = "Second Gen Cake";
        ResponseEntity<Recipe> secondResponse = sut.clone(firstId, secondName);
        assertEquals(OK, secondResponse.getStatusCode(), "First clone should be successful.");
        Recipe secondRecipe = secondResponse.getBody();
        assertNotNull(secondRecipe, "Second recipe should not be null.");
        long secondId = secondRecipe.id;
        assertNotEquals(firstId, secondId, "First and second IDs must be different.");

        // 3. Perform: Clone the second recipe to create a third one.
        String thirdName = "Third Gen Cake";
        ResponseEntity<Recipe> thirdResponse = sut.clone(secondId, thirdName);
        assertEquals(OK, thirdResponse.getStatusCode(), "Second clone should be successful.");
        Recipe thirdRecipe = thirdResponse.getBody();
        assertNotNull(thirdRecipe, "Third recipe should not be null.");
        long thirdId = thirdRecipe.id;
        assertNotEquals(secondId, thirdId, "Second and third IDs must be different.");
        assertNotEquals(firstId, thirdId, "First and third IDs must be different.");

        // 4. Assert: Verify that all three recipes exist in the repository.
        assertEquals(3, repo.count(), "There should be three recipes in the repository.");
        assertTrue(repo.existsById(firstId), "First recipe should still exist.");
        assertTrue(repo.existsById(secondId), "Second recipe should exist.");
        assertTrue(repo.existsById(thirdId), "Third recipe should exist.");
    }
}
