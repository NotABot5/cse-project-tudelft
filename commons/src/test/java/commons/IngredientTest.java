package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientTest {
    private Ingredient testIngredient;
    private Ingredient testIngredientEqual;
    private Ingredient testIngredientNotEqual;

    @BeforeEach
    void setUp() {
        testIngredient = new Ingredient("test", 1, 1, 1);
        testIngredientEqual = new Ingredient("test", 1, 1, 1);
        testIngredientNotEqual = new Ingredient("test", 1, 1, 2);
    }

    @Test
    void testEqualsHashcode() {
        assertEquals(testIngredient.hashCode(), testIngredientEqual.hashCode());
        assertEquals(testIngredient, testIngredientEqual);
    }

    @Test
    void testNotEqualsHashcode() {
        assertNotEquals(testIngredient.hashCode(), testIngredientNotEqual.hashCode());
        assertNotEquals(testIngredient, testIngredientNotEqual);
    }
}