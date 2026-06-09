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
            try {
                // Preleva i testi dai campi e inviali all'oggetto (il parsing avviene dentro i setter)
                fatturaOggetto.setTotimp(gui.totImp.getText().trim());
                fatturaOggetto.setIva(gui.ivaPerc.getText().trim());
                fatturaOggetto.setTotIva(gui.totIva.getText().trim());
                fatturaOggetto.setTot(gui.tot.getText().trim());

                // Specifica il nome del file PDF da salvare (es. sul desktop o nella cartella del progetto)
                String nomeFilePdf = "Fattura_Numero_" + fatturaOggetto.getNumeroFattura() + ".pdf";

                // CHIAMATA FONDAMENTALE AL GENERATORE PDF
                GeneratorePDF.creaPdf(nomeFilePdf, fatturaOggetto);

                gui.dispose();
                JOptionPane.showMessageDialog(null, "Fattura Salvata e PDF Generato con Successo!");
                System.exit(0);

            } catch (NumberFormatException ex) {
                // Questo evita il crash se l'utente inserisce lettere, simboli € o lascia vuoto
                JOptionPane.showMessageDialog(gui, 
                    "Errore: Assicurati di inserire solo numeri nei campi dell'IVA e dei Totali (usa il punto per i decimali, es: 150.50)", 
                    "Errore di Formato", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}