package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        session.addCharacter(character);
        session.deleteCharacter(character.getId());
        assertTrue(session.getCharacters().isEmpty());
        assertNull(session.getCharacter(character.getId()));
    }

    @Test
    void testDeleteDiceCalculation() {
        session.addDiceCalculation(diceCalculation);
        session.deleteDiceCalculation(diceCalculation.getId());
        assertTrue(session.getDiceCalculations().isEmpty());
        assertNull(session.getDiceCalculation(diceCalculation.getId()));
    }

    @Test
    void testDeleteDiceRoll() {
        session.addDiceRoll(diceRoll);
        session.deleteDiceRoll(diceRoll.getId());
        assertTrue(session.getDiceRolls().isEmpty());
        assertNull(session.getDiceRoll(diceRoll.getId()));
    }

    @Test
    void testDeleteSessionNote() {
        session.addSessionNote(sessionNote);
        session.deleteSessionNote(sessionNote.getId());
        assertTrue(session.getSessionNotes().isEmpty());
        assertNull(session.getSessionNote(sessionNote.getId()));
    }

    @Test
    void testDeleteAdditionalNote() {
        session.addAdditionalNote(additionalNote);
        session.deleteAdditionalNote(additionalNote.getId());
        assertTrue(session.getAdditionalNotes().isEmpty());
        assertNull(session.getAdditionalNote(additionalNote.getId()));
    }
}