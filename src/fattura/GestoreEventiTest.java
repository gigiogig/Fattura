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
                fatturaOggetto.setNomeFornitore(gui.nomeForn.getText().trim());
                fatturaOggetto.setAziendaCedente(gui.aziendaCed.getText().trim());
                fatturaOggetto.setpIvaCedente(gui.pIvaCed.getText().trim());

                fatturaOggetto.setNomeDestinatario(gui.nomeDest.getText().trim());
                fatturaOggetto.setAziendaDestinataria(gui.aziendaDest.getText().trim());
                fatturaOggetto.setpIvaDestinatario(gui.pIvaDest.getText().trim());

                fatturaOggetto.setTipoFattura(gui.tipoFattura.getText().trim());
                fatturaOggetto.setDataFattura(gui.dataFattura.getText().trim());
                fatturaOggetto.setNumeroFattura(gui.numFattura.getText().trim());

                fatturaOggetto.setTipoPagamento(gui.tipoPagamento.getText().trim());
                fatturaOggetto.setBancaAppoggio(gui.banca.getText().trim());

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
