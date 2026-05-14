package br.com.feiraassinatura.domain;

import java.time.LocalDateTime;

public class ProtocoloAssinatura {
    private final String numero;
    private final LocalDateTime dataGeracao;

    public ProtocoloAssinatura(String numero, LocalDateTime dataGeracao) {
        this.numero = numero;
        this.dataGeracao = dataGeracao;
    }

    public String getNumero() {
        return numero;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }
}
