/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fattura;

import java.awt.event.*;
import javax.swing.*;

public class GestoreEventiPiede implements ActionListener {

    private final InterfacciaPiede gui;
    Fattura fatturaOggetto;

    public GestoreEventiPiede(InterfacciaPiede gui, Fattura fatturaOggetto) {
        this.gui = gui;
        this.fatturaOggetto = fatturaOggetto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == gui.avanti) {
            fatturaOggetto.setTotimp(gui.totImp.getText());
            fatturaOggetto.setIva(gui.ivaPerc.getText());
            fatturaOggetto.setTotIva(gui.totIva.getText());
            fatturaOggetto.setTot(gui.tot.getText());

            gui.dispose();
            JOptionPane.showMessageDialog(null, "Fattura Salvata");
            System.exit(0);
        }

    }
}
