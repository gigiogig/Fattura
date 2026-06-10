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
            if (e.getSource() == gui.avanti) {
                // SOSTITUISCE LA VIRGOLA CON IL PUNTO PRIMA DEL PARSING
                double ivaPercentuale = Double.parseDouble(gui.ivaPerc.getText().trim().replace(",", "."));
                double totaleIva = Double.parseDouble(gui.totIva.getText().trim().replace(",", "."));
                double totaleGenerale = Double.parseDouble(gui.tot.getText().trim().replace(",", "."));

                fatturaOggetto.setIva(ivaPercentuale);
                fatturaOggetto.setTotIva(totaleIva);
                fatturaOggetto.setTot(totaleGenerale);

                String nomeFilePdf = "Fattura_Numero_" + fatturaOggetto.getNumeroFattura() + ".pdf";
                GeneratorePDF.creaPdf(nomeFilePdf, fatturaOggetto);

                gui.dispose();
                JOptionPane.showMessageDialog(null, "Fattura Salvata e PDF Generato con Successo!");
                System.exit(0);

            } else {
                double totImp = Double.parseDouble(fatturaOggetto.getTotimp().replace(",", "."));
                double ivaPercentuale = Double.parseDouble(gui.ivaPerc.getText().trim().replace(",", "."));

                double calcoloIva = (totImp / 100) * ivaPercentuale;
                String ivaFormattata = String.format(Locale.US, "%.2f", calcoloIva);
                gui.totIva.setText(ivaFormattata);

                double calcoloTotale = totImp + Double.parseDouble(ivaFormattata);
                String totaleFormattato = String.format(Locale.US, "%.2f", calcoloTotale);
                gui.tot.setText(totaleFormattato);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui,
                    "Errore: Assicurati di inserire correttamente i valori numerici.",
                    "Errore di Formato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
