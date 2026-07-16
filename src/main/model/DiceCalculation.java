package model;

import java.util.Map;
import java.util.HashMap;

// Represents a dice calculation with number of each dnd die, and computes
// the lowest possible, average, and highest possible value of the combination
public class DiceCalculation {

    private static int nextId = 1;

    private int id;
    private String name;
    private Map<Integer, Integer> dice;
    private int sessionId;

    // EFFECTS: constructs a dice calculation with the given name and no dice
    // added yet, and assigns it a unique id
    public DiceCalculation(String name) {
        this.id = nextId;
        nextId++;
        this.name = name;
        this.dice = new HashMap<>();
        this.sessionId = 0;
    }

    // EFFECTS: returns the id of this dice calculation
    public int getId() {
        return id;
    }

    // EFFECTS: returns the name of this dice calculation
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: sets the name of this dice calculation
    public void setName(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: sets the number of dice with the given number of sides used in
    // this calculation (i.e. setDice(6, 2) means 2d6); overwrites any previous
    // count for that die type
    public void setDice(int sides, int count) {
        dice.put(sides, count);
    }

    // EFFECTS: returns a map of die type (number of sides) to quantity used
    // in this calculation
    public Map<Integer, Integer> getDice() {
        return dice;
    }

    // EFFECTS: returns the lowest possible value of this dice calculation
    // (sum of 1 * count for each die type)
    public int getLowestValue() {
        int total = 0;
        for (Map.Entry<Integer, Integer> entry : dice.entrySet()) {
            total += 1 * entry.getValue();
        }
        return total;
    }

    // EFFECTS: returns the average value of this dice calculation (sum of
    // ((sides + 1) / 2) * count for each die type)
    public double getAverageValue() {
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : dice.entrySet()) {
            int sides = entry.getKey();
            int count = entry.getValue();
            total += ((sides + 1) / 2.0) * count;
        }
        return total;
    }

    // EFFECTS: returns the highest possible value of this dice calculation
    // (sum of sides * count for each die type)
    public int getHighestValue() {
        int total = 0;
        for (Map.Entry<Integer, Integer> entry : dice.entrySet()) {
            int sides = entry.getKey();
            int count = entry.getValue();
            total += sides * count;
        }
        return total;
    }

    // EFFECTS: returns the session id of the session this dice calculation
    // belongs to
    public int getSessionId() {
        return sessionId;
    }

    // MODIFIES: this
    // EFFECTS: sets the session id of the session this dice calculation
    // belongs to
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}