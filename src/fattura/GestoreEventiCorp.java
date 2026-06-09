/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fattura;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestoreEventiCorp implements ActionListener {

    private final InterfacciaCorpo gui;
    Fattura fatturaOggetto;

    public GestoreEventiCorp(InterfacciaCorpo gui, Fattura fatturaOggetto) {
        this.gui = gui;
        this.fatturaOggetto = fatturaOggetto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.aggiungi) {
            fatturaOggetto.setArticolo(gui.articolo.getText());
            fatturaOggetto.setDescrizione(gui.descrizione.getText());
            fatturaOggetto.setQuantita(gui.qta.getText());
            fatturaOggetto.setPrezzoPezzo(gui.prezzoPezzo.getText());
            fatturaOggetto.setImporto(gui.importo.getText());
            gui.dispose();
            InterfacciaCorpo f = new InterfacciaCorpo(fatturaOggetto);
            f.setVisible(true);

        } else {
            gui.dispose();
            InterfacciaPiede f = new InterfacciaPiede(fatturaOggetto);
            f.setVisible(true);

        }
    }

}
