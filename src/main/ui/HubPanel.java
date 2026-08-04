package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
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

import model.Character;
import model.Hub;
import model.Session;

// Represents the hub screen with 3 smaller panels: pinned sessions, sessions,
// and characters. each panel is scrollable and displays a list of their
// respective element. Each element displays the name, timeCreated, and
// timeLastOpened on the associated session
public class HubPanel extends JPanel implements ActionListener {
    private static final String ADD_SESSION = "addSession";
    private static final String OPEN_SESSION_WITH_SESSION = "openSessionWithSession";
    private static final String OPEN_SESSION_WITH_CHARACTER = "openSessionWithCharacter";
    private static final String RENAME_SESSION = "renameSession";
    private static final String PIN_SESSION = "pinSession";
    private static final String UNPIN_SESSION = "unpinSession";
    private static final String DELETE_SESSION = "deleteSession";

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("M/d/yy, E, h:mm a",
            Locale.ENGLISH);

    private NotebookFrame frame;
    private Hub mainHub;

    private JPanel pinnedPanel;
    private JPanel sessionsPanel;
    private JPanel charactersPanel;

    private JTextField newSessionField;

    private Map<JButton, Session> sessionButtons = new HashMap<>();
    private Map<JButton, Character> characterButtons = new HashMap<>();

    private Map<JMenuItem, Session> rightClickMenuItem = new HashMap<>();

    // EFFECTS: constructs this panel and initialize all neccessary info; set its
    // innerpanels
    public HubPanel(NotebookFrame frame, Hub mainHub) {
        super(new BorderLayout());
        setOpaque(true);

        this.frame = frame;
        this.mainHub = mainHub;
        pinnedPanel = new JPanel();
        sessionsPanel = new JPanel();
        charactersPanel = new JPanel();

        add(buildTitlePanel(), BorderLayout.NORTH);
        add(buildMainPanel(), BorderLayout.CENTER);

        refresh();
    }

