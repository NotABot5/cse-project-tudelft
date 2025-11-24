package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
    private Recipe testRecipe;
    private Recipe testRecipeEqual;
    private Recipe testRecipeNotEqual;

    @BeforeEach
    void setUp() {
        testRecipe = new Recipe("test", "en", List.of("a"));
        testRecipeEqual = new Recipe("test", "en", List.of("a"));
        testRecipeNotEqual = new Recipe("test", "en", List.of("b"));
    }

    @Test
    void testEqualsHashcode() {
        assertEquals(testRecipe.hashCode(), testRecipeEqual.hashCode());
        assertEquals(testRecipe, testRecipeEqual);
    }

    @Test
    void testNotEqualsHashcode() {
        assertNotEquals(testRecipe.hashCode(), testRecipeNotEqual.hashCode());
        assertNotEquals(testRecipe, testRecipeNotEqual);
    }

    @Test
    void testToString() {
        System.out.println(testRecipe.toString());
        String toStringRes = testRecipe.toString();
        assertTrue(toStringRes.contains("Recipe"));
        assertTrue(toStringRes.contains("lang=en"));
        assertTrue(toStringRes.contains("name=test"));
        assertTrue(toStringRes.contains("preparation=[a]"));
        assertTrue(toStringRes.contains("ingredients"));
    }
}