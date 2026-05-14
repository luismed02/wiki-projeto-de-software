package br.com.feiraassinatura.service;

import br.com.feiraassinatura.domain.Assinatura;
import br.com.feiraassinatura.domain.CartaoCredito;
import br.com.feiraassinatura.domain.Pagamento;
import br.com.feiraassinatura.domain.StatusPagamento;
import br.com.feiraassinatura.gateway.OperadoraCartaoCreditoGateway;
import br.com.feiraassinatura.gateway.ResultadoAutorizacao;
import br.com.feiraassinatura.repository.PagamentoRepository;
import java.time.LocalDateTime;

public class PagamentoService {
    private final OperadoraCartaoCreditoGateway operadoraGateway;
    private final PagamentoRepository repository;

    public PagamentoService(OperadoraCartaoCreditoGateway operadoraGateway, PagamentoRepository repository) {
        this.operadoraGateway = operadoraGateway;
        this.repository = repository;
    }

    public Pagamento processarPagamento(Assinatura assinatura, CartaoCredito cartao) {
        ResultadoAutorizacao retorno = operadoraGateway.autorizarPagamento(assinatura.getPlano().getPreco(), cartao);
        StatusPagamento status = retorno.isAprovado() ? StatusPagamento.APROVADO : StatusPagamento.RECUSADO;
        Pagamento pagamento = new Pagamento(
                repository.proximoId(),
                assinatura.getPlano().getPreco(),
                LocalDateTime.now(),
                status,
                retorno.getCodigoTransacao(),
                cartao);
        registrarPagamento(assinatura, pagamento);

        if (!retorno.isAprovado()) {
            throw new IllegalStateException(retorno.getMensagem());
        }
        return pagamento;
    }

    public void registrarPagamento(Assinatura assinatura, Pagamento pagamento) {
        repository.salvar(assinatura.getId(), pagamento);
    }
}
