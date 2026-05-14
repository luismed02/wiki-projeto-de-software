package br.com.feiraassinatura.repository;

import br.com.feiraassinatura.domain.CodigoVerificacao;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CodigoVerificacaoRepository {
    private final CsvStore store;

    public CodigoVerificacaoRepository(Path dataDir) {
        this.store = new CsvStore(dataDir, "codigos_verificacao.csv", "celular", "codigo", "dataEnvio", "utilizado");
    }

    public void salvar(CodigoVerificacao codigo) {
        store.append(List.of(
                codigo.getCelular(),
                codigo.getCodigo(),
                codigo.getDataEnvio().toString(),
                String.valueOf(codigo.isUtilizado())));
    }

    public Optional<CodigoVerificacao> buscarCodigoValido(String celular, String codigo) {
        List<List<String>> rows = store.readRows();
        for (int index = rows.size() - 1; index >= 0; index--) {
            CodigoVerificacao candidato = toCodigo(rows.get(index));
            if (candidato.getCelular().equals(celular)
                    && candidato.getCodigo().equals(codigo)
                    && !candidato.isUtilizado()
                    && !candidato.isExpirado()) {
                return Optional.of(candidato);
            }
        }
        return Optional.empty();
    }

    public void marcarComoUtilizado(CodigoVerificacao codigo) {
        List<List<String>> rows = store.readRows();
        for (List<String> row : rows) {
            boolean mesmoCodigo = row.get(0).equals(codigo.getCelular())
                    && row.get(1).equals(codigo.getCodigo())
                    && row.get(2).equals(codigo.getDataEnvio().toString());
            if (mesmoCodigo) {
                row.set(3, "true");
            }
        }
        store.rewrite(rows);
    }

    private CodigoVerificacao toCodigo(List<String> row) {
        return new CodigoVerificacao(
                row.get(0),
                row.get(1),
                LocalDateTime.parse(row.get(2)),
                Boolean.parseBoolean(row.get(3)));
    }
}
