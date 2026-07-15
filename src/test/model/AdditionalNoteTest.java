package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdditionalNoteTest {

    private AdditionalNote additionalNote;

    @BeforeEach
    void runBefore() {
        additionalNote = new AdditionalNote("Test");
        additionalNote.setSessionId(-1);
    }

    @Test
    void testConstructor() {
        assertEquals("Test", additionalNote.getTitle());
        assertEquals("", additionalNote.getText());
        assertEquals(-1, additionalNote.getSessionId());
    }

    @Test
    void testIdsAreUnique() {
        AdditionalNote n2 = new AdditionalNote("Test 2");
        assertNotEquals(additionalNote.getId(), n2.getId());
    }
}