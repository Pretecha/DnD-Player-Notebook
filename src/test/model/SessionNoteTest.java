package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}