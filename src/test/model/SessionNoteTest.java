package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.json.JSONObject;

class SessionNoteTest {

    private SessionNote sessionNote;

    @BeforeEach
    void runBefore() {
        sessionNote = new SessionNote("Test");
        sessionNote.setSessionId(-1);
    }

    @Test
    void testConstructor() {
        assertEquals("Test", sessionNote.getTitle());
        assertEquals("", sessionNote.getText());
        assertEquals(-1, sessionNote.getSessionId());
    }

    @Test
    void testIdsAreUnique() {
        SessionNote n2 = new SessionNote("Test 2");
        assertNotEquals(sessionNote.getId(), n2.getId());
    }

    @Test
    void testSessionNoteJson() {
        JSONObject sessionNoteJson = sessionNote.toJsonObject();
        assertEquals(sessionNote.getId(), sessionNoteJson.getInt("id"));
        assertEquals(sessionNote.getTitle(), sessionNoteJson.getString("title"));
        assertEquals(sessionNote.getText(), sessionNoteJson.getString("text"));
        assertEquals(sessionNote.getSessionId(), sessionNoteJson.getInt("sessionId"));
    }

    @Test
    void testSetId() {
        SessionNote n2 = new SessionNote("Test 2");
        int id = n2.getId();
        n2.setId(id + 1);
        assertEquals(id + 1, n2.getId());
        n2.setId(0);
        SessionNote n3 = new SessionNote("Test 3");
        assertEquals(id + 2, n3.getId());
    }
}