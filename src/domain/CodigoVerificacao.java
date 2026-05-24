package domain;

import java.time.LocalDateTime;

public class CodigoVerificacao {
    public String celular;
    public String codigo;
    public LocalDateTime dataEnvio;
    public boolean utilizado;
    public boolean expirado;

    public CodigoVerificacao() {}

    public CodigoVerificacao(String celular, String codigo, LocalDateTime dataEnvio, boolean utilizado, boolean expirado) {
        this.celular = celular;
        this.codigo = codigo;
        this.dataEnvio = dataEnvio;
        this.utilizado = utilizado;
        this.expirado = expirado;
    }
}
