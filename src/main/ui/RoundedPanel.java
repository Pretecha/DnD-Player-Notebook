package ui;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

// Represents a panel that paints itself as a rounded rectangle with no visible border
public class RoundedPanel extends JPanel {
    private static final int RADIUS = 18;

    private Color fillColor;

    // EFFECTS: constructs a rounded panel filled with the given colour
    public RoundedPanel(Color fillColor) {
        this.fillColor = fillColor;
        setOpaque(false);
    }

    // MODIFIES: this
    // EFFECTS: paints this panel as a filled rounded rectangle
    @Override
    protected void paintComponent(Graphics graphic) {
        Graphics2D panel = (Graphics2D) graphic.create();
        panel.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        panel.setColor(fillColor);
        panel.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
        panel.dispose();
        super.paintComponent(graphic);
    }
}