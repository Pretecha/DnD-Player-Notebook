package persistence;

import model.AdditionalNote;
import model.Character;
import model.DiceCalculation;
import model.DiceRoll;
import model.Hub;
import model.Session;
import model.SessionNote;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JsonReadTest {

    @Test
    void testReaderNonExistentFile() {
        JsonRead reader = new JsonRead("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadEmptyHub() {
        JsonRead reader = new JsonRead("./data/testReadEmptyHub.json");
        try {
            Hub hub = reader.read();
            assertTrue(hub.getSessions().isEmpty());
            assertTrue(hub.getPinnedSessionsId().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReadGeneralHub() {
        JsonRead reader = new JsonRead("./data/testReadGeneralHub.json");
        try {
            Hub hub = reader.read();

            assertEquals(1, hub.getSessions().size());
            assertEquals(1, hub.getPinnedSessionsId().size());
            assertEquals(1, (int) hub.getPinnedSessionsId().get(0));

            Session session = hub.getSessions().get(0);
            assertEquals(1, session.getId());
            assertEquals("Lab", session.getName());
            assertEquals(LocalDateTime.parse("2026-06-01T20:00:00"), session.getTimeCreated());
            assertEquals(LocalDateTime.parse("2026-08-01T20:00:00"), session.getTimeLastOpened());

            assertEquals(1, session.getCharacters().size());
            Character character = session.getCharacters().get(0);
            assertEquals(1, character.getId());
            assertEquals("TestSubject", character.getName());
            assertEquals("Lv 6 Peace Cleric", character.getLevelAndClass());
            assertEquals("Autognome", character.getRace());
            assertEquals("Chaotic Good", character.getAlignment());
            assertEquals("Hermit", character.getBackground());
            assertEquals(8, character.getStr());
            assertEquals(14, character.getCon());
            assertEquals(14, character.getDex());
            assertEquals(12, character.getCha());
            assertEquals(10, character.getIntel());
            assertEquals(17, character.getWis());
            assertEquals(1, character.getSessionId());

            assertEquals(1, session.getDiceCalculations().size());
            DiceCalculation diceCalculation = session.getDiceCalculations().get(0);
            assertEquals(1, diceCalculation.getId());
            assertEquals("Beacon of Hope + Aura of Vitality + Healing Word + 2 Goodberries each",
                    diceCalculation.getName());
            assertEquals(1, diceCalculation.getSessionId());
            assertEquals(2, (int) diceCalculation.getDice().get(6));
            assertEquals(1, (int) diceCalculation.getDice().get(4));
            assertEquals(7, (int) diceCalculation.getDice().get(1));
            assertEquals(10, diceCalculation.getLowestValue());
            assertEquals(16.5, diceCalculation.getAverageValue());
            assertEquals(23, diceCalculation.getHighestValue());

            assertEquals(2, session.getDiceRolls().size());
            DiceRoll roll1 = session.getDiceRoll(1);
            assertEquals(17, roll1.getResult());
            assertEquals(1, roll1.getSessionId());
            assertEquals(1, (int) roll1.getDice().get(20));

            DiceRoll roll2 = session.getDiceRoll(2);
            assertEquals(10, roll2.getResult());
            assertEquals(1, roll2.getSessionId());
            assertEquals(2, (int) roll2.getDice().get(6));

            assertEquals(1, session.getSessionNotes().size());
            SessionNote sessionNote = session.getSessionNotes().get(0);
            assertEquals(1, sessionNote.getId());
            assertEquals("Experiment 0", sessionNote.getTitle());
            assertEquals(1, sessionNote.getSessionId());

            assertEquals(1, session.getAdditionalNotes().size());
            AdditionalNote additionalNote = session.getAdditionalNotes().get(0);
            assertEquals(1, additionalNote.getId());
            assertEquals("Backstory", additionalNote.getTitle());
            assertTrue(additionalNote.getText().contains("\"holy\""));
            assertEquals(1, additionalNote.getSessionId());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
