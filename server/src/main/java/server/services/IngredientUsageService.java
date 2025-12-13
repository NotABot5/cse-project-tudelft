package server.services;

import commons.*;
import org.springframework.stereotype.Service;
import server.database.*;


@Service
public class IngredientUsageService {
    public final IngredientUsageRepository ingredientUsageRepository;

    public IngredientUsageService(IngredientUsageRepository ingredientUsageRepository) {
        this.ingredientUsageRepository = ingredientUsageRepository;
    }

    /**
     * Adds ingredient usage to repository, given that it is not null and does not already exist
     * "Already existing" refers to whether an ingredient usage with the same ID exists
     * @param ingredientUsage ingredient usage to be added
     * @return the added ingredient usage if it was added successfully, null otherwise
     */
    public IngredientUsage addIngredientUsage(IngredientUsage ingredientUsage) {
        if ((ingredientUsage != null)
                && (!ingredientUsageRepository.existsById(ingredientUsage.getId()))) {
            return ingredientUsageRepository.save(ingredientUsage);
        }
        return null;
    }

    /**
     * Removes ingredient usage, given that it exists
     * "Existing" refers to whether an ingredient usage with the same ID exists
     * @param id id of ingredient usage to be deleted
     * @return true iff ingredient usage was deleted successfully
     */
    public boolean deleteIngredientUsage (long id) {
        if ((ingredientUsageRepository.existsById(id))) {
            ingredientUsageRepository.deleteById(id);
            return(true);
        }
        return(false);
    }

    /**
     * Changes ingredient amount to new value specified, given that it is not null
     * Produces NoSuchElementException in case ingredient usage with id does not exist
     * @param id id of ingredient usage to be changed
     * @param newValue new protein value
     */
    public void changeAmount(long id, int newValue) {
        IngredientUsage toBeChanged = ingredientUsageRepository.findById(id).orElseThrow();
        toBeChanged.setAmount(newValue);
        //Note: According to documentation, Spring saves entities that are updated this way automatically
    }

    //NOTE: Have not added a "changeUnit" method as the datatype of units may be changed later
}
