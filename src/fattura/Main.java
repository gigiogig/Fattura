package fattura;

import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        
        //la gui e stata creata usando claude ma tutto il processo logico e stato fatto a mano
        
        UIManager.put("TextField.background",         new java.awt.Color(0xF5F7FF));
        UIManager.put("TextField.foreground",         new java.awt.Color(0x1A1A2E));
        UIManager.put("TextField.inactiveForeground", new java.awt.Color(0x8899AA));
        UIManager.put("TextField.caretForeground",    new java.awt.Color(0x1A1A2E));
        UIManager.put("TextField.selectionBackground",new java.awt.Color(0xE94560));
        UIManager.put("TextField.selectionForeground",java.awt.Color.WHITE);
        UIManager.put("TextField.disabledBackground", new java.awt.Color(0xF5F7FF));
        UIManager.put("TextField.disabledForeground", new java.awt.Color(0x1A1A2E));

        InterfacciaTestata f = new InterfacciaTestata();
        f.setVisible(true);
    }
}