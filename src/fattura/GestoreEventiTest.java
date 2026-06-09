/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fattura;

import java.awt.event.*;

public class GestoreEventiTest implements ActionListener {

    private final InterfacciaTestata gui;

    public GestoreEventiTest(InterfacciaTestata gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==gui.avanti){
            
            gui.dispose();
            InterfacciaCorpo f = new InterfacciaCorpo();
            f.setVisible(true);
            
        }
        
    }
}
