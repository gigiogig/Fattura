package fattura;

import java.awt.event.*;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class GestoreEventiCorp implements ActionListener {

    private final InterfacciaCorpo gui;
    private final Fattura fatturaOggetto;

    public GestoreEventiCorp(InterfacciaCorpo gui, Fattura fatturaOggetto) {
        this.gui = gui;
        this.fatturaOggetto = fatturaOggetto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == gui.calcola) {

            try {
                int qta = Integer.parseInt(gui.qta.getText().trim());
                double prezzo = Double.parseDouble(
                        gui.prezzoPezzo.getText().trim().replace(",", "."));
                String risultato = String.format(Locale.US, "%.2f", qta * prezzo);
                gui.importo.setText(risultato);
                gui.importo.setForeground(new java.awt.Color(0x0F3460));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui,
                        "Inserisci valori numerici validi per Quantità e Prezzo.",
                        "Errore", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == gui.aggiungi) {

            try {
                String nome = gui.articolo.getText().trim();
                String desc = gui.descrizione.getText().trim();
                int qta = Integer.parseInt(gui.qta.getText().trim());
                double prezzo = Double.parseDouble(
                        gui.prezzoPezzo.getText().trim().replace(",", "."));

                Articolo articolo = new Articolo(nome, desc, qta, prezzo);
                fatturaOggetto.aggiungiArticolo(articolo);

                DefaultTableModel model = (DefaultTableModel) gui.tabellaArticoli.getModel();
                model.addRow(new Object[]{
                    nome, desc, qta,
                    String.format("%.2f \u20ac", prezzo),
                    String.format("%.2f \u20ac", qta * prezzo)
                });
                gui.labelContatore.setText(
                        "ARTICOLI AGGIUNTI  (" + fatturaOggetto.getListaArticoli().size() + ")");

                gui.articolo.setText("");
                gui.descrizione.setText("");
                gui.qta.setText("");
                gui.prezzoPezzo.setText("");
                gui.importo.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui,
                        "Controlla che Quantità e Prezzo siano numerici.",
                        "Errore di Inserimento", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == gui.avanti) {
            gui.dispose();
            InterfacciaPiede f = new InterfacciaPiede(fatturaOggetto);
            f.setVisible(true);
        }
    }
}
