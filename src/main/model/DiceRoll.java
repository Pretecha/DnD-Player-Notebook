package model;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

import org.json.JSONObject;
import org.json.JSONArray;

// Represents a dice roll with number of each dnd die, and the total after rolling
public class DiceRoll {

    private static int nextId = 1;

    private int id;
    private Map<Integer, Integer> dice;
    private int result;
    private int sessionId;

    // EFFECTS: constructs a dice roll with the given number of each die
    // type, and assigns it a unique id; does not roll the dice yet
    public DiceRoll(int d4, int d6, int d8, int d10, int d12, int d20) {
        this.id = nextId;
        nextId++;
        this.dice = new HashMap<>();
        if (d4 > 0) {
            dice.put(4, d4);
        }
        if (d6 > 0) {
            dice.put(6, d6);
        }
        if (d8 > 0) {
            dice.put(8, d8);
        }
        if (d10 > 0) {
            dice.put(10, d10);
        }
        if (d12 > 0) {
            dice.put(12, d12);
        }
        if (d20 > 0) {
            dice.put(20, d20);
        }
        this.result = 0;
        this.sessionId = 0;
    }

    // EFFECTS: returns the id of this dice roll
    public int getId() {
        return id;
    }

    // EFFECTS: returns a map of die type (number of sides) to quantity used
    // in this dice roll
    public Map<Integer, Integer> getDice() {
        return dice;
    }

    // MODIFIES: this
    // EFFECTS: rolls all dice in this dice roll and updates the result
    public void roll() {
        Random rand = new Random();
        int total = 0;
        for (Map.Entry<Integer, Integer> entry : dice.entrySet()) {
            int sides = entry.getKey();
            int count = entry.getValue();
            for (int i = 0; i < count; i++) {
                total += rand.nextInt(sides) + 1;
            }
        }
        this.result = total;
    }

    // EFFECTS: returns the total result of this dice roll; result is 0 if
    // roll() has not been called yet
    public int getResult() {
        return result;
    }

    // EFFECTS: returns the session id of the session this dice roll belongs to
    public int getSessionId() {
        return sessionId;
    }

    // MODIFIES: this
    // EFFECTS: sets the session id of the session this dice roll belongs to
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    // EFFECTS: returns this dice roll as a JSON object
    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("dice", diceToJsonArray());
        json.put("result", this.result);
        json.put("sessionId", this.sessionId);
        return json;
    }

    // EFFECTS: returns the dice map as a JSON array, each with a "sides" and
    // "count" key
    public JSONArray diceToJsonArray() {
        JSONArray diceAsJson = new JSONArray();
        for (Map.Entry<Integer, Integer> entry : dice.entrySet()) {
            JSONObject entryJson = new JSONObject();
            entryJson.put("sides", entry.getKey());
            entryJson.put("count", entry.getValue());
            diceAsJson.put(entryJson);
        }
        return diceAsJson;
    }

    // MODIFIES: this
    // EFFECTS: sets the id of this dice roll to the given value; if the given id is
    // greater than or equal to the next id, the next id is assigned as id + 1.
    public void setId(int id) {
        this.id = id;
        if (id >= nextId) {
            nextId = id + 1;
        }
    }
}