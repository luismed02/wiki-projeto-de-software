package br.com.feiraassinatura.repository;

import br.com.feiraassinatura.domain.ProtocoloAssinatura;
import java.nio.file.Path;
import java.util.List;

public class ProtocoloRepository {
    private final CsvStore store;

    public ProtocoloRepository(Path dataDir) {
        this.store = new CsvStore(dataDir, "protocolos.csv", "assinaturaId", "numero", "dataGeracao");
    }

    public void salvar(long assinaturaId, ProtocoloAssinatura protocolo) {
        store.append(List.of(
                String.valueOf(assinaturaId),
                protocolo.getNumero(),
                protocolo.getDataGeracao().toString()));
    }
}
