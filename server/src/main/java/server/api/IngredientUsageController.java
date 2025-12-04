package server.api;

import commons.IngredientUsage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import server.database.IngredientUsageRepository;
import server.services.IngredientUsageService;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/api/ingredientUsage")
public class IngredientUsageController {
    public final IngredientUsageRepository ingredientUsageRepository;
    public final IngredientUsageService ingredientUsageService;

    public IngredientUsageController(
            IngredientUsageRepository ingredientUsageRepository, IngredientUsageService ingredientUsageService
    ) {
        this.ingredientUsageRepository = ingredientUsageRepository;
        this.ingredientUsageService = ingredientUsageService;
    }

    /**
     * Fetches all ingredient usages from database
     * @return list of all ingredient usages
     */
    @GetMapping("/")
    public List<IngredientUsage> fetchAllIngredientUsages() {
        return ingredientUsageRepository.findAll();
    }

    /**
     * Fetches all ingredient usages matching specified recipe id
     * @return list of all matching ingredient usages
     */
    @GetMapping("/{id}")
    public List<IngredientUsage> fetchAllIngredientsInRecipe(@PathVariable("id") Long id) {
        return ingredientUsageRepository.findAll().stream().filter(
                (IngredientUsage ing) -> Objects.equals(ing.getRecipe().getId(), id)
        ).toList();
    }

    /**
     * Fetches how many recipes the ingredient with the specified id matches to
     * @return count of recipes in which an ingredient is present
     */
    @GetMapping("/recipeCount/{id}")
    public long fetchRecipeCount(@PathVariable("id") Long id) {
        return ingredientUsageRepository.findAll().stream().filter(
                (IngredientUsage ing) -> ing.getIngredient().getId() == id
        ).count();
    }

    /**
     * Adds a new ingredient usage to the database
     * @param ingredientUsage the ingredient usage to add
     * @return added ingredient usage, or null if adding unsuccessful
     */
    @PostMapping("/")
    public IngredientUsage addIngredientUsage(@RequestBody IngredientUsage ingredientUsage) {
        return ingredientUsageService.addIngredientUsage(ingredientUsage);
    }

    /**
     * Deletes an ingredient usage from the database
     * @param id id of ingredient usage to delete
     * @return true if deleted successfully, false otherwise
     */
    @DeleteMapping("/{id}")
    public boolean deleteIngredientUsage(@PathVariable("id") Long id) {
        return ingredientUsageService.deleteIngredientUsage(id);
    }
}
