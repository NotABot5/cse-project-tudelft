package services;

import commons.*;
import server.Main;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import server.database.*;
import server.services.IngredientUsageService;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = Main.class)
public class IngredientUsageServiceTest {

    private IngredientUsageService ingredientUsageService;
    private IngredientUsage ingredientUsage;
    @Autowired
    private IngredientUsageRepository tester;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setup() {
        ingredientUsageService = new IngredientUsageService(tester);
        Recipe recipe = new Recipe("Lasagna", "Dutch", new ArrayList<>());
        recipe = recipeRepository.save(recipe);
        Ingredient ingredient = new Ingredient("Tomato sauce", 0, 1, 2);
        ingredient = ingredientRepository.save(ingredient);
        ingredientUsage = new IngredientUsage(recipe, ingredient, 500, "milliliters");
    }

    @Test
    public void addTest(){
        ingredientUsageService.addIngredientUsage(ingredientUsage);
        IngredientUsage found = tester.findById(ingredientUsage.getId()).get();
        assertEquals(ingredientUsage, found);
    }

    @Test
    public void addExistingTest() {
        ingredientUsageService.addIngredientUsage(ingredientUsage);
        assertFalse(ingredientUsageService.addIngredientUsage(ingredientUsage));
    }

    @Test
    public void deleteTest() {
        ingredientUsageService.addIngredientUsage(ingredientUsage);
        assertTrue(ingredientUsageService.deleteIngredientUsage(ingredientUsage));
    }

    @Test
    public void changeAmountTest() {
        ingredientUsageService.addIngredientUsage(ingredientUsage);
        ingredientUsageService.changeAmount(ingredientUsage, 5000);
        IngredientUsage found = tester.findById(ingredientUsage.getId()).get();
        assertEquals(5000, found.getAmount());
    }
}
