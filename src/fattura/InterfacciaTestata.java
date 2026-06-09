/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fattura;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class InterfacciaTestata extends JFrame {

    private JTextField nomeForn;
    private JTextField nomeDest;
    private JTextField aziendaCed;
    private JTextField aziendaDest;
    private JTextField pIvaDest;
    private JTextField pIvaCed;
    private JTextField numFattura;
    private JTextField dataFattura;
    private JTextField tipoFattura;
    private JTextField tipoPagamento;
    private JTextField banca;
    JButton avanti;

    public InterfacciaTestata() {

        setTitle("Fattura");

        nomeForn = new JTextField(15);
        nomeDest = new JTextField(15);
        aziendaCed = new JTextField(15);
        aziendaDest = new JTextField(15);
        pIvaDest = new JTextField(15);
        pIvaCed = new JTextField(15);
        numFattura = new JTextField(15);
        dataFattura = new JTextField(15);
        tipoFattura = new JTextField(15);
        tipoPagamento = new JTextField(15);
        banca = new JTextField(15);
        avanti = new JButton("AVANTI");
        
        GestoreEventiTest gestore = new GestoreEventiTest(this);
        avanti.addActionListener(gestore);

        JPanel pannello = new JPanel(new GridLayout(0, 2, 0, 10));

        pannello.add(new JLabel("Dati Fornitore"));
        pannello.add(new JLabel(" "));

        pannello.add(new JLabel("Nome e Cognome Fornitore"));
        pannello.add(nomeForn);
        
        pannello.add(new JLabel("Azienda Cedente"));
        pannello.add(aziendaCed);
        
        pannello.add(new JLabel("Partita IVA Cedente"));
        pannello.add(pIvaCed);

        pannello.add(new JLabel("Dati Destinatario"));
        pannello.add(new JLabel(" "));

        pannello.add(new JLabel("Nome e Cognome Destinatario"));
        pannello.add(nomeDest);
        
        pannello.add(new JLabel("Azienda Destinataria"));
        pannello.add(aziendaDest);
        
        pannello.add(new JLabel("Partita IVA Destinatario"));
        pannello.add(pIvaDest);
        
        pannello.add(new JLabel("Dati Fattura"));
        pannello.add(new JLabel(" "));

        pannello.add(new JLabel("tipoFattura"));
        pannello.add(tipoFattura);
        
        pannello.add(new JLabel("Data Fattura"));
        pannello.add(dataFattura);
        
        pannello.add(new JLabel("Numero Fattura"));
        pannello.add(numFattura);

        pannello.add(new JLabel("Dati Pagamento"));
        pannello.add(new JLabel(" "));

        pannello.add(new JLabel("Tipo di Pagamento"));
        pannello.add(tipoPagamento);
        
        pannello.add(new JLabel("Banca di Appoggio"));
        pannello.add(banca);
        
        pannello.add(new JLabel(" "));
        pannello.add(avanti);

        pannello.setBorder(new EmptyBorder(20, 20, 20, 20));

        add(pannello);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }
}
