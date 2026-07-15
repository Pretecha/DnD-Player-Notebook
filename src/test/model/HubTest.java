package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HubTest {

    private Hub hub;

    @BeforeEach
    void runBefore() {
        hub = new Hub();
    }

    @Test
    void testConstructor() {
        assertNull(hub.getCurrentSession());
        assertTrue(hub.getSessions().isEmpty());
        assertTrue(hub.getPinnedSessions().isEmpty());
    }

    @Test
    void testAddSession() {
        hub.addSession("Test");
        assertEquals(1, hub.getSessions().size());
        assertEquals("Test", hub.getSessions().get(0).getName());
    }

    @Test
    void testAddMultipleSessions() {
        hub.addSession("Test");
        hub.addSession("Test 2");
        assertEquals(2, hub.getSessions().size());
    }

    @Test
    void testGetSession() {
        hub.addSession("Test");
        int id = hub.getSessions().get(0).getId();
        assertEquals("Test", hub.getSession(id).getName());
    }

    @Test
    void testGetSessionNotFound() {
        assertNull(hub.getSession(-1));
    }

    @Test
    void testDeleteSession() {
        hub.addSession("Test");
        int id = hub.getSessions().get(0).getId();
        hub.deleteSession(id);
        assertTrue(hub.getSessions().isEmpty());
        assertNull(hub.getSession(id));
    }

    @Test
    void testSelectSession() {
        hub.addSession("Test");
        int id = hub.getSessions().get(0).getId();
        hub.selectSession(id);
        assertEquals(id, hub.getCurrentSession().getId());
    }

    @Test
    void testSelectSessionWithCharacter() {
        hub.addSession("Test");
        Session s = hub.getSessions().get(0);
        Character c = new Character("Test");
        s.addCharacter(c);
        hub.selectSessionWithCharacter(c.getId());
        assertEquals(s.getId(), hub.getCurrentSession().getId());
    }

    @Test
    void testGetCharacterFromHub() {
        hub.addSession("Test");
        Session s = hub.getSessions().get(0);
        Character c = new Character("Test");
        s.addCharacter(c);
        assertEquals(c, hub.getCharacterFromHub(c.getId()));
    }

    @Test
    void testGetCharacterFromHubNotFound() {
        assertNull(hub.getCharacterFromHub(-1));
    }

    @Test
    void testPinSession() {
        hub.addSession("Test");
        int id = hub.getSessions().get(0).getId();
        hub.pinSession(id);
        assertTrue(hub.getPinnedSessions().contains(id));
    }

    @Test
    void testUnpinSession() {
        hub.addSession("Test");
        int id = hub.getSessions().get(0).getId();
        hub.pinSession(id);
        hub.unpinSession(id);
        assertFalse(hub.getPinnedSessions().contains(id));
    }
}