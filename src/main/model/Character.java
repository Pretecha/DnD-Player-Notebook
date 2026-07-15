package model;

// Represents a character with various dnd character stats and descriptions
public class Character {

    private static int nextId = 0;

    private int id;
    private String name;
    private String levelAndClass;
    private String race;
    private String alignment;
    private String background;
    private int str;
    private int con;
    private int dex;
    private int cha;
    private int intel;
    private int wis;
    private int sessionId;

    // EFFECTS: constructs a character with the given name, and initializes
    // levelAndClass, race, alignment, and background as empty strings, all six
    // stats as 0, and assigns it a unique id
    public Character(String name) {
        this.id = nextId;
        nextId++;
        this.name = name;
        this.levelAndClass = "";
        this.race = "";
        this.alignment = "";
        this.background = "";
        this.str = 0;
        this.con = 0;
        this.dex = 0;
        this.cha = 0;
        this.intel = 0;
        this.wis = 0;
        this.sessionId = 0;
    }

    // EFFECTS: returns the id of this character
    public int getId() {
        return id;
    }

    // EFFECTS: returns the name of this character
    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: sets the name of this character
    public void setName(String name) {
        this.name = name;
    }

    // EFFECTS: returns the level and class of this character
    public String getLevelAndClass() {
        return levelAndClass;
    }

    // MODIFIES: this
    // EFFECTS: sets the level and class of this character
    public void setLevelAndClass(String levelAndClass) {
        this.levelAndClass = levelAndClass;
    }

    // EFFECTS: returns the race of this character
    public String getRace() {
        return race;
    }

    // MODIFIES: this
    // EFFECTS: sets the race of this character
    public void setRace(String race) {
        this.race = race;
    }

    // EFFECTS: returns the alignment of this character
    public String getAlignment() {
        return alignment;
    }

    // MODIFIES: this
    // EFFECTS: sets the alignment of this character
    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    // EFFECTS: returns the background of this character
    public String getBackground() {
        return background;
    }

    // MODIFIES: this
    // EFFECTS: sets the background of this character
    public void setBackground(String background) {
        this.background = background;
    }

    // EFFECTS: returns the str stat of this character
    public int getStr() {
        return str;
    }

    // MODIFIES: this
    // EFFECTS: sets the str stat of this character
    public void setStr(int str) {
        this.str = str;
    }

    // EFFECTS: returns the con stat of this character
    public int getCon() {
        return con;
    }

    // MODIFIES: this
    // EFFECTS: sets the con stat of this character
    public void setCon(int con) {
        this.con = con;
    }

    // EFFECTS: returns the dex stat of this character
    public int getDex() {
        return dex;
    }

    // MODIFIES: this
    // EFFECTS: sets the dex stat of this character
    public void setDex(int dex) {
        this.dex = dex;
    }

    // EFFECTS: returns the cha stat of this character
    public int getCha() {
        return cha;
    }

    // MODIFIES: this
    // EFFECTS: sets the cha stat of this character
    public void setCha(int cha) {
        this.cha = cha;
    }

    // EFFECTS: returns the int stat of this character
    public int getIntel() {
        return intel;
    }

    // MODIFIES: this
    // EFFECTS: sets the int stat of this character
    public void setIntel(int intel) {
        this.intel = intel;
    }

    // EFFECTS: returns the wis stat of this character
    public int getWis() {
        return wis;
    }

    // MODIFIES: this
    // EFFECTS: sets the wis stat of this character
    public void setWis(int wis) {
        this.wis = wis;
    }

    // EFFECTS: returns the session id of the session this character belongs to
    public int getSessionId() {
        return sessionId;
    }

    // MODIFIES: this
    // EFFECTS: sets the session id of the session this character belongs to
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}