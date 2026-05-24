package repository;

import domain.Pagamento;

public class PagamentoRepository {
    private static final String ARQ = CsvUtil.DATA_DIR + "/pagamentos.csv";
    private static final String CAB = "id;assinaturaId;valor;dataProcessamento;status;codigoTransacao;cartaoNumeroMascarado;cartaoTitular;cartaoValidade;cartaoBandeira";

    public Pagamento salvar(Pagamento p) {
        if (p.id == null) p.id = CsvUtil.proximoId(ARQ);
        String linha = p.id + ";" + p.assinaturaId + ";" + p.valor + ";" + p.dataProcessamento + ";" +
                p.status + ";" + nv(p.codigoTransacao) + ";" +
                nv(p.cartao.numeroMascarado) + ";" + nv(p.cartao.nomeTitular) + ";" +
                nv(p.cartao.validade) + ";" + nv(p.cartao.bandeira);
        CsvUtil.anexarLinha(ARQ, CAB, linha);
        return p;
    }

    private static String nv(String s) { return s == null ? "" : s; }
}
