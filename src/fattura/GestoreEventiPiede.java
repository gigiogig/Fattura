package fattura;

import java.awt.event.*;
import java.util.Locale;
import javax.swing.*;

public class GestoreEventiPiede implements ActionListener {

    private final InterfacciaPiede gui;
    private final Fattura fatturaOggetto;

    public GestoreEventiPiede(InterfacciaPiede gui, Fattura fatturaOggetto) {
        this.gui = gui;
        this.fatturaOggetto = fatturaOggetto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Estraiamo l'aliquota IVA gestendo il caso in cui ci sia ancora il placeholder
            String ivaStr = gui.ivaPerc.getText().trim().replace(",", ".");
            if (ivaStr.isEmpty() || ivaStr.equals("es. 22")) {
                ivaStr = "0.0"; // Valore di default se non inserito o non valido
            }
            double ivaPercentuale = Double.parseDouble(ivaStr);
            double totImp = Double.parseDouble(fatturaOggetto.getTotimp().replace(",", "."));

            // Calcolo preventivo automatico dei totali per evitare stringhe vuote
            double calcoloIva = (totImp / 100) * ivaPercentuale;
            double calcoloTotale = totImp + calcoloIva;

            if (e.getSource() == gui.avanti) {
                // Salviamo i dati corretti nel modello
                fatturaOggetto.setIva(ivaPercentuale);
                fatturaOggetto.setTotIva(calcoloIva); // Passiamo direttamente il valore calcolato numerico
                fatturaOggetto.setTot(calcoloTotale);

                // Sanatizzazione del nome del file (sostituisce / e \ con trattini per evitare errori di percorso)
                String numeroFatturaPulito = fatturaOggetto.getNumeroFattura().replace("/", "-").replace("\\", "-");
                String nomeFilePdf = "Fattura_Numero_" + numeroFatturaPulito + ".pdf";

                // Chiamata di generazione del PDF
                GeneratorePDF.creaPdf(nomeFilePdf, fatturaOggetto);

                gui.dispose();
                JOptionPane.showMessageDialog(null, "Fattura Salvata e PDF Generato con Successo!\nFile creato: " + nomeFilePdf);
                System.exit(0);

            } else {
                // Se l'utente clicca semplicemente sul tasto "= CALCOLA" aggiorniamo la GUI
                String ivaFormattata = String.format(Locale.US, "%.2f", calcoloIva);
                gui.totIva.setText(ivaFormattata + " €");

                String totaleFormattato = String.format(Locale.US, "%.2f", calcoloTotale);
                gui.tot.setText(totaleFormattato + " €");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui,
                    "Errore: Assicurati di inserire correttamente l'aliquota IVA numerica.",
                    "Errore di Formato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
