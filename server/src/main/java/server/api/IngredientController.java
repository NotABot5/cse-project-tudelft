package server.api;

import commons.Ingredient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.services.IngredientService;

import java.util.List;

@Controller
@RequestMapping("/api/ingredients")
public class IngredientController {
    IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Fetch all ingredients from database and return on /
     * @return list of all ingredients in database
     */
    @GetMapping("/")
    public List<Ingredient> fetchAllIngredients() {
        return ingredientService.fetchAllIngredients();
    }

    /**
     * Fetch all ingredients from database and return on /{id}
     * @return response with fetched ingredient if ID was valid, NOT FOUND otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> fetchIngredientWithID(@PathVariable("id") Long id) {
        return ResponseEntity.of(ingredientService.fetchIngredientByID(id));
    }

    /**
     * Returns a sorted list of ingredients
     */
    @GetMapping("/sorted")
    public List<Ingredient> fetchIngredientsSorted() {
        return ingredientService.fetchIngredientsSorted();
    }

    /**
     * Add ingredient in request body to database
     * @param ingredient the ingredient to add
     * @return true if ingredient added successfully, false otherwise
     */
    @PostMapping("/")
    public boolean addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.addIngredient(ingredient);
    }

    /**
     * Delete ingredient in request body to database
     * @param ingredient the ingredient to delete
     * @return true if ingredient deleted successfully, false otherwise
     */
    @DeleteMapping("/")
    public boolean deleteIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.deleteIngredient(ingredient);
    }

    /**
     * Update name of ingredient with an updated ingredient
     * @param updatedIngredient ingredient to replace the old value with
     */
    @PutMapping("/name")
    public void changeName(@RequestBody Ingredient updatedIngredient) {
        if (updatedIngredient != null) {
            ingredientService.changeName(updatedIngredient, updatedIngredient.getName());
        }
    }

    /**
     * Update fat in ingredient with an updated ingredient
     * @param updatedIngredient ingredient to replace the old value with
     */
    @PutMapping("/fat")
    public void changeFat(@RequestBody Ingredient updatedIngredient) {
        if (updatedIngredient != null) {
            ingredientService.changeFat(updatedIngredient, updatedIngredient.getFat());
        }
    }

    /**
     * Update carbs in ingredient with an updated ingredient
     * @param updatedIngredient ingredient to replace the old value with
     */
    @PutMapping("/carbs")
    public void changeCarbs(@RequestBody Ingredient updatedIngredient) {
        if (updatedIngredient != null) {
            ingredientService.changeCarbs(updatedIngredient, updatedIngredient.getCarbs());
        }
    }

    /**
     * Update protein in ingredient with an updated ingredient
     * @param updatedIngredient ingredient to replace the old value with
     */
    @PutMapping("/protein")
    public void changeProtein(@RequestBody Ingredient updatedIngredient) {
        if (updatedIngredient != null) {
            ingredientService.changeProtein(updatedIngredient, updatedIngredient.getProtein());
        }
    }
}
