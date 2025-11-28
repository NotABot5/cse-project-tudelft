package server.api;

import java.util.List;

import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import commons.Recipe;
import server.database.RecipeRepository;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeRepository repo;

    public RecipeController(RecipeRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public List<Recipe> getAll() {
        return repo.findAll();
    }

    @GetMapping("/names")
    public List<String> getRecipeNames() {
        return repo.findAll().stream().map(r -> r.name).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        return repo.findById(id)
                .map(recipe -> ResponseEntity.ok().body(recipe))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(path = { "", "/" })
    public ResponseEntity<Recipe> add(@RequestBody Recipe recipe) {

        if (StringUtils.isEmpty(recipe.name)) {
            return ResponseEntity.badRequest().build();
        }

        Recipe saved = repo.save(recipe);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> update(@PathVariable("id") long id, @RequestBody Recipe recipe) {
        // First, check if a recipe with this ID actually exists in the database.
        if (!repo.existsById(id)) {
            // If it doesn't exist, we can't update it. Return a "404 Not Found" error.
            return ResponseEntity.notFound().build();
        }

        // You could add more validation here, for example, checking if the provided
        // recipe name is empty, similar to the add() method.
        if (StringUtils.isEmpty(recipe.name)) {
            return ResponseEntity.badRequest().build();
        }

        // The save() method is smart. Since the 'recipe' object sent by the client
        // will contain the 'id' of an existing recipe, JPA will perform an UPDATE
        // operation in the database instead of an INSERT.
        Recipe saved = repo.save(recipe);

        // Return the updated recipe and a "200 OK" status.
        return ResponseEntity.ok(saved);
    }
}
