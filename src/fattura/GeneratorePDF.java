package fattura;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import java.io.FileOutputStream;

public class GeneratorePDF {

    public static void creaPdf(String percorsoFile, Fattura f) {
        // Inizializzazione corretta del documento A4 in OpenPDF
        Document document = new Document(PageSize.A4);
        
        try {
            PdfWriter.getInstance(document, new FileOutputStream(percorsoFile));
            document.open();
            
            // Definizione Font (utilizzando Color di java.awt)
            Font fontTitolo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Color.DARK_GRAY);
            Font fontSezione = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.BLACK);
            Font fontTesto = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
            
            // Intestazione documento
            document.add(new Paragraph("DOCUMENTO DI FATTURAZIONE", fontTitolo));
            document.add(new Paragraph("Fattura N: " + f.getNumeroFattura() + "   |   Data: " + f.getDataFattura(), fontTesto));
            document.add(new Paragraph(" ")); // Spazio vuoto
            
            // Tabella per i dati anagrafici (Mittente / Destinatario)
            Table tabAnagrafica = new Table(2);
            tabAnagrafica.setBorderWidth(1);
            tabAnagrafica.setPadding(5);
            tabAnagrafica.setWidth(100);
            
            tabAnagrafica.addCell(creaCellaSezione("Azienda Cedente (Mittente):", f.getAziendaCedente() + "\nP.IVA: " + f.getpIvaCedente(), fontSezione, fontTesto));
            tabAnagrafica.addCell(creaCellaSezione("Azienda Destinataria (Cliente):", f.getAziendaDestinataria() + "\nP.IVA: " + f.getpIvaDestinatario(), fontSezione, fontTesto));
            document.add(tabAnagrafica);
            
            document.add(new Paragraph(" ")); 
            
            // Tabella per i dati di riepilogo economici (Piede della fattura)
            Table tabPiede = new Table(2);
            tabPiede.setBorderWidth(1);
            tabPiede.setPadding(5);
            tabPiede.setWidth(50); // Più stretta, allineata solitamente a destra o centro
            
            tabPiede.addCell(new Phrase("Totale Imponibile:", fontSezione));
            tabPiede.addCell(new Phrase(f.getTotimp() + " €", fontTesto));
            
            tabPiede.addCell(new Phrase("Aliquota IVA:", fontSezione));
            tabPiede.addCell(new Phrase(f.getIva() + " %", fontTesto));
            
            tabPiede.addCell(new Phrase("Totale IVA:", fontSezione));
            tabPiede.addCell(new Phrase(f.getTotIva() + " €", fontTesto));
            
            tabPiede.addCell(new Phrase("Totale Fattura:", fontSezione));
            tabPiede.addCell(new Phrase(f.getTot() + " €", fontTesto));
            
            document.add(tabPiede);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    // Helper per generare le celle strutturate usando com.lowagie.text.Cell (no PdfPCell)
    private static Cell creaCellaSezione(String titolo, String testo, Font fTit, Font fTxt) {
        Cell cella = new Cell();
        try {
            cella.add(new Paragraph(titolo, fTit));
            cella.add(new Paragraph(testo, fTxt));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cella;
    }
}