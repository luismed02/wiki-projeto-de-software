package br.com.feiraassinatura.domain;

import java.time.LocalDateTime;

public class CodigoVerificacao {
    private static final long MINUTOS_VALIDADE = 10;

    private final String celular;
    private final String codigo;
    private final LocalDateTime dataEnvio;
    private final boolean utilizado;

    public CodigoVerificacao(String celular, String codigo, LocalDateTime dataEnvio, boolean utilizado) {
        this.celular = celular;
        this.codigo = codigo;
        this.dataEnvio = dataEnvio;
        this.utilizado = utilizado;
    }

    public String getCelular() {
        return celular;
    }

    public String getCodigo() {
        return codigo;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public boolean isUtilizado() {
        return utilizado;
    }

    public boolean isExpirado() {
        return dataEnvio.plusMinutes(MINUTOS_VALIDADE).isBefore(LocalDateTime.now());
    }
}
