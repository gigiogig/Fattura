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
    private String articolo;
    private String descrizione;
    private int quantita;
    private double importo;
    private double prezzoPezzo;
    private double totimp;
    private double iva;
    private double tot;
    private double totIva;

    // GETTER E SETTER per la Testata
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

    // GETTER E SETTER AGGIUNTI PER IL CORPO E IL PIEDE
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

    public String getImporto() {
        return Double.toString(importo);
    }

    public void setImporto(Double importo) {
        this.importo = importo;
    }

    public String getPrezzoPezzo() {
        return Double.toString(prezzoPezzo);
    }

    public void setPrezzoPezzo(Double prezzoPezzo) {
        this.prezzoPezzo = prezzoPezzo;
    }

    public String getTotimp() {
        return getTot();
    }

    public void setTotimp() {
        totimp = tot;
    }

    public String getIva() {
        return Double.toString(iva);
    }

    public void setIva(String iva) {
        this.iva = Double.parseDouble(iva);
    }

    public String getTot() {
        return Double.toString(tot);
    }

    public void setTot(String tot) {
        this.tot = Double.parseDouble(tot);
    }

    public String getTotIva() {
        return Double.toString(totIva);
    }

    public void setTotIva(String totIva) {
        this.totIva = Double.parseDouble(totIva);
    }
}