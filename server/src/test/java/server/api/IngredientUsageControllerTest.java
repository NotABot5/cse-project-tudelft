package server.api;

import commons.Ingredient;
import commons.IngredientUsage;
import commons.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import server.Main;
import server.database.IngredientRepository;
import server.database.IngredientUsageRepository;
import server.database.RecipeRepository;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = Main.class)
class IngredientUsageControllerTest {
    private IngredientUsage testIngUsage;
    private Ingredient ing;
    private Recipe rec;
    private IngredientUsageController ingredientUsageController;
    @Autowired
    private IngredientUsageRepository tester;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setup() {
        ing = new Ingredient("Beef", 20, 10, 0);
        rec = new Recipe("Test Recipe", "en", List.of("Step 1", "Step 2"));
        testIngUsage = new IngredientUsage(rec, ing, 5, "g");
        ingredientRepository.save(ing);
        recipeRepository.save(rec);
        tester.save(testIngUsage);
        ingredientUsageController = new IngredientUsageController(tester);
    }

    @Test
    public void testFetchAllIngredientUsages() {
        assertEquals(List.of(testIngUsage), ingredientUsageController.fetchAllIngredientUsages());
    }

    @Test
    public void testFetchAllIngredientsInRecipe() {
        assertEquals(List.of(testIngUsage), ingredientUsageController.fetchAllIngredientsInRecipe(rec.id));
    }

    @Test
    public void testFetchRecipeCount() {
        assertEquals(1, ingredientUsageController.fetchRecipeCount(ing.id));
    }
}