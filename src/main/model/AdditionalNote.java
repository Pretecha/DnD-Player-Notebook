package model;

// Represents an additional note with a title and text
public class AdditionalNote {

    private static int nextId = 1;

    private int id;
    private String title;
    private String text;
    private int sessionId;

    // EFFECTS: constructs an additional note with the given title and no text,
    // and assigns it a unique id
    public AdditionalNote(String title) {
        this.id = nextId;
        nextId++;
        this.title = title;
        this.text = "";
        this.sessionId = 0;
    }

    // EFFECTS: returns the id of this additional note
    public int getId() {
        return id;
    }

    // EFFECTS: returns the title of this additional note
    public String getTitle() {
        return title;
    }

    // MODIFIES: this
    // EFFECTS: sets the title of this additional note
    public void setTitle(String title) {
        this.title = title;
    }

    // EFFECTS: returns the text of this additional note
    public String getText() {
        return text;
    }

    // MODIFIES: this
    // EFFECTS: sets the text of this additional note
    public void setText(String text) {
        this.text = text;
    }

    // EFFECTS: returns the session id of the session this additional note belongs to
    public int getSessionId() {
        return sessionId;
    }

    // MODIFIES: this
    // EFFECTS: sets the session id of the session this additional note belongs to
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}