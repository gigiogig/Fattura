package bho;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class FinestraDecriptazione extends JFrame implements ActionListener {

    private JTextField txfTargetUser;
    private JPasswordField txfTargetPass;
    private JButton btnEsegui, btnAnnulla;

    public FinestraDecriptazione() {
        setTitle("Utility Decodifica Database Cifrati (.dat)");

        txfTargetUser = new JTextField(20);
        txfTargetPass = new JPasswordField(20);
        btnEsegui = new JButton("Decripta ed Esporta");
        btnAnnulla = new JButton("Torna al Login");

        getRootPane().setDefaultButton(btnEsegui);
        btnEsegui.addActionListener(this);
        btnAnnulla.addActionListener(this);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                new EmptyBorder(8, 10, 8, 10)));

        JLabel lblInfo = new JLabel("<html><b>Area Ripristino:</b> Inserisci le credenziali per decifrare il database.<br></html>");
        infoPanel.add(lblInfo, BorderLayout.CENTER);

        JPanel gridPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        gridPanel.add(new JLabel("Username Target:"), c);
        c.gridx = 1;
        gridPanel.add(txfTargetUser, c);
        c.gridx = 0;
        c.gridy = 1;
        gridPanel.add(new JLabel("Password Target:"), c);
        c.gridx = 1;
        gridPanel.add(txfTargetPass, c);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.add(btnAnnulla);
        btnPanel.add(btnEsegui);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(gridPanel, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getRootPane().setDoubleBuffered(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAnnulla) {
            NavigationManager.navigateTo(new InterfacciaLogin());
        } else if (e.getSource() == btnEsegui) {
            eseguiDecriptazione();
        }
    }

    private void eseguiDecriptazione() {
        String targetUser = txfTargetUser.getText().trim();
        String targetPass = new String(txfTargetPass.getPassword());

        if (targetUser.isEmpty() || targetPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Compila tutti i campi.", "Errore", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<String[]> records = GestoreFile.leggiTuttoIlDatabase(targetUser, targetPass, "file generati");

            Path dirOut = Paths.get("file generati", "decriptati");
            Files.createDirectories(dirOut);
            Path pathOut = dirOut.resolve(targetUser + "_decriptato.txt");

            try (BufferedWriter writer = Files.newBufferedWriter(pathOut)) {
                writer.write("=== DATABASE ESTRATTO IN CHIARO: " + targetUser + " ===");
                writer.newLine();
                writer.newLine();

                for (String[] r : records) {
                    // r[0]=SitoWeb, r[1]=Username, r[2]=Password, r[3]=Email, r[4]=Telefono,
                    // r[5]=Provincia, r[6]=Comune, r[7]=Via, r[8]=Civico, r[9]=Nome, r[10]=Cognome, r[11]=DataNascita
                    writer.write("Sito Web:     " + r[0]);
                    writer.newLine();
                    writer.write("Username:     " + r[1]);
                    writer.newLine();
                    writer.write("Password:     " + r[2]);
                    writer.newLine();
                    writer.write("Email:        " + r[3]);
                    writer.newLine();
                    writer.write("Telefono:     " + r[4]);
                    writer.newLine();
                    writer.write("Provincia:    " + r[5]);
                    writer.newLine();
                    writer.write("Comune:       " + r[6]);
                    writer.newLine();
                    writer.write("Indirizzo:    " + r[7] + " n." + r[8]);
                    writer.newLine();
                    writer.write("Nome:         " + r[9] + " " + r[10]);
                    writer.newLine();
                    writer.write("Data Nascita: " + r[11]);
                    writer.newLine();
                    writer.write("---------------------------------------------------------");
                    writer.newLine();
                    writer.newLine();
                }
            }

            JOptionPane.showMessageDialog(this, "Decifratura completata con successo!\nFile salvato in: " + pathOut);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore di decodifica:\n" + ex.getMessage(),
                    "Errore Critico", JOptionPane.ERROR_MESSAGE);
        }
    }
}
