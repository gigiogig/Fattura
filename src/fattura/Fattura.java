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

    // Aggiungi qui in futuro i getter e setter per i dati del Corpo e del Piede
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

    public void setArticolo(String articolo) {
        this.articolo = articolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setQuantita(String quantita) {
        this.quantita = Integer.parseInt(quantita);
    }

    public void setImporto(String importo) {
        this.importo = Double.parseDouble(importo);
    }

    public void setPrezzoPezzo(String prezzoPezzo) {
        this.prezzoPezzo = Double.parseDouble(prezzoPezzo);
    }

    public void setTotimp(String totimp) {
        this.totimp = Double.parseDouble(totimp) ;
    }

    public void setIva(String iva) {
        this.iva = Double.parseDouble(iva) ;
    }

    public void setTot(String tot) {
        this.tot = Double.parseDouble(tot);
    }

    public void setTotIva(String totIva) {
        this.totIva = Double.parseDouble(totIva);
    }
    
    

}
