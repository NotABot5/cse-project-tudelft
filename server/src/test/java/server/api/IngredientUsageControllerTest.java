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
import server.services.IngredientUsageService;

import java.util.List;
import java.util.Optional;

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
    private IngredientUsage tempIngUsage;

    @BeforeEach
    public void setup() {
        ing = new Ingredient("Beef", 20, 10, 0);
        rec = new Recipe("Test Recipe", "en", List.of("Step 1", "Step 2"));
        testIngUsage = new IngredientUsage(rec, ing, 5, "g");
        ingredientRepository.save(ing);
        recipeRepository.save(rec);
        tester.save(testIngUsage);
        IngredientUsageService ingredientUsageService = new IngredientUsageService(tester);
        ingredientUsageController = new IngredientUsageController(tester, ingredientUsageService);
        tempIngUsage = new IngredientUsage(
                new Recipe("Test Recipe", "en", List.of("Step 1", "Step 2")),
                new Ingredient("Beef", 20, 10, 0),
                5,
                "g"
        );
    }

    @Test
    public void testFetchAllIngredientUsages() {
        assertEquals(List.of(testIngUsage), ingredientUsageController.fetchAllIngredientUsages().getBody());
    }

    @Test
    public void testFetchAllIngredientsInRecipe() {
        assertEquals(List.of(testIngUsage), ingredientUsageController.fetchAllIngredientsInRecipe(rec.id).getBody());
    }

    @Test
    public void testFetchRecipeCount() {
        assertEquals(1, ingredientUsageController.fetchRecipeCount(ing.id).getBody());
    }

    @Test
    public void testAddIngredientUsage() {
        assertNotNull(ingredientUsageController.addIngredientUsage(tempIngUsage));
        Optional<IngredientUsage> res = tester.findById(tempIngUsage.getId());
        assertTrue(res.isPresent());
        assertEquals(tempIngUsage, res.get());
        tester.delete(tempIngUsage);
    }

    @Test
    public void testDeleteIngredientUsage() {
        IngredientUsage toDelete = new IngredientUsage(rec, ing, 5, "g");
        tester.save(toDelete);
        assertTrue(ingredientUsageController.deleteIngredientUsage(toDelete.getId()).getBody());
        assertEquals(List.of(testIngUsage), tester.findAll());
    }
}