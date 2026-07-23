package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.json.JSONArray;
import org.json.JSONObject;

class DiceRollTest {

    private DiceRoll diceRoll;

    @BeforeEach
    void runBefore() {
        diceRoll = new DiceRoll(1, 2, 1, 2, 1, 1);
        diceRoll.setSessionId(-1);
    }

    @Test
    void testConstructor() {
        assertEquals(0, diceRoll.getResult());
        assertEquals(-1, diceRoll.getSessionId());
        assertEquals(1, diceRoll.getDice().get(4));
        assertEquals(2, diceRoll.getDice().get(6));
        assertEquals(1, diceRoll.getDice().get(8));
        assertEquals(2, diceRoll.getDice().get(10));
        assertEquals(1, diceRoll.getDice().get(12));
        assertEquals(1, diceRoll.getDice().get(20));
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
        assertTrue(result >= 8);
        assertTrue(result <= 76);
    }

    @Test
    void testRollChangesResultFromDefault() {
        assertEquals(0, diceRoll.getResult());
        diceRoll.roll();
        assertNotEquals(0, diceRoll.getResult());
    }

    @Test
    void testDiceRollJson() {
        JSONObject diceRollJson = diceRoll.toJsonObject();
        assertEquals(diceRoll.getId(), diceRollJson.getInt("id"));
        assertEquals(diceRoll.getResult(), diceRollJson.getInt("result"));
        assertEquals(diceRoll.getSessionId(), diceRollJson.getInt("sessionId"));
    }

    @Test
    void testDiceToJsonArray() {
        JSONArray diceAsJsonArray = diceRoll.diceToJsonArray();
        boolean foundD6 = false;
        boolean foundD20 = false;
        for (int i = 0; i < diceAsJsonArray.length(); i++) {
            JSONObject entryJson = diceAsJsonArray.getJSONObject(i);
            if (entryJson.getInt("sides") == 6) {
                assertEquals(2, entryJson.getInt("count"));
                foundD6 = true;
            }
            if (entryJson.getInt("sides") == 20) {
                assertEquals(1, entryJson.getInt("count"));
                foundD20 = true;
            }
        }
        assertTrue(foundD6);
        assertTrue(foundD20);
    }

    @Test
    void testSetId() {
        DiceRoll r2 = new DiceRoll(1, 0, 0, 0, 0, 0);
        int id = r2.getId();
        r2.setId(id + 1);
        assertEquals(id + 1, r2.getId());
        r2.setId(0);
        DiceRoll r3 = new DiceRoll(1, 1, 0, 0, 0, 0);
        assertEquals(id + 2, r3.getId());
    }
}