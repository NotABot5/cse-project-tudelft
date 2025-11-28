package server.services;

import commons.Recipe;
import org.springframework.stereotype.Service;
import server.database.RecipeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    /**
     * Makes sure that the recipe is not null
     * @param recipe the recipe that is being checked
     * @throws IllegalArgumentException if recipe is null
     */
    private void recipeNotNull(Recipe recipe) {
        if  (recipe == null) {
            throw new IllegalArgumentException("Recipe cannot be null");
        }

    }
    /**
     * Makes sure that the Preparation steps are not null
     * @param preparation the preparations that is being checked
     */
    private void preparationListNotNull(List<String> preparation) {
        if (preparation == null) {
            throw new IllegalArgumentException("Preparation steps cannot be null");
        }
    }

    /**
     * Adds a recipe to the repository if no other recipe with the same id exists
     * @param recipe recipe to be added
     * @return true if recipe was added successfully
     */
    public boolean addRecipe(Recipe recipe) {
        recipeNotNull(recipe);

        if (!recipeRepository.existsById(recipe.getId())) {
            recipeRepository.save(recipe);
            return true;
        }
        return false;
    }


    /**
     * Deletes a recipe from the repository if no other recipe with the same id exists
     * @param recipe recipe to be deleted
     * @return true if recipe existed and was deleted successfully
     */
    public boolean deleteRecipe(Recipe recipe) {
        recipeNotNull(recipe);

        if (recipeRepository.existsById(recipe.getId())) {
            recipeRepository.deleteById(recipe.getId());
            return true;
        }
        return false;
    }
    /**
     * Changes the languages of the recipe into a new one, given if is not null
     * Throws NoSuchElementException in case ingredient with id does not exist
     * @param recipe recipe of whose language will be changed
     * @param newLang new language
     */

    public void changeLang(Recipe recipe, String newLang) {
        recipeNotNull(recipe);

        if (newLang != null) {
            Recipe toBeChanged = recipeRepository.findById(recipe.getId()).orElseThrow();
            toBeChanged.setLang(newLang);
            recipeRepository.save(toBeChanged);
        }
    }

    /**
     * Changes the name of a recipe into a new one, if it is not null
     * @param recipe recipe of whose name will be changed
     * @param newName the new name
     */
    public void changeName(Recipe recipe, String newName) {
        recipeNotNull(recipe);

        if (newName != null) {
            Recipe toBeChanged = recipeRepository.findById(recipe.getId()).orElseThrow();
            toBeChanged.setName(newName);
            recipeRepository.save(toBeChanged);
        }
    }
    /**
     * Changes the steps of the preparation of a recipe, if they are not null and it exists
     * @param recipe recipe of whose preparation steps will be changed
     * @param newPreparation the preparation steps
     */
    public void changePreparation(Recipe recipe, List<String> newPreparation) {
        recipeNotNull(recipe);
        preparationListNotNull(newPreparation);


            Recipe toBeChanged = recipeRepository.findById(recipe.getId()).orElseThrow();
            toBeChanged.setPreparation(new ArrayList<>(newPreparation));
            recipeRepository.save(toBeChanged);

    }

    /**
     * Fetches all recipes from the database
     * @return a list with all recipes
     */
    public List<Recipe> fetchAllRecipes() {
        return recipeRepository.findAll();
    }

    /**
     * Fetches a recipe from the database by its id
     * @param id the id of the recipe
     * @return optional of the fetched recipe by its id only if it has an id, empty optional otherwise
     */
    public Optional<Recipe> fetchRecipeByID(long id) {
        return recipeRepository.findById(id);
    }
}
