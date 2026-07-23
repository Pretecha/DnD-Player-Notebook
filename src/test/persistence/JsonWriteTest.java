package persistence;

import model.Character;
import model.Hub;
import model.Session;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class JsonWriteTest {

    @Test
    void testWriteInvalidFile() {
        try {
            JsonWrite writer = new JsonWrite("./data/\0illegal:fileName.json");
            writer.open();
            fail("FileNotFoundException expected");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testWriteEmptyHub() {
        try {
            JsonWrite writer = new JsonWrite("./data/testWriteEmptyHub.json");
            Hub hub = new Hub();
            writer.open();
            writer.save(hub);
            writer.close();

            JsonRead reader = new JsonRead("./data/testWriterEmptyHub.json");
            Hub readHub = reader.read();
            assertTrue(readHub.getSessions().isEmpty());
            assertTrue(readHub.getPinnedSessionsId().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriteGeneralHub() {
        try {
            Hub hub = new Hub();
            Session session = new Session("Test");
            hub.addSession(session);

            Character character = new Character("TestSubject");
            character.setRace("Autognome");
            session.addCharacter(character);
            hub.pinSession(session.getId());

            JsonWrite writer = new JsonWrite("./data/testWriteGeneralHub.json");
            writer.open();
            writer.save(hub);
            writer.close();

            JsonRead reader = new JsonRead("./data/testWriteGeneralHub.json");
            Hub readHub = reader.read();

            assertEquals(1, readHub.getSessions().size());
            Session readSession = readHub.getSessions().get(0);
            assertEquals(session.getId(), readSession.getId());
            assertEquals("Test", readSession.getName());

            assertEquals(1, readSession.getCharacters().size());
            Character readCharacter = readSession.getCharacters().get(0);
            assertEquals("TestSubject", readCharacter.getName());
            assertEquals("Autognome", readCharacter.getRace());

            assertEquals(1, readHub.getPinnedSessionsId().size());
            assertEquals(session.getId(), (int) readHub.getPinnedSessionsId().get(0));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}