package bho;

import javax.swing.JFrame;

public class NavigationManager {

    private static JFrame currentFrame;

    public static void navigateTo(JFrame nextFrame) {
        if (currentFrame != null) {
            currentFrame.dispose();
        }
        currentFrame = nextFrame;
        currentFrame.setVisible(true);
        nextFrame.revalidate(); // Forza il ricalcolo del layout
        nextFrame.repaint();
    }

    // Aggiungi questo metodo per impostare la finestra corrente manualmente 
    // se la apri senza passare per navigateTo
    public static void setCurrentFrame(JFrame frame) {
        currentFrame = frame;
    }
}
