package br.com.feiraassinatura.domain;

public class CartaoCredito {
    private final String numeroMascarado;
    private final String nomeTitular;
    private final String validade;
    private final String bandeira;
    private final String tokenOperadora;

    private CartaoCredito(
            String numeroMascarado,
            String nomeTitular,
            String validade,
            String bandeira,
            String tokenOperadora) {
        this.numeroMascarado = numeroMascarado;
        this.nomeTitular = nomeTitular;
        this.validade = validade;
        this.bandeira = bandeira;
        this.tokenOperadora = tokenOperadora;
    }

    public static CartaoCredito criar(String numero, String nomeTitular, String validade, String bandeira) {
        String digitos = numero == null ? "" : numero.replaceAll("\\D", "");
        if (digitos.length() < 13 || digitos.length() > 19) {
            throw new IllegalArgumentException("Numero do cartao invalido.");
        }
        if (nomeTitular == null || nomeTitular.isBlank()) {
            throw new IllegalArgumentException("Nome do titular e obrigatorio.");
        }
        if (validade == null || validade.isBlank()) {
            throw new IllegalArgumentException("Validade do cartao e obrigatoria.");
        }
        if (bandeira == null || bandeira.isBlank()) {
            throw new IllegalArgumentException("Bandeira do cartao e obrigatoria.");
        }
        String ultimosDigitos = digitos.substring(digitos.length() - 4);
        String token = "tok_" + Math.abs((digitos + nomeTitular + validade).hashCode());
        return new CartaoCredito("**** **** **** " + ultimosDigitos, nomeTitular.trim(), validade.trim(), bandeira.trim(), token);
    }

    public String getNumeroMascarado() {
        return numeroMascarado;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public String getValidade() {
        return validade;
    }

    public String getBandeira() {
        return bandeira;
    }

    public String getTokenOperadora() {
        return tokenOperadora;
    }
}
