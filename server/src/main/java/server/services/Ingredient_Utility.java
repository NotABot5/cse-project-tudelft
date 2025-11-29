package server.services;

import commons.IngredientUsage;
import commons.Ingredient;
import server.database.IngredientRepository;
import server.database.IngredientUsageRepository;
import server.database.RecipeRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Ingredient_Utility {

    private final IngredientRepository ingredientRepo;
    private final IngredientUsageRepository usageRepo;
    private final RecipeRepository recipeRepo;

    /**
     * Creates a new IngredientUtility using the given repositories.
     *
     * @param ingredientRepo      repository for storing ingredients
     * @param usageRepo           repository for ingredient usages inside recipes
     * @param recipeRepo          repository containing recipes
     */
    public Ingredient_Utility(IngredientRepository ingredientRepo,
                              IngredientUsageRepository usageRepo,
                              RecipeRepository recipeRepo) {

        this.ingredientRepo = ingredientRepo;
        this.usageRepo = usageRepo;
        this.recipeRepo = recipeRepo;
    }
    /**
     * Returns a list of all ingredients sorted alphabetically by name.
     *
     * @return alphabetically sorted ingredient list
     */
    public List<Ingredient> getAllAlphabetically() {
        return ingredientRepo.findAll().stream()
                .sorted(Comparator.comparing(Ingredient::getName))
                .collect(Collectors.toList());
    }

    /**
     * Counts in how many recipes a given ingredient is used.
     * Used for the distinct() and count() the website https://www.geeksforgeeks.org/java/stream-count-method-java/
     *
     * @param ingredientId id of the ingredient to check
     * @return number of recipes where this ingredient appears
     */

    public long countUsageInRecipes(long ingredientId) {
        return usageRepo.findAll().stream()
                .filter(u -> u.getIngredient().getId() == ingredientId)
                .map(IngredientUsage::getRecipe)
                .distinct()
                .count();
    }

}
