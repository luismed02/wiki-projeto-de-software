package domain;

public class CartaoCredito {
    public String numeroMascarado;
    public String nomeTitular;
    public String validade;
    public String bandeira;
    public String tokenOperadora;

    public CartaoCredito() {}

    public CartaoCredito(String numeroMascarado, String nomeTitular, String validade, String bandeira) {
        this.numeroMascarado = numeroMascarado;
        this.nomeTitular = nomeTitular;
        this.validade = validade;
        this.bandeira = bandeira;
    }
}
