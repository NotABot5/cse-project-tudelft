package services;

import server.Main;
import commons.Ingredient;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import server.database.IngredientRepository;
import server.services.IngredientService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(classes = Main.class)
public class IngredientServiceTest {

    private IngredientService ingredientService;
    private Ingredient ingredient;

    @Autowired
    private IngredientRepository tester;

    @BeforeEach
    public void setup() {
        ingredientService = new IngredientService(tester);
        ingredient = new Ingredient("Beef", 20, 10, 0);
    }

    @Test
    public void addTest() {
        ingredientService.addIngredient(ingredient);
        Ingredient found = tester.findById(ingredient.getId()).get();
        assertEquals(ingredient, found);
    }

    @Test
    public void addExistingTest() {
        ingredientService.addIngredient(ingredient);
        assertFalse(ingredientService.addIngredient(ingredient));
    }

    @Test
    public void deleteTest() {
        ingredientService.addIngredient(ingredient);
        assertTrue(ingredientService.deleteIngredient(ingredient));
    }

    @Test
    public void changeProteinTest() {
        ingredientService.addIngredient(ingredient);
        ingredientService.changeProtein(ingredient, 100);
        Ingredient found = tester.findById(ingredient.getId()).get();
        assertEquals(100, found.getProtein());
    }

    @Test
    public void changeFatTest() {
        ingredientService.addIngredient(ingredient);
        ingredientService.changeFat(ingredient, 22);
        Ingredient found = tester.findById(ingredient.getId()).get();
        assertEquals(22, found.getFat());
    }

    @Test
    public void changeCarbsTest() {
        ingredientService.addIngredient(ingredient);
        ingredientService.changeCarbs(ingredient, 1234);
        Ingredient found = tester.findById(ingredient.getId()).get();
        assertEquals(1234, found.getCarbs());
    }

    @Test
    public void changeNameTest() {
        ingredientService.addIngredient(ingredient);
        ingredientService.changeName(ingredient, "Pork");
        Ingredient found = tester.findById(ingredient.getId()).get();
        assertEquals("Pork", found.getName());
    }

    @Test
    public void fetchSortedTest() {
        ingredientService.addIngredient(ingredient);
        Ingredient ingredientFirst = new Ingredient("Apple",0,0,0);
        Ingredient ingredientLast = new Ingredient("Potato",10,4,2);
        ingredientService.addIngredient(ingredientFirst);
        ingredientService.addIngredient(ingredientLast);
        List<Ingredient> found = ingredientService.fetchIngredientsSorted();
        assertEquals(3, found.size());
        assertEquals(ingredientFirst, found.get(0));
        assertEquals(ingredient, found.get(1));
        assertEquals(ingredientLast, found.get(2));
    }
}
