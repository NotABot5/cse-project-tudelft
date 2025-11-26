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
     * @return true iff ingredient usage was added successfully
     */
    public boolean addIngredientUsage(IngredientUsage ingredientUsage) {
        if ((ingredientUsage != null)
                && (!ingredientUsageRepository.existsById(ingredientUsage.getId()))) {
            ingredientUsageRepository.save(ingredientUsage);
            return(true);
        }
        return(false);
    }

    /**
     * Removes ingredient usage, given that it is not null and exists
     * "Existing" refers to whether an ingredient usage with the same ID exists
     * @param ingredientUsage ingredient usage to be deleted
     * @return true iff ingredient usage was deleted successfully
     */
    public boolean deleteIngredientUsage (IngredientUsage ingredientUsage) {
        if ((ingredientUsage != null)
                && (ingredientUsageRepository.existsById(ingredientUsage.getId()))) {
            ingredientUsageRepository.deleteById(ingredientUsage.getId());
            return(true);
        }
        return(false);
    }

    /**
     * Changes ingredient amount to new value specified, given that it is not null
     * Produces NoSuchElementException in case ingredient usage with id does not exist
     * @param ingredientUsage ingredient usage to be changed
     * @param newValue new protein value
     */
    public void changeAmount(IngredientUsage ingredientUsage, int newValue) {
        if ((ingredientUsage != null)) {
            IngredientUsage toBeChanged = ingredientUsageRepository.findById(ingredientUsage.getId()).orElseThrow();
            toBeChanged.setAmount(newValue);
            //Note: According to documentation, Spring saves entities that are updated this way automatically
        }
    }

    //NOTE: Have not added a "changeUnit" method as the datatype of units may be changed later
}
