package bho;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("sun.awt.X11.XToolkit")
                .setLevel(java.util.logging.Level.SEVERE);

        // Suggerisce la dark title bar su macOS/Windows 11 moderni
        System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");

        SwingUtilities.invokeLater(() -> {
            NavigationManager.navigateTo(new InterfacciaLogin());
        });
    }
}
