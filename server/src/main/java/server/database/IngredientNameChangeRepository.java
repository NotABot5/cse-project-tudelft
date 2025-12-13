package server.database;

import org.springframework.data.jpa.repository.JpaRepository;
import commons.IngredientNameChange;

public interface IngredientNameChangeRepository extends JpaRepository<IngredientNameChange, Long> {
}
