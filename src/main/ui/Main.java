package ui;

import java.io.FileNotFoundException;

import javax.swing.BorderFactory;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            try {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (Exception e) {
                // fall back to default look and feel
            }
            UIManager.put("MenuBar.background", Palette.BACKGROUND);
            UIManager.put("MenuBar.foreground", Palette.TEXT);
            UIManager.put("MenuBar.border", BorderFactory.createEmptyBorder());
            UIManager.put("MenuBar.borderColor", Palette.BACKGROUND);

            UIManager.put("Menu.background", Palette.BACKGROUND);
            UIManager.put("Menu.foreground", Palette.TEXT);
            UIManager.put("Menu.border", BorderFactory.createEmptyBorder());
            UIManager.put("Menu.selectionBackground", Palette.HIGHLIGHT);
            UIManager.put("Menu.selectionForeground", Palette.TEXT);

            UIManager.put("MenuItem.background", Palette.BUTTON);
            UIManager.put("MenuItem.foreground", Palette.TEXT);
            UIManager.put("MenuItem.border", BorderFactory.createEmptyBorder(4, 8, 4, 8));
            UIManager.put("MenuItem.selectionBackground", Palette.HIGHLIGHT);
            UIManager.put("MenuItem.selectionForeground", Palette.TEXT);

            UIManager.put("PopupMenu.background", Palette.BUTTON);
            UIManager.put("PopupMenu.border", BorderFactory.createEmptyBorder(4, 0, 4, 0));

            UIManager.put("Panel.background", Palette.BACKGROUND);
            UIManager.put("ScrollPane.background", Palette.BACKGROUND);
            UIManager.put("ScrollPane.border", BorderFactory.createEmptyBorder());
            UIManager.put("Viewport.background", Palette.BACKGROUND);

            UIManager.put("TextField.background", Palette.BUTTON);
            UIManager.put("TextField.foreground", Palette.TEXT);
            UIManager.put("TextField.border", BorderFactory.createEmptyBorder(6, 8, 6, 8));

            UIManager.put("OptionPane.background", Palette.BACKGROUND);
            UIManager.put("OptionPane.messageForeground", Palette.TEXT);

            new NotebookFrame();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
