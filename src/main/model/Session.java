package model;

import java.util.ArrayList;
import java.util.List;

// Represent a session with a list of characters, dice calculations, dice rolls,
// session notes, and additional notes
public class Session {

    private static int nextId = 1;

    private int id;
    private String name;
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
        this.name = name;
    }

    // MODIFIES: this, character
    // EFFECTS: adds the given character to the list of characters, and sets
    // the character's session id to this session's id
    public void addCharacter(Character character) {
        characters.add(character);
        character.setSessionId(this.id);
    }

    // MODIFIES: this, diceCalculation
    // EFFECTS: adds the given dice calculation to the list of dice calculations,
    // and sets the dice calculation's session id to this session's id
    public void addDiceCalculation(DiceCalculation diceCalculation) {
        diceCalculations.add(diceCalculation);
        diceCalculation.setSessionId(this.id);
    }

    // MODIFIES: this, diceRoll
    // EFFECTS: adds the given dice roll to the list of dice rolls, and sets
    // the dice roll's session id to this session's id
    public void addDiceRoll(DiceRoll diceRoll) {
        diceRolls.add(diceRoll);
        diceRoll.setSessionId(this.id);
    }

    // MODIFIES: this, sessionNote
    // EFFECTS: adds the given session note to the list of session notes, and
    // sets the session note's session id to this session's id
    public void addSessionNote(SessionNote sessionNote) {
        sessionNotes.add(sessionNote);
        sessionNote.setSessionId(this.id);
    }

    // MODIFIES: this, additionalNote
    // EFFECTS: adds the given additional note to the list of additional notes,
    // and sets the additional note's session id to this session's id
    public void addAdditionalNote(AdditionalNote additionalNote) {
        additionalNotes.add(additionalNote);
        additionalNote.setSessionId(this.id);
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

    // REQUIRES: character with the given id is present in characters
    // MODIFIES: this
    // EFFECTS: delete a character in session
    public void deleteCharacter(int id) {
        for (Character c : characters) {
            if (c.getId() == id) {
                characters.remove(c);
                break;
            }
        }
    }

    // REQUIRES: dice calculation with the given id is present in diceCalculations
    // MODIFIES: this
    // EFFECTS: delete a dice calculation in session
    public void deleteDiceCalculation(int id) {
        for (DiceCalculation d : diceCalculations) {
            if (d.getId() == id) {
                diceCalculations.remove(d);
                break;
            }
        }
    }

    // REQUIRES: dice roll with the given id is present in diceRolls
    // MODIFIES: this
    // EFFECTS: delete a dice roll in session
    public void deleteDiceRoll(int id) {
        for (DiceRoll d : diceRolls) {
            if (d.getId() == id) {
                diceRolls.remove(d);
                break;
            }
        }
    }

    // REQUIRES: session note with the given id is present in sessionNotes
    // MODIFIES: this
    // EFFECTS: delete a session note in session
    public void deleteSessionNote(int id) {
        for (SessionNote s : sessionNotes) {
            if (s.getId() == id) {
                sessionNotes.remove(s);
                break;
            }
        }
    }

    // REQUIRES: additional note with the given id is present in additionalNotes
    // MODIFIES: this
    // EFFECTS: delete an additional note in session
    public void deleteAdditionalNote(int id) {
        for (AdditionalNote a : additionalNotes) {
            if (a.getId() == id) {
                additionalNotes.remove(a);
                break;
            }
        }
    }
}