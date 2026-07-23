package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.JSONArray;
import org.json.JSONObject;

class SessionTest {

    private Session session;
    private Character character;
    private DiceCalculation diceCalculation;
    private DiceRoll diceRoll;
    private SessionNote sessionNote;
    private AdditionalNote additionalNote;

    @BeforeEach
    void runBefore() {
        session = new Session("Test");
        character = new Character("Test");
        diceCalculation = new DiceCalculation("Test");
        diceRoll = new DiceRoll(0, 2, 0, 0, 0, 1);
        sessionNote = new SessionNote("Test");
        additionalNote = new AdditionalNote("Test");
    }

    @Test
    void testConstructor() {
        assertEquals("Test", session.getName());
        assertTrue(session.getCharacters().isEmpty());
        assertTrue(session.getDiceCalculations().isEmpty());
        assertTrue(session.getDiceRolls().isEmpty());
        assertTrue(session.getSessionNotes().isEmpty());
        assertTrue(session.getAdditionalNotes().isEmpty());
    }

    @Test
    void testIdsAreUnique() {
        Session s2 = new Session("Test 2");
        assertNotEquals(session.getId(), s2.getId());
    }

    @Test
    void testAddCharacter() {
        session.addCharacter(character);
        assertEquals(1, session.getCharacters().size());
        assertEquals(character, session.getCharacter(character.getId()));
        assertEquals(session.getId(), character.getSessionId());
    }

    @Test
    void testAddDiceCalculation() {
        session.addDiceCalculation(diceCalculation);
        assertEquals(1, session.getDiceCalculations().size());
        assertEquals(diceCalculation, session.getDiceCalculation(diceCalculation.getId()));
        assertEquals(session.getId(), diceCalculation.getSessionId());
    }

    @Test
    void testAddDiceRoll() {
        session.addDiceRoll(diceRoll);
        assertEquals(1, session.getDiceRolls().size());
        assertEquals(diceRoll, session.getDiceRoll(diceRoll.getId()));
        assertEquals(session.getId(), diceRoll.getSessionId());
    }

    @Test
    void testAddSessionNote() {
        session.addSessionNote(sessionNote);
        assertEquals(1, session.getSessionNotes().size());
        assertEquals(sessionNote, session.getSessionNote(sessionNote.getId()));
        assertEquals(session.getId(), sessionNote.getSessionId());
    }

    @Test
    void testAddAdditionalNote() {
        session.addAdditionalNote(additionalNote);
        assertEquals(1, session.getAdditionalNotes().size());
        assertEquals(additionalNote, session.getAdditionalNote(additionalNote.getId()));
        assertEquals(session.getId(), additionalNote.getSessionId());
    }

    @Test
    void testAddMultipleCharacters() {
        Character c2 = new Character("Test 2");
        session.addCharacter(character);
        session.addCharacter(c2);
        assertEquals(2, session.getCharacters().size());
    }

    @Test
    void testGetCharacterNotFound() {
        assertNull(session.getCharacter(-1));
    }

    @Test
    void testAddMultipleDiceCalculations() {
        DiceCalculation d2 = new DiceCalculation("Test 2");
        session.addDiceCalculation(diceCalculation);
        session.addDiceCalculation(d2);
        assertEquals(2, session.getDiceCalculations().size());
    }

    @Test
    void testGetDiceCalculationNotFound() {
        assertNull(session.getDiceCalculation(-1));
    }

    @Test
    void testAddMultipleDiceRolls() {
        DiceRoll r2 = new DiceRoll(1, 0, 0, 0, 0, 0);
        session.addDiceRoll(diceRoll);
        session.addDiceRoll(r2);
        assertEquals(2, session.getDiceRolls().size());
    }

    @Test
    void testGetDiceRollNotFound() {
        assertNull(session.getDiceRoll(-1));
    }

    @Test
    void testAddMultipleSessionNotes() {
        SessionNote n2 = new SessionNote("Test 2");
        session.addSessionNote(sessionNote);
        session.addSessionNote(n2);
        assertEquals(2, session.getSessionNotes().size());
    }

    @Test
    void testGetSessionNoteNotFound() {
        assertNull(session.getSessionNote(-1));
    }

    @Test
    void testAddMultipleAdditionalNotes() {
        AdditionalNote n2 = new AdditionalNote("Test 2");
        session.addAdditionalNote(additionalNote);
        session.addAdditionalNote(n2);
        assertEquals(2, session.getAdditionalNotes().size());
    }

    @Test
    void testGetAdditionalNoteNotFound() {
        assertNull(session.getAdditionalNote(-1));
    }

    @Test
    void testDeleteCharacter() {
        Character c2 = new Character("Test Subject 2");
        session.addCharacter(c2);
        session.addCharacter(character);
        session.deleteCharacter(character.getId());
        assertEquals(1, session.getCharacters().size());
        assertNull(session.getCharacter(character.getId()));
        assertEquals(c2, session.getCharacter(c2.getId()));
    }

    @Test
    void testDeleteDiceCalculation() {
        DiceCalculation d2 = new DiceCalculation("Test 2");
        session.addDiceCalculation(d2);
        session.addDiceCalculation(diceCalculation);
        session.deleteDiceCalculation(diceCalculation.getId());
        assertEquals(1, session.getDiceCalculations().size());
        assertNull(session.getDiceCalculation(diceCalculation.getId()));
        assertEquals(d2, session.getDiceCalculation(d2.getId()));
    }

    @Test
    void testDeleteDiceRoll() {
        DiceRoll r2 = new DiceRoll(1, 0, 0, 0, 0, 0);
        session.addDiceRoll(r2);
        session.addDiceRoll(diceRoll);
        session.deleteDiceRoll(diceRoll.getId());
        assertEquals(1, session.getDiceRolls().size());
        assertNull(session.getDiceRoll(diceRoll.getId()));
        assertEquals(r2, session.getDiceRoll(r2.getId()));
    }

    @Test
    void testDeleteSessionNote() {
        SessionNote n2 = new SessionNote("Test 2");
        session.addSessionNote(n2);
        session.addSessionNote(sessionNote);
        session.deleteSessionNote(sessionNote.getId());
        assertEquals(1, session.getSessionNotes().size());
        assertNull(session.getSessionNote(sessionNote.getId()));
        assertEquals(n2, session.getSessionNote(n2.getId()));
    }

    @Test
    void testDeleteAdditionalNote() {
        AdditionalNote n2 = new AdditionalNote("Test 2");
        session.addAdditionalNote(n2);
        session.addAdditionalNote(additionalNote);
        session.deleteAdditionalNote(additionalNote.getId());
        assertEquals(1, session.getAdditionalNotes().size());
        assertNull(session.getAdditionalNote(additionalNote.getId()));
        assertEquals(n2, session.getAdditionalNote(n2.getId()));
    }

    @Test
    void testSessionJson() {
        JSONObject sessionJson = session.toJsonObject();
        assertEquals(session.getId(), sessionJson.getInt("id"));
        assertEquals(session.getName(), sessionJson.getString("name"));
    }

    @Test
    void testCharactersToJsonArray() {
        Character c2 = new Character("Test 2");
        session.addCharacter(character);
        session.addCharacter(c2);

        JSONArray charactersAsJsonArray = session.charactersToJsonArray();
        boolean foundFirst = false;
        boolean foundSecond = false;
        for (int i = 0; i < charactersAsJsonArray.length(); i++) {
            JSONObject entryJson = charactersAsJsonArray.getJSONObject(i);
            if (entryJson.getInt("id") == character.getId()) {
                foundFirst = true;
            }
            if (entryJson.getInt("id") == c2.getId()) {
                foundSecond = true;
            }
        }
        assertTrue(foundFirst);
        assertTrue(foundSecond);
    }

    @Test
    void testDiceCalculationsToJsonArray() {
        DiceCalculation d2 = new DiceCalculation("Test 2");
        session.addDiceCalculation(diceCalculation);
        session.addDiceCalculation(d2);

        JSONArray diceCalculationsAsJsonArray = session.diceCalculationsToJsonArray();
        boolean foundFirst = false;
        boolean foundSecond = false;
        for (int i = 0; i < diceCalculationsAsJsonArray.length(); i++) {
            JSONObject entryJson = diceCalculationsAsJsonArray.getJSONObject(i);
            if (entryJson.getInt("id") == diceCalculation.getId()) {
                foundFirst = true;
            }
            if (entryJson.getInt("id") == d2.getId()) {
                foundSecond = true;
            }
        }
        assertTrue(foundFirst);
        assertTrue(foundSecond);
    }

    @Test
    void testDiceRollsToJsonArray() {
        DiceRoll r2 = new DiceRoll(1, 0, 0, 0, 0, 0);
        session.addDiceRoll(diceRoll);
        session.addDiceRoll(r2);

        JSONArray diceRollsAsJsonArray = session.diceRollsToJsonArray();
        boolean foundFirst = false;
        boolean foundSecond = false;
        for (int i = 0; i < diceRollsAsJsonArray.length(); i++) {
            JSONObject entryJson = diceRollsAsJsonArray.getJSONObject(i);
            if (entryJson.getInt("id") == diceRoll.getId()) {
                foundFirst = true;
            }
            if (entryJson.getInt("id") == r2.getId()) {
                foundSecond = true;
            }
        }
        assertTrue(foundFirst);
        assertTrue(foundSecond);
    }

    @Test
    void testSessionNotesToJsonArray() {
        SessionNote n2 = new SessionNote("Test 2");
        session.addSessionNote(sessionNote);
        session.addSessionNote(n2);

        JSONArray sessionNotesAsJsonArray = session.sessionNotesToJsonArray();
        boolean foundFirst = false;
        boolean foundSecond = false;
        for (int i = 0; i < sessionNotesAsJsonArray.length(); i++) {
            JSONObject entryJson = sessionNotesAsJsonArray.getJSONObject(i);
            if (entryJson.getInt("id") == sessionNote.getId()) {
                foundFirst = true;
            }
            if (entryJson.getInt("id") == n2.getId()) {
                foundSecond = true;
            }
        }
        assertTrue(foundFirst);
        assertTrue(foundSecond);
    }

    @Test
    void testAdditionalNotesToJsonArray() {
        AdditionalNote n2 = new AdditionalNote("Test 2");
        session.addAdditionalNote(additionalNote);
        session.addAdditionalNote(n2);

        JSONArray additionalNotesAsJsonArray = session.additionalNotesToJsonArray();
        boolean foundFirst = false;
        boolean foundSecond = false;
        for (int i = 0; i < additionalNotesAsJsonArray.length(); i++) {
            JSONObject entryJson = additionalNotesAsJsonArray.getJSONObject(i);
            if (entryJson.getInt("id") == additionalNote.getId()) {
                foundFirst = true;
            }
            if (entryJson.getInt("id") == n2.getId()) {
                foundSecond = true;
            }
        }
        assertTrue(foundFirst);
        assertTrue(foundSecond);
    }

    @Test
    void testSetId() {
        Session s2 = new Session("Test 2");
        int id = s2.getId();
        s2.setId(id + 1);
        assertEquals(id + 1, s2.getId());
        s2.setId(0);
        Session s3 = new Session("Test 3");
        assertEquals(id + 2, s3.getId());
    }

    @Test
    void testNameSetter() {
        assertEquals("Test", session.getName());
        session.setName("Test 2");
        assertEquals("Test 2", session.getName());
    }

}