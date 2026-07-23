package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.json.JSONArray;
import org.json.JSONObject;

class DiceCalculationTest {

    private DiceCalculation diceCalculation;

    @BeforeEach
    void runBefore() {
        diceCalculation = new DiceCalculation("Test");
        diceCalculation.setSessionId(-1);
    }

    @Test
    void testConstructor() {
        assertEquals("Test", diceCalculation.getName());
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

    @Test
    void testDiceCalculationJson() {
        diceCalculation.setDice(6, 2);
        diceCalculation.setDice(12, 1);
        JSONObject diceCalculationJson = diceCalculation.toJsonObject();
        assertEquals(diceCalculation.getId(), diceCalculationJson.getInt("id"));
        assertEquals(diceCalculation.getName(), diceCalculationJson.getString("name"));
        assertEquals(diceCalculation.getSessionId(), diceCalculationJson.getInt("sessionId"));
    }

    @Test
    void testDiceToJsonArray() {
        diceCalculation.setDice(6, 1);
        diceCalculation.setDice(20, 2);
        JSONArray diceAsJsonArray = diceCalculation.diceToJsonArray();
        boolean foundD6 = false;
        boolean foundD20 = false;
        for (int i = 0; i < diceAsJsonArray.length(); i++) {
            JSONObject entryJson = diceAsJsonArray.getJSONObject(i);
            if (entryJson.getInt("sides") == 6) {
                assertEquals(1, entryJson.getInt("count"));
                foundD6 = true;
            }
            if (entryJson.getInt("sides") == 20) {
                assertEquals(2, entryJson.getInt("count"));
                foundD20 = true;
            }
        }
        assertTrue(foundD6);
        assertTrue(foundD20);
    }

    @Test
    void testSetId() {
        DiceCalculation c2 = new DiceCalculation("Test 2");
        int id = c2.getId();
        c2.setId(id + 1);
        assertEquals(id + 1, c2.getId());
        c2.setId(0);
        DiceCalculation c3 = new DiceCalculation("Test 3");
        assertEquals(id + 2, c3.getId());
    }

    @Test
    void testSetName(){
        assertEquals("Test", diceCalculation.getName());
        diceCalculation.setName("Test 2");
        assertEquals("Test 2", diceCalculation.getName());
    }
}