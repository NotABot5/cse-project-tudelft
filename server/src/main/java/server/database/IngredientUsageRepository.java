package server.database;

import commons.IngredientUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientUsageRepository extends JpaRepository<IngredientUsage, Long> {
}
