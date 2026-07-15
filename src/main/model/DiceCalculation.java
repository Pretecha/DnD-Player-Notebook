package model;

import java.util.Map;

// Represents a dice calculation with number of each dnd die, and computes
// the lowest possible, average, and highest possible value of the combination
public class DiceCalculation {

    // EFFECTS: constructs a dice calculation with the given name and no dice
    // added yet, and assigns it a unique id
    public DiceCalculation(String name) {
        // stub
    }

    // EFFECTS: returns the id of this dice calculation
    public int getId() {
        // stub
        return 0;
    }

    // EFFECTS: returns the label of this dice calculation
    public String getLabel() {
        // stub
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sets the label of this dice calculation
    public void setLabel(String label) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: sets the number of dice with the given number of sides used in
    // this calculation (i.e. setDice(6, 2) means 2d6); overwrites any previous
    // count for that die type
    public void setDice(int sides, int count) {
        // stub
    }

    // EFFECTS: returns a map of die type (number of sides) to quantity used
    // in this calculation
    public Map<Integer, Integer> getDice() {
        // stub
        return null;
    }

    // EFFECTS: returns the lowest possible value of this dice calculation
    // (sum of 1 * count for each die type)
    public int getLowestValue() {
        // stub
        return 0;
    }

    // EFFECTS: returns the average value of this dice calculation (sum of
    // ((sides + 1) / 2) * count for each die type)
    public double getAverageValue() {
        // stub
        return 0;
    }

    // EFFECTS: returns the highest possible value of this dice calculation
    // (sum of sides * count for each die type)
    public int getHighestValue() {
        // stub
        return 0;
    }

    // EFFECTS: returns the session id of the session this dice calculation
    // belongs to
    public int getSessionId() {
        // stub
        return 0;
    }

    // MODIFIES: this
    // EFFECTS: sets the session id of the session this dice calculation
    // belongs to
    public void setSessionId(int sessionId) {
        // stub
    }
}