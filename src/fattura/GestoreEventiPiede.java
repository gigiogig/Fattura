/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fattura;

import java.awt.event.*;
import javax.swing.*;

public class GestoreEventiPiede implements ActionListener {

    private final InterfacciaPiede gui;

    public GestoreEventiPiede(InterfacciaPiede gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource()==gui.avanti){
            
            gui.dispose();
            JOptionPane.showMessageDialog(gui, "Fattura Salvata");
        }
        
    }
}
