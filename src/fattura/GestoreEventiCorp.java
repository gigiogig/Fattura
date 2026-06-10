package fattura;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.JOptionPane;

public class GestoreEventiCorp implements ActionListener {

    private final InterfacciaCorpo gui;
    Fattura fatturaOggetto;
    private String str;
    private double importo;
    private int qta;
    private double pp;

    public GestoreEventiCorp(InterfacciaCorpo gui, Fattura fatturaOggetto) {
        this.gui = gui;
        this.fatturaOggetto = fatturaOggetto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == gui.aggiungi) {
                String nomeArt = gui.articolo.getText().trim();
                String descArt = gui.descrizione.getText().trim();
                int quantitaCorrente = Integer.parseInt(gui.qta.getText().trim());

                String prezzoStr = gui.prezzoPezzo.getText().trim().replace(",", ".");
                double prezzoCorrente = Double.parseDouble(prezzoStr);

                double importoGrezzo = quantitaCorrente * prezzoCorrente;
                String importoFormattato = String.format(Locale.US, "%.2f", importoGrezzo);

                Articolo nuovoArticolo = new Articolo(nomeArt, descArt, quantitaCorrente, prezzoCorrente);
                fatturaOggetto.aggiungiArticolo(nuovoArticolo);

                gui.dispose();
                InterfacciaCorpo f = new InterfacciaCorpo(fatturaOggetto);
                f.setVisible(true);

            } else if (e.getSource() == gui.calcola) {
                str = gui.qta.getText().trim();
                qta = Integer.parseInt(str);
                str = "";

                str = gui.prezzoPezzo.getText().trim().replace(",", ".");
                pp = Double.parseDouble(str);
                str = "";

                importo = qta * pp;
                String importoFormattato = String.format(Locale.US, "%.2f", importo);
                gui.importo.setText(importoFormattato);

            } else {
                if (!gui.qta.getText().trim().isEmpty() && !gui.prezzoPezzo.getText().trim().isEmpty()) {
                    String nomeArt = gui.articolo.getText().trim();
                    String descArt = gui.descrizione.getText().trim();
                    int quantitaCorrente = Integer.parseInt(gui.qta.getText().trim());

                    String prezzoStr = gui.prezzoPezzo.getText().trim().replace(",", ".");
                    double prezzoCorrente = Double.parseDouble(prezzoStr);

                    Articolo ultimoArticolo = new Articolo(nomeArt, descArt, quantitaCorrente, prezzoCorrente);
                    fatturaOggetto.aggiungiArticolo(ultimoArticolo);
                }

                gui.dispose();
                InterfacciaPiede f = new InterfacciaPiede(fatturaOggetto);
                f.setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore durante l'inserimento dell'articolo: " + ex.getMessage());
        }
    }
}
