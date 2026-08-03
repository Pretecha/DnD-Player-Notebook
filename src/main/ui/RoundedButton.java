package ui;

import javax.swing.JButton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

// Represents a flat button that paints itself as a filled rounded rectangle
// with no visible border, and lightens when the mouse is over it
public class RoundedButton extends JButton {
    private static final int RADIUS = 14;

    private Color normalColor;
    private Color hoverColor;

    // EFFECTS: constructs a rounded button with the given label and colours
    public RoundedButton(String text, Color normalColor, Color hoverColor) {
        super(text);
        this.normalColor = normalColor;
        this.hoverColor = hoverColor;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
    }

    // MODIFIES: this
    // EFFECTS: paints this button as a filled rounded rectangle, using the
    // hover colour while the mouse is over it
    @Override
    protected void paintComponent(Graphics graphic) {
        Graphics2D button = (Graphics2D) graphic.create();
        button.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if (getModel().isRollover()) {
            button.setColor(hoverColor);
        } else {
            button.setColor(normalColor);
        }
        button.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
        button.dispose();
        super.paintComponent(graphic);
    }
}