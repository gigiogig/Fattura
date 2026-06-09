/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fattura;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

public class InterfacciaPiede extends JFrame {

    private JTextField totImp;
    private JTextField ivaPerc;
    private JTextField tot;
    private JTextField totIva;
    JButton avanti;

    public InterfacciaPiede() {

        setTitle("Fattura");

        totImp = new JTextField(15);
        ivaPerc = new JTextField(15);
        tot= new JTextField(15);
        totIva = new JTextField(15);
        avanti = new JButton("FINE");

        GestoreEventiPiede gestore = new GestoreEventiPiede(this);
        avanti.addActionListener(gestore);
         
        JPanel pannello = new JPanel(new GridLayout(0, 2, 10, 10));

        pannello.add(new JLabel("Piede Fattura"));
        pannello.add(new JLabel(" "));
        
        pannello.add(new JLabel("Totale Imponibile"));
        pannello.add(totImp);
        
        pannello.add(new JLabel("IVA %"));
        pannello.add(ivaPerc);
        
        pannello.add(new JLabel("Totale IVA"));
        pannello.add(totIva);
        
        pannello.add(new JLabel("Totale Complessivo"));
        pannello.add(tot);
        
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
