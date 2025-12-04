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
     * @return the added ingredient if added successfully, null otherwise
     */
    @PostMapping("/")
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.addIngredient(ingredient);
    }

    /**
     * Delete ingredient in request body to database
     * @param id id of the ingredient to delete
     */
    @DeleteMapping("/{id}")
    public void deleteIngredient(@PathVariable("id") Long id) {
        ingredientService.deleteIngredient(id);
    }

    /**
     * Update name of ingredient with an updated ingredient
     * @param id id of ingredient to replace the old value in
     * @param updatedData new data to replace old with
     */
    @PutMapping("/name/{id}")
    public void changeName(@PathVariable("id") Long id, @RequestBody String updatedData) {
        if (updatedData != null) {
            ingredientService.changeName(id, updatedData);
        }
    }

    /**
     * Update fat in ingredient with an updated ingredient
     * @param id id of ingredient to replace the old value in
     * @param updatedData new data to replace old with
     */
    @PutMapping("/fat/{id}")
    public void changeFat(@PathVariable("id") Long id, @RequestBody Double updatedData) {
        if (updatedData != null) {
            ingredientService.changeFat(id, updatedData);
        }
    }

    /**
     * Update carbs in ingredient with an updated ingredient
     * @param id id of ingredient to replace the old value in
     * @param updatedData new data to replace old with
     */
    @PutMapping("/carbs/{id}")
    public void changeCarbs(@PathVariable("id") Long id, @RequestBody Double updatedData) {
        if (updatedData != null) {
            ingredientService.changeCarbs(id, updatedData);
        }
    }

    /**
     * Update protein in ingredient with an updated ingredient
     * @param id id of ingredient to replace the old value in
     * @param updatedData new data to replace old with
     */
    @PutMapping("/protein/{id}")
    public void changeProtein(@PathVariable("id") Long id, @RequestBody Double updatedData) {
        if (updatedData != null) {
            ingredientService.changeProtein(id, updatedData);
        }
    }
}
