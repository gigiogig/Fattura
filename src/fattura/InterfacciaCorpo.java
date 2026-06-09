/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fattura;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

public class InterfacciaCorpo extends JFrame {

    private JTextField articolo;
    private JTextField descrizione;
    private JTextField qta;
    private JTextField prezzoPezzo;
    private JTextField importo;
    private JTextField iva;
    JButton aggiungi;
    JButton avanti;

    public InterfacciaCorpo() {

        setTitle("Fattura");

        articolo = new JTextField(15);
        descrizione = new JTextField(15);
        qta= new JTextField(15);
        prezzoPezzo = new JTextField(15);
        importo = new JTextField(15);
        iva = new JTextField(15);
        aggiungi = new JButton("AGGIUNGI");
        avanti = new JButton("SALVA");

        GestoreEventiCorp gestore = new GestoreEventiCorp(this);
        aggiungi.addActionListener(gestore);
        avanti.addActionListener(gestore);
                ;
        JPanel pannello = new JPanel(new GridLayout(0, 6, 10, 10));

        pannello.add(new JLabel("Corpo Fattura"));
        pannello.add(new JLabel(" "));
        pannello.add(new JLabel(" "));
        pannello.add(new JLabel(" "));
        pannello.add(new JLabel(" "));
        pannello.add(new JLabel(" "));
        
        pannello.add(new JLabel("Articolo"));
        pannello.add(new JLabel("Descrizione"));
        pannello.add(new JLabel("Quantità"));
        pannello.add(new JLabel("Prezzo al Pezzo"));
        pannello.add(new JLabel("Importo"));
        pannello.add(new JLabel("IVA"));
        
        pannello.add(articolo);
        pannello.add(descrizione);
        pannello.add(qta);
        pannello.add(prezzoPezzo);
        pannello.add(importo);
        pannello.add(iva);
        
        pannello.add(aggiungi);
        pannello.add(new JLabel(" "));
        pannello.add(new JLabel(" "));
        pannello.add(new JLabel(" "));
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
