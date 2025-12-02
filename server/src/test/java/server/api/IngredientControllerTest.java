package server.api;

import commons.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import server.Main;
import server.database.IngredientRepository;
import server.services.IngredientService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = Main.class)
class IngredientControllerTest {
    private IngredientController ingredientController;
    private Ingredient ingredient;
    private Ingredient ingredientCopy;

    @Autowired
    private IngredientRepository tester;

    @BeforeEach
    public void setup() {
        ingredient = new Ingredient("Beef", 20, 10, 0);
        ingredientCopy = new Ingredient("Beef", 20, 10, 0);
        ingredientController = new IngredientController(new IngredientService(tester));
        tester.save(ingredient);
    }

    @Test
    public void fetchAllTest() {
        assertEquals(List.of(ingredient), ingredientController.fetchAllIngredients());
    }

    @Test
    public void fetchIngredientWithIDTest() {
        assertEquals(ingredient, ingredientController.fetchIngredientWithID(ingredient.id).getBody());
    }

    @Test
    public void fetchSortedTest() {
        ingredientController.addIngredient(ingredient);
        Ingredient ingredientFirst = new Ingredient("Apple",0,0,0);
        Ingredient ingredientLast = new Ingredient("Potato",10,4,2);
        ingredientController.addIngredient(ingredientFirst);
        ingredientController.addIngredient(ingredientLast);
        List<Ingredient> found = ingredientController.fetchIngredientsSorted();
        assertEquals(3, found.size());
        assertEquals(ingredientFirst, found.get(0));
        assertEquals(ingredient, found.get(1));
        assertEquals(ingredientLast, found.get(2));
    }

    @Test
    public void addDeleteIngredientTest() {
        assertTrue(ingredientController.addIngredient(ingredientCopy));
        assertEquals(2, ingredientController.fetchAllIngredients().size());
        assertTrue(ingredientController.deleteIngredient(ingredientCopy));
        assertEquals(List.of(ingredient), ingredientController.fetchAllIngredients());
    }

    @Test
    public void updateNameTest() {
        ingredient.name = "Pork";
        ingredientController.changeName(ingredient);
        assertEquals(List.of(ingredient), ingredientController.fetchAllIngredients());
    }


    @Test
    public void updateCarbsTest() {
        ingredient.carbs = 100;
        ingredientController.changeCarbs(ingredient);
        assertEquals(List.of(ingredient), ingredientController.fetchAllIngredients());
    }


    @Test
    public void updateProteinTest() {
        ingredient.protein = 100;
        ingredientController.changeProtein(ingredient);
        assertEquals(List.of(ingredient), ingredientController.fetchAllIngredients());
    }


    @Test
    public void updateFatTest() {
        ingredient.fat = 100;
        ingredientController.changeFat(ingredient);
        assertEquals(List.of(ingredient), ingredientController.fetchAllIngredients());
    }
}