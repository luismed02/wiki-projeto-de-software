package repository;

import domain.CodigoVerificacao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CodigoVerificacaoRepository {
    private static final String ARQ = CsvUtil.DATA_DIR + "/codigos_verificacao.csv";
    private static final String CAB = "celular;codigo;dataEnvio;utilizado;expirado";

    public void salvar(CodigoVerificacao c) {
        String linha = c.celular + ";" + c.codigo + ";" + c.dataEnvio + ";" + c.utilizado + ";" + c.expirado;
        CsvUtil.anexarLinha(ARQ, CAB, linha);
    }

    public CodigoVerificacao buscarCodigoValido(String celular, String codigo) {
        for (String[] cols : CsvUtil.lerLinhas(ARQ)) {
            if (cols[0].equals(celular) && cols[1].equals(codigo) && !Boolean.parseBoolean(cols[3]) && !Boolean.parseBoolean(cols[4])) {
                return new CodigoVerificacao(cols[0], cols[1], LocalDateTime.parse(cols[2]),
                        Boolean.parseBoolean(cols[3]), Boolean.parseBoolean(cols[4]));
            }
        }
        return null;
    }

    public void marcarComoUtilizado(String celular, String codigo) {
        List<String[]> linhas = CsvUtil.lerLinhas(ARQ);
        List<String> novas = new ArrayList<>();
        for (String[] cols : linhas) {
            if (cols[0].equals(celular) && cols[1].equals(codigo)) {
                cols[3] = "true";
            }
            novas.add(String.join(";", cols));
        }
        CsvUtil.reescrever(ARQ, CAB, novas);
    }
}
