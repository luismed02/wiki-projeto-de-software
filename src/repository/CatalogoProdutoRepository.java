package repository;

import domain.Produto;
import domain.TipoProduto;
import java.util.ArrayList;
import java.util.List;

public class CatalogoProdutoRepository {
    private static final String ARQ = CsvUtil.DATA_DIR + "/produtos_semana.csv";

    public List<Produto> buscarProdutosDaSemana(TipoProduto tipo) {
        List<Produto> produtos = new ArrayList<>();
        for (String[] cols : CsvUtil.lerLinhas(ARQ)) {
            TipoProduto t = TipoProduto.valueOf(cols[2]);
            if (t == tipo) {
                produtos.add(new Produto(Long.parseLong(cols[0]), cols[1], t, Integer.parseInt(cols[3])));
            }
        }
        return produtos;
    }

    public Produto buscarPorId(Long id) {
        for (String[] cols : CsvUtil.lerLinhas(ARQ)) {
            if (Long.parseLong(cols[0]) == id) {
                return new Produto(id, cols[1], TipoProduto.valueOf(cols[2]), Integer.parseInt(cols[3]));
            }
        }
        return null;
    }
}
