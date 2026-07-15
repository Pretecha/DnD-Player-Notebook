package model;

import java.util.List;
import java.util.ArrayList;

// Represents the hub of the application, where the user can access all other sections
public class Hub {

    private Session currentSession;
    private List<Session> sessions;
    private List<Integer> pinnedSessionsId;

    // EFFECTS: construct a hub with no sessions
    public Hub() {
        this.currentSession = null;
        this.sessions = new ArrayList<>();
        this.pinnedSessionsId = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: add a session to hub and give it an id
    public void addSession(String name) {
        sessions.add(new Session(name));
    }

    // EFFECTS: returns the session with the given id, or null if none exists
    public Session getSession(int id) {
        for (Session s : sessions) {
            if (s.getId() == id) {
                return s;
            }
        }
        return null;
    }

    // EFFECTS: returns the list of all sessions in this hub
    public List<Session> getSessions() {
        return sessions;
    }

    // EFFECTS: returns a character with the given id in hub, or null if none exists
    public Character getCharacterFromHub(int id) {
        for (Session s : sessions) {
            if (s.getCharacter(id) != null) {
                return s.getCharacter(id);
            }
        }
        return null;
    }

    // REQUIRES: session with selected id is present in sessions
    // MODIFIES: this
    // EFFECTS: selects current session as active session.
    public void selectSession(int id) {
        currentSession = getSession(id);
    }

    // REQUIRES: session with selected id is present in characters
    // MODIFIES: this
    // EFFECTS: selects the session which the current character belongs to as active
    // session.
    public void selectSessionWithCharacter(int id) {
        for (Session s : sessions) {
            if (s.getCharacter(id) != null) {
                currentSession = s;
                break;
            }
        }
    }

    // REQUIRES: session with selected id is present in sessions
    // MODIFIES: this
    // EFFECTS: delete a session in hub
    public void deleteSession(int id) {
        for (Session s : sessions) {
            if (s.getId() == id) {
                sessions.remove(s);
                break;
            }
        }
    }

    // EFFECTS: returns the session currently selected as active, or null if none
    // has been selected
    public Session getCurrentSession() {
        return currentSession;
    }

    // REQUIRES: session with the given id is present in sessions AND that given id
    // is not present in pinnedSessionsId
    // MODIFIES: this
    // EFFECTS: add selected session into list of pinned sessions
    public void pinSession(int id) {
        pinnedSessionsId.add(id);
    }

    // REQUIRES: given id is present in pinnedSessionsId
    // MODIFIES: this
    // EFFECTS: remove selected session from list of pinned sessions
    public void unpinSession(int id) {
        pinnedSessionsId.remove(Integer.valueOf(id));
    }

    // EFFECTS: returns the list of ids for sessions currently pinned
    public List<Integer> getPinnedSessionsId() {
        return pinnedSessionsId;
    }
}
