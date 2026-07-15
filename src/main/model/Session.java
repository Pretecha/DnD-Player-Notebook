package model;

import java.util.List;

// Represent a session with a list of characters, dice calculations, dice rolls,
// session notes, and additional notes
public class Session {

    // EFFECTS: constructs a session with the given name, no items in any list,
    // and assigns it a unique id
    public Session(String name) {
        // stub
    }

    // EFFECTS: returns the id of this session
    public int getId() {
        // stub
        return 0;
    }

    // EFFECTS: returns the name of this session
    public String getName() {
        // stub
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sets the name of this session
    public void setName(String name) {
        // stub
    }

    // MODIFIES: this, character
    // EFFECTS: adds the given character to the list of characters, and sets
    // the character's session id to this session's id
    public void addCharacter(Character character) {
        // stub
    }

    // MODIFIES: this, diceCalculation
    // EFFECTS: adds the given dice calculation to the list of dice calculations,
    // and sets the dice calculation's session id to this session's id
    public void addDiceCalculation(DiceCalculation diceCalculation) {
        // stub
    }

    // MODIFIES: this, diceRoll
    // EFFECTS: adds the given dice roll to the list of dice rolls, and sets
    // the dice roll's session id to this session's id
    public void addDiceRoll(DiceRoll diceRoll) {
        // stub
    }

    // MODIFIES: this, sessionNote
    // EFFECTS: adds the given session note to the list of session notes, and
    // sets the session note's session id to this session's id
    public void addSessionNote(SessionNote sessionNote) {
        // stub
    }

    // MODIFIES: this, additionalNote
    // EFFECTS: adds the given additional note to the list of additional notes,
    // and sets the additional note's session id to this session's id
    public void addAdditionalNote(AdditionalNote additionalNote) {
        // stub
    }

    // EFFECTS: returns the character with the given id, or null if none exists
    public Character getCharacter(int id) {
        // stub
        return null;
    }

    // EFFECTS: returns the dice calculation with the given id, or null if none
    // exists
    public DiceCalculation getDiceCalculation(int id) {
        // stub
        return null;
    }

    // EFFECTS: returns the dice roll with the given id, or null if none exists
    public DiceRoll getDiceRoll(int id) {
        // stub
        return null;
    }

    // EFFECTS: returns the session note with the given id, or null if none exists
    public SessionNote getSessionNote(int id) {
        // stub
        return null;
    }

    // EFFECTS: returns the additional note with the given id, or null if none
    // exists
    public AdditionalNote getAdditionalNote(int id) {
        // stub
        return null;
    }

    // EFFECTS: returns the list of all characters in this session
    public List<Character> getCharacters() {
        // stub
        return null;
    }

    // EFFECTS: returns the list of all dice calculations in this session
    public List<DiceCalculation> getDiceCalculations() {
        // stub
        return null;
    }

    // EFFECTS: returns the list of all dice rolls in this session
    public List<DiceRoll> getDiceRolls() {
        // stub
        return null;
    }

    // EFFECTS: returns the list of all session notes in this session
    public List<SessionNote> getSessionNotes() {
        // stub
        return null;
    }

    // EFFECTS: returns the list of all additional notes in this session
    public List<AdditionalNote> getAdditionalNotes() {
        // stub
        return null;
    }

    // REQUIRES: character with the given id is present in characters
    // MODIFIES: this
    // EFFECTS: delete a character in session
    public void deleteCharacter(int id) {
        // stub
    }

    // REQUIRES: dice calculation with the given id is present in diceCalculations
    // MODIFIES: this
    // EFFECTS: delete a dice calculation in session
    public void deleteDiceCalculation(int id) {
        // stub
    }

    // REQUIRES: dice roll with the given id is present in diceRolls
    // MODIFIES: this
    // EFFECTS: delete a dice roll in session
    public void deleteDiceRoll(int id) {
        // stub
    }

    // REQUIRES: session note with the given id is present in sessionNotes
    // MODIFIES: this
    // EFFECTS: delete a session note in session
    public void deleteSessionNote(int id) {
        // stub
    }

    // REQUIRES: additional note with the given id is present in additionalNotes
    // MODIFIES: this
    // EFFECTS: delete an additional note in session
    public void deleteAdditionalNote(int id) {
        // stub
    }
}