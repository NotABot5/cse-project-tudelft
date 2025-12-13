package server.services;

import commons.Ingredient;
import commons.IngredientNameChange;
import org.springframework.stereotype.Service;
import server.database.IngredientNameChangeRepository;
import server.database.IngredientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    public final IngredientRepository ingredientRepository;
    public final IngredientNameChangeRepository nameChangeRepository;

    public IngredientService(IngredientRepository ingredientRepository,
                             IngredientNameChangeRepository nameChangeRepository) {
        this.ingredientRepository = ingredientRepository;
        this.nameChangeRepository = nameChangeRepository;
    }


    /**
     * Adds ingredient to repository, given that it is not null and does not already exist
     * "Already existing" refers to whether an ingredient with the same ID exists
     * @param ingredient ingredient to be added
     * @return added ingredient if it was added successfully, null otherwise
     */
    public Ingredient addIngredient(Ingredient ingredient) {
        if ((ingredient != null)
                && (!ingredientRepository.existsById(ingredient.getId()))) {
            return ingredientRepository.save(ingredient);
        }
        return null;
    }

    /**
     * Removes ingredient, given that it exists
     * "Existing" refers to whether an ingredient with the same ID exists
     * @param id id of ingredient to be deleted
     * @return true iff ingredient was deleted successfully
     */
    public boolean deleteIngredient (long id) {
        if (ingredientRepository.existsById(id)) {
            ingredientRepository.deleteById(id);
            return(true);
        }
        return(false);
    }

    /**
     * Changes protein per 100g to new value specified, given that it is not null
     * Produces NoSuchElementException in case ingredient with id does not exist
     * @param id id of ingredient to be changed
     * @param newValue new protein value
     */
    public void changeProtein(long id, double newValue) {
        Ingredient toBeChanged = ingredientRepository.findById(id).orElseThrow();
        toBeChanged.setProtein(newValue);
        //Note: According to documentation, Spring saves entities that are updated this way automatically
    }

    /**
     * Changes fat per 100g to new value specified, given that it is not null
     * Produces NoSuchElementException in case ingredient with id does not exist
     * @param id id of ingredient to be changed
     * @param newValue new fat value
     */
    public void changeFat(long id, double newValue) {
        Ingredient toBeChanged = ingredientRepository.findById(id).orElseThrow();
        toBeChanged.setFat(newValue);
        //Note: According to documentation, Spring saves entities that are updated this way automatically
    }

    /**
     * Changes carbs per 100g to new value specified, given that it is not null
     * Produces NoSuchElementException in case ingredient with id does not exist
     * @param id id of ingredient to be changed
     * @param newValue new carbs value
     */
    public void changeCarbs(long id, double newValue) {
        Ingredient toBeChanged = ingredientRepository.findById(id).orElseThrow();
        toBeChanged.setCarbs(newValue);
        //Note: According to documentation, Spring saves entities that are updated this way automatically
    }

    /**
     * Changes name of ingredient to new value specified, given that it is not null
     * Produces NoSuchElementException in case ingredient with id does not exist
     * @param id id of ingredient to be changed
     * @param newName new name of ingredient
     */
    public void changeName(long id, String newName) {
        if (newName != null) {
            Ingredient toBeChanged = ingredientRepository.findById(id).orElseThrow();
            toBeChanged.setName(newName);
        }
    }

    /**
     * Fetches all ingredients from database
     * @return list of all ingredients
     */
    public List<Ingredient> fetchAllIngredients() {
        return ingredientRepository.findAll();
    }

    /**
     * Fetches an ingredient from database by their ID
     * @return optional of the fetched ingredient if ID present, empty optional otherwise
     */
    public Optional<Ingredient> fetchIngredientByID(long id) {
        return ingredientRepository.findById(id);
    }

    /**
     * Fetches all ingredients from database and sorts them by name
     * @return list of all ingredients, sorted by name (alphabetically a -> z)
     */
    public List<Ingredient> fetchIngredientsSorted() {
        List<Ingredient> rawList = ingredientRepository.findAll();
        return(rawList.stream().sorted().toList());
    }
    /**
     * Changes the name of an Ingredient with a new one
     * @param id the id of the ingredient
     * @param newName the new name of the ingredient
     */
    public void renameIngredient(long id, String newName) {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow();

        String oldName = ingredient.getName();

        ingredient.setName(newName);

        IngredientNameChange log =new IngredientNameChange( id,  oldName, newName);

        nameChangeRepository.save(log);
    }
}

