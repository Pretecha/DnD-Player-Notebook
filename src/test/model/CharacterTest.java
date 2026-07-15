package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharacterTest {

    private Character character;

    @BeforeEach
    void runBefore() {
        character = new Character("Test Subject");
        character.setSessionId(-1);
    }

    @Test
    void testConstructor() {
        assertEquals("Test Subject", character.getName());
        assertEquals("", character.getLevelAndClass());
        assertEquals("", character.getRace());
        assertEquals("", character.getAlignment());
        assertEquals("", character.getBackground());
        assertEquals(0, character.getStr());
        assertEquals(0, character.getCon());
        assertEquals(0, character.getDex());
        assertEquals(0, character.getCha());
        assertEquals(0, character.getIntel());
        assertEquals(0, character.getWis());
        assertEquals(-1, character.getSessionId());
    }

    @Test
    void testIdsAreUnique() {
        Character c2 = new Character("Test Subject 2");
        assertNotEquals(character.getId(), c2.getId());
    }
}