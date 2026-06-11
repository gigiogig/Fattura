package bho;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CSV colonne (sep. ';'):
 * 0=AdminUser, 1=AdminPass, 2=UsernameSito, 3=PasswordSito,
 * 4=Nome, 5=Cognome, 6=Email, 7=Telefono, 8=DataNascita,
 * 9=Via, 10=Civico, 11=Provincia, 12=Comune, 13=SitoWeb
 *
 * Ordine GestoreFile (0-11):
 * 0=SitoWeb, 1=Username, 2=Password, 3=Email, 4=Telefono,
 * 5=Provincia, 6=Comune, 7=Via, 8=Civico, 9=Nome, 10=Cognome, 11=DataNascita
 */
public class TestSenzaGUI {

    public static void main(String[] args) {
        System.out.println("🔄 FASE 1: Lettura file e generazione database nella cartella 'test'...");

        Path percorsoFileTest = Paths.get("dati_test.csv");
        if (!Files.exists(percorsoFileTest)) {
            System.err.println("❌ File 'dati_test.csv' non trovato nella root del progetto!");
            return;
        }

        Map<String, String> mappaAdmin = new HashMap<>();
        int count = 0;

        try (BufferedReader reader = Files.newBufferedReader(percorsoFileTest,
                java.nio.charset.StandardCharsets.UTF_8)) {
            String riga;
            boolean primaRiga = true;
            while ((riga = reader.readLine()) != null) {
                if (riga.trim().isEmpty()) continue;
                if (primaRiga) {
                    primaRiga = false;
                    if (riga.toLowerCase().contains("adminuser")) continue;
                }
                String[] d = riga.split(";");
                if (d.length < 14) continue;
                for (int i = 0; i < d.length; i++) d[i] = d[i].trim();

                // d[0]=AdminUser, d[1]=AdminPass, d[2]=UsernameSito, d[3]=PasswordSito,
                // d[4]=Nome, d[5]=Cognome, d[6]=Email, d[7]=Telefono, d[8]=DataNascita,
                // d[9]=Via, d[10]=Civico, d[11]=Provincia, d[12]=Comune, d[13]=SitoWeb

                String adminUser = d[0];
                String adminPass = d[1];
                mappaAdmin.put(adminUser, adminPass);

                // Argomenti GestoreFile: (nomeSito, username, password, email, telefono,
                //                         provincia, comune, via, civico, nome, cognome, dataNascita, adminUser, cartella)
                try {
                    GestoreFile.salvaSuCsv(
                            d[13], d[2], d[3], d[6], d[7], d[11], d[12], d[9], d[10], d[4], d[5], d[8],
                            adminUser, "test");
                    GestoreFile.salvaOCifreUtente(
                            d[13], d[2], d[3], d[6], d[7], d[11], d[12], d[9], d[10], d[4], d[5], d[8],
                            adminUser, adminPass, "test");
                    count++;
                } catch (Exception e) {
                    System.err.println("❌ Errore salvataggio per " + adminUser + ": " + e.getMessage());
                }
            }
            System.out.println("✅ FASE 1 Completata! Elaborati " + count + " record.\n");
        } catch (Exception e) {
            System.err.println("❌ Errore lettura:");
            e.printStackTrace();
            return;
        }

        System.out.println("🔄 FASE 2: Test decrittazione .dat...");
        for (Map.Entry<String, String> entry : mappaAdmin.entrySet()) {
            String adminUser = entry.getKey();
            String adminPass = entry.getValue();
            try {
                List<String[]> records = GestoreFile.leggiTuttoIlDatabase(adminUser, adminPass, "test");
                Path dirOut = Paths.get("test", "decriptati");
                if (!Files.exists(dirOut)) Files.createDirectories(dirOut);
                Path out = dirOut.resolve(adminUser + "_verifica.txt");

                try (BufferedWriter w = Files.newBufferedWriter(out, java.nio.charset.StandardCharsets.UTF_8)) {
                    w.write("=== DATI DECIFRATI: " + adminUser + " ===");
                    w.newLine();
                    for (String[] r : records) {
                        // r[0]=SitoWeb,r[1]=Username,r[2]=Password,r[3]=Email,r[4]=Telefono,
                        // r[5]=Provincia,r[6]=Comune,r[7]=Via,r[8]=Civico,r[9]=Nome,r[10]=Cognome,r[11]=DataNascita
                        w.write("Sito Web:     " + r[0]);       w.newLine();
                        w.write("Username:     " + r[1]);       w.newLine();
                        w.write("Password:     " + r[2]);       w.newLine();
                        w.write("Email:        " + r[3]);       w.newLine();
                        w.write("Telefono:     " + r[4]);       w.newLine();
                        w.write("Provincia:    " + r[5]);       w.newLine();
                        w.write("Comune:       " + r[6]);       w.newLine();
                        w.write("Indirizzo:    " + r[7] + " n." + r[8]); w.newLine();
                        w.write("Nome:         " + r[9] + " " + r[10]); w.newLine();
                        w.write("Data Nascita: " + r[11]);      w.newLine();
                        w.write("---------------------------------------------------------"); w.newLine();
                    }
                }
                System.out.println("🔓 OK: '" + adminUser + "' — " + records.size() + " record decifrati.");
            } catch (Exception e) {
                System.err.println("❌ FALLITO per '" + adminUser + "': " + e.getMessage());
            }
        }
        System.out.println("\n🎯 TEST COMPLETATO! Controlla la cartella 'test'.");
    }
}
