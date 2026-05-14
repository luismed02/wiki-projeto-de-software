package br.com.feiraassinatura.domain;

import java.time.LocalDate;

public class Assinatura {
    private final long id;
    private final Assinante assinante;
    private final PlanoAssinatura plano;
    private final CestaSemanal cesta;
    private final EnderecoEntrega enderecoEntrega;
    private Pagamento pagamento;
    private String protocolo;
    private final LocalDate dataInicio;
    private StatusAssinatura status;
    private final LocalDate proximaEntrega;

    public Assinatura(
            long id,
            Assinante assinante,
            PlanoAssinatura plano,
            CestaSemanal cesta,
            EnderecoEntrega enderecoEntrega,
            LocalDate dataInicio,
            StatusAssinatura status,
            LocalDate proximaEntrega) {
        this.id = id;
        this.assinante = assinante;
        this.plano = plano;
        this.cesta = cesta;
        this.enderecoEntrega = enderecoEntrega;
        this.dataInicio = dataInicio;
        this.status = status;
        this.proximaEntrega = proximaEntrega;
    }

    public long getId() {
        return id;
    }

    public Assinante getAssinante() {
        return assinante;
    }

    public PlanoAssinatura getPlano() {
        return plano;
    }

    public CestaSemanal getCesta() {
        return cesta;
    }

    public EnderecoEntrega getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public StatusAssinatura getStatus() {
        return status;
    }

    public LocalDate getProximaEntrega() {
        return proximaEntrega;
    }

    public void aguardarAprovacao() {
        status = StatusAssinatura.AGUARDANDO_APROVACAO;
    }

    public void aprovar() {
        status = StatusAssinatura.APROVADA;
    }

    public void registrarPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public void registrarProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }
}
