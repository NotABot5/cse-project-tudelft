package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IngredientUsageTest {
    private Ingredient testIngredient;
    private Recipe testRecipe;
    private IngredientUnit testUnit;
    private IngredientUnit testUnitInformal;
    private IngredientUsage testIngredientUsage;
    private IngredientUsage testIngredientUsageEqual;
    private IngredientUsage testIngredientUsageNotEqual;

    @BeforeEach
    void setUp() {
        testIngredient = new Ingredient("test", 1, 1, 1);
        testRecipe = new Recipe("test", "en", List.of("a"));
        testUnit = new IngredientUnit("kilograms", UnitType.MASS, true, 1000);
        testUnitInformal = new IngredientUnit("A pinch", UnitType.MASS, false, 0);
        testIngredientUsage = new IngredientUsage(testRecipe, testIngredient, 5, testUnit);
        testIngredientUsageEqual = new IngredientUsage(testRecipe, testIngredient, 5, testUnit);
        testIngredientUsageNotEqual = new IngredientUsage(testRecipe, testIngredient, 10, testUnit);
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
        assertTrue(toStringRes.contains("unit=The formal measurement unit kilograms"));
        assertTrue(toStringRes.contains("Recipe"));
        assertTrue(toStringRes.contains("Ingredient"));
    }

    @Test
    void testConversion() {
        int expected = 5000;
        assertEquals(expected, testIngredientUsage.convertedAmount());
    }

    @Test
    void testConvertedToStringFormal() {
        String expected = "5000 grams of test";
        assertEquals(expected, testIngredientUsage.convertedAmountToString());
    }

    @Test
    void testConvertedToStringInformal() {
        testIngredientUsage.setUnit(testUnitInformal);
        testIngredientUsage.setAmount(0);
        String expected = "A pinch of test";
        assertEquals(expected, testIngredientUsage.convertedAmountToString());
    }

    @Test
    void testConvertedToStringCount() {
        testIngredientUsage.setUnit(new IngredientUnit("", UnitType.COUNT, true, 1));
        String expected = "5 test";
        assertEquals(expected, testIngredientUsage.convertedAmountToString());
    }
}