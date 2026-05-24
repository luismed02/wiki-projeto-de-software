package repository;

import domain.Assinante;
import java.time.LocalDate;

public class AssinanteRepository {
    private static final String ARQ = CsvUtil.DATA_DIR + "/assinantes.csv";
    private static final String CAB = "id;nome;celular;email;dataCadastro";

    public Assinante salvar(Assinante a) {
        if (a.id == null) a.id = CsvUtil.proximoId(ARQ);
        String linha = a.id + ";" + nv(a.nome) + ";" + nv(a.celular) + ";" + nv(a.email) + ";" + a.dataCadastro;
        CsvUtil.anexarLinha(ARQ, CAB, linha);
        return a;
    }

    public Assinante buscarPorCelular(String celular) {
        for (String[] cols : CsvUtil.lerLinhas(ARQ)) {
            if (cols[2].equals(celular)) {
                return new Assinante(Long.parseLong(cols[0]), cols[1], cols[2], cols[3], LocalDate.parse(cols[4]));
            }
        }
        return null;
    }

    private static String nv(String s) { return s == null ? "" : s; }
}
