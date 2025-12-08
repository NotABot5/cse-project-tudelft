package server.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import commons.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.database.RecipeRepository;

import java.util.List;
import java.util.Optional;

public class RecipeServiceTest {

    @Mock
    private RecipeRepository repo;

    @InjectMocks
    private RecipeService sut;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCloneRecipe() {
        // 1. Prepare: Create a recipe and mock the repository.
        Recipe original = new Recipe("Spaghetti", "en", List.of("Boil water", "Add pasta"));
        original.id = 1L;
        Recipe cloned = new Recipe("Cloned Spaghetti", "en", List.of("Boil water", "Add pasta"));
        when(repo.findById(1L)).thenReturn(Optional.of(original));
        when(repo.save(new Recipe("Cloned Spaghetti", "en", List.of("Boil water", "Add pasta")))).thenReturn(cloned);

        // 2. Perform: Call the clone method.
        Optional<Recipe> result = sut.cloneRecipe(1L, "Cloned Spaghetti");

        // 3. Assert: Check that the result is correct.
        assertTrue(result.isPresent());
        assertEquals(cloned, result.get());
    }

    @Test
    public void testCloneRecipeWithEmptyName() {
        // 1. Perform: Call the clone method with an empty name.
        Optional<Recipe> result = sut.cloneRecipe(1L, "");

        // 2. Assert: Check that the result is empty.
        assertTrue(result.isEmpty());
    }

    @Test
    public void testCloneNonExistentRecipe() {
        // 1. Prepare: Mock the repository to return an empty optional.
        when(repo.findById(1L)).thenReturn(Optional.empty());

        // 2. Perform: Call the clone method.
        Optional<Recipe> result = sut.cloneRecipe(1L, "Cloned Spaghetti");

        // 3. Assert: Check that the result is empty.
        assertTrue(result.isEmpty());
    }
}
