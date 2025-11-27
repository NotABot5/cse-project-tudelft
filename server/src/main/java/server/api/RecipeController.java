/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server.api;

import java.util.List;
import java.util.Random;

import io.micrometer.common.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import commons.Recipe;
import server.database.RecipeRepository;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final Random random;
    private final RecipeRepository repo;

    public RecipeController(Random random, RecipeRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    @GetMapping(path = { "", "/" })
    public List<Recipe> getAll() {
        return repo.findAll();
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

    @GetMapping("rnd")
    public ResponseEntity<Recipe> getRandom() {
        var quotes = repo.findAll();
        var idx = random.nextInt((int) repo.count());
        return ResponseEntity.ok(quotes.get(idx));
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
