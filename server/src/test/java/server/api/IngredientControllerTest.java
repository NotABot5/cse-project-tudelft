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

    @Autowired
    private IngredientRepository tester;

    @BeforeEach
    public void setup() {
        ingredient = new Ingredient("Beef", 20, 10, 0);
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
}