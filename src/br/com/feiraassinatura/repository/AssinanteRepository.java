package br.com.feiraassinatura.repository;

import br.com.feiraassinatura.domain.Assinante;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AssinanteRepository {
    private final CsvStore store;

    public AssinanteRepository(Path dataDir) {
        this.store = new CsvStore(dataDir, "assinantes.csv", "id", "nome", "celular", "email", "dataCadastro");
    }

    public Optional<Assinante> buscarPorCelular(String celular) {
        return store.readRows().stream()
                .filter(row -> row.get(2).equals(celular))
                .findFirst()
                .map(this::toAssinante);
    }

    public Assinante buscarOuCriarPorCelular(String celular) {
        return buscarPorCelular(celular).orElseGet(() -> {
            String ultimosDigitos = celular.length() <= 4 ? celular : celular.substring(celular.length() - 4);
            Assinante assinante = new Assinante(
                    store.nextLongId(),
                    "Assinante " + ultimosDigitos,
                    celular,
                    "",
                    LocalDate.now());
            salvar(assinante);
            return assinante;
        });
    }

    public void salvar(Assinante assinante) {
        store.append(List.of(
                String.valueOf(assinante.getId()),
                assinante.getNome(),
                assinante.getCelular(),
                assinante.getEmail(),
                assinante.getDataCadastro().toString()));
    }

    private Assinante toAssinante(List<String> row) {
        return new Assinante(
                Long.parseLong(row.get(0)),
                row.get(1),
                row.get(2),
                row.get(3),
                LocalDate.parse(row.get(4)));
    }
}
