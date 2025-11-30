package server.api;

import commons.IngredientUsage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import server.database.IngredientUsageRepository;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/api/ingredientUsage")
public class IngredientUsageController {
    public final IngredientUsageRepository ingredientUsageRepository;

    public IngredientUsageController(IngredientUsageRepository ingredientUsageRepository) {
        this.ingredientUsageRepository = ingredientUsageRepository;
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
}
