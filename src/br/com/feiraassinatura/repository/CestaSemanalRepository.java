package br.com.feiraassinatura.repository;

import br.com.feiraassinatura.domain.CestaSemanal;
import br.com.feiraassinatura.domain.ItemCesta;
import br.com.feiraassinatura.domain.PlanoAssinatura;
import br.com.feiraassinatura.domain.StatusCesta;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CestaSemanalRepository {
    private final CsvStore cestas;
    private final CsvStore itens;

    public CestaSemanalRepository(Path dataDir) {
        this.cestas = new CsvStore(dataDir, "cestas.csv", "id", "dataComposicao", "status", "planoId");
        this.itens = new CsvStore(dataDir, "itens_cesta.csv", "cestaId", "produtoId", "produtoNome", "tipo", "quantidade");
    }

    public long proximoId() {
        return cestas.nextLongId();
    }

    public void salvar(CestaSemanal cesta, PlanoAssinatura plano) {
        cestas.append(List.of(
                String.valueOf(cesta.getId()),
                cesta.getDataComposicao().toString(),
                cesta.getStatus().name(),
                String.valueOf(plano.getId())));
    }

    public void salvarItens(CestaSemanal cesta, List<ItemCesta> itensCesta) {
        List<List<String>> rows = new ArrayList<>();
        for (List<String> row : itens.readRows()) {
            if (!row.get(0).equals(String.valueOf(cesta.getId()))) {
                rows.add(row);
            }
        }
        for (ItemCesta item : itensCesta) {
            rows.add(List.of(
                    String.valueOf(cesta.getId()),
                    String.valueOf(item.getProduto().getId()),
                    item.getProduto().getNome(),
                    item.getProduto().getTipo().name(),
                    String.valueOf(item.getQuantidade())));
        }
        itens.rewrite(rows);
    }

    public void atualizarStatus(CestaSemanal cesta, StatusCesta status) {
        List<List<String>> rows = cestas.readRows();
        for (List<String> row : rows) {
            if (row.get(0).equals(String.valueOf(cesta.getId()))) {
                row.set(2, status.name());
            }
        }
        cestas.rewrite(rows);
    }
}
