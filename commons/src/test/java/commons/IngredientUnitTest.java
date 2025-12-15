package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IngredientUnitTest {
    private IngredientUnit testUnitFormal;
    private IngredientUnit testUnitInformal;

    @BeforeEach
    void setUp() {
        testUnitFormal = new IngredientUnit("kilograms", UnitType.MASS, true, 0.001);
        testUnitInformal = new IngredientUnit("drop", UnitType.VOLUME, false, 0);
    }

    @Test
    void toStringTestFormal() {
        String expected = "The formal measurement unit kilograms";
        assertEquals(expected, testUnitFormal.toString());
    }

    @Test
    void toStringTestInformal() {
        String expected = "The informal measurement unit pinch";
        assertEquals(expected, testUnitInformal.toString());
    }
}