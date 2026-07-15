package model;

import java.util.Map;

// Represents a dice roll with number of each dnd die, and the total after rolling
public class DiceRoll {

    // EFFECTS: constructs a dice roll with the given number of each die
    // type, and assigns it a unique id; does not roll the dice yet
    public DiceRoll(int d4, int d6, int d8, int d10, int d12, int d20) {
        // stub
    }

    // EFFECTS: returns the id of this dice roll
    public int getId() {
        // stub
        return 0;
    }

    // EFFECTS: returns a map of die type (number of sides) to quantity used
    // in this dice roll
    public Map<Integer, Integer> getDice() {
        // stub
        return null;
    }

    // MODIFIES: this
    // EFFECTS: rolls all dice in this dice roll and updates the result
    public void roll() {
        // stub
    }

    // EFFECTS: returns the total result of this dice roll; result is 0 if
    // roll() has not been called yet
    public int getResult() {
        // stub
        return 0;
    }

    // EFFECTS: returns the session id of the session this dice roll belongs to
    public int getSessionId() {
        // stub
        return 0;
    }

    // MODIFIES: this
    // EFFECTS: sets the session id of the session this dice roll belongs to
    public void setSessionId(int sessionId) {
        // stub
    }
}