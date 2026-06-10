package fattura;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class InterfacciaPiede extends JFrame {

    JTextField totImp;
    JTextField ivaPerc;
    JTextField tot;
    JTextField totIva;
    JButton avanti;
    JButton calcola;

    public InterfacciaPiede(Fattura fatturaOggetto) {

        setTitle("Fattura");

        totImp = new JTextField(15);
        totImp.setEditable(false);
        totImp.setText(fatturaOggetto.getTotimp() );
        ivaPerc = new JTextField(15);
        tot= new JTextField(15);
        tot.setEditable(false);
        totIva = new JTextField(15);
        totIva.setEditable(false);
        avanti = new JButton("FINE");
        calcola = new JButton("CALCOLA");

        GestoreEventiPiede gestore = new GestoreEventiPiede(this, fatturaOggetto);
        avanti.addActionListener(gestore);
        calcola.addActionListener(gestore);
         
        JPanel pannello = new JPanel(new GridLayout(0, 2, 10, 10));

        pannello.add(new JLabel("Piede Fattura"));
        pannello.add(new JLabel(" "));
        
        pannello.add(new JLabel("Totale Imponibile"));
        pannello.add(totImp);
        
        pannello.add(new JLabel("Inserisci IVA %"));
        pannello.add(ivaPerc);
        
        pannello.add(new JLabel("Totale IVA"));
        pannello.add(totIva);
        
        pannello.add(new JLabel("Totale Complessivo"));
        pannello.add(tot);
        
        pannello.add(avanti);
        pannello.add(calcola);
        
        pannello.setBorder(new EmptyBorder(20, 20, 20, 20));

        add(pannello);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

}
