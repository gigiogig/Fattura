package fattura;

public class Articolo {

    private String nome;
    private String descrizione;
    private int quantita;
    private double prezzoPezzo;
    private double importo;

    public Articolo(String nome, String descrizione, int quantita, double prezzoPezzo) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.quantita = quantita;
        this.prezzoPezzo = prezzoPezzo;
        this.importo = quantita * prezzoPezzo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getQuantita() {
        return quantita;
    }

    public double getPrezzoPezzo() {
        return prezzoPezzo;
    }

    public double getImporto() {
        return importo;
    }
}
