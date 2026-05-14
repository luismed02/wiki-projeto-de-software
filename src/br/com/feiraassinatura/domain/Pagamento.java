package br.com.feiraassinatura.domain;

import java.time.LocalDateTime;

public class Pagamento {
    private final long id;
    private final double valor;
    private final LocalDateTime dataProcessamento;
    private final StatusPagamento status;
    private final String codigoTransacao;
    private final CartaoCredito cartao;

    public Pagamento(
            long id,
            double valor,
            LocalDateTime dataProcessamento,
            StatusPagamento status,
            String codigoTransacao,
            CartaoCredito cartao) {
        this.id = id;
        this.valor = valor;
        this.dataProcessamento = dataProcessamento;
        this.status = status;
        this.codigoTransacao = codigoTransacao;
        this.cartao = cartao;
    }

    public long getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public LocalDateTime getDataProcessamento() {
        return dataProcessamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public CartaoCredito getCartao() {
        return cartao;
    }
}
