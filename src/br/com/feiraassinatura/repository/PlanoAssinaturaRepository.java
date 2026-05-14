package br.com.feiraassinatura.repository;

import br.com.feiraassinatura.domain.PlanoAssinatura;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public class PlanoAssinaturaRepository {
    private final CsvStore store;

    public PlanoAssinaturaRepository(Path dataDir) {
        this.store = new CsvStore(
                dataDir,
                "planos.csv",
                "id",
                "nome",
                "preco",
                "limiteFrutas",
                "limiteLegumes",
                "limiteVerduras",
                "descricao",
                "ativo");
        seed();
    }

    public List<PlanoAssinatura> buscarPlanosAtivos() {
        return store.readRows().stream()
                .map(this::toPlano)
                .filter(PlanoAssinatura::isAtivo)
                .toList();
    }

    public Optional<PlanoAssinatura> buscarPorId(long idPlano) {
        return store.readRows().stream()
                .map(this::toPlano)
                .filter(plano -> plano.getId() == idPlano && plano.isAtivo())
                .findFirst();
    }

    private void seed() {
        if (!store.isEmpty()) {
            return;
        }
        store.append(List.of("1", "Cesta Essencial", "49.90", "3", "2", "2", "Ideal para 1 pessoa", "true"));
        store.append(List.of("2", "Cesta Familia", "89.90", "5", "4", "4", "Para uma familia pequena", "true"));
        store.append(List.of("3", "Cesta Premium", "129.90", "7", "5", "5", "Mais variedade semanal", "true"));
    }

    private PlanoAssinatura toPlano(List<String> row) {
        return new PlanoAssinatura(
                Long.parseLong(row.get(0)),
                row.get(1),
                Double.parseDouble(row.get(2)),
                Integer.parseInt(row.get(3)),
                Integer.parseInt(row.get(4)),
                Integer.parseInt(row.get(5)),
                row.get(6),
                Boolean.parseBoolean(row.get(7)));
    }
}
