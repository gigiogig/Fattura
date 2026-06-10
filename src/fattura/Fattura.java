package fattura;

public class Fattura {

    // Dati provenienti da InterfacciaTestata
    private String nomeFornitore;
    private String aziendaCedente;
    private String pIvaCedente;
    private String nomeDestinatario;
    private String aziendaDestinataria;
    private String pIvaDestinatario;
    private String tipoFattura;
    private String dataFattura;
    private String numeroFattura;
    private String tipoPagamento;
    private String bancaAppoggio;

    // Dati provenienti da InterfacciaCorpo
    private String articolo;
    private String descrizione;
    private int quantita;
    private double prezzoPezzo;
    private double importo; // Questo rappresenta anche l'imponibile per singolo articolo

    // Dati provenienti da InterfacciaPiede
    private double iva;        // Percentuale (es. 22)
    private double totIva;     // Valore monetario dell'IVA
    private double tot;        // Totale fattura (Imponibile + TotIva)

    // ==========================================
    // GETTER E SETTER TESTATA
    // ==========================================
    public String getNomeFornitore() {
        return nomeFornitore;
    }

    public void setNomeFornitore(String nomeFornitore) {
        this.nomeFornitore = nomeFornitore;
    }

    public String getAziendaCedente() {
        return aziendaCedente;
    }

    public void setAziendaCedente(String aziendaCedente) {
        this.aziendaCedente = aziendaCedente;
    }

    public String getpIvaCedente() {
        return pIvaCedente;
    }

    public void setpIvaCedente(String pIvaCedente) {
        this.pIvaCedente = pIvaCedente;
    }

    public String getNomeDestinatario() {
        return nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

    public String getAziendaDestinataria() {
        return aziendaDestinataria;
    }

    public void setAziendaDestinataria(String aziendaDestinataria) {
        this.aziendaDestinataria = aziendaDestinataria;
    }

    public String getpIvaDestinatario() {
        return pIvaDestinatario;
    }

    public void setpIvaDestinatario(String pIvaDestinatario) {
        this.pIvaDestinatario = pIvaDestinatario;
    }

    public String getTipoFattura() {
        return tipoFattura;
    }

    public void setTipoFattura(String tipoFattura) {
        this.tipoFattura = tipoFattura;
    }

    public String getDataFattura() {
        return dataFattura;
    }

    public void setDataFattura(String dataFattura) {
        this.dataFattura = dataFattura;
    }

    public String getNumeroFattura() {
        return numeroFattura;
    }

    public void setNumeroFattura(String numeroFattura) {
        this.numeroFattura = numeroFattura;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public String getBancaAppoggio() {
        return bancaAppoggio;
    }

    public void setBancaAppoggio(String bancaAppoggio) {
        this.bancaAppoggio = bancaAppoggio;
    }

    // ==========================================
    // GETTER E SETTER CORPO E PIEDE (CORRETTI)
    // ==========================================
    public String getArticolo() {
        return articolo;
    }

    public void setArticolo(String articolo) {
        this.articolo = articolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getQuantita() {
        return Integer.toString(quantita);
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public String getPrezzoPezzo() {
        return Double.toString(prezzoPezzo);
    }

    public void setPrezzoPezzo(double prezzoPezzo) {
        this.prezzoPezzo = prezzoPezzo;
    }

    public String getImporto() {
        return Double.toString(importo);
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    // Il totale imponibile coincide con l'importo calcolato nel corpo
    public String getTotimp() {
        return Double.toString(this.importo);
    }

    // Rimosso il vecchio setTotimp() errato che creava confusione
    public String getIva() {
        return Double.toString(iva);
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public String getTotIva() {
        return Double.toString(totIva);
    }

    public void setTotIva(double totIva) {
        this.totIva = totIva;
    }

    public String getTot() {
        return Double.toString(tot);
    }

    public void setTot(double tot) {
        this.tot = tot;
    }
}
