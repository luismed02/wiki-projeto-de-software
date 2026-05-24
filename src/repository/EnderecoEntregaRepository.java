package repository;

import domain.EnderecoEntrega;

public class EnderecoEntregaRepository {
    private static final String ARQ = CsvUtil.DATA_DIR + "/enderecos.csv";
    private static final String CAB = "id;assinaturaId;cep;rua;numero;bairro;cidade;complemento";

    public EnderecoEntrega salvar(EnderecoEntrega e) {
        if (e.id == null) e.id = CsvUtil.proximoId(ARQ);
        String linha = e.id + ";" + (e.assinaturaId == null ? "" : e.assinaturaId) + ";" +
                nv(e.cep) + ";" + nv(e.rua) + ";" + nv(e.numero) + ";" +
                nv(e.bairro) + ";" + nv(e.cidade) + ";" + nv(e.complemento);
        CsvUtil.anexarLinha(ARQ, CAB, linha);
        return e;
    }

    private static String nv(String s) { return s == null ? "" : s; }
}
