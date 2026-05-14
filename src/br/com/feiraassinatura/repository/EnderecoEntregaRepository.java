package br.com.feiraassinatura.repository;

import br.com.feiraassinatura.domain.EnderecoEntrega;
import java.nio.file.Path;
import java.util.List;

public class EnderecoEntregaRepository {
    private final CsvStore store;

    public EnderecoEntregaRepository(Path dataDir) {
        this.store = new CsvStore(
                dataDir,
                "enderecos.csv",
                "id",
                "assinaturaId",
                "cep",
                "rua",
                "numero",
                "bairro",
                "cidade",
                "complemento");
    }

    public long salvar(long assinaturaId, EnderecoEntrega endereco) {
        long id = store.nextLongId();
        store.append(List.of(
                String.valueOf(id),
                String.valueOf(assinaturaId),
                endereco.getCep(),
                endereco.getRua(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getComplemento()));
        return id;
    }
}
