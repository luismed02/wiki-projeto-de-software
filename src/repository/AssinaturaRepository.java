package repository;

import domain.Assinatura;
import domain.StatusAssinatura;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AssinaturaRepository {
    private static final String ARQ = CsvUtil.DATA_DIR + "/assinaturas.csv";
    private static final String CAB = "id;assinanteId;planoId;cestaId;enderecoId;dataInicio;status;proximaEntrega;protocolo";

    public Assinatura salvar(Assinatura a) {
        if (a.id == null) a.id = CsvUtil.proximoId(ARQ);
        CsvUtil.anexarLinha(ARQ, CAB, serializar(a));
        return a;
    }

    public void atualizarStatus(Assinatura a, StatusAssinatura status) {
        a.status = status;
        List<String[]> linhas = CsvUtil.lerLinhas(ARQ);
        List<String> novas = new ArrayList<>();
        for (String[] cols : linhas) {
            if (Long.parseLong(cols[0]) == a.id) {
                cols[6] = status.name();
            }
            novas.add(String.join(";", cols));
        }
        CsvUtil.reescrever(ARQ, CAB, novas);
    }

    public void salvarProtocolo(Assinatura a, String protocolo) {
        a.protocolo = protocolo;
        List<String[]> linhas = CsvUtil.lerLinhas(ARQ);
        List<String> novas = new ArrayList<>();
        for (String[] cols : linhas) {
            if (Long.parseLong(cols[0]) == a.id) {
                cols[8] = protocolo;
            }
            novas.add(String.join(";", cols));
        }
        CsvUtil.reescrever(ARQ, CAB, novas);
    }

    private String serializar(Assinatura a) {
        return a.id + ";" + a.assinanteId + ";" + a.planoId + ";" + a.cestaId + ";" +
                (a.enderecoId == null ? "" : a.enderecoId) + ";" +
                a.dataInicio + ";" + a.status + ";" + a.proximaEntrega + ";" +
                (a.protocolo == null ? "" : a.protocolo);
    }
}
