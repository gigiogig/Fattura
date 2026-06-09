package fattura;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Cell;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.FileOutputStream;

public class GeneratorePDF {

    public static void creaPdf(String percorsoFile, Fattura f) {
        // In OpenPDF l'istanza corretta si crea definendo il formato della pagina
        Document document = new Document(PageSize.A4);
        
        try {
            PdfWriter.getInstance(document, new FileOutputStream(percorsoFile));
            document.open();
            
            // Definizione dei Font utilizzando la classe di compatibilità Color di AWT
            Font fontTitolo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, Color.DARK_GRAY);
            Font fontSezione = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11, Color.BLACK);
            Font fontTesto = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);
            
            // Intestazione del PDF
            String tipoFatturaVal = "FATTURA DI VENDITA"; 
            Paragraph tipoFattura = new Paragraph(tipoFatturaVal, fontTitolo);
            document.add(tipoFattura);
            
            Paragraph dettagliFattura = new Paragraph("Fattura N: " + f.getNumeroFattura() + "   |   Data: " + f.getDataFattura(), fontTesto);
            document.add(dettagliFattura);
            document.add(new Paragraph(" ")); // Riga vuota di spaziatura
            
            // Gestione delle tabelle: OpenPDF utilizza le classi stabili Table e Cell
            Table tabAnagrafica = new Table(2); // Tabella a 2 colonne
            tabAnagrafica.setBorderWidth(1);
            tabAnagrafica.setPadding(5);
            
            tabAnagrafica.addCell(creaCellaSezione("Mittente:", "Tua Azienda S.r.l.", fontSezione, fontTesto));
            tabAnagrafica.addCell(creaCellaSezione("Destinatario:", "Dati Cliente", fontSezione, fontTesto));
            document.add(tabAnagrafica);
            
            document.add(new Paragraph(" "));
            
            // Tabella per i dati del Piede recuperati dall'interfaccia
            Table tabPiede = new Table(2);
            tabPiede.setBorderWidth(1);
            tabPiede.setPadding(5);
            
            tabPiede.addCell(new Phrase("Totale Imponibile:", fontSezione));
            tabPiede.addCell(new Phrase(f.getTotimp() + " €", fontTesto));
            
            tabPiede.addCell(new Phrase("IVA %:", fontSezione));
            tabPiede.addCell(new Phrase(f.getIva() + " %", fontTesto));
            
            tabPiede.addCell(new Phrase("Totale IVA:", fontSezione));
            tabPiede.addCell(new Phrase(f.getTotIva() + " €", fontTesto));
            
            tabPiede.addCell(new Phrase("Totale Complessivo:", fontSezione));
            tabPiede.addCell(new Phrase(f.getTot(), fontTesto));
            
            document.add(tabPiede);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // Assicura la chiusura del flusso del documento
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    // Metodo helper che sostituisce il vecchio PdfPCell restituendo un oggetto com.lowagie.text.Cell
    private static Cell creaCellaSezione(String titolo, String testo, Font fTit, Font fTxt) {
        Cell cella = new Cell();
        Paragraph pTitolo = new Paragraph(titolo, fTit);
        Paragraph pTesto = new Paragraph(testo, fTxt);
        
        try {
            cella.add(pTitolo);
            cella.add(pTesto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cella;
    }
}