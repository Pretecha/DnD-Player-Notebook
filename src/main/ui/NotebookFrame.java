package ui;

import model.*;
import model.Character;

import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

import persistence.JsonRead;
import persistence.JsonWrite;
import java.io.*;

import javax.swing.*;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

// Represents the dnd player notebook application using java swing
public class NotebookFrame extends JFrame implements ActionListener {
    private Hub mainHub;

    private static final String DATA = "./data/storeddata.json";
    private JsonWrite jsonWriter;
    private JsonRead jsonReader;

    public static final String HUB_STATE = "hub";
    public static final String SESSION_STATE = "session";
    private CardLayout panelManager;
    private JPanel panelCollection;
    private HubPanel hubPanel;
    private SessionPanel sessionPanel;

    private Timer autosaveTimer;

    // EFFECTS: constructs a frame with title, initialize the jsonWriter and
    // jsonReader, initializes the main frame fields using methods, and run the app
    public NotebookFrame() throws FileNotFoundException {
        super("DnD Player Notebook");
        jsonWriter = new JsonWrite(DATA);
        jsonReader = new JsonRead(DATA);
        readHub();
        setUpAutosaveTimer();

        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        setJMenuBar(buildMenuBar());

        panelManager = new CardLayout();
        panelCollection = new JPanel(panelManager);
        hubPanel = new HubPanel(this, mainHub);
        sessionPanel = new SessionPanel(this);
        panelCollection.add(hubPanel, HUB_STATE);
        panelCollection.add(sessionPanel, SESSION_STATE);
        add(panelCollection);

        setVisible(true);

        hubPanel.refresh();
    }

    // MODIFIES: this
    // EFFECTS: read hub from DATA
    public void readHub() {
        try {
            mainHub = jsonReader.read();
            if (hubPanel != null) {
                hubPanel.refresh();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to read file from " + DATA, "Read Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: jsonWriter
    // EFFECTS: save hub to DATA
    public void saveHub() {
        try {
            jsonWriter.open();
            jsonWriter.save(mainHub);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to save file at " + DATA, "Save Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // EFFECTS: constructs a timer that saves the hub 4 seconds after being
    // (re)started, and hasn't fired yet
    private void setUpAutosaveTimer() {
        autosaveTimer = new Timer(4000, e -> saveHub());
        autosaveTimer.setRepeats(false);
    }

    // MODIFIES: this
    // EFFECTS: restarts the autosave countdown; if called again before the
    // countdown finishes, the previous countdown is replaced by a new one
    public void notifyInputChanged() {
        autosaveTimer.restart();
    }

    // MODIFIES: this
    // EFFECTS: interupts the window-closing event to save before exiting; all
    // other window events are passed through normally
    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            saveHub();
            System.exit(0);
        } else {
            super.processWindowEvent(e);
        }
    }

    // MODIFIES: this
    // EFFECTS: action listener for all button presses
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "save":
                saveHub();
                JOptionPane.showMessageDialog(this, "Saved to " + DATA);
                break;
            case "load":
                readHub();
                JOptionPane.showMessageDialog(this, "Loaded from " + DATA);
                break;
        }
    }

    // EFFECTS: builds the menu bar with (File -> Save) and (File -> Load)
    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setActionCommand("save");
        saveMenuItem.addActionListener(this);

        JMenuItem loadMenuItem = new JMenuItem("Load");
        loadMenuItem.setActionCommand("load");
        loadMenuItem.addActionListener(this);

        fileMenu.add(saveMenuItem);
        fileMenu.add(loadMenuItem);
        menuBar.add(fileMenu);
        return menuBar;
    }

    // EFFECTS: formats the given panel vertically and wraps it in a scroll pane
    // with no visible scroll bar
    public JScrollPane wrapInScroll(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        return scrollPane;
    }

    public Hub getMainHub() {
        return mainHub;
    }

    // EFFECTS: sets the current panel to session and change session open time.
    public void showSession(Session session) {
        session.markOpened();
        sessionPanel.setSession(session);
        panelManager.show(panelCollection, SESSION_STATE);
    }

    // EFFECTS: sets the current panel to hub
    public void showHub() {
        panelManager.show(panelCollection, HUB_STATE);
    }
}