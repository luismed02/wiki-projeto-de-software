package domain;

import java.time.LocalDateTime;

public class Pagamento {
    public Long id;
    public Long assinaturaId;
    public double valor;
    public LocalDateTime dataProcessamento;
    public StatusPagamento status;
    public String codigoTransacao;
    public CartaoCredito cartao;

    public Pagamento() {}

    public Pagamento(Long assinaturaId, double valor, CartaoCredito cartao) {
        this.assinaturaId = assinaturaId;
        this.valor = valor;
        this.cartao = cartao;
        this.status = StatusPagamento.PENDENTE;
    }
}
