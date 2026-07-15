package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiceRollTest {

    private DiceRoll diceRoll;

    @BeforeEach
    void runBefore() {
        diceRoll = new DiceRoll(0, 2, 0, 0, 0, 1);
        diceRoll.setSessionId(-1);
    }

    @Test
    void testConstructor() {
        assertEquals(0, diceRoll.getResult());
        assertEquals(-1, diceRoll.getSessionId());
        assertEquals(2, diceRoll.getDice().get(6));
        assertEquals(1, diceRoll.getDice().get(20));
        assertFalse(diceRoll.getDice().containsKey(4));
    }

    @Test
    void testIdsAreUnique() {
        DiceRoll r2 = new DiceRoll(1, 0, 0, 0, 0, 0);
        assertNotEquals(diceRoll.getId(), r2.getId());
    }

    @Test
    void testRoll() {
        diceRoll.roll();
        int result = diceRoll.getResult();
        assertTrue(result >= 3);
        assertTrue(result <= 26);
    }

    @Test
    void testRollChangesResultFromDefault() {
        assertEquals(0, diceRoll.getResult());
        diceRoll.roll();
        assertNotEquals(0, diceRoll.getResult());
    }
}