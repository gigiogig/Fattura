package bho;

import exc.ValidazioneException;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

public class GestoreEventi implements ActionListener {

    private final Interfaccia gui;

    public GestoreEventi(Interfaccia gui) {
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.chkMostraPassword) {
            char echo = gui.chkMostraPassword.isSelected() ? (char) 0 : '•';
            gui.txfPassword.setEchoChar(echo);
            gui.txfPassword2.setEchoChar(echo);

        } else if (e.getSource() == gui.salva) {
            char[] pas1 = gui.txfPassword.getPassword();
            char[] pas2 = gui.txfPassword2.getPassword();
            String username = gui.txfUser.getText().trim();
            String nomeSito = gui.txfNomeSito.getText().trim();
            String nome = gui.txfNome.getText().trim();
            String cognome = gui.txfCognome.getText().trim();
            String email = gui.txfEmail.getText().trim();
            String telefono = gui.txfTelefono.getText().trim();
            String dataNascita = gui.txfDataNascita.getText().trim();
            String via = gui.txfVia.getText().trim();
            String civico = gui.txfCivico.getText().trim();
            String provincia = gui.txfProvincia.getText().trim().toUpperCase();
            String comune = gui.txfComune.getText().trim();

            try {
                if (username.isEmpty() || nomeSito.isEmpty()) {
                    throw new ValidazioneException("Il campo Sito Web e Nome Utente del sito non possono essere vuoti.");
                }
                if (pas1.length == 0 || pas2.length == 0) {
                    throw new ValidazioneException("La password non può essere vuota.");
                }
                if (!Arrays.equals(pas1, pas2)) {
                    throw new ValidazioneException("Le password non coincidono.");
                }
                if (pas1.length < 8) {
                    throw new ValidazioneException("La password è troppo corta! (Minimo 8 caratteri).");
                }
                if (!new String(pas1).matches(".*[!@_#*].*")) {
                    throw new ValidazioneException("La password richiede almeno un carattere speciale tra (!, @, _, #, *).");
                }
                if (!new String(pas1).matches(".*\\d.*")) {
                    throw new ValidazioneException("La password richiede almeno un numero.");
                }
                if (nome.isEmpty() || cognome.isEmpty() || via.isEmpty() || comune.isEmpty()) {
                    throw new ValidazioneException("I campi Nome, Cognome, Via e Comune sono obbligatori.");
                }

                if (!dataNascita.isEmpty()) {
                    try {
                        java.time.LocalDate.parse(dataNascita,
                                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    } catch (java.time.format.DateTimeParseException ex) {
                        throw new ValidazioneException("Formato data non valido. Usa gg/mm/aaaa (es. 15/03/1995).");
                    }
                }

                if (!provincia.isEmpty() && !provincia.matches("[A-Z]{2}")) {
                    throw new ValidazioneException("La provincia deve essere una sigla di 2 lettere (es. MI, RM, NA).");
                }

                if (nomeSito.length() > 20 || username.length() > 20
                        || email.length() > 50 || telefono.length() > 15
                        || via.length() > 30 || civico.length() > 5
                        || provincia.length() > 2 || comune.length() > 30
                        || nome.length() > 30 || cognome.length() > 15
                        || dataNascita.length() > 10) {
                    throw new ValidazioneException("Uno o più campi superano la lunghezza massima consentita.");
                }
                // Ordine argomenti GestoreFile: (nomeSito, username, password, email, telefono,
                //                                provincia, comune, via, civico, nome, cognome, dataNascita, adminUser, ...)
                if (gui.getAdminPass().isEmpty()) {
                    GestoreFile.salvaSuCsv(
                            nomeSito, username, new String(pas1), email, telefono,
                            provincia, comune, via, civico, nome, cognome, dataNascita,
                            gui.getAdminUser(), "file generati");
                } else {
                    GestoreFile.salvaOCifreUtente(
                            nomeSito, username, new String(pas1), email, telefono,
                            provincia, comune, via, civico, nome, cognome, dataNascita,
                            gui.getAdminUser(), gui.getAdminPass(), "file generati");
                }

                JOptionPane.showMessageDialog(gui, "Dati registrati e salvati correttamente!",
                        "Operazione Completata", JOptionPane.INFORMATION_MESSAGE);
                pulisciCampiParziale();

            } catch (ValidazioneException ex) {
                JOptionPane.showMessageDialog(gui, ex.getMessage(), "Errore di Validazione", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(gui, "Errore I/O: " + ex.getMessage(), "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } finally {
                Arrays.fill(pas1, '\0');
                Arrays.fill(pas2, '\0');
            }

        } else if (e.getSource() == gui.annulla) {
            pulisciCampiTotale();

        } else if (e.getSource() == gui.tornaLogin) {
            NavigationManager.navigateTo(new InterfacciaLogin());
        }
    }

    private void pulisciCampiParziale() {
        gui.txfUser.setText("");
        gui.txfPassword.setText("");
        gui.txfPassword2.setText("");
        gui.txfNomeSito.setText("");
        gui.chkMostraPassword.setSelected(false);
        gui.txfPassword.setEchoChar('•');
        gui.txfPassword2.setEchoChar('•');
    }

    private void pulisciCampiTotale() {
        pulisciCampiParziale();
        gui.txfNome.setText("");
        gui.txfCognome.setText("");
        gui.txfEmail.setText("");
        gui.txfTelefono.setText("");
        gui.txfDataNascita.setText("");
        gui.txfVia.setText("");
        gui.txfCivico.setText("");
        gui.txfProvincia.setText("");
        gui.txfComune.setText("");
    }
}
