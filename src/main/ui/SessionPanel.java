package ui;

import model.AdditionalNote;
import model.Character;
import model.DiceCalculation;
import model.DiceRoll;
import model.Session;
import model.SessionNote;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

// Represents the session screen: a tab strip on the left, the list of items
// in the selected tab beside it, and an editable content area on the right
public class SessionPanel extends JPanel implements ActionListener {
    private static final String CHARACTER_TAB = "characterTab";
    private static final String DICE_TAB = "diceTab";
    private static final String DICE_ROLL_TAB = "diceRollTab";
    private static final String DICE_CALCULATION_TAB = "diceCalculationTab";
    private static final String SESSION_NOTE_TAB = "sessionNoteTab";
    private static final String ADDITIONAL_NOTE_TAB = "additionalNoteTab";
    private static final String BACK_TO_HUB = "backToHub";
    private static final String ADD_ITEM = "addItem";
    private static final String SELECT_OBJECT = "selectObject";
    private static final String DELETE = "remove";

    private static final int TAB_WIDTH = 170;
    private static final int ITEM_LIST_WIDTH = 190;

    private NotebookFrame frame;
    private Session session;
    private String currentTab;
    private int selectedId;
    private boolean diceMenuExpanded;

    private JLabel sessionNameLabel;
    private JPanel tabPanel;
    private JPanel itemListPanel;
    private JPanel contentPanel;
    private JTextField newItemField;

    private Map<JButton, Integer> itemButtons = new HashMap<>();

    private Map<JMenuItem, Integer> rightClickMenu = new HashMap<>();

    // EFFECTS: constructs the session panel for the given frame, with the tab,
    // item list, and content area laid out from left to right
    public SessionPanel(NotebookFrame frame) {
        super(new BorderLayout());
        setOpaque(true);
        this.frame = frame;
        this.currentTab = CHARACTER_TAB;
        this.selectedId = -1;
        this.diceMenuExpanded = false;

        tabPanel = new JPanel();
        itemListPanel = new JPanel();
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);

        RoundedPanel leftSide = new RoundedPanel(Palette.COLUMN);
        leftSide.setLayout(new BorderLayout(10, 0));
        leftSide.add(buildTabStrip(), BorderLayout.WEST);
        leftSide.add(buildItemListPanel(), BorderLayout.CENTER);

        add(leftSide, BorderLayout.WEST);
        add(buildContentPanel(), BorderLayout.CENTER);
    }

    // EFFECTS: builds the leftmost panel: the back button, session name, and
    // the tab buttons
    private JPanel buildTabStrip() {
        RoundedPanel leftPanel = new RoundedPanel(Palette.BUTTON);
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(new EmptyBorder(12, 10, 12, 10));
        leftPanel.setPreferredSize(new Dimension(TAB_WIDTH, 0));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        top.add(buildSidebarButton("< Hub", BACK_TO_HUB));
        top.add(Box.createRigidArea(new Dimension(0, 10)));

        sessionNameLabel = new JLabel("");
        sessionNameLabel.setFont(new Font(sessionNameLabel.getFont().getName(), Font.BOLD, 16));
        sessionNameLabel.setBorder(new EmptyBorder(0, 4, 10, 0));
        sessionNameLabel.setAlignmentX(LEFT_ALIGNMENT);
        top.add(sessionNameLabel);

        leftPanel.add(top, BorderLayout.NORTH);

        tabPanel.setOpaque(false);
        tabPanel.setLayout(new BoxLayout(tabPanel, BoxLayout.Y_AXIS));
        leftPanel.add(tabPanel, BorderLayout.CENTER);

        return leftPanel;
    }

    // EFFECTS: builds one big button with the given label and action command
    private JButton buildSidebarButton(String label, String actionCommand) {
        RoundedButton button = new RoundedButton(label, Palette.BUTTON, Palette.BUTTON_HOVER);
        button.setFont(new Font(button.getFont().getName(), Font.BOLD, 13));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        button.setAlignmentX(LEFT_ALIGNMENT);
        Dimension preferred = button.getPreferredSize();
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferred.height));
        button.setPreferredSize(new Dimension(0, preferred.height));
        return button;
    }

    // EFFECTS: builds one small button with the given label and action command; for
    // dropdown menu
    private JButton buildSubTabButton(String label, String actionCommand) {
        JButton button = buildSidebarButton("   " + label, actionCommand);
        return button;
    }

    // EFFECTS: builds the middle panel: an add row on top, and the scrollable
    // list of items in the current tab below it
    private JPanel buildItemListPanel() {
        RoundedPanel panel = new RoundedPanel(Palette.COLUMN);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(12, 10, 12, 10));
        panel.setPreferredSize(new Dimension(ITEM_LIST_WIDTH, 0));

        panel.add(buildAddRow(), BorderLayout.NORTH);

        JScrollPane scroll = frame.wrapInScroll(itemListPanel);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        itemListPanel.setOpaque(false);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // EFFECTS: builds the add row: a text field and add button, separated
    // from the item list below by a gap
    private JPanel buildAddRow() {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(false);
        row.setBorder(new EmptyBorder(0, 0, 12, 0));

        newItemField = new JTextField();
        row.add(newItemField, BorderLayout.CENTER);

        RoundedButton addButton = new RoundedButton("+", Palette.BUTTON, Palette.BUTTON_HOVER);
        addButton.setActionCommand(ADD_ITEM);
        addButton.addActionListener(this);
        addButton.setHorizontalAlignment(SwingConstants.CENTER);
        addButton.setFont(new Font(addButton.getFont().getName(), Font.BOLD, 15));
        addButton.setPreferredSize(new Dimension(32, 32));
        addButton.setMinimumSize(new Dimension(32, 32));
        addButton.setMargin(new Insets(0, 0, 0, 0));
        row.add(addButton, BorderLayout.EAST);

        return row;
    }

    // EFFECTS: builds the content panel on the right
    private JPanel buildContentPanel() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.setBorder(new EmptyBorder(12, 12, 12, 12));
        wrapper.add(contentPanel, BorderLayout.CENTER);
        return wrapper;
    }

    // MODIFIES: this
    // EFFECTS: sets the session displayed by this screen, resets to the
    // character tab, and rebuilds the screen
    public void setSession(Session session) {
        this.session = session;
        this.currentTab = CHARACTER_TAB;
        this.selectedId = -1;
        this.diceMenuExpanded = false;
        refresh();
    }

    // MODIFIES: this
    // EFFECTS: rebuilds the tab strip, the item list, and the content area
    public void refresh() {
        sessionNameLabel.setText(session.getName());
        refreshTabStrip();
        refreshItemList();
        refreshContent();
    }

    // MODIFIES: this
    // EFFECTS: rebuilds the tab buttons
    private void refreshTabStrip() {
        tabPanel.removeAll();

        tabPanel.add(buildSidebarButton("Characters", CHARACTER_TAB));

        String diceLabel = "Dice  >";
        if (diceMenuExpanded) {
            diceLabel = "Dice  v";
            tabPanel.add(buildSidebarButton(diceLabel, DICE_TAB));
            tabPanel.add(buildSubTabButton("Rolls", DICE_ROLL_TAB));
            tabPanel.add(buildSubTabButton("Calculations", DICE_CALCULATION_TAB));
        } else {
            tabPanel.add(buildSidebarButton(diceLabel, DICE_TAB));
        }

        tabPanel.add(buildSidebarButton("Session Notes", SESSION_NOTE_TAB));
        tabPanel.add(buildSidebarButton("Extra Notes", ADDITIONAL_NOTE_TAB));

        tabPanel.revalidate();
        tabPanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: rebuilds the item list for the current tab and selects its
    // newest item
    private void refreshItemList() {
        itemListPanel.removeAll();
        itemButtons.clear();
        rightClickMenu.clear();

        switch (currentTab) {
            case CHARACTER_TAB:
                addItemButtons(session.getCharacters(), Character::getName, Character::getId);
                break;
            case DICE_ROLL_TAB:
                addItemButtons(session.getDiceRolls(), r -> formatDice(r.getDice()) + " = " + r.getResult(),
                        DiceRoll::getId);
                break;
            case DICE_CALCULATION_TAB:
                addItemButtons(session.getDiceCalculations(), DiceCalculation::getName, DiceCalculation::getId);
                break;
            case SESSION_NOTE_TAB:
                addItemButtons(session.getSessionNotes(), SessionNote::getTitle, SessionNote::getId);
                break;
            case ADDITIONAL_NOTE_TAB:
                addItemButtons(session.getAdditionalNotes(), AdditionalNote::getTitle, AdditionalNote::getId);
                break;
            default:
                break;
        }

        itemListPanel.revalidate();
        itemListPanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: adds one item button per item in the given list, newest first
    private <T> void addItemButtons(List<T> items, Function<T, String> getName,
            Function<T, Integer> getId) {
        for (int i = items.size() - 1; i >= 0; i--) {
            T item = items.get(i);
            itemListPanel.add(buildItemButton(getName.apply(item), getId.apply(item)));
            itemListPanel.add(Box.createRigidArea(new Dimension(0, 4)));
        }
    }

    // EFFECTS: builds one item list button with the given label, carrying the
    // id of the item it represents
    private JButton buildItemButton(String name, int id) {
        JButton button = buildSidebarButton(name, SELECT_OBJECT);
        button.addMouseListener(new RightClickListener(buildMenu(id)));
        itemButtons.put(button, id);
        return button;
    }

    // MODIFIES: this
    // EFFECTS: rebuilds the content area for the current tab and selection
    private void refreshContent() {
        contentPanel.removeAll();
        switch (currentTab) {
            case CHARACTER_TAB:
                if (session.getCharacter(selectedId) != null) {
                    contentPanel.add(buildCharacterSheet(), BorderLayout.NORTH);
                }
                break;
            case DICE_ROLL_TAB:
                if (session.getDiceRoll(selectedId) != null) {
                    // contentPanel.add(buildDiceRollPanel(), BorderLayout.NORTH);
                }
                break;
            case DICE_CALCULATION_TAB:
                if (session.getDiceCalculation(selectedId) != null) {
                    // contentPanel.add(buildDiceCalculationPanel(), BorderLayout.NORTH);
                }
                break;
            case SESSION_NOTE_TAB:
                if (session.getSessionNote(selectedId) != null) {
                    // contentPanel.add(buildSessionNotesPanel(), BorderLayout.NORTH);
                }
                break;
            case ADDITIONAL_NOTE_TAB:
                if (session.getAdditionalNote(selectedId) != null) {
                    // contentPanel.add(buildAdditionalNotesPanel(), BorderLayout.NORTH);
                }
                break;
            default:
                break;
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // MODIFIES: this, frame
    // EFFECTS: responds to a button click: switches tabs, expands or collapses
    // the dice tab, adds an item, or returns to the hub
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case DICE_TAB:
                diceMenuExpanded = !diceMenuExpanded;
                refreshTabStrip();
                break;
            case CHARACTER_TAB:
                switchTab(e.getActionCommand());
                break;
            case DICE_ROLL_TAB:
                switchTab(e.getActionCommand());
                break;
            case DICE_CALCULATION_TAB:
                switchTab(e.getActionCommand());
                break;
            case SESSION_NOTE_TAB:
                switchTab(e.getActionCommand());
                break;
            case ADDITIONAL_NOTE_TAB:
                switchTab(e.getActionCommand());
                break;
            case SELECT_OBJECT:
                selectedId = itemButtons.get(e.getSource());
                refreshContent();
                break;
            case ADD_ITEM:
                addItem();
                break;
            case BACK_TO_HUB:
                frame.showHub();
                break;
            case DELETE:
                int choice = JOptionPane.showConfirmDialog(this,
                        "Delete this item? (Not recoverable)", "Delete Item",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (choice == JOptionPane.YES_OPTION) {
                    deleteItem(rightClickMenu.get(e.getSource()));
                    refresh();
                }
                break;
            default:
                break;
        }
    }

    // MODIFIES: session, this
    // EFFECTS: deletes the item with the currently selected id from the current
    // tab's list
    private void deleteItem(int id) {
        switch (currentTab) {
            case CHARACTER_TAB:
                session.deleteCharacter(id);
                break;
            case DICE_ROLL_TAB:
                session.deleteDiceRoll(id);
                break;
            case DICE_CALCULATION_TAB:
                session.deleteDiceCalculation(id);
                break;
            case SESSION_NOTE_TAB:
                session.deleteSessionNote(id);
                break;
            case ADDITIONAL_NOTE_TAB:
                session.deleteAdditionalNote(id);
                break;
            default:
                break;
        }
        refreshItemList();
        refreshContent();
    }

    // MODIFIES: this
    // EFFECTS: switches to the given tab and rebuilds the item list and content
    private void switchTab(String tab) {
        currentTab = tab;
        selectedId = -1;
        refreshItemList();
        refreshContent();
    }

    // MODIFIES: session, this, frame
    // EFFECTS: adds a new item to the current tab's list using the input typed
    // into the add field
    private void addItem() {
        String input = newItemField.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name.");
            return;
        }
        switch (currentTab) {
            case CHARACTER_TAB:
                Character character = new Character(input);
                session.addCharacter(character);
                selectedId = character.getId();
                break;
            case DICE_ROLL_TAB:
                Map<Integer, Integer> parsedRoll = parseDiceString(input);
                if (parsedRoll == null) {
                    break;
                }
                DiceRoll roll = new DiceRoll(
                        parsedRoll.getOrDefault(4, 0),
                        parsedRoll.getOrDefault(6, 0),
                        parsedRoll.getOrDefault(8, 0),
                        parsedRoll.getOrDefault(10, 0),
                        parsedRoll.getOrDefault(12, 0),
                        parsedRoll.getOrDefault(20, 0));
                roll.roll();
                session.addDiceRoll(roll);
                selectedId = roll.getId();
                break;
            case DICE_CALCULATION_TAB:
                DiceCalculation diceCalculation = new DiceCalculation(input);
                session.addDiceCalculation(diceCalculation);
                selectedId = diceCalculation.getId();
                break;
            case SESSION_NOTE_TAB:
                SessionNote sessionNote = new SessionNote(input);
                session.addSessionNote(sessionNote);
                selectedId = sessionNote.getId();
                break;
            case ADDITIONAL_NOTE_TAB:
                AdditionalNote additionalNote = new AdditionalNote(input);
                session.addAdditionalNote(additionalNote);
                selectedId = additionalNote.getId();
                break;
            default:
                break;
        }

        newItemField.setText("");
        refreshItemList();
        frame.notifyInputChanged();
    }

    // EFFECTS: parses a dice notation string (e.g. "2d6 + 1d8 + 15") into a map of
    // die
    // sides to count; a term with no "d" is treated as a flat constant, encoded as
    // that many 1-sided dice; returns null if any term is not valid dice notation
    @SuppressWarnings("methodlength") // ta Marcus said this was ok
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

    // Character tab

    // REQUIRES: a character is currently selected
    // EFFECTS: builds the editable character sheet for the selected character
    private JPanel buildCharacterSheet() {
        JPanel sheet = new JPanel();
        sheet.setOpaque(false);
        sheet.setLayout(new BoxLayout(sheet, BoxLayout.Y_AXIS));

        sheet.add(buildHeaderPanel());
        sheet.add(Box.createRigidArea(new Dimension(0, 15)));
        sheet.add(buildAbilityPanel());
        return sheet;
    }

    // REQUIRES: a character is currently selected
    // EFFECTS: builds the header: the name, class and level, race, alignment, and
    // background
    private JPanel buildHeaderPanel() {
        Character character = session.getCharacter(selectedId);
        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.setOpaque(false);
        header.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        header.add(buildNamePanel(), BorderLayout.WEST);

        JPanel infoGrid = new JPanel(new GridLayout(2, 2, 8, 8));
        infoGrid.setOpaque(false);
        infoGrid.add(buildFieldPanel("CLASS & LEVEL", character.getLevelAndClass(),
                character::setLevelAndClass));
        infoGrid.add(buildFieldPanel("RACE", character.getRace(), character::setRace));
        infoGrid.add(buildFieldPanel("ALIGNMENT", character.getAlignment(), character::setAlignment));
        infoGrid.add(buildFieldPanel("BACKGROUND", character.getBackground(),
                character::setBackground));
        header.add(infoGrid, BorderLayout.CENTER);

        return header;
    }

    // REQUIRES: a character is currently selected
    // EFFECTS: builds the large panel holding the character's name
    private JPanel buildNamePanel() {
        Character character = session.getCharacter(selectedId);
        RoundedPanel panel = new RoundedPanel(Palette.BUTTON);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(12, 14, 12, 14));
        panel.setPreferredSize(new Dimension(240, 0));

        JLabel caption = new JLabel("CHARACTER NAME");
        caption.setFont(new Font(caption.getFont().getName(), Font.PLAIN, 10));
        panel.add(caption, BorderLayout.NORTH);

        JTextField field = new JTextField(character.getName());
        field.setFont(new Font(field.getFont().getName(), Font.BOLD, 20));
        field.setBorder(null);
        field.setOpaque(false);
        attachTextListener(field, character::setName);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    // EFFECTS: builds one captioned panel containing an editable field that
    // applies its contents through the given setter as the user types
    private JPanel buildFieldPanel(String caption, String value, TextSetter setter) {
        RoundedPanel panel = new RoundedPanel(Palette.BUTTON);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel captionLabel = new JLabel(caption);
        captionLabel.setFont(new Font(captionLabel.getFont().getName(), Font.PLAIN, 10));
        panel.add(captionLabel, BorderLayout.NORTH);

        JTextField field = new JTextField(value);
        field.setBorder(null);
        field.setOpaque(false);
        attachTextListener(field, setter);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    // REQUIRES: a character is currently selected
    // EFFECTS: builds the row of six ability score panels
    private JPanel buildAbilityPanel() {
        Character character = session.getCharacter(selectedId);
        JPanel panel = new JPanel(new GridLayout(1, 6, 8, 0));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        panel.add(buildAbilityBox("STRENGTH", character.getStr(), character::setStr));
        panel.add(buildAbilityBox("DEXTERITY", character.getDex(), character::setDex));
        panel.add(buildAbilityBox("CONSTITUTION", character.getCon(), character::setCon));
        panel.add(buildAbilityBox("INTELLIGENCE", character.getIntel(), character::setIntel));
        panel.add(buildAbilityBox("WISDOM", character.getWis(), character::setWis));
        panel.add(buildAbilityBox("CHARISMA", character.getCha(), character::setCha));
        return panel;
    }

    // EFFECTS: builds one ability score panel with the ability's name and an
    // editable field that applies its contents through the given setter
    private JPanel buildAbilityBox(String abilityName, int score, IntSetter setter) {
        RoundedPanel panel = new RoundedPanel(Palette.BUTTON);
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 6, 10, 6));

        JLabel nameLabel = new JLabel(abilityName, JLabel.CENTER);
        nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 9));
        panel.add(nameLabel, BorderLayout.NORTH);

        JTextField field = new JTextField(String.valueOf(score));
        field.setHorizontalAlignment(JTextField.CENTER);
        field.setFont(new Font(field.getFont().getName(), Font.BOLD, 22));
        field.setBorder(null);
        field.setOpaque(false);
        attachIntListener(field, setter);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    // MODIFIES: field
    // EFFECTS: makes the given field apply its contents through the given
    // setter whenever it changes
    private void attachTextListener(JTextField field, TextSetter setter) {
        field.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
            setter.apply(field.getText());
            onFieldEdited();
        }));
    }

    // MODIFIES: field
    // EFFECTS: makes the given field apply its contents as an integer through
    // the given setter whenever it changes, ignoring non-integer values
    private void attachIntListener(JTextField field, IntSetter setter) {
        field.getDocument().addDocumentListener(new SimpleDocumentListener(() -> {
            try {
                setter.apply(Integer.parseInt(field.getText().trim()));
                onFieldEdited();
            } catch (NumberFormatException e) {
                // don't change
            }
        }));
    }

    // MODIFIES: this, frame
    // EFFECTS: updates the item list labels and restarts the autosave countdown
    private void onFieldEdited() {
        refreshItemList();
        frame.notifyInputChanged();
    }

    // right click menu

    // EFFECTS: builds the right-click menu for the given character
    private JPopupMenu buildMenu(int id) {
        JPopupMenu menu = new JPopupMenu();

        menu.add(buildMenuItem("Delete", DELETE, id));

        return menu;
    }

    // EFFECTS: builds one menu item with the given name and action command,
    // carrying the given character so the action knows which one to act on
    private JMenuItem buildMenuItem(String name, String actionCommand, int id) {
        JMenuItem item = new JMenuItem(name);
        item.setActionCommand(actionCommand);
        item.addActionListener(this);
        rightClickMenu.put(item, id);
        return item;
    }

    // Util

    // Represents an action that applies a new text value to the model
    private interface TextSetter {
        void apply(String value);
    }

    // Represents an action that applies a new integer value to the model
    private interface IntSetter {
        void apply(int value);
    }

    // Represents a document listener that runs the same action for every kind
    // of document change
    private static class SimpleDocumentListener implements DocumentListener {
        private Runnable action;

        // EFFECTS: constructs a listener that runs the given action on any change
        SimpleDocumentListener(Runnable action) {
            this.action = action;
        }

        // EFFECTS: runs the action when text is inserted
        @Override
        public void insertUpdate(DocumentEvent e) {
            action.run();
        }

        // EFFECTS: runs the action when text is removed
        @Override
        public void removeUpdate(DocumentEvent e) {
            action.run();
        }

        // EFFECTS: runs the action when the document changes
        @Override
        public void changedUpdate(DocumentEvent e) {
            action.run();
        }
    }

}