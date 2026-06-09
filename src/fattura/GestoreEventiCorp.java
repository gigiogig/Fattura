/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fattura;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author debian
 */
public class GestoreEventiCorp implements ActionListener{

    private final InterfacciaCorpo gui;

    public GestoreEventiCorp(InterfacciaCorpo gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == gui.aggiungi){
            
        }else{
            
        }
    }
    
    
}
