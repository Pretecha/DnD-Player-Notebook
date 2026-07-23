package persistence;

import model.AdditionalNote;
import model.Character;
import model.DiceCalculation;
import model.DiceRoll;
import model.Hub;
import model.Session;
import model.SessionNote;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

// Represents a reader that reads a file located in data and convert that String
// into JSONObject and into data for all classes.
public class JsonRead {
    private String data;

    // EFFECTS: constructs a JsonWrite object with the path to data as parameter.
    public JsonRead(String data) {
        this.data = data;
    }

    // EFFECTS: reads from file and returns it; throws IOException if an error
    // occurs reading data from file
    public Hub read() throws IOException {
        JSONObject jsonObject = new JSONObject(Files.readString(Paths.get(data)));
        return parseHub(jsonObject);
    }

    // EFFECTS: parses a hub from JSON object and returns it
    public Hub parseHub(JSONObject jsonObject) {
        Hub hub = new Hub();
        addSessions(hub, jsonObject);
        addPinnedSessions(hub, jsonObject);
        return hub;
    }

    // MODIFIES: hub
    // EFFECTS: parses list of sessions from JSON object and adds them to hub
    public void addSessions(Hub hub, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sessions");
        for (int i = 0; i < jsonArray.length(); i++) {
            addSession(hub, jsonArray.getJSONObject(i));
        }
    }

    // MODIFIES: hub
    // EFFECTS: parses a session from JSON object, builds it, adds it to hub,
    // and restores every item it contains
    public void addSession(Hub hub, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Session session = new Session(name);
        session.setId(jsonObject.getInt("id"));
        addCharacters(session, jsonObject);
        addDiceCalculations(session, jsonObject);
        addDiceRolls(session, jsonObject);
        addSessionNotes(session, jsonObject);
        addAdditionalNotes(session, jsonObject);
        hub.addSession(session);
    }

    // MODIFIES: session
    // EFFECTS: parses list of characters from JSON object and adds them to session
    public void addCharacters(Session session, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("characters");
        for (int i = 0; i < jsonArray.length(); i++) {
            addCharacter(session, jsonArray.getJSONObject(i));
        }
    }

    // MODIFIES: session
    // EFFECTS: parses a character from JSON object, builds it, restores all
    // of its fields, and adds it to session
    public void addCharacter(Session session, JSONObject jsonObject) {
        Character character = new Character(jsonObject.getString("name"));
        character.setId(jsonObject.getInt("id"));
        character.setLevelAndClass(jsonObject.getString("levelAndClass"));
        character.setRace(jsonObject.getString("race"));
        character.setAlignment(jsonObject.getString("alignment"));
        character.setBackground(jsonObject.getString("background"));
        character.setStr(jsonObject.getInt("str"));
        character.setCon(jsonObject.getInt("con"));
        character.setDex(jsonObject.getInt("dex"));
        character.setCha(jsonObject.getInt("cha"));
        character.setIntel(jsonObject.getInt("intel"));
        character.setWis(jsonObject.getInt("wis"));
        character.setSessionId(jsonObject.getInt("sessionId"));
        session.addCharacter(character);
    }

    // MODIFIES: session
    // EFFECTS: parses list of dice calculations from JSON object and adds them to
    // session
    public void addDiceCalculations(Session session, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("diceCalculations");
        for (int i = 0; i < jsonArray.length(); i++) {
            addDiceCalculation(session, jsonArray.getJSONObject(i));
        }
    }

    // MODIFIES: session
    // EFFECTS: parses a dice calculation from JSON object, builds it,
    // restores all of its fields, and adds it to session
    public void addDiceCalculation(Session session, JSONObject jsonObject) {
        DiceCalculation diceCalculation = new DiceCalculation(jsonObject.getString("name"));
        diceCalculation.setId(jsonObject.getInt("id"));
        diceCalculation.setSessionId(jsonObject.getInt("sessionId"));
        JSONArray diceArray = jsonObject.getJSONArray("dice");
        for (int i = 0; i < diceArray.length(); i++) {
            JSONObject entry = diceArray.getJSONObject(i);
            diceCalculation.setDice(entry.getInt("sides"), entry.getInt("count"));
        }
        session.addDiceCalculation(diceCalculation);
    }

    // MODIFIES: session
    // EFFECTS: parses list of dice rolls from JSON object and adds them to session
    public void addDiceRolls(Session session, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("diceRolls");
        for (int i = 0; i < jsonArray.length(); i++) {
            addDiceRoll(session, jsonArray.getJSONObject(i));
        }
    }

    // MODIFIES: session
    // EFFECTS: parses a dice roll from JSON object, restores all of its fields, and
    // adds it to session
    public void addDiceRoll(Session session, JSONObject jsonObject) {
        JSONArray diceArray = jsonObject.getJSONArray("dice");
        Map<Integer, Integer> diceMap = new HashMap<>();
        for (int i = 0; i < diceArray.length(); i++) {
            JSONObject entry = diceArray.getJSONObject(i);
            diceMap.put(entry.getInt("sides"), entry.getInt("count"));
        }

        DiceRoll diceRoll = new DiceRoll(
                diceMap.getOrDefault(4, 0),
                diceMap.getOrDefault(6, 0),
                diceMap.getOrDefault(8, 0),
                diceMap.getOrDefault(10, 0),
                diceMap.getOrDefault(12, 0),
                diceMap.getOrDefault(20, 0));
        diceRoll.setId(jsonObject.getInt("id"));
        diceRoll.setResult(jsonObject.getInt("result"));
        diceRoll.setSessionId(jsonObject.getInt("sessionId"));
        session.addDiceRoll(diceRoll);
    }

    // MODIFIES: session
    // EFFECTS: parses list of session notes from JSON object and adds them to
    // session
    public void addSessionNotes(Session session, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("sessionNotes");
        for (int i = 0; i < jsonArray.length(); i++) {
            addSessionNote(session, jsonArray.getJSONObject(i));
        }
    }

    // MODIFIES: session
    // EFFECTS: parses a session note from JSON object, builds it, restores
    // all of its fields, and adds it to session
    public void addSessionNote(Session session, JSONObject jsonObject) {
        SessionNote sessionNote = new SessionNote(jsonObject.getString("title"));
        sessionNote.setId(jsonObject.getInt("id"));
        sessionNote.setText(jsonObject.getString("text"));
        sessionNote.setSessionId(jsonObject.getInt("sessionId"));
        session.addSessionNote(sessionNote);
    }

    // MODIFIES: session
    // EFFECTS: parses list of additional notes from JSON object and adds them to
    // session
    public void addAdditionalNotes(Session session, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("additionalNotes");
        for (int i = 0; i < jsonArray.length(); i++) {
            addAdditionalNote(session, jsonArray.getJSONObject(i));
        }
    }

    // MODIFIES: session
    // EFFECTS: parses an additional note from JSON object, builds it,
    // restores all of its fields, and adds it to session
    public void addAdditionalNote(Session session, JSONObject jsonObject) {
        AdditionalNote additionalNote = new AdditionalNote(jsonObject.getString("title"));
        additionalNote.setId(jsonObject.getInt("id"));
        additionalNote.setText(jsonObject.getString("text"));
        additionalNote.setSessionId(jsonObject.getInt("sessionId"));
        session.addAdditionalNote(additionalNote);
    }

    // MODIFIES: hub
    // EFFECTS: parses pinned session ids from JSON object and pins the
    // corresponding sessions in hub
    public void addPinnedSessions(Hub hub, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("pinnedSessions");
        for (int i = 0; i < jsonArray.length(); i++) {
            hub.pinSession(jsonArray.getInt(i));
        }
    }
}