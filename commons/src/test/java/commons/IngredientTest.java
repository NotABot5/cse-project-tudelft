package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void testToString() {
        String toStringRes = testIngredient.toString();
        assertTrue(toStringRes.contains("Ingredient"));
        assertTrue(toStringRes.contains("carbs=1.0"));
        assertTrue(toStringRes.contains("fat=1.0"));
        assertTrue(toStringRes.contains("protein=1.0"));
        assertTrue(toStringRes.contains("name=test"));
    }

    @Test
    public void compareTest() {
        Ingredient smallerIngredient = new Ingredient("Beef", 1, 1, 1);
        Ingredient largerIngredient = new Ingredient("Onion", 0, 0,0);
        assertEquals(-1, smallerIngredient.compareTo(largerIngredient));
    }
}