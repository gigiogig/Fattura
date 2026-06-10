package fattura;

import java.util.ArrayList;
import java.util.List;

public class Fattura {

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

    private List<Articolo> listaArticoli = new ArrayList<>();

    private double iva;
    private double totIva;
    private double tot;

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

    public void aggiungiArticolo(Articolo art) {
        this.listaArticoli.add(art);
    }

    public List<Articolo> getListaArticoli() {
        return listaArticoli;
    }

    public String getTotimp() {
        double sommaImponibile = 0;
        for (Articolo art : listaArticoli) {
            sommaImponibile += art.getImporto();
        }
        return Double.toString(sommaImponibile);
    }

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
