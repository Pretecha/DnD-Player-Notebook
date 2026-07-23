package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.json.JSONArray;
import org.json.JSONObject;

class HubTest {

    private Hub hub;
    private Session session;

    @BeforeEach
    void runBefore() {
        hub = new Hub();
        session = new Session("Test");
        hub.addSession(session);
    }

    @Test
    void testConstructor() {
        assertNull(hub.getCurrentSession());
        assertFalse(hub.getSessions().isEmpty());
        assertTrue(hub.getPinnedSessionsId().isEmpty());
    }

    @Test
    void testAddSession() {
        assertEquals(1, hub.getSessions().size());
        assertEquals("Test", session.getName());

        Session s2 = new Session("Test 2");
        hub.addSession(s2);
        assertEquals(2, hub.getSessions().size());
        assertEquals("Test 2", s2.getName());
    }

    @Test
    void testAddMultipleSessions() {
        hub.addSession(new Session("Test 2"));
        assertEquals(2, hub.getSessions().size());
        hub.addSession(new Session("Test 3"));
        assertEquals(3, hub.getSessions().size());
    }

    @Test
    void testGetSession() {
        assertEquals("Test", hub.getSession(session.getId()).getName());
    }

    @Test
    void testGetSessionNotFound() {
        assertNull(hub.getSession(-1));
    }

    @Test
    void testDeleteSession() {
        Session s2 = new Session("Test 2");
        hub.addSession(s2);
        hub.deleteSession(session.getId());
        assertFalse(hub.getSessions().isEmpty());
        assertNull(hub.getSession(session.getId()));
    }

    @Test
    void testSelectSession() {
        hub.selectSession(session.getId());
        assertEquals(session.getId(), hub.getCurrentSession().getId());
    }

    @Test
    void testSelectSessionWithCharacter() {
        Character c = new Character("Test");
        session.addCharacter(c);
        hub.selectSessionWithCharacter(c.getId());
        assertEquals(session.getId(), hub.getCurrentSession().getId());
    }

    @Test
    void testGetCharacterFromHub() {
        Character c = new Character("Test");
        session.addCharacter(c);
        assertEquals(c, hub.getCharacterFromHub(c.getId()));
    }

    @Test
    void testGetCharacterFromHubNotFound() {
        assertNull(hub.getCharacterFromHub(-1));
    }

    @Test
    void testPinSession() {
        int id = session.getId();
        hub.pinSession(id);
        assertTrue(hub.getPinnedSessionsId().contains(id));
    }

    @Test
    void testUnpinSession() {
        int id = session.getId();
        hub.pinSession(id);
        hub.unpinSession(id);
        assertFalse(hub.getPinnedSessionsId().contains(id));
    }

    @Test
    void testHubJson() {
        Session s2 = new Session("Test 2");
        hub.addSession(s2);
        int id2 = s2.getId();
        hub.pinSession(id2);
        JSONObject hubJson = hub.toJsonObject();

        JSONArray sessionsJsonArray = hubJson.getJSONArray("sessions");
        assertEquals(2, sessionsJsonArray.length());
        assertEquals("Test", sessionsJsonArray.getJSONObject(0).getString("name"));
        assertEquals("Test 2", sessionsJsonArray.getJSONObject(1).getString("name"));

        JSONArray pinnedSessionsJsonArray = hubJson.getJSONArray("pinnedSessions");
        assertEquals(1, pinnedSessionsJsonArray.length());
        assertEquals(id2, pinnedSessionsJsonArray.getInt(0));
    }

    @Test
    void testSessionsToJsonArrayEmpty() {
        Hub hub2 = new Hub();
        JSONArray sessionsArray = hub2.sessionsToJsonArray();
        assertEquals(0, sessionsArray.length());
    }

    @Test
    void testSessionsToJsonArray() {
        hub.addSession(new Session("Test 2"));

        JSONArray sessionsArray = hub.sessionsToJsonArray();
        assertEquals(2, sessionsArray.length());

        boolean foundFirst = false;
        boolean foundSecond = false;
        for (int i = 0; i < sessionsArray.length(); i++) {
            String name = sessionsArray.getJSONObject(i).getString("name");
            if (name.equals("Test")) {
                foundFirst = true;
            }
            if (name.equals("Test 2")) {
                foundSecond = true;
            }
        }
        assertTrue(foundFirst);
        assertTrue(foundSecond);
    }

    @Test
    void testPinnedSessionsToJsonArrayEmpty() {
        Hub hub2 = new Hub();
        JSONArray pinnedArray = hub2.pinnedSessionsToJsonArray();
        assertEquals(0, pinnedArray.length());
    }

    @Test
    void testPinnedSessionsToJsonArray() {
        hub.pinSession(session.getId());

        JSONArray pinnedArray = hub.pinnedSessionsToJsonArray();
        assertEquals(1, pinnedArray.length());
        assertEquals(session.getId(), pinnedArray.getInt(0));
    }

    @Test
    void testNoIdSelectCharacter() {
        Character c = new Character("Test");
        session.addCharacter(c);
        hub.selectSessionWithCharacter(-1);
        assertNull(hub.getCurrentSession());
    }
}
