/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fattura;

import java.awt.event.*;
import javax.swing.*;

public class GestoreEventiTest implements ActionListener {

    private final InterfacciaTestata gui;

    public GestoreEventiTest(InterfacciaTestata gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == gui.avanti) {
                // 1. Istanziamo il modello e inseriamo i dati estratti dalla GUI della Testata
                Fattura fatturaOggetto = new Fattura();
                fatturaOggetto.setNomeFornitore(gui.nomeForn.getText());
                fatturaOggetto.setAziendaCedente(gui.aziendaCed.getText());
                fatturaOggetto.setpIvaCedente(gui.pIvaCed.getText());

                fatturaOggetto.setNomeDestinatario(gui.nomeDest.getText());
                fatturaOggetto.setAziendaDestinataria(gui.aziendaDest.getText());
                fatturaOggetto.setpIvaDestinatario(gui.pIvaDest.getText());

                fatturaOggetto.setTipoFattura(gui.tipoFattura.getText());
                fatturaOggetto.setDataFattura(gui.dataFattura.getText());
                fatturaOggetto.setNumeroFattura(gui.numFattura.getText());

                fatturaOggetto.setTipoPagamento(gui.tipoPagamento.getText());
                fatturaOggetto.setBancaAppoggio(gui.banca.getText());

                // 2. Chiudiamo la finestra attuale
                gui.dispose();

                // 3. Apriamo il Corpo passando l'oggetto fattura (dovrai aggiornare il costruttore di InterfacciaCorpo per accettarlo)
                InterfacciaCorpo f = new InterfacciaCorpo(fatturaOggetto);
                f.setVisible(true);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore durante il passaggio dei dati: " + ex.getMessage());
        }
    }
}
