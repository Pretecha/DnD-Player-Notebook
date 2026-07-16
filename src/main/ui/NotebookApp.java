package ui;

import model.*;
import model.Character;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

// the dnd player notebook application
public class NotebookApp {

    private Hub mainHub;
    private Scanner scanner;
    private String stage;

    // EFFECTS: constructs a new NotebookApp with an empty hub, initializes the input
    // scanner, sets the stage to "hub", and starts running the application
    public NotebookApp() {
        mainHub = new Hub();
        scanner = new Scanner(System.in);
        stage = "hub";
        runApp();
    }

    // MODIFIES: this
    // EFFECTS: repeatedly displays the menu for the current stage and processes the
    // user's input, until the stage is set to "quit"
    public void runApp() {
        while (!stage.equals("quit")) {
            switch (stage) {
                case "hub":
                    System.out.println("======== HUB ========");
                    System.out.println("1. View Sessions");
                    System.out.println("2. View Characters");
                    System.out.println("3. View Pinned Sessions");
                    System.out.println("4. Quit");
                    switch (readInt()) {
                        case 1:
                            viewSessions();
                            break;
                        case 2:
                            viewCharacters();
                            break;
                        case 3:
                            viewPinnedSessions();
                            break;
                        case 4:
                            stage = "quit";
                            break;
                    }
                    break;
                case "session":
                    Session current = mainHub.getCurrentSession();
                    System.out.println("======== " + current.getName() + " ========");
                    System.out.println("1. Character Sheets");
                    System.out.println("2. Dice Operations");
                    System.out.println("3. Session Notes");
                    System.out.println("4. Additional Notes");
                    System.out.println("5. Back to Hub");
                    switch (readInt()) {
                        case 1:
                            sessionCharacters();
                            break;
                        case 2:
                            sessionDice();
                            break;
                        case 3:
                            sessionNotes();
                            break;
                        case 4:
                            sessionAdditionalNotes();
                            break;
                        case 5:
                            stage = "hub";
                            break;
                    }
                    break;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: displays all sessions in the hub along with their characters, then
    // processes one user choice: select a session (sets stage to "session"), add a
    // new session, edit an existing session, remove a session, or return to the hub
    public void viewSessions() {
        System.out.println("====== Sessions ======");
        for (Session s : mainHub.getSessions()) {
            System.out.println(s.getId() + " " + s.getName());
            System.out.println("----- Characters -----");
            for (Character c : s.getCharacters()) {
                System.out.println(c.getId() + " " + c.getName() + " " + c.getLevelAndClass());
            }
            System.out.println("======================");
        }
        System.out.println("1. Select Session");
        System.out.println("2. Add Session");
        System.out.println("3. Edit Session");
        System.out.println("4. Remove Session");
        System.out.println("5. Back to Hub");
        switch (readInt()) {
            case 1:
                int id = readInt("Please enter the id of the session you wish to inspect");
                if (mainHub.getSession(id) == null) {
                    System.out.println("Session with given id does not exist");
                } else {
                    stage = "session";
                    mainHub.selectSession(id);
                }
                break;
            case 2:
                System.out.println("Please enter the name of the session");
                String name = scanner.nextLine();
                mainHub.addSession(name);
                System.out.println("Session of name " + name + " has been added");
                break;
            case 3:
                if (mainHub.getSessions().size() < 1) {
                    System.out.println("There is no existing session");
                    break;
                }
                editSessions();
                break;
            case 4:
                if (mainHub.getSessions().size() < 1) {
                    System.out.println("There is no existing session");
                    break;
                }
                int id2 = readInt("Please enter the id of the session you wish to remove");
                if (mainHub.getSession(id2) == null) {
                    System.out.println("Session with given id does not exist");
                } else {
                    if (mainHub.getPinnedSessionsId().contains(Integer.valueOf(id2))) {
                        mainHub.unpinSession(id2);
                    }
                    mainHub.deleteSession(id2);
                    System.out.println("Session has been removed");
                }
                break;
            case 5:
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: if no character exists in any session, displays a message and returns;
    // otherwise displays every character across all sessions, then processes one user
    // choice: select a character (sets stage to "session" and selects that character's
    // session), or return to the hub
    public void viewCharacters() {
        boolean haveCharacters = false;
        for (Session s : mainHub.getSessions()) {
            if (!s.getCharacters().isEmpty()) {
                haveCharacters = true;
            }
        }
        if (!haveCharacters) {
            System.out.println("There is no existing character");
            return;
        }
        System.out.println("===== Characters =====");
        for (Session s : mainHub.getSessions()) {
            for (Character c : s.getCharacters()) {
                System.out.println(c.getId() + " " + c.getName() + " " + c.getLevelAndClass());
                System.out.println("From: " + s.getId() + " " + s.getName());
            }
            System.out.println("======================");
        }
        System.out.println("1. Select Character");
        System.out.println("2. Back to Hub");
        switch (readInt()) {
            case 1:
                int id = readInt("Please enter the id of the character you wish to inspect");
                if (mainHub.getCharacterFromHub(id) != null) {
                    mainHub.selectSessionWithCharacter(id);
                    stage = "session";
                } else {
                    System.out.println("Character with given id does not exist");
                }
                break;
            case 2:
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: if there are no pinned sessions, displays a message and returns;
    // otherwise displays all pinned sessions and their characters, then processes one
    // user choice: select a pinned session (sets stage to "session"), unpin a session,
    // or return to the hub
    public void viewPinnedSessions() {
        if (mainHub.getPinnedSessionsId().size() < 1) {
            System.out.println("There is no existing pinned session");
            return;
        }
        System.out.println("===== Pinned Sessions =====");
        for (int id : mainHub.getPinnedSessionsId()) {
            Session s = mainHub.getSession(id);
            if (s != null) {
                System.out.println(s.getId() + " " + s.getName());
                System.out.println("----- Characters -----");
                for (Character c : s.getCharacters()) {
                    System.out.println(c.getId() + " " + c.getName() + " " + c.getLevelAndClass());
                }
                System.out.println("=======================");
            }
        }
        System.out.println("1. Select Pinned Session");
        System.out.println("2. Unpin Pinned Session");
        System.out.println("3. Back to Hub");

        switch (readInt()) {
            case 1:
                int id = readInt("Please enter the id of the session you wish to inspect");

                if (mainHub.getSession(id) == null) {
                    System.out.println("Session with given id does not exist");
                } else {
                    stage = "session";
                    mainHub.selectSession(id);
                }
                break;
            case 2:
                int id2 = readInt("Please enter the id of the session you wish to unpin");
                Session s = mainHub.getSession(id2);
                boolean pinned = mainHub.getPinnedSessionsId().contains(Integer.valueOf(id2));

                if (s == null) {
                    System.out.println("Session with given id does not exist");
                } else if (!pinned) {
                    System.out.println("Session with given id is not pinned");
                } else {
                    mainHub.unpinSession(id2);
                    System.out.println("Session has been unpinned");
                }
                break;
            case 3:
                break;
        }

    }

    // MODIFIES: this
    // EFFECTS: prompts for a session id; if no session with that id exists, displays a
    // message and returns; otherwise displays the session's details and processes one
    // user choice: edit its name, pin it, unpin it, or return to the hub
    public void editSessions() {
        int id = readInt("Please enter the id of the session you wish to edit");
        Session session = mainHub.getSession(id);
        if (session == null) {
            System.out.println("Session with given id does not exist");
            return;
        }

        System.out.println("Session: " + session.getId() + " " + session.getName());
        System.out.println("----- Characters -----");
        for (Character c : session.getCharacters()) {
            System.out.println(c.getId() + " " + c.getName() + " " + c.getLevelAndClass());
        }
        System.out.println("1. Edit Name");
        System.out.println("2. Pin Session");
        System.out.println("3. Unpin Session");
        System.out.println("4. Back to Hub");

        switch (readInt()) {
            case 1:
                System.out.println("Please enter the new name of the session");
                String name = scanner.nextLine();
                session.setName(name);
                System.out.println("Session has been renamed to " + name);
                break;
            case 2:
                if (mainHub.getPinnedSessionsId().contains(Integer.valueOf(id))) {
                    System.out.println("Session is already pinned");
                } else {
                    mainHub.pinSession(id);
                    System.out.println("Session has been pinned");
                }
                break;
            case 3:
                if (!mainHub.getPinnedSessionsId().contains(Integer.valueOf(id))) {
                    System.out.println("Session is not pinned");
                } else {
                    mainHub.unpinSession(id);
                    System.out.println("Session has been unpinned");
                }
                break;
            case 4:
                break;
        }
    }

    // REQUIRES: a session is currently selected in mainHub
    // MODIFIES: this
    // EFFECTS: displays all characters in the current session, then processes one user
    // choice: add a new character, edit an existing character, remove a character, or
    // return to the session menu
    public void sessionCharacters() {
        Session session = mainHub.getCurrentSession();
        System.out.println("===== Characters =====");
        for (Character c : session.getCharacters()) {
            System.out.println(c.getId() + " " + c.getName() + " " + c.getLevelAndClass());
        }
        System.out.println("1. Add Character");
        System.out.println("2. Edit Character");
        System.out.println("3. Remove Character");
        System.out.println("4. Back to Session");

        switch (readInt()) {
            case 1:
                System.out.println("Please enter the name of the character");
                Character c = new Character(scanner.nextLine());
                session.addCharacter(c);
                System.out.println("Character has been added");
                break;
            case 2:
                int id = readInt("Please enter the id of the character you wish to edit");
                Character character = session.getCharacter(id);
                if (character == null) {
                    System.out.println("Character with given id does not exist");
                } else {
                    editCharacterFields(character);
                    System.out.println("Character has been updated");
                }
                break;
            case 3:
                int id2 = readInt("Please enter the id of the character you wish to remove");
                if (session.getCharacter(id2) == null) {
                    System.out.println("Character with given id does not exist");
                } else {
                    session.deleteCharacter(id2);
                    System.out.println("Character has been removed");
                }
                break;
            case 4:
                break;
        }
    }

    // MODIFIES: c
    // EFFECTS: repeatedly displays c's fields and current stats, and lets the user
    // choose one field or stat to edit at a time, until the user chooses to be done
    public void editCharacterFields(Character c) {
        boolean done = false;
        while (!done) {
            System.out.println("Name: " + c.getName());
            System.out.println("Level and Class: " + c.getLevelAndClass());
            System.out.println("Race: " + c.getRace());
            System.out.println("Alignment: " + c.getAlignment());
            System.out.println("Background: " + c.getBackground());
            System.out.println("STR:" + c.getStr() + " CON:" + c.getCon() + " DEX:" + c.getDex());
            System.out.println("CHA:" + c.getCha() + " INT:" + c.getIntel() + " WIS:" + c.getWis());
            System.out.println("Please select the fields you wish to change: ");
            System.out.println("1. Name       2. Level and Class 3. Race");
            System.out.println("4. Alignment  5. Background      6. Stats");
            System.out.println("7. Done");
            switch (readInt()) {
                case 1:
                    System.out.println("Please enter the new name");
                    c.setName(scanner.nextLine());
                    break;
                case 2:
                    System.out.println("Please enter the new level and class");
                    c.setLevelAndClass(scanner.nextLine());
                    break;
                case 3:
                    System.out.println("Please enter the new race");
                    c.setRace(scanner.nextLine());
                    break;
                case 4:
                    System.out.println("Please enter the new alignment");
                    c.setAlignment(scanner.nextLine());
                    break;
                case 5:
                    System.out.println("Please enter the new background");
                    c.setBackground(scanner.nextLine());
                    break;
                case 6:
                    System.out.println("Please select the stats you wish to change:");
                    System.out.println("1. Strength(STR)  2. Constitution(CON)  3. Dexterity(DEX)");
                    System.out.println("4. Charisma(CHA)  5. Intelligence(INT)  6. Wisdom(WIS)");
                    System.out.println("7. Back");
                    switch (readInt()) {
                        case 1:
                            c.setStr(readInt("Please enter the new Strength(STR)"));
                            break;
                        case 2:
                            c.setCon(readInt("Please enter the new Constitution(CON)"));
                            break;
                        case 3:
                            c.setDex(readInt("Please enter the new Dexterity(DEX)"));
                            break;
                        case 4:
                            c.setCha(readInt("Please enter the new Charisma(CHA)"));
                            break;
                        case 5:
                            c.setIntel(readInt("Please enter the new Intelligence(INT)"));
                            break;
                        case 6:
                            c.setWis(readInt("Please enter the new Wisdom(WIS)"));
                            break;
                        case 7:
                            break;
                    }
                    break;
                case 7:
                    done = true;
                    break;
            }
        }
    }

    // REQUIRES: a session is currently selected in mainHub
    // MODIFIES: this
    // EFFECTS: processes one user choice: go to the dice rolls menu, go to the dice
    // calculations menu, or return to the session menu
    public void sessionDice() {
        System.out.println("1. Dice Rolls");
        System.out.println("2. Dice Calculations");
        System.out.println("3. Back to Session");
        switch (readInt()) {
            case 1:
                sessionDiceRolls();
                break;
            case 2:
                sessionDiceCalculations();
                break;
            case 3:
                break;
        }
    }

    // REQUIRES: a session is currently selected in mainHub
    // MODIFIES: this
    // EFFECTS: displays all dice rolls in the current session, then processes one user
    // choice: roll new dice from entered dice notation and add the result, reroll an
    // existing dice roll (adding a new dice roll with the same dice and a new result),
    // remove a dice roll, or return to the dice menu
    public void sessionDiceRolls() {
        Session session = mainHub.getCurrentSession();
        System.out.println("===== Dice Rolls =====");
        for (DiceRoll r : session.getDiceRolls()) {
            System.out.println(r.getId() + " " + formatDice(r.getDice()) + " = " + r.getResult());
        }
        System.out.println("1. Roll Dice");
        System.out.println("2. Reroll");
        System.out.println("3. Remove Dice Roll");
        System.out.println("4. Back to Dice");
        switch (readInt()) {
            case 1:
                System.out.println("Please enter dice notation (e.g. 2d6 + 1d8)");
                Map<Integer, Integer> parsedRoll = parseDiceString(scanner.nextLine());
                if (parsedRoll == null) {
                    System.out.println("Invalid dice notation. Roll not added.");
                } else {
                    DiceRoll roll = new DiceRoll(
                            parsedRoll.getOrDefault(4, 0),
                            parsedRoll.getOrDefault(6, 0),
                            parsedRoll.getOrDefault(8, 0),
                            parsedRoll.getOrDefault(10, 0),
                            parsedRoll.getOrDefault(12, 0),
                            parsedRoll.getOrDefault(20, 0));
                    roll.roll();
                    session.addDiceRoll(roll);
                    System.out.println("New Roll (" + formatDice(roll.getDice()) + "): " + roll.getResult());
                }
                break;
            case 2:
                int id = readInt("Please enter the id of the dice roll you wish to reroll");
                DiceRoll roll = session.getDiceRoll(id);
                if (roll == null) {
                    System.out.println("Dice roll with given id does not exist");
                } else {
                    Map<Integer, Integer> diceMap = roll.getDice();
                    DiceRoll newRoll = new DiceRoll(
                            diceMap.getOrDefault(4, 0),
                            diceMap.getOrDefault(6, 0),
                            diceMap.getOrDefault(8, 0),
                            diceMap.getOrDefault(10, 0),
                            diceMap.getOrDefault(12, 0),
                            diceMap.getOrDefault(20, 0));
                    newRoll.roll();
                    session.addDiceRoll(newRoll);
                    System.out.println("New roll (" + formatDice(newRoll.getDice()) + "): " + newRoll.getResult());
                }
                break;
            case 3:
                int id2 = readInt("Please enter the id of the dice roll you wish to remove");
                if (session.getDiceRoll(id2) == null) {
                    System.out.println("Dice roll with given id does not exist");
                } else {
                    session.deleteDiceRoll(id2);
                    System.out.println("Dice roll has been removed");
                }
                break;
            case 4:
                break;
        }
    }

    // REQUIRES: a session is currently selected in mainHub
    // MODIFIES: this
    // EFFECTS: displays all dice calculations in the current session, then processes
    // one user choice: add a new dice calculation from entered dice notation, update an
    // existing dice calculation's dice from entered dice notation, remove a dice
    // calculation, or return to the dice menu
    public void sessionDiceCalculations() {
        Session session = mainHub.getCurrentSession();
        System.out.println("===== Dice Calculations =====");
        for (DiceCalculation d : session.getDiceCalculations()) {
            System.out.println(d.getId() + " " + d.getName() + " low=" + d.getLowestValue()
                    + " avg=" + d.getAverageValue() + " high=" + d.getHighestValue());
        }
        System.out.println("1. Add Dice Calculation");
        System.out.println("2. Edit Dice Calculation");
        System.out.println("3. Remove Dice Calculation");
        System.out.println("4. Back to Dice");
        switch (readInt()) {
            case 1:
                System.out.println("Please enter the label of the calculation");
                String name = scanner.nextLine();
                System.out.println("Please enter dice notation (e.g. 2d6 + 1d8 + 15)");
                Map<Integer, Integer> parsedCalc = parseDiceString(scanner.nextLine());
                if (parsedCalc == null) {
                    System.out.println("Invalid dice notation. Calculation not added.");
                } else {
                    DiceCalculation dc = new DiceCalculation(name);
                    for (Map.Entry<Integer, Integer> e : parsedCalc.entrySet()) {
                        dc.setDice(e.getKey(), e.getValue());
                    }
                    session.addDiceCalculation(dc);
                    System.out.println("Dice calculation has been added. Low: " + dc.getLowestValue()
                            + " Avg: " + dc.getAverageValue() + " High: " + dc.getHighestValue());
                }
                break;
            case 2:
                int id = readInt("Please enter the id of the dice calculation you wish to edit");
                DiceCalculation calc = session.getDiceCalculation(id);
                if (calc == null) {
                    System.out.println("Dice calculation with given id does not exist");
                } else {
                    System.out.println(calc.getId() + " " + calc.getName() + ": " + formatDice(calc.getDice()));
                    System.out.println("Please enter dice notation to update (e.g. 2d6 + 1d8 + 15)");
                    Map<Integer, Integer> parsedEdit = parseDiceString(scanner.nextLine());
                    if (parsedEdit == null) {
                        System.out.println("Invalid dice notation. Calculation not changed.");
                    } else {
                        for (Map.Entry<Integer, Integer> e : parsedEdit.entrySet()) {
                            calc.setDice(e.getKey(), e.getValue());
                        }
                        System.out.println("Dice calculation has been updated");
                    }
                }
                break;
            case 3:
                int id2 = readInt("Please enter the id of the dice calculation you wish to remove");
                if (session.getDiceCalculation(id2) == null) {
                    System.out.println("Dice calculation with given id does not exist");
                } else {
                    session.deleteDiceCalculation(id2);
                    System.out.println("Dice calculation has been removed");
                }
                break;
            case 4:
                break;
        }
    }

    // EFFECTS: parses a dice notation string (e.g. "2d6 + 1d8 + 15") into a map of die
    // sides to count; a term with no "d" is treated as a flat constant, encoded as that
    // many 1-sided dice; returns null if any term is not valid dice notation
    private Map<Integer, Integer> parseDiceString(String input) {
        Map<Integer, Integer> dice = new HashMap<>();
        for (String rawTerm : input.split("\\+")) {
            String term = rawTerm.trim().toLowerCase();
            if (term.isEmpty()) {
                return null;
            }
            if (term.contains("d")) {
                String[] parts = term.split("d");
                if (parts.length != 2 || !parts[1].matches("\\d+")) {
                    return null;
                }

                int count;
                if (parts[0].isEmpty()) {
                    count = 1;
                } else if (parts[0].matches("\\d+")) {
                    count = Integer.parseInt(parts[0]);
                } else {
                    return null;
                }

                int sides = Integer.parseInt(parts[1]);
                if (dice.containsKey(sides)) {
                    int existing = dice.get(sides);
                    dice.put(sides, existing + count);
                } else {
                    dice.put(sides, count);
                }

            } else {
                if (!term.matches("\\d+")) {
                    return null;
                }
                dice.merge(1, Integer.parseInt(term), Integer::sum);
            }
        }
        return dice;
    }

    // EFFECTS: returns a String representation of the given sides-to-count dice map
    // (e.g. "2d6, 1d8"), or an empty string if the map is empty
    private String formatDice(Map<Integer, Integer> dice) {
        if (dice.isEmpty()) {
            return "";
        }
        String result = "";
        for (Map.Entry<Integer, Integer> e : dice.entrySet()) {
            if (!result.isEmpty()) {
                result += ", ";
            }
            result += e.getValue() + "d" + e.getKey();
        }
        return result;
    }

    // REQUIRES: a session is currently selected in mainHub
    // MODIFIES: this
    // EFFECTS: displays all session notes in the current session, then processes one
    // user choice: add a new session note, edit an existing session note's title, edit
    // an existing session note's text, remove a session note, or return to the session
    // menu
    public void sessionNotes() {
        Session session = mainHub.getCurrentSession();
        System.out.println("===== Session Notes =====");
        for (SessionNote n : session.getSessionNotes()) {
            System.out.println(n.getId() + " " + n.getTitle());
        }
        System.out.println("1. Add Session Note");
        System.out.println("2. Edit Session Note Title");
        System.out.println("3. Edit Session Note Text");
        System.out.println("4. Remove Session Note");
        System.out.println("5. Back to Session");
        switch (readInt()) {
            case 1:
                System.out.println("Please enter the title of the note");
                String title = scanner.nextLine();
                System.out.println("Please enter the text of the note");
                String text = scanner.nextLine();
                SessionNote n = new SessionNote(title);
                n.setText(text);
                session.addSessionNote(n);
                System.out.println("Session note has been added");
                break;
            case 2:
                int id = readInt("Please enter the id of the session note you wish to edit");
                SessionNote note = session.getSessionNote(id);
                if (note == null) {
                    System.out.println("Session note with given id does not exist");
                } else {
                    System.out.println("Title: " + note.getTitle());
                    System.out.println("Please enter the new title");
                    note.setTitle(scanner.nextLine());
                }
                break;
            case 3:
                int id2 = readInt("Please enter the id of the session note you wish to edit");
                SessionNote note2 = session.getSessionNote(id2);
                if (note2 == null) {
                    System.out.println("Session note with given id does not exist");
                } else {
                    System.out.println("Text: " + note2.getText());
                    System.out.println("Please enter the new text");
                    note2.setText(scanner.nextLine());
                }
                break;
            case 4:
                int id3 = readInt("Please enter the id of the session note you wish to remove");
                if (session.getSessionNote(id3) == null) {
                    System.out.println("Session note with given id does not exist");
                } else {
                    session.deleteSessionNote(id3);
                    System.out.println("Session note has been removed");
                }
                break;
            case 5:
                break;
        }
    }

    // REQUIRES: a session is currently selected in mainHub
    // MODIFIES: this
    // EFFECTS: displays all additional notes in the current session, then processes one
    // user choice: add a new additional note, edit an existing additional note's title,
    // edit an existing additional note's text, remove an additional note, or return to
    // the session menu
    public void sessionAdditionalNotes() {
        Session session = mainHub.getCurrentSession();
        System.out.println("===== Additional Notes =====");
        for (AdditionalNote n : session.getAdditionalNotes()) {
            System.out.println(n.getId() + " " + n.getTitle());
        }
        System.out.println("1. Add Additional Note");
        System.out.println("2. Edit Additional Note Title");
        System.out.println("3. Edit Additional Note Text");
        System.out.println("4. Remove Additional Note");
        System.out.println("5. Back to Session");
        switch (readInt()) {
            case 1:
                System.out.println("Please enter the title of the note");
                String title = scanner.nextLine();
                System.out.println("Please enter the text of the note");
                String text = scanner.nextLine();
                AdditionalNote n = new AdditionalNote(title);
                n.setText(text);
                session.addAdditionalNote(n);
                System.out.println("Additional note has been added");
                break;
            case 2:
                int id = readInt("Please enter the id of the additional note you wish to edit");
                AdditionalNote note = session.getAdditionalNote(id);
                if (note == null) {
                    System.out.println("Additional note with given id does not exist");
                } else {
                    System.out.println("Title: " + note.getTitle());
                    System.out.println("Please enter the new title");
                    note.setTitle(scanner.nextLine());
                }
                break;
            case 3:
                int id2 = readInt("Please enter the id of the additional note you wish to edit");
                AdditionalNote note2 = session.getAdditionalNote(id2);
                if (note2 == null) {
                    System.out.println("Additional note with given id does not exist");
                } else {
                    System.out.println("Text: " + note2.getText());
                    System.out.println("Please enter the new text");
                    note2.setText(scanner.nextLine());
                }
                break;
            case 4:
                int id3 = readInt("Please enter the id of the additional note you wish to remove");
                if (session.getAdditionalNote(id3) == null) {
                    System.out.println("Additional note with given id does not exist");
                } else {
                    session.deleteAdditionalNote(id3);
                    System.out.println("Additional note has been removed");
                }
                break;
            case 5:
                break;
        }
    }

    // EFFECTS: reads a line of input and returns it parsed as an int, reprompting
    // until a valid integer is entered
    private int readInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("The number entered is invalid");
            }
        }
    }

    // EFFECTS: prints the given prompt, then reads a line of input and returns it
    // parsed as an int, reprompting until a valid integer is entered
    private int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("The number entered is invalid");
            }
        }
    }
}