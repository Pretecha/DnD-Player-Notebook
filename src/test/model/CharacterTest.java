package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.json.JSONObject;

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

    @Test
    void testCharacterJson() {
        JSONObject characterJson = character.toJsonObject();
        assertEquals(character.getId(), characterJson.getInt("id"));
        assertEquals(character.getName(), characterJson.getString("name"));
        assertEquals(character.getLevelAndClass(), characterJson.getString("levelAndClass"));
        assertEquals(character.getRace(), characterJson.getString("race"));
        assertEquals(character.getAlignment(), characterJson.getString("alignment"));
        assertEquals(character.getBackground(), characterJson.getString("background"));
        assertEquals(character.getStr(), characterJson.getInt("str"));
        assertEquals(character.getCon(), characterJson.getInt("con"));
        assertEquals(character.getDex(), characterJson.getInt("dex"));
        assertEquals(character.getCha(), characterJson.getInt("cha"));
        assertEquals(character.getIntel(), characterJson.getInt("intel"));
        assertEquals(character.getWis(), characterJson.getInt("wis"));
        assertEquals(character.getSessionId(), characterJson.getInt("sessionId"));
    }

    @Test
    void testSetId() {
        Character c2 = new Character("Test Subject 2");
        int id = c2.getId();
        c2.setId(id + 1);
        assertEquals(id + 1, c2.getId());
        c2.setId(0);
        Character c3 = new Character("Test Subject 3");
        assertEquals(id + 2, c3.getId());
    }

    @Test
    void testSimpleSetters() {
        character.setName("New Test");
        character.setLevelAndClass("Level 5 Test");
        character.setRace("Test Subject");
        character.setAlignment("True Neutral");
        character.setBackground("Scientist");
        character.setStr(21);
        character.setCon(22);
        character.setDex(23);
        character.setCha(23);
        character.setIntel(25);
        character.setWis(26);

        assertEquals("New Test", character.getName());
        assertEquals("Level 5 Test", character.getLevelAndClass());
        assertEquals("Test Subject", character.getRace());
        assertEquals("True Neutral", character.getAlignment());
        assertEquals("Scientist", character.getBackground());
        assertEquals(21, character.getStr());
        assertEquals(22, character.getCon());
        assertEquals(23, character.getDex());
        assertEquals(24, character.getCha());
        assertEquals(25, character.getIntel());
        assertEquals(26, character.getWis());
    }
}