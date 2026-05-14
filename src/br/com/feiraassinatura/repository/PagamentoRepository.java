package br.com.feiraassinatura.repository;

import br.com.feiraassinatura.domain.Pagamento;
import java.nio.file.Path;
import java.util.List;

public class PagamentoRepository {
    private final CsvStore store;

    public PagamentoRepository(Path dataDir) {
        this.store = new CsvStore(
                dataDir,
                "pagamentos.csv",
                "id",
                "assinaturaId",
                "valor",
                "dataProcessamento",
                "status",
                "codigoTransacao",
                "numeroMascarado",
                "nomeTitular",
                "validade",
                "bandeira",
                "tokenOperadora");
    }

    public long proximoId() {
        return store.nextLongId();
    }

    public void salvar(long assinaturaId, Pagamento pagamento) {
        store.append(List.of(
                String.valueOf(pagamento.getId()),
                String.valueOf(assinaturaId),
                String.format(java.util.Locale.US, "%.2f", pagamento.getValor()),
                pagamento.getDataProcessamento().toString(),
                pagamento.getStatus().name(),
                pagamento.getCodigoTransacao(),
                pagamento.getCartao().getNumeroMascarado(),
                pagamento.getCartao().getNomeTitular(),
                pagamento.getCartao().getValidade(),
                pagamento.getCartao().getBandeira(),
                pagamento.getCartao().getTokenOperadora()));
    }
}
