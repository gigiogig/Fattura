package fattura;

import java.awt.event.*;
import javax.swing.*;

public class GestoreEventiPiede implements ActionListener {

    private final InterfacciaPiede gui;
    Fattura fatturaOggetto;
    private String str;
    private double iva;
    private double totIva;
    private double tot;
    private double totImp;

    public GestoreEventiPiede(InterfacciaPiede gui, Fattura fatturaOggetto) {
        this.gui = gui;
        this.fatturaOggetto = fatturaOggetto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == gui.avanti) {

                fatturaOggetto.setTotimp();
                fatturaOggetto.setIva(gui.ivaPerc.getText().trim());
                fatturaOggetto.setTotIva(gui.totIva.getText().trim());
                fatturaOggetto.setTot(gui.tot.getText().trim());

                String nomeFilePdf = "Fattura_Numero_" + fatturaOggetto.getNumeroFattura() + ".pdf";

                GeneratorePDF.creaPdf(nomeFilePdf, fatturaOggetto);

                gui.dispose();
                JOptionPane.showMessageDialog(null, "Fattura Salvata e PDF Generato con Successo!");
                System.exit(0);

            } else {

                totImp = Double.parseDouble(fatturaOggetto.getTotimp());

                str = gui.ivaPerc.getText();
                iva = Double.parseDouble(str);
                str = "";

                totIva = (totImp / 100) * iva;
                gui.totIva.setText(Double.toString(totIva));
                
                tot = totImp + totIva;
                gui.tot.setText(Double.toString(tot));

            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(gui,
                    "Errore: Assicurati di inserire solo numeri nei campi dell'IVA e dei Totali (usa il punto per i decimali, es: 150.50)",
                    "Errore di Formato",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
