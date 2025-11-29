package services;

import commons.Ingredient;
import commons.IngredientUsage;
import commons.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.database.IngredientRepository;
import server.database.IngredientUsageRepository;
import server.database.RecipeRepository;
import server.services.Ingredient_Utility;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

// We use Mockito to create a mock of IngredientRepository, so we don't need to use a real database, and can provide controlled test data.
//I only got this idea/info from chatgpt. I wrote the tests myself


@ExtendWith(MockitoExtension.class)
class IngredientUtilityTest {
    @Mock
    IngredientRepository ingredientRepo;
    @Mock
    IngredientUsageRepository usageRepo;
    @Mock
    RecipeRepository recipeRepo;

    Ingredient_Utility util;
    @BeforeEach
    void setup() {
        util = new Ingredient_Utility(ingredientRepo, usageRepo, recipeRepo);
    }
    @Test
    void testGetAllAlphabeticallySortsCorrectly() {
        Ingredient b = new Ingredient("Banana", 0, 0, 0);

        Ingredient a = new Ingredient("Apple", 0, 0, 0);

        List<Ingredient> mockList = List.of(b, a);
        when(ingredientRepo.findAll()).thenReturn(mockList);

        List<Ingredient> sorted = util.getAllAlphabetically();

        assertEquals(List.of(a, b), sorted);
    }
    @Test
    void testGetAllAlphabeticallyEmpty() {
        when(ingredientRepo.findAll()).thenReturn(List.of());

        List<Ingredient> result = util.getAllAlphabetically();

        assertTrue(result.isEmpty());
    }
    // A test for the counting has to be implemented, but idk how to that...




}