    // EFFECTS: builds a panel with title and a random subtitle
    private JPanel buildTitlePanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(15, 0, 15, 0));

        ImageIcon raw = new ImageIcon("./data/banner.png");
        Image scaled = raw.getImage().getScaledInstance(300, -1, Image.SCALE_SMOOTH);
        JLabel title = new JLabel(new ImageIcon(scaled));
        title.setAlignmentX(CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel(randomSubtitle(), JLabel.CENTER);
        subtitle.setFont(new Font(subtitle.getFont().getName(), Font.PLAIN, 14));
        subtitle.setAlignmentX(CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(subtitle);
        return panel;
    }

    // EFFECTS: returns a random subtitle
    private String randomSubtitle() {
        List<String> subtitles = new ArrayList<>();
        subtitles.add("Welcome back!");
        subtitles.add("Ready for another session?");
        subtitles.add("Roll for initiative!");
        subtitles.add("The dice await.");
        subtitles.add("Don't forget your spells!");
        Random random = new Random();
        return subtitles.get(random.nextInt(subtitles.size()));
    }

    // EFFECTS: builds a panel with 3 smaller panels, each representing its
    // respective element
    private JPanel buildMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 15, 20, 15));
        panel.setOpaque(false);

        JPanel columns = new JPanel(new GridLayout(1, 3, 15, 0));
        columns.setOpaque(false);
        columns.add(buildPinnedPanel());
        columns.add(buildSessionsPanel());
        columns.add(buildCharactersPanel());

        panel.add(columns, BorderLayout.CENTER);
        return panel;
    }

    // EFFECTS: builds one rounded column with the given title and content panel
    private JPanel buildColumn(String title, JPanel panel) {
        RoundedPanel column = new RoundedPanel(Palette.COLUMN);
        column.setLayout(new BorderLayout());
        column.setBorder(new EmptyBorder(12, 12, 12, 12));

        JLabel label = new JLabel(title);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 15));
        label.setBorder(new EmptyBorder(0, 4, 8, 0));
        column.add(label, BorderLayout.NORTH);

        JScrollPane scroll = frame.wrapInScroll(panel);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        panel.setOpaque(false);
        column.add(scroll, BorderLayout.CENTER);

        return column;
    }

    // EFFECTS: builds the pinned sessions panel
    private JPanel buildPinnedPanel() {
        return buildColumn("Pinned Sessions", pinnedPanel);
    }

    // EFFECTS: builds the sessions panel
    private JPanel buildSessionsPanel() {
        JPanel panel = buildColumn("Sessions", sessionsPanel);

        JPanel addPanel = new JPanel(new BorderLayout(8, 0));
        addPanel.setOpaque(false);

        newSessionField = new JTextField();

        JButton addButton = new RoundedButton("add", Palette.BUTTON, Palette.BUTTON_HOVER);
        addButton.setActionCommand(ADD_SESSION);
        addButton.addActionListener(this);

        addPanel.add(newSessionField, BorderLayout.CENTER);
        addPanel.add(addButton, BorderLayout.EAST);
        panel.add(addPanel, BorderLayout.SOUTH);

        return panel;
    }

    // EFFECTS: builds the characters panel
    private JPanel buildCharactersPanel() {
        return buildColumn("Characters", charactersPanel);
    }

    // MODIFIES: this
    // EFFECTS: refresh all panels within this panel
    public void refresh() {
        mainHub = frame.getMainHub();
        sessionButtons.clear();
        characterButtons.clear();
        rightClickMenuItem.clear();
        refreshPinned();
        refreshSessions();
        refreshCharacters();
    }

    // MODIFIES: this
    // EFFECTS: rebuilds pinnedPanel using info from hub
    private void refreshPinned() {
        pinnedPanel.removeAll();
        for (int id : mainHub.getPinnedSessionsId()) {
            Session session = mainHub.getSession(id);
            if (session != null) {
                pinnedPanel.add(buildSessionButton(session));
                pinnedPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            }
        }
        pinnedPanel.revalidate();
        pinnedPanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: rebuilds sessionsPanel using info from hub
    private void refreshSessions() {
        sessionsPanel.removeAll();
        for (Session session : mainHub.getSessions()) {
            sessionsPanel.add(buildSessionButton(session));
            sessionsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }
        sessionsPanel.revalidate();
        sessionsPanel.repaint();
    }

    // MODIFIES: this
    // EFFECTS: rebuilds charactersPanel using info from hub
    private void refreshCharacters() {
        charactersPanel.removeAll();
        for (Session session : mainHub.getSessions()) {
            for (Character character : session.getCharacters()) {
                charactersPanel.add(buildCharacterButton(character));
                charactersPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            }
        }
        charactersPanel.revalidate();
        charactersPanel.repaint();
    }

    // EFFECTS: format a button
    private JButton formatButton(JButton button, String actionCommand) {
        button.setAlignmentX(CENTER_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setActionCommand(actionCommand);
        button.addActionListener(this);

        Dimension preferred = button.getPreferredSize();
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, preferred.height));
        button.setPreferredSize(new Dimension(0, preferred.height));
        return button;
    }

    // EFFECTS: build a session button
    private JButton buildSessionButton(Session session) {
        JButton button = formatButton(
                new RoundedButton(sessionButtonText(session), Palette.BUTTON, Palette.BUTTON_HOVER),
                OPEN_SESSION_WITH_SESSION);
        button.addMouseListener(new RightClickListener(buildSessionMenu(session)));
        sessionButtons.put(button, session);
        return button;
    }

    // EFFECTS: returns the HTML-formatted label text for a session button,
    // including name, timeCreated, timeLastOpened, and character count
    private String sessionButtonText(Session session) {
        return "<html><div style='padding: 4px 8px; width: 250px; '><b>" + session.getName() + "</b><br>"
                + "Created: " + session.getTimeCreated().format(TIME_FORMAT) + "<br>"
                + "Last opened: " + session.getTimeLastOpened().format(TIME_FORMAT) + "<br>"
                + session.getCharacters().size() + " characters</div></html>";
    }

    // EFFECTS: build a character button
    private JButton buildCharacterButton(Character character) {
        JButton button = formatButton(
                new RoundedButton(characterButtonText(character), Palette.BUTTON, Palette.BUTTON_HOVER),
                OPEN_SESSION_WITH_CHARACTER);
        characterButtons.put(button, character);
        return button;
    }

    // EFFECTS: returns the HTML-formatted label text for a character button,
    // including name, class and level, session name, session's timeCreated, and
    // session's timeLastOpened
    private String characterButtonText(Character character) {
        Session session = mainHub.getSession(character.getSessionId());
        return "<html><div style='padding: 4px 8px; width: 250px; '><b>" + character.getName() + "</b><br>"
                + character.getLevelAndClass() + "<br>"
                + "Session name: " + session.getName() + "<br>"
                + "Session created: " + session.getTimeCreated().format(TIME_FORMAT) + "<br>"
                + "Session last opened: " + session.getTimeLastOpened().format(TIME_FORMAT) + "</div></html>";
    }

    // MODIFIES: this, frame, mainHub
    // EFFECTS: deals with button clicks; adds a new session using the text field's
    // contents; opens the session carried by session buttons; opens the session
    // that the character button belongs to
    @Override
    public void actionPerformed(ActionEvent e) {
        String name;
        Session session;
        frame.notifyInputChanged();
        switch (e.getActionCommand()) {
            case ADD_SESSION:
                name = newSessionField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please enter a session name.");
                    return;
                }
                mainHub.addSession(new Session(name));
                newSessionField.setText("");
                refresh();
                break;
            case OPEN_SESSION_WITH_SESSION:
                frame.showSession(sessionButtons.get(e.getSource()));
                break;
            case OPEN_SESSION_WITH_CHARACTER:
                frame.showSession(mainHub.getSession(characterButtons.get(e.getSource()).getSessionId()));
                break;
            case RENAME_SESSION:
                session = rightClickMenuItem.get(e.getSource());
                name = JOptionPane.showInputDialog(this, "New name:", session.getName(), JOptionPane.PLAIN_MESSAGE);
                if (name != null && !name.trim().isEmpty()) {
                    session.setName(name.trim());
                    refresh();
                }
                break;
            case PIN_SESSION:
                mainHub.pinSession(rightClickMenuItem.get(e.getSource()).getId());
                refresh();
                break;
            case UNPIN_SESSION:
                mainHub.unpinSession(rightClickMenuItem.get(e.getSource()).getId());
                refresh();
                break;
            case DELETE_SESSION:
                session = rightClickMenuItem.get(e.getSource());
                int choice = JOptionPane.showConfirmDialog(this,
                        "Delete \"" + session.getName() + "\"?", "Delete Session",
                        JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (choice == JOptionPane.YES_OPTION) {
                    if (mainHub.getPinnedSessionsId().contains(session.getId())) {
                        mainHub.unpinSession(session.getId());
                    }
                    mainHub.deleteSession(session.getId());
                    refresh();
                }
                break;
            default:
                break;
        }
    }

    // EFFECTS: builds the right-click menu for the given session, with rename,
    // pin or unpin (depending on the session's current state), and delete
    private JPopupMenu buildSessionMenu(Session session) {
        JPopupMenu menu = new JPopupMenu();

        menu.add(buildMenuItem("Rename", RENAME_SESSION, session));

        if (mainHub.getPinnedSessionsId().contains(session.getId())) {
            menu.add(buildMenuItem("Unpin", UNPIN_SESSION, session));
        } else {
            menu.add(buildMenuItem("Pin", PIN_SESSION, session));
        }

        menu.add(buildMenuItem("Delete", DELETE_SESSION, session));

        return menu;
    }

    // EFFECTS: builds one menu item with the given name and action command,
    // carrying the given session so the action knows which one to act on
    private JMenuItem buildMenuItem(String name, String actionCommand, Session session) {
        JMenuItem item = new JMenuItem(name);
        item.setActionCommand(actionCommand);
        item.addActionListener(this);
        rightClickMenuItem.put(item, session);
        return item;
    }
}
