package model;

// Represents a session note with a title and text
public class SessionNote {

    private static int nextId = 0;

    private int id;
    private String title;
    private String text;
    private int sessionId;

    // EFFECTS: constructs a session note with the given title and no text,
    // and assigns it a unique id
    public SessionNote(String title) {
        this.id = nextId;
        nextId++;
        this.title = title;
        this.text = "";
        this.sessionId = 0;
    }

    // EFFECTS: returns the id of this session note
    public int getId() {
        return id;
    }

    // EFFECTS: returns the title of this session note
    public String getTitle() {
        return title;
    }

    // MODIFIES: this
    // EFFECTS: sets the title of this session note
    public void setTitle(String title) {
        this.title = title;
    }

    // EFFECTS: returns the text of this session note
    public String getText() {
        return text;
    }

    // MODIFIES: this
    // EFFECTS: sets the text of this session note
    public void setText(String text) {
        this.text = text;
    }

    // EFFECTS: returns the session id of the session this session note belongs to
    public int getSessionId() {
        return sessionId;
    }

    // MODIFIES: this
    // EFFECTS: sets the session id of the session this session note belongs to
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}