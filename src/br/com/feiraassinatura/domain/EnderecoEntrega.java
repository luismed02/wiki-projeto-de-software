package br.com.feiraassinatura.domain;

public class EnderecoEntrega {
    private final String cep;
    private final String rua;
    private final String numero;
    private final String bairro;
    private final String cidade;
    private final String complemento;

    public EnderecoEntrega(String cep, String rua, String numero, String bairro, String cidade, String complemento) {
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.complemento = complemento == null ? "" : complemento;
    }

    public String getCep() {
        return cep;
    }

    public String getRua() {
        return rua;
    }

    public String getNumero() {
        return numero;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public String resumo() {
        String base = rua + ", " + numero + " - " + bairro + ", " + cidade + " - CEP " + cep;
        if (complemento.isBlank()) {
            return base;
        }
        return base + " (" + complemento + ")";
    }
}
