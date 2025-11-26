package server.services;

import commons.Ingredient;
import org.springframework.stereotype.Service;
import server.database.IngredientRepository;

@Service
public class IngredientService {
    public final IngredientRepository ingredientRepository;

    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }


    /**
     * Adds ingredient to repository, given that it is not null and does not already exist
     * "Already existing" refers to whether an ingredient with the same ID exists
     * @param ingredient ingredient to be added
     * @return true iff ingredient was added successfully
     */
    public boolean addIngredient(Ingredient ingredient) {
        if ((ingredient != null)
                && (!ingredientRepository.existsById(ingredient.getId()))) {
            ingredientRepository.save(ingredient);
            return(true);
        }
        return(false);
    }

    /**
     * Removes ingredient, given that it is not null and exists
     * "Existing" refers to whether an ingredient with the same ID exists
     * @param ingredient ingredient to be deleted
     * @return true iff ingredient was deleted successfully
     */
    public boolean deleteIngredient (Ingredient ingredient) {
        if ((ingredient != null)
                && (ingredientRepository.existsById(ingredient.getId()))) {
            ingredientRepository.deleteById(ingredient.getId());
            return(true);
        }
        return(false);
    }

    /**
     * Changes protein per 100g to new value specified, given that it is not null
     * Produces NoSuchElementException in case ingredient with id does not exist
     * @param ingredient ingredient to be changed
     * @param newValue new protein value
     */
    public void changeProtein(Ingredient ingredient, double newValue) {
        if ((ingredient != null)) {
            Ingredient toBeChanged = ingredientRepository.findById(ingredient.getId()).orElseThrow();
            toBeChanged.setProtein(newValue);
            //Note: According to documentation, Spring saves entities that are updated this way automatically
        }
    }

    /**
     * Changes fat per 100g to new value specified, given that it is not null
     * Produces NoSuchElementException in case ingredient with id does not exist
     * @param ingredient ingredient to be changed
     * @param newValue new fat value
     */
    public void changeFat(Ingredient ingredient, double newValue) {
        if ((ingredient != null)) {
            Ingredient toBeChanged = ingredientRepository.findById(ingredient.getId()).orElseThrow();
            toBeChanged.setFat(newValue);
            //Note: According to documentation, Spring saves entities that are updated this way automatically
        }
    }

    /**
     * Changes carbs per 100g to new value specified, given that it is not null
     * Produces NoSuchElementException in case ingredient with id does not exist
     * @param ingredient ingredient to be changed
     * @param newValue new carbs value
     */
    public void changeCarbs(Ingredient ingredient, double newValue) {
        if ((ingredient != null)) {
            Ingredient toBeChanged = ingredientRepository.findById(ingredient.getId()).orElseThrow();
            toBeChanged.setCarbs(newValue);
            //Note: According to documentation, Spring saves entities that are updated this way automatically
        }
    }

    /**
     * Changes name of ingredient to new value specified, given that it is not null
     * Produces NoSuchElementException in case ingredient with id does not exist
     * @param ingredient ingredient to be changed
     * @param newName new name of ingredient
     */
    public void changeName(Ingredient ingredient, String newName) {
        if (ingredient != null && newName != null) {
            Ingredient toBeChanged = ingredientRepository.findById(ingredient.getId()).orElseThrow();
            toBeChanged.setName(newName);
        }
    }

}
