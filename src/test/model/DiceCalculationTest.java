package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiceCalculationTest {

    private DiceCalculation diceCalculation;

    @BeforeEach
    void runBefore() {
        diceCalculation = new DiceCalculation("Test");
        diceCalculation.setSessionId(-1);
    }

    @Test
    void testConstructor() {
        assertEquals("Test", diceCalculation.getLabel());
        assertEquals(-1, diceCalculation.getSessionId());
        assertTrue(diceCalculation.getDice().isEmpty());
        assertEquals(0, diceCalculation.getLowestValue());
        assertEquals(0, diceCalculation.getAverageValue());
        assertEquals(0, diceCalculation.getHighestValue());
    }

    @Test
    void testIdsAreUnique() {
        DiceCalculation c2 = new DiceCalculation("Test 2");
        assertNotEquals(diceCalculation.getId(), c2.getId());
    }

    @Test
    void testSetDiceSingleType() {
        diceCalculation.setDice(6, 2);
        assertEquals(2, diceCalculation.getLowestValue());
        assertEquals(7, diceCalculation.getAverageValue());
        assertEquals(12, diceCalculation.getHighestValue());
    }

    @Test
    void testSetDiceMultipleTypes() {
        diceCalculation.setDice(6, 2);
        diceCalculation.setDice(8, 1);
        assertEquals(3, diceCalculation.getLowestValue());
        assertEquals(11.5, diceCalculation.getAverageValue());
        assertEquals(20, diceCalculation.getHighestValue());
    }

    @Test
    void testSetDiceAllTypes() {
        diceCalculation.setDice(4, 1);
        diceCalculation.setDice(6, 1);
        diceCalculation.setDice(8, 1);
        diceCalculation.setDice(10, 1);
        diceCalculation.setDice(12, 1);
        diceCalculation.setDice(20, 1);
        assertEquals(6, diceCalculation.getLowestValue());
        assertEquals(33.0, diceCalculation.getAverageValue());
        assertEquals(60, diceCalculation.getHighestValue());
    }

    @Test
    void testSetDiceOverwrite() {
        diceCalculation.setDice(6, 2);
        diceCalculation.setDice(6, 3);
        assertEquals(3, diceCalculation.getLowestValue());
        assertEquals(18, diceCalculation.getHighestValue());
    }
}