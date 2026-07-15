package model;

// Represents a session note with a title and text
public class SessionNote {

    // EFFECTS: constructs a session note with the given title and no text,
    // and assigns it a unique id
    public SessionNote(String title) {
        // stub
    }

    // EFFECTS: returns the id of this session note
    public int getId() {
        // stub
        return 0;
    }

    // EFFECTS: returns the title of this session note
    public String getTitle() {
        // stub
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sets the title of this session note
    public void setTitle(String title) {
        // stub
    }

    // EFFECTS: returns the text of this session note
    public String getText() {
        // stub
        return null;
    }

    // MODIFIES: this
    // EFFECTS: sets the text of this session note
    public void setText(String text) {
        // stub
    }

    // EFFECTS: returns the session id of the session this session note belongs to
    public int getSessionId() {
        // stub
        return 0;
    }

    // MODIFIES: this
    // EFFECTS: sets the session id of the session this session note belongs to
    public void setSessionId(int sessionId) {
        // stub
    }
}