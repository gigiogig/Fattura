package fattura;

import java.awt.event.*;
import javax.swing.*;

public class GestoreEventiCorp implements ActionListener {

    private final InterfacciaCorpo gui;
    private final Fattura fatturaOggetto;

    public GestoreEventiCorp(InterfacciaCorpo gui, Fattura fatturaOggetto) {
        this.gui = gui;
        this.fatturaOggetto = fatturaOggetto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.aggiungi) { // Nome originale del pulsante: gui.aggiungi
            try {
                // Recupero dei dati usando i nomi esatti dei componenti della tua GUI
                String nome = gui.articolo.getText();
                String desc = gui.descrizione.getText();
                int qta = Integer.parseInt(gui.qta.getText().trim());
                double prezzo = Double.parseDouble(gui.prezzoPezzo.getText().trim().replace(",", "."));

                // Creazione dell'articolo e aggiunta alla lista della fattura
                Articolo articolo = new Articolo(nome, desc, qta, prezzo);
                fatturaOggetto.aggiungiArticolo(articolo);

                // Resetta i campi di testo per permettere un nuovo inserimento
                gui.articolo.setText("");
                gui.descrizione.setText("");
                gui.qta.setText("");
                gui.prezzoPezzo.setText("");

                JOptionPane.showMessageDialog(gui, "Articolo aggiunto con successo alla fattura!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui,
                        "Errore nei dati dell'articolo: controlla che Quantità e Prezzo siano numerici.",
                        "Errore di Inserimento",
                        JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == gui.avanti) { // Nome originale del pulsante: gui.avanti
            // Quando si va avanti alla schermata successiva, NON creiamo né aggiungiamo articoli.
            // Ci limitiamo a chiudere la finestra attuale e ad aprire quella del Piede della fattura.
            gui.dispose();
            InterfacciaPiede f = new InterfacciaPiede(fatturaOggetto);
            f.setVisible(true);
        }
    }
}
