package br.com.feiraassinatura.repository;

import br.com.feiraassinatura.domain.Assinatura;
import br.com.feiraassinatura.domain.StatusAssinatura;
import java.nio.file.Path;
import java.util.List;

public class AssinaturaRepository {
    private final CsvStore store;

    public AssinaturaRepository(Path dataDir) {
        this.store = new CsvStore(
                dataDir,
                "assinaturas.csv",
                "id",
                "assinanteId",
                "planoId",
                "cestaId",
                "protocolo",
                "dataInicio",
                "status",
                "proximaEntrega");
    }

    public long proximoId() {
        return store.nextLongId();
    }

    public void salvar(Assinatura assinatura) {
        store.append(List.of(
                String.valueOf(assinatura.getId()),
                String.valueOf(assinatura.getAssinante().getId()),
                String.valueOf(assinatura.getPlano().getId()),
                String.valueOf(assinatura.getCesta().getId()),
                assinatura.getProtocolo() == null ? "" : assinatura.getProtocolo(),
                assinatura.getDataInicio().toString(),
                assinatura.getStatus().name(),
                assinatura.getProximaEntrega().toString()));
    }

    public void atualizarStatus(Assinatura assinatura, StatusAssinatura status) {
        List<List<String>> rows = store.readRows();
        for (List<String> row : rows) {
            if (row.get(0).equals(String.valueOf(assinatura.getId()))) {
                row.set(6, status.name());
            }
        }
        store.rewrite(rows);
    }

    public void salvarProtocolo(Assinatura assinatura, String protocolo) {
        List<List<String>> rows = store.readRows();
        for (List<String> row : rows) {
            if (row.get(0).equals(String.valueOf(assinatura.getId()))) {
                row.set(4, protocolo);
            }
        }
        store.rewrite(rows);
    }
}
