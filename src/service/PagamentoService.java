package service;

import domain.Assinatura;
import domain.CartaoCredito;
import domain.Pagamento;
import domain.StatusPagamento;
import gateway.OperadoraCartaoCreditoGateway;
import gateway.OperadoraCartaoCreditoGateway.RetornoAutorizacao;
import repository.PagamentoRepository;
import java.time.LocalDateTime;

public class PagamentoService {
    private final OperadoraCartaoCreditoGateway operadora;
    private final PagamentoRepository repo;

    public PagamentoService(OperadoraCartaoCreditoGateway operadora, PagamentoRepository repo) {
        this.operadora = operadora;
        this.repo = repo;
    }

    public Pagamento processarPagamento(Assinatura assinatura, double valor, CartaoCredito cartao) {
        Pagamento pagamento = new Pagamento(assinatura.id, valor, cartao);
        RetornoAutorizacao retorno = operadora.autorizarPagamento(valor, cartao);
        pagamento.dataProcessamento = LocalDateTime.now();
        pagamento.status = retorno.aprovado ? StatusPagamento.APROVADO : StatusPagamento.RECUSADO;
        pagamento.codigoTransacao = retorno.codigoTransacao;
        return repo.salvar(pagamento);
    }
}
