package br.com.feiraassinatura.gateway;

import br.com.feiraassinatura.domain.CartaoCredito;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OperadoraCartaoCreditoGateway {
    public ResultadoAutorizacao autorizarPagamento(double valor, CartaoCredito cartao) {
        boolean dadosValidos = valor > 0
                && cartao != null
                && !cartao.getNumeroMascarado().isBlank()
                && !cartao.getNomeTitular().isBlank()
                && !cartao.getValidade().isBlank()
                && !cartao.getBandeira().isBlank();

        if (!dadosValidos) {
            return new ResultadoAutorizacao(false, "", "Pagamento recusado pela operadora simulada.");
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return new ResultadoAutorizacao(true, "TRX-" + timestamp, "Pagamento aprovado.");
    }
}
