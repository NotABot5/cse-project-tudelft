package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientUsageTest {
    private Ingredient testIngredient;
    private Recipe testRecipe;
    private IngredientUsage testIngredientUsage;
    private IngredientUsage testIngredientUsageEqual;
    private IngredientUsage testIngredientUsageNotEqual;

    @BeforeEach
    void setUp() {
        testIngredient = new Ingredient("test", 1, 1, 1);
        testRecipe = new Recipe("test", "en", List.of("a"));
        testIngredientUsage = new IngredientUsage(testRecipe, testIngredient, 5, "g");
        testIngredientUsageEqual = new IngredientUsage(testRecipe, testIngredient, 5, "g");
        testIngredientUsageNotEqual = new IngredientUsage(testRecipe, testIngredient, 10, "g");
    }

    @Test
    void testEqualsHashcode() {
        assertEquals(testIngredientUsage.hashCode(), testIngredientUsageEqual.hashCode());
        assertEquals(testIngredientUsage, testIngredientUsageEqual);
    }

    @Test
    void testNotEqualsHashcode() {
        assertNotEquals(testIngredientUsage.hashCode(), testIngredientUsageNotEqual.hashCode());
        assertNotEquals(testIngredientUsage, testIngredientUsageNotEqual);
    }

    @Test
    void testToString() {
        String toStringRes = testIngredientUsage.toString();
        assertTrue(toStringRes.contains("IngredientUsage"));
        assertTrue(toStringRes.contains("amount=5"));
        assertTrue(toStringRes.contains("unit=g"));
        assertTrue(toStringRes.contains("Recipe"));
        assertTrue(toStringRes.contains("Ingredient"));
    }
}