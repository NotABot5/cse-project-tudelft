package server.api;

import commons.Ingredient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import server.services.IngredientService;

import java.util.List;

@Controller
@RequestMapping("/api/ingredients")
public class IngredientController {
    IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/")
    public List<Ingredient> fetchAllIngredients() {
        return ingredientService.fetchAllIngredients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> fetchIngredientWithID(@PathVariable("id") Long id) {
        return ResponseEntity.of(ingredientService.fetchIngredientByID(id));
    }
}
