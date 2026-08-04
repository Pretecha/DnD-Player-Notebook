package ui;

import javax.swing.JPopupMenu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Represents a mouse listener that shows the given popup menu when the user
// right clicks the component it is attached to
public class RightClickListener extends MouseAdapter {
    private JPopupMenu menu;

    // EFFECTS: constructs a listener that shows the given menu on right-click
    public RightClickListener(JPopupMenu menu) {
        this.menu = menu;
    }

    // EFFECTS: shows the menu if this was a mouse press
    @Override
    public void mousePressed(MouseEvent e) {
        showIfPopupTrigger(e);
    }

    // EFFECTS: shows the menu if this was a mouse release
    @Override
    public void mouseReleased(MouseEvent e) {
        showIfPopupTrigger(e);
    }

    // EFFECTS: shows the menu at the mouse position if the given event is
    // this platform's popup trigger
    private void showIfPopupTrigger(MouseEvent e) {
        if (e.isPopupTrigger()) {
            menu.show(e.getComponent(), e.getX(), e.getY());
        }
    }
}