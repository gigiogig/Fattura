package fattura;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

public class GeneratorePDF {

    public static void creaPdf(String nomeFile, Fattura fattura) {
        try (Document document = new Document()) {
            PdfWriter.getInstance(document, new FileOutputStream(nomeFile));
            document.open();

            Font fontTitolo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLACK);
            Font fontSottoTitolo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.DARK_GRAY);
            Font fontTestoBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.BLACK);
            Font fontTesto = FontFactory.getFont(FontFactory.HELVETICA, 10, Color.BLACK);

            Paragraph titolo = new Paragraph("DOCUMENTO DI FATTURA", fontTitolo);
            titolo.setAlignment(Paragraph.ALIGN_CENTER);
            titolo.setSpacingAfter(20);
            document.add(titolo);

            PdfPTable tabellaAnagrafica = new PdfPTable(2);
            tabellaAnagrafica.setWidthPercentage(100);
            tabellaAnagrafica.setSpacingAfter(15);

            PdfPCell cellaCedente = new PdfPCell();
            cellaCedente.setBorder(PdfPCell.NO_BORDER);
            cellaCedente.addElement(new Paragraph("Dati del Cedente / Fornitore:", fontSottoTitolo));
            cellaCedente.addElement(new Paragraph("Nome: " + fattura.getNomeFornitore(), fontTesto));
            cellaCedente.addElement(new Paragraph("Azienda: " + fattura.getAziendaCedente(), fontTesto));
            cellaCedente.addElement(new Paragraph("P. IVA: " + fattura.getpIvaCedente(), fontTesto));
            tabellaAnagrafica.addCell(cellaCedente);

            PdfPCell cellaDestinatario = new PdfPCell();
            cellaDestinatario.setBorder(PdfPCell.NO_BORDER);
            cellaDestinatario.addElement(new Paragraph("Destinatario:", fontSottoTitolo));
            cellaDestinatario.addElement(new Paragraph("Nome: " + fattura.getNomeDestinatario(), fontTesto));
            cellaDestinatario.addElement(new Paragraph("Azienda: " + fattura.getAziendaDestinataria(), fontTesto));
            cellaDestinatario.addElement(new Paragraph("P. IVA: " + fattura.getpIvaDestinatario(), fontTesto));
            tabellaAnagrafica.addCell(cellaDestinatario);

            document.add(tabellaAnagrafica);

            PdfPTable tabellaDatiFattura = new PdfPTable(4);
            tabellaDatiFattura.setWidthPercentage(100);
            tabellaDatiFattura.setSpacingAfter(20);

            tabellaDatiFattura.addCell(new PdfPCell(new Phrase("Tipo Documento", fontTestoBold)));
            tabellaDatiFattura.addCell(new PdfPCell(new Phrase("Numero Fattura", fontTestoBold)));
            tabellaDatiFattura.addCell(new PdfPCell(new Phrase("Data", fontTestoBold)));
            tabellaDatiFattura.addCell(new PdfPCell(new Phrase("Tipo Pagamento", fontTestoBold)));

            tabellaDatiFattura.addCell(new PdfPCell(new Phrase(fattura.getTipoFattura(), fontTesto)));
            tabellaDatiFattura.addCell(new PdfPCell(new Phrase(fattura.getNumeroFattura(), fontTesto)));
            tabellaDatiFattura.addCell(new PdfPCell(new Phrase(fattura.getDataFattura(), fontTesto)));
            tabellaDatiFattura.addCell(new PdfPCell(new Phrase(fattura.getTipoPagamento(), fontTesto)));

            document.add(tabellaDatiFattura);

            Paragraph banca = new Paragraph("Banca d'Appoggio: " + fattura.getBancaAppoggio(), fontTesto);
            banca.setSpacingAfter(20);
            document.add(banca);

            Paragraph sezioneCorpo = new Paragraph("Dettaglio Beni / Servizi", fontSottoTitolo);
            sezioneCorpo.setSpacingAfter(5);
            document.add(sezioneCorpo);

            PdfPTable tabellaCorpo = new PdfPTable(5);
            tabellaCorpo.setWidthPercentage(100);
            tabellaCorpo.setSpacingAfter(20);
            tabellaCorpo.setWidths(new float[]{2f, 4f, 1f, 1.5f, 1.5f});

            tabellaCorpo.addCell(new PdfPCell(new Phrase("Articolo", fontTestoBold)));
            tabellaCorpo.addCell(new PdfPCell(new Phrase("Descrizione", fontTestoBold)));
            tabellaCorpo.addCell(new PdfPCell(new Phrase("Q.tà", fontTestoBold)));
            tabellaCorpo.addCell(new PdfPCell(new Phrase("Prezzo Unitario", fontTestoBold)));
            tabellaCorpo.addCell(new PdfPCell(new Phrase("Importo", fontTestoBold)));

            for (Articolo art : fattura.getListaArticoli()) {
                if (art.getNome() == null || art.getNome().trim().isEmpty() || art.getQuantita() == 0) {
                    continue;
                }

                String prezzoUnitarioFormattato = String.format(java.util.Locale.US, "%.2f", art.getPrezzoPezzo());
                String importoFormattato = String.format(java.util.Locale.US, "%.2f", art.getImporto());

                tabellaCorpo.addCell(new PdfPCell(new Phrase(art.getNome(), fontTesto)));
                tabellaCorpo.addCell(new PdfPCell(new Phrase(art.getDescrizione(), fontTesto)));
                tabellaCorpo.addCell(new PdfPCell(new Phrase(Integer.toString(art.getQuantita()), fontTesto)));
                tabellaCorpo.addCell(new PdfPCell(new Phrase(prezzoUnitarioFormattato + " €", fontTesto)));
                tabellaCorpo.addCell(new PdfPCell(new Phrase(importoFormattato + " €", fontTesto)));
            }

            document.add(tabellaCorpo);

            PdfPTable tabellaTotali = new PdfPTable(2);
            tabellaTotali.setWidthPercentage(40);
            tabellaTotali.setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);

            tabellaTotali.addCell(new PdfPCell(new Phrase("Totale Imponibile:", fontTestoBold)));
            tabellaTotali.addCell(new PdfPCell(new Phrase(fattura.getTotimp() + " €", fontTesto)));

            tabellaTotali.addCell(new PdfPCell(new Phrase("IVA (" + fattura.getIva() + "%):", fontTestoBold)));
            tabellaTotali.addCell(new PdfPCell(new Phrase(fattura.getTotIva() + " €", fontTesto)));

            PdfPCell cellaTotBold = new PdfPCell(new Phrase("TOTALE FATTURA:", fontTestoBold));
            cellaTotBold.setBackgroundColor(Color.LIGHT_GRAY);
            tabellaTotali.addCell(cellaTotBold);

            PdfPCell cellaTotValore = new PdfPCell(new Phrase(fattura.getTot() + " €", fontTestoBold));
            cellaTotValore.setBackgroundColor(Color.LIGHT_GRAY);
            tabellaTotali.addCell(cellaTotValore);

            document.add(tabellaTotali);

        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Impossibile scrivere il file PDF. Verificare che non sia aperto in un altro programma.\nErrore: " + de.getMessage(),
                    "Errore di Scrittura",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}
