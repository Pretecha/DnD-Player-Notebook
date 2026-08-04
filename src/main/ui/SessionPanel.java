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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

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

    private static final int TAB_WIDTH = 170;
    private static final int ITEM_LIST_WIDTH = 190;

    private NotebookFrame frame;
    private Session session;
    private String currentTab;
    private boolean diceMenuExpanded;

    private JLabel sessionNameLabel;
    private JPanel tabPanel;
    private JPanel itemListPanel;
    private JPanel contentPanel;
    private JTextField newItemField;

    // EFFECTS: constructs the session panel for the given frame, with the tab,
    // item list, and content area laid out from left to right
    public SessionPanel(NotebookFrame frame) {
        super(new BorderLayout());
        setOpaque(true);
        this.frame = frame;
        this.currentTab = CHARACTER_TAB;
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
    // EFFECTS: rebuilds the item list for the current tab
    private void refreshItemList() {
        itemListPanel.removeAll();
        itemListPanel.revalidate();
        itemListPanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: rebuilds the content area for the current tab and selection
    private void refreshContent() {
        contentPanel.removeAll();
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
            case DICE_ROLL_TAB:
                switchTab(e.getActionCommand());
            case DICE_CALCULATION_TAB:
                switchTab(e.getActionCommand());
            case SESSION_NOTE_TAB:
                switchTab(e.getActionCommand());
            case ADDITIONAL_NOTE_TAB:
                switchTab(e.getActionCommand());
                break;
            case ADD_ITEM:
                addItem();
                break;
            case BACK_TO_HUB:
                frame.showHub();
                break;
            default:
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: switches to the given tab and rebuilds the item list and content
    private void switchTab(String tab) {
        currentTab = tab;
        refreshItemList();
        refreshContent();
    }

    // MODIFIES: session, this, frame
    // EFFECTS: adds a new item to the current tab's list using the input typed
    // into the add field
    private void addItem() {
        String input = newItemField.getText().trim();
        if (input.isEmpty()) {
            return;
        }
        switch (currentTab) {
            case CHARACTER_TAB:
                session.addCharacter(new Character(input));
                break;
            case DICE_ROLL_TAB:
                Map<Integer, Integer> parsedRoll = parseDiceString(input);
                DiceRoll roll = new DiceRoll(
                        parsedRoll.getOrDefault(4, 0),
                        parsedRoll.getOrDefault(6, 0),
                        parsedRoll.getOrDefault(8, 0),
                        parsedRoll.getOrDefault(10, 0),
                        parsedRoll.getOrDefault(12, 0),
                        parsedRoll.getOrDefault(20, 0));
                roll.roll();
                session.addDiceRoll(roll);
                break;
            case DICE_CALCULATION_TAB:
                session.addDiceCalculation(new DiceCalculation(input));
                break;
            case SESSION_NOTE_TAB:
                session.addSessionNote(new SessionNote(input));
                break;
            case ADDITIONAL_NOTE_TAB:
                session.addAdditionalNote(new AdditionalNote(input));
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
}