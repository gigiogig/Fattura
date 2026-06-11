package bho;

import java.io.*;
import java.nio.file.*;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class GestoreFile {

    // --- METODI PER LA SICUREZZA (Cifratura AES 256 + Salt + IV) ---
    private static SecretKeySpec generaChiaveConSalt(String adminPass, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(adminPass.toCharArray(), salt, 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    public static void salvaOCifreUtente(
            String nomeSito, String username, String password,
            String email, String telefono, String provincia, String comune,
            String via, String civico, String nome, String cognome, String dataNascita,
            String adminUser, String adminPass, String cartellaBase) throws Exception {

        Path dirDat = Paths.get(cartellaBase, "dat");
        if (!Files.exists(dirDat)) {
            Files.createDirectories(dirDat);
        }
        Path pathFile = dirDat.resolve(adminUser + ".dat");

        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);

        SecretKeySpec chiave = generaChiaveConSalt(adminPass, salt);
        Cipher cifratore = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cifratore.init(Cipher.ENCRYPT_MODE, chiave, new IvParameterSpec(iv));

        String[] campi = {nomeSito, username, password, email, telefono,
            provincia, comune, via, civico, nome, cognome, dataNascita};
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (DataOutputStream out = new DataOutputStream(buf)) {
            out.write(salt);
            out.write(iv);
            out.writeInt(campi.length);
            for (String campo : campi) {
                byte[] cifrato = cifratore.doFinal(campo.getBytes("UTF-8"));
                out.writeInt(cifrato.length);
                out.write(cifrato);
            }
        }
        
        Files.write(pathFile, buf.toByteArray(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static List<String[]> leggiTuttoIlDatabase(
            String adminUser, String adminPass, String cartellaBase) throws Exception {

        Path pathFile = Paths.get(cartellaBase, "dat", adminUser + ".dat");
        if (!Files.exists(pathFile)) {
            throw new FileNotFoundException("Database non trovato.");
        }

        List<String[]> records = new ArrayList<>();
        Cipher cifratore = Cipher.getInstance("AES/CBC/PKCS5Padding");

        try (DataInputStream in = new DataInputStream(Files.newInputStream(pathFile))) {
            while (true) {
                try {
                    byte[] salt = new byte[16];
                    in.readFully(salt);
                    byte[] iv = new byte[16];
                    in.readFully(iv);

                    SecretKeySpec chiave = generaChiaveConSalt(adminPass, salt);
                    cifratore.init(Cipher.DECRYPT_MODE, chiave, new IvParameterSpec(iv));

                    int n = in.readInt();
                    String[] campi = new String[n];
                    for (int i = 0; i < n; i++) {
                        int len = in.readInt();
                        byte[] buf = new byte[len];
                        in.readFully(buf);
                        campi[i] = new String(cifratore.doFinal(buf), "UTF-8");
                    }
                    records.add(campi);
                } catch (java.io.EOFException eof) {
                    break;
                }

            }
        }
        return records;
    }

    // --- METODO DI EXPORT IN CHIARO (NON SICURO) ---
    public static void salvaSuCsv(
            String nomeSito, String username, String password,
            String email, String telefono, String provincia, String comune,
            String via, String civico, String nome, String cognome, String dataNascita,
            String adminUser, String cartellaBase) throws Exception {

        Path dirCsv = Paths.get(cartellaBase, "csv");
        if (!Files.exists(dirCsv)) {
            Files.createDirectories(dirCsv);
        }

        Path pathFile = dirCsv.resolve(adminUser + ".csv");
        boolean esiste = Files.exists(pathFile);

        try (BufferedWriter w = Files.newBufferedWriter(pathFile,
                java.nio.charset.StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            if (!esiste) {
                w.write("SitoWeb,Username,Password,Email,Telefono,Provincia,Comune,Via,Civico,Nome,Cognome,DataNascita");
                w.newLine();
            }

            // Scrittura in formato CSV standard
            w.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                    norm(nomeSito), norm(username), norm(password),
                    norm(email), norm(telefono), norm(provincia), norm(comune),
                    norm(via), norm(civico), norm(nome), norm(cognome), norm(dataNascita)));
            w.newLine();
        }
    }

    private static String norm(String input) {
        if (input == null) {
            return "";
        }

        // Rimuoviamo i caratteri di a capo che rompono la riga del CSV
        String pulito = input.replace("\n", "").replace("\r", "");

        // Se il campo contiene virgolette, le raddoppiamo per l'escaping CSV
        // Esempio: Il sito si chiama "Google", diventa ""Google""
        pulito = pulito.replace("\"", "\"\"");

        return pulito;
    }
}
