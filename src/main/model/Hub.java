package model;

import java.util.List;

// Represents the hub of the application, where the user can access all other sections
public class Hub {

    // EFFECTS: construct a hub with no sessions
    public Hub() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: add a session to hub and give it an id
    public void addSession(String name) {
        // stub
    }

    // EFFECTS: returns the session with the given id, or null if none exists
    public Session getSession(int id) {
        // stub
        return null;
    }

    // EFFECTS: returns the list of all sessions in this hub
    public List<Session> getSessions() {
        // stub
        return null;
    }

    // EFFECTS: returns a character with the given id in hub, or null if none exists
    public Character getCharacterFromHub(int id) {
        // stub
        return null;
    }

    // REQUIRES: session with selected id is present in sessions
    // MODIFIES: this
    // EFFECTS: selects current session as active session.
    public void selectSession(int id) {
        // stub
    }

    // REQUIRES: session with selected id is present in characters
    // MODIFIES: this
    // EFFECTS: selects the session which the current character belongs to as active
    // session.
    public void selectSessionWithCharacter(int id) {
        // stub
    }

    // REQUIRES: session with selected id is present in sessions
    // MODIFIES: this
    // EFFECTS: delete a session in hub
    public void deleteSession(int id) {
        // stub
    }

    // EFFECTS: returns the session currently selected as active, or null if none
    // has been selected
    public Session getCurrentSession() {
        // stub
        return null;
    }

    // REQUIRES: session with the given id is present in sessions AND that session
    // is not present in pinnedSessions
    // MODIFIES: this
    // EFFECTS: add selected session into list of pinned sessions
    public void pinSession(int id) {
        // stub
    }

    // REQUIRES: session with selected id is present in pinnedSessions
    // MODIFIES: this
    // EFFECTS: remove selected session from list of pinned sessions
    public void unpinSession(int id) {
        // stub
    }

    // EFFECTS: returns the list of ids of all sessions currently pinned
    public List<Integer> getPinnedSessions() {
        // stub
        return null;
    }
}
