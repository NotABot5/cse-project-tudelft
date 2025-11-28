package services;

import server.Main;
import commons.Recipe;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import server.database.RecipeRepository;
import server.services.RecipeService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = Main.class)
public class RecipeServiceTest {

    private RecipeService recipeService;
    private Recipe recipe;

    @Autowired
    private RecipeRepository tester;


    @BeforeEach
    public void setup() {
        recipeService = new RecipeService(tester);
        recipe = new Recipe("Test Recipe", "en", List.of("Step 1", "Step 2"));
    }

    @Test
    public void addTest() {
        recipeService.addRecipe(recipe);
        Recipe found = tester.findById(recipe.getId()).get();
        assertEquals(recipe, found);
    }

    @Test
    public void addExistingTest() {
            recipeService.addRecipe(recipe);
            assertFalse(recipeService.addRecipe(recipe));
        }

    @Test
    public void deleteTest() {
        recipeService.addRecipe(recipe);
        assertTrue(recipeService.deleteRecipe(recipe));
    }

    @Test
    public void changeNameTest() {
        recipeService.addRecipe(recipe);
        recipeService.changeName(recipe, "Pizza Margherita");
        Recipe found = tester.findById(recipe.getId()).get();
        assertEquals("Pizza Margherita", found.getName());
    }
    @Test
    public void changeLangTest() {
        recipeService.addRecipe(recipe);
        recipeService.changeLang(recipe, "En");
        Recipe found = tester.findById(recipe.getId()).get();
        assertEquals("En", found.getLang());
    }
    @Test
    public void changePreparationTest() {
        recipeService.addRecipe(recipe);
        recipeService.changePreparation(recipe, List.of("Step 1", "Step 2"));
        Recipe found = tester.findById(recipe.getId()).get();
        assertEquals(List.of ("Step 1", "Step 2"), found.getPreparation());
    }
}
