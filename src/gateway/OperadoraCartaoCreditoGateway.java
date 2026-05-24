package gateway;

import domain.CartaoCredito;
import java.util.UUID;

public class OperadoraCartaoCreditoGateway {

    public static class RetornoAutorizacao {
        public boolean aprovado;
        public String codigoTransacao;

        public RetornoAutorizacao(boolean aprovado, String codigoTransacao) {
            this.aprovado = aprovado;
            this.codigoTransacao = codigoTransacao;
        }
    }

    public RetornoAutorizacao autorizarPagamento(double valor, CartaoCredito cartao) {
        System.out.println("[OPERADORA] Autorizando R$ " + valor + " no cartão " + cartao.numeroMascarado + " (" + cartao.bandeira + ")...");
        String codigo = UUID.randomUUID().toString().substring(0, 12).toUpperCase();
        return new RetornoAutorizacao(true, codigo);
    }
}
