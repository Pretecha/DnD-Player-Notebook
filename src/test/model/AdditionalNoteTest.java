package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.json.JSONObject;

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

    @Test
    void testAdditionalNoteJson() {
        JSONObject additionalNoteJson = additionalNote.toJsonObject();
        assertEquals(additionalNote.getId(), additionalNoteJson.getInt("id"));
        assertEquals(additionalNote.getTitle(), additionalNoteJson.getString("title"));
        assertEquals(additionalNote.getText(), additionalNoteJson.getString("text"));
        assertEquals(additionalNote.getSessionId(), additionalNoteJson.getInt("sessionId"));
    }

    @Test
    void testSetId() {
        AdditionalNote n2 = new AdditionalNote("Test 2");
        int id = n2.getId();
        n2.setId(id + 1);
        assertEquals(id + 1, n2.getId());
        n2.setId(0);
        AdditionalNote n3 = new AdditionalNote("Test 3");
        assertEquals(id + 2, n3.getId());
    }

    @Test
    void testSimpleSetters() {
        additionalNote.setTitle("New Title");
        additionalNote.setText("New text");

        assertEquals("New Title", additionalNote.getTitle());
        assertEquals("New text", additionalNote.getText());
    }
}