package repository;

import domain.CestaSemanal;
import domain.ItemCesta;
import domain.StatusCesta;
import java.util.ArrayList;
import java.util.List;

public class CestaSemanalRepository {
    private static final String ARQ_CESTA = CsvUtil.DATA_DIR + "/cestas.csv";
    private static final String CAB_CESTA = "id;planoId;dataComposicao;status";
    private static final String ARQ_ITENS = CsvUtil.DATA_DIR + "/itens_cesta.csv";
    private static final String CAB_ITENS = "cestaId;produtoId;quantidade";

    public CestaSemanal salvar(CestaSemanal c) {
        if (c.id == null) c.id = CsvUtil.proximoId(ARQ_CESTA);
        String linha = c.id + ";" + c.planoId + ";" + c.dataComposicao + ";" + c.status;
        CsvUtil.anexarLinha(ARQ_CESTA, CAB_CESTA, linha);
        return c;
    }

    public void salvarItens(CestaSemanal cesta, List<ItemCesta> itens) {
        for (ItemCesta item : itens) {
            String linha = cesta.id + ";" + item.produtoId + ";" + item.quantidade;
            CsvUtil.anexarLinha(ARQ_ITENS, CAB_ITENS, linha);
        }
    }

    public void atualizarStatus(CestaSemanal cesta, StatusCesta status) {
        cesta.status = status;
        List<String[]> linhas = CsvUtil.lerLinhas(ARQ_CESTA);
        List<String> novas = new ArrayList<>();
        for (String[] cols : linhas) {
            if (Long.parseLong(cols[0]) == cesta.id) {
                cols[3] = status.name();
            }
            novas.add(String.join(";", cols));
        }
        CsvUtil.reescrever(ARQ_CESTA, CAB_CESTA, novas);
    }
}
