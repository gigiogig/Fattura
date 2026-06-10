package fattura;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                fatturaOggetto.setArticolo(gui.articolo.getText());
                fatturaOggetto.setDescrizione(gui.descrizione.getText());

                fatturaOggetto.setQuantita(qta);

                fatturaOggetto.setPrezzoPezzo(pp);

                fatturaOggetto.setImporto(importo);
                gui.dispose();
                InterfacciaCorpo f = new InterfacciaCorpo(fatturaOggetto);
                f.setVisible(true);

            } else if (e.getSource() == gui.calcola) {
                str = gui.qta.getText();
                qta = Integer.parseInt(str);
                str = "";

                str = gui.prezzoPezzo.getText();
                pp = Double.parseDouble(str);
                str = "";

                importo = qta * pp;
                gui.importo.setText(Double.toString(importo));

            } else {
                gui.dispose();
                InterfacciaPiede f = new InterfacciaPiede(fatturaOggetto);
                f.setVisible(true);

            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore durante il passaggio dei dati: " + ex.getMessage());
        }
    }

}
