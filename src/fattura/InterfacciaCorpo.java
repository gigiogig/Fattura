/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fattura;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class InterfacciaCorpo extends JFrame {

    JTextField articolo;
    JTextField descrizione;
    JTextField qta;
    JTextField prezzoPezzo;
    JTextField importo;
    JButton aggiungi;
    JButton avanti;
    JButton calcola;

    public InterfacciaCorpo(Fattura fatturaOggetto) {
        
        setTitle("Fattura");

        articolo = new JTextField(15);
        descrizione = new JTextField(15);
        qta= new JTextField(15);
        prezzoPezzo = new JTextField(15);
        importo = new JTextField(15);
        importo.setEditable(false);
        aggiungi = new JButton("AGGIUNGI");
        avanti = new JButton("AVANTI");
        calcola = new JButton("CALCOLA");

        GestoreEventiCorp gestore = new GestoreEventiCorp(this, fatturaOggetto);
        aggiungi.addActionListener(gestore);
        avanti.addActionListener(gestore);
        calcola.addActionListener(gestore);
                
        JPanel pannello = new JPanel(new GridLayout(0, 5, 10, 10));

        pannello.add(new JLabel("Corpo Fattura"));
        pannello.add(new JLabel(" "));
        pannello.add(new JLabel(" "));
        pannello.add(new JLabel(" "));
        pannello.add(new JLabel(" "));
        
        pannello.add(new JLabel("Articolo"));
        pannello.add(new JLabel("Descrizione"));
        pannello.add(new JLabel("Quantità"));
        pannello.add(new JLabel("Prezzo al Pezzo"));
        pannello.add(new JLabel("Importo"));
        
        pannello.add(articolo);
        pannello.add(descrizione);
        pannello.add(qta);
        pannello.add(prezzoPezzo);
        pannello.add(importo);
        
        pannello.add(aggiungi);
        pannello.add(new JLabel(" "));
        pannello.add(avanti);
        pannello.add(new JLabel(" "));
        pannello.add(calcola);
        

        pannello.setBorder(new EmptyBorder(20, 20, 20, 20));

        add(pannello);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

}
