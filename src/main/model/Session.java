package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;

// Represent a session with a list of characters, dice calculations, dice rolls,
// session notes, and additional notes
public class Session {

    private static int nextId = 1;

    private int id;
    private String name;
    private LocalDateTime timeCreated;
    private LocalDateTime timeLastOpened;
    private List<Character> characters;
    private List<DiceCalculation> diceCalculations;
    private List<DiceRoll> diceRolls;
    private List<SessionNote> sessionNotes;
    private List<AdditionalNote> additionalNotes;

    // EFFECTS: constructs a session with the given name, no items in any list,
    // and assigns it a unique id
    public Session(String name) {
        this.id = nextId;
        nextId++;
        this.name = name;
        this.timeCreated = LocalDateTime.now();
        this.timeLastOpened = LocalDateTime.now();
        this.characters = new ArrayList<>();
        this.diceCalculations = new ArrayList<>();
        this.diceRolls = new ArrayList<>();
        this.sessionNotes = new ArrayList<>();
        this.additionalNotes = new ArrayList<>();
    }

    // EFFECTS: returns the id of this session
    public int getId() {
        return id;
    }

    // EFFECTS: returns the name of this session
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: sets the name of this session
    public void setName(String name) {
        EventLog.getInstance().logEvent(new Event("Session " + this.name + " renamed to " + name));
        this.name = name;
    }

    // MODIFIES: this, character
    // EFFECTS: adds the given character to the list of characters, and sets
    // the character's session id to this session's id
    public void addCharacter(Character character) {
        characters.add(character);
        character.setSessionId(this.id);
        EventLog.getInstance()
                .logEvent(new Event("Character" + character.getName() + " added to Session " + this.getName()));
    }

    // MODIFIES: this, diceCalculation
    // EFFECTS: adds the given dice calculation to the list of dice calculations,
    // and sets the dice calculation's session id to this session's id
    public void addDiceCalculation(DiceCalculation diceCalculation) {
        diceCalculations.add(diceCalculation);
        diceCalculation.setSessionId(this.id);
        EventLog.getInstance().logEvent(
                new Event("Dice Calculation" + diceCalculation.getName() + " added to Session " + this.getName()));
    }

    // MODIFIES: this, diceRoll
    // EFFECTS: adds the given dice roll to the list of dice rolls, and sets
    // the dice roll's session id to this session's id
    public void addDiceRoll(DiceRoll diceRoll) {
        diceRolls.add(diceRoll);
        diceRoll.setSessionId(this.id);
        EventLog.getInstance()
                .logEvent(new Event("Dice Roll" + diceRoll.getId() + " added to Session " + this.getName()));
    }

    // MODIFIES: this, sessionNote
    // EFFECTS: adds the given session note to the list of session notes, and
    // sets the session note's session id to this session's id
    public void addSessionNote(SessionNote sessionNote) {
        sessionNotes.add(sessionNote);
        sessionNote.setSessionId(this.id);
        EventLog.getInstance()
                .logEvent(new Event("Session Note" + sessionNote.getTitle() + " added to Session " + this.getName()));
    }

    // MODIFIES: this, additionalNote
    // EFFECTS: adds the given additional note to the list of additional notes,
    // and sets the additional note's session id to this session's id
    public void addAdditionalNote(AdditionalNote additionalNote) {
        additionalNotes.add(additionalNote);
        additionalNote.setSessionId(this.id);
        EventLog.getInstance().logEvent(
                new Event("Additional Note" + additionalNote.getTitle() + " added to Session " + this.getName()));
    }

    // EFFECTS: returns the character with the given id, or null if none exists
    public Character getCharacter(int id) {
        for (Character c : characters) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    // EFFECTS: returns the dice calculation with the given id, or null if none
    // exists
    public DiceCalculation getDiceCalculation(int id) {
        for (DiceCalculation d : diceCalculations) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    // EFFECTS: returns the dice roll with the given id, or null if none exists
    public DiceRoll getDiceRoll(int id) {
        for (DiceRoll d : diceRolls) {
            if (d.getId() == id) {
                return d;
            }
        }
        return null;
    }

    // EFFECTS: returns the session note with the given id, or null if none exists
    public SessionNote getSessionNote(int id) {
        for (SessionNote s : sessionNotes) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    // EFFECTS: returns the additional note with the given id, or null if none
    // exists
    public AdditionalNote getAdditionalNote(int id) {
        for (AdditionalNote a : additionalNotes) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    // EFFECTS: returns the list of all characters in this session
    public List<Character> getCharacters() {
        return characters;
    }

    // EFFECTS: returns the list of all dice calculations in this session
    public List<DiceCalculation> getDiceCalculations() {
        return diceCalculations;
    }

    // EFFECTS: returns the list of all dice rolls in this session
    public List<DiceRoll> getDiceRolls() {
        return diceRolls;
    }

    // EFFECTS: returns the list of all session notes in this session
    public List<SessionNote> getSessionNotes() {
        return sessionNotes;
    }

    // EFFECTS: returns the list of all additional notes in this session
    public List<AdditionalNote> getAdditionalNotes() {
        return additionalNotes;
    }

    // MODIFIES: this
    // EFFECTS: delete a character in session
    public void deleteCharacter(int id) {
        EventLog.getInstance().logEvent(new Event("Character" + getCharacter(id).getName() + " removed from Session " + this.getName()));
        characters.removeIf(c -> c.getId() == id);
    }

    // MODIFIES: this
    // EFFECTS: delete a dice calculation in session
    public void deleteDiceCalculation(int id) {
        EventLog.getInstance().logEvent(new Event("Dice Calculation" + getDiceCalculation(id).getName() + " removed from Session " + this.getName()));
        diceCalculations.removeIf(c -> c.getId() == id);
    }

    // MODIFIES: this
    // EFFECTS: delete a dice roll in session
    public void deleteDiceRoll(int id) {
        EventLog.getInstance().logEvent(new Event("Dice Roll" + id + " removed from Session " + this.getName()));
        diceRolls.removeIf(r -> r.getId() == id);
    }

    // MODIFIES: this
    // EFFECTS: delete a session note in session
    public void deleteSessionNote(int id) {
        EventLog.getInstance().logEvent(new Event("Session Note" + getSessionNote(id).getTitle() + " removed from Session " + this.getName()));
        sessionNotes.removeIf(n -> n.getId() == id);
    }

    // MODIFIES: this
    // EFFECTS: delete an additional note in session
    public void deleteAdditionalNote(int id) {
        EventLog.getInstance().logEvent(new Event("Additional Note" + getAdditionalNote(id).getTitle() + " removed from Session " + this.getName()));
        additionalNotes.removeIf(n -> n.getId() == id);
    }

    // EFFECTS: returns the time this session was created
    public LocalDateTime getTimeCreated() {
        return timeCreated;
    }

    // EFFECTS: returns the time this session was last opened
    public LocalDateTime getTimeLastOpened() {
        return timeLastOpened;
    }

    // MODIFIES: this
    // EFFECTS: sets the time this session was last opened to now
    public void markOpened() {
        timeLastOpened = LocalDateTime.now();
    }

    // MODIFIES: this
    // EFFECTS: sets the time this session was created (for reading)
    public void setTimeCreated(LocalDateTime timeCreated) {
        this.timeCreated = timeCreated;
    }

    // MODIFIES: this
    // EFFECTS: sets the time this session was last opened (for reading)
    public void setTimeLastOpened(LocalDateTime timeLastOpened) {
        this.timeLastOpened = timeLastOpened;
    }

    // EFFECTS: returns this session as a JSON object
    public JSONObject toJsonObject() {
        JSONObject sessionAsJson = new JSONObject();
        sessionAsJson.put("id", this.id);
        sessionAsJson.put("name", this.name);
        sessionAsJson.put("timeCreated", this.timeCreated.toString());
        sessionAsJson.put("timeLastOpened", this.timeLastOpened.toString());
        sessionAsJson.put("characters", charactersToJsonArray());
        sessionAsJson.put("diceCalculations", diceCalculationsToJsonArray());
        sessionAsJson.put("diceRolls", diceRollsToJsonArray());
        sessionAsJson.put("sessionNotes", sessionNotesToJsonArray());
        sessionAsJson.put("additionalNotes", additionalNotesToJsonArray());
        return sessionAsJson;
    }

    // EFFECTS: returns the list of characters as a JSON array
    public JSONArray charactersToJsonArray() {
        JSONArray charactersAsJson = new JSONArray();
        for (Character character : characters) {
            charactersAsJson.put(character.toJsonObject());
        }
        return charactersAsJson;
    }

    // EFFECTS: returns the list of dice calculations as a JSON array
    public JSONArray diceCalculationsToJsonArray() {
        JSONArray diceCalculationsAsJson = new JSONArray();
        for (DiceCalculation diceCalculation : diceCalculations) {
            diceCalculationsAsJson.put(diceCalculation.toJsonObject());
        }
        return diceCalculationsAsJson;
    }

    // EFFECTS: returns the list of dice rolls as a JSON array
    public JSONArray diceRollsToJsonArray() {
        JSONArray diceRollsAsJson = new JSONArray();
        for (DiceRoll diceRoll : diceRolls) {
            diceRollsAsJson.put(diceRoll.toJsonObject());
        }
        return diceRollsAsJson;
    }

    // EFFECTS: returns the list of session notes as a JSON array
    public JSONArray sessionNotesToJsonArray() {
        JSONArray sessionNotesAsJson = new JSONArray();
        for (SessionNote sessionNote : sessionNotes) {
            sessionNotesAsJson.put(sessionNote.toJsonObject());
        }
        return sessionNotesAsJson;
    }

    // EFFECTS: returns the list of additional notes as a JSON array
    public JSONArray additionalNotesToJsonArray() {
        JSONArray additionalNotesAsJson = new JSONArray();
        for (AdditionalNote additionalNote : additionalNotes) {
            additionalNotesAsJson.put(additionalNote.toJsonObject());
        }
        return additionalNotesAsJson;
    }

    // MODIFIES: this
    // EFFECTS: sets the id of this session to the given value; if the given id is
    // greater than or equal to the next id, the next id is assigned as id + 1.
    public void setId(int id) {
        this.id = id;
        if (id >= nextId) {
            nextId = id + 1;
        }
    }
}