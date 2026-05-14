package br.com.feiraassinatura.repository;

import br.com.feiraassinatura.domain.Produto;
import br.com.feiraassinatura.domain.TipoProduto;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class CatalogoProdutoRepository {
    private final CsvStore store;

    public CatalogoProdutoRepository(Path dataDir) {
        this.store = new CsvStore(
                dataDir,
                "produtos.csv",
                "id",
                "nome",
                "tipo",
                "quantidadeDisponivel",
                "disponivel");
        seed();
    }

    public List<Produto> buscarProdutosDaSemana(TipoProduto tipo) {
        return store.readRows().stream()
                .map(this::toProduto)
                .filter(produto -> produto.getTipo() == tipo)
                .filter(Produto::isDisponivel)
                .toList();
    }

    public Optional<Produto> buscarPorId(long idProduto) {
        return store.readRows().stream()
                .map(this::toProduto)
                .filter(produto -> produto.getId() == idProduto && produto.isDisponivel())
                .findFirst();
    }

    private void seed() {
        if (!store.isEmpty()) {
            return;
        }
        store.append(List.of("1", "Banana", "FRUTA", "30", "true"));
        store.append(List.of("2", "Maca", "FRUTA", "25", "true"));
        store.append(List.of("3", "Laranja", "FRUTA", "25", "true"));
        store.append(List.of("4", "Mamao", "FRUTA", "15", "true"));
        store.append(List.of("5", "Uva", "FRUTA", "12", "true"));
        store.append(List.of("6", "Cenoura", "LEGUME", "25", "true"));
        store.append(List.of("7", "Batata", "LEGUME", "30", "true"));
        store.append(List.of("8", "Abobrinha", "LEGUME", "18", "true"));
        store.append(List.of("9", "Beterraba", "LEGUME", "18", "true"));
        store.append(List.of("10", "Tomate", "LEGUME", "24", "true"));
        store.append(List.of("11", "Alface", "VERDURA", "20", "true"));
        store.append(List.of("12", "Couve", "VERDURA", "18", "true"));
        store.append(List.of("13", "Rucula", "VERDURA", "18", "true"));
        store.append(List.of("14", "Espinafre", "VERDURA", "12", "true"));
        store.append(List.of("15", "Agriao", "VERDURA", "12", "true"));
    }

    private Produto toProduto(List<String> row) {
        return new Produto(
                Long.parseLong(row.get(0)),
                row.get(1),
                TipoProduto.valueOf(row.get(2)),
                Integer.parseInt(row.get(3)),
                Boolean.parseBoolean(row.get(4)));
    }
}
