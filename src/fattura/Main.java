package fattura;

import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        impostaStile();
        InterfacciaTestata f = new InterfacciaTestata();
        f.setVisible(true);
    }
    
    private static void impostaStile() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.out.println("Impossibile impostare lo stile di sistema");
        }
    }
    
}
