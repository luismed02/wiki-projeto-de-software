package repository;

import domain.PlanoAssinatura;
import java.util.ArrayList;
import java.util.List;

public class PlanoAssinaturaRepository {
    private static final String ARQ = CsvUtil.DATA_DIR + "/planos.csv";

    public List<PlanoAssinatura> buscarPlanosAtivos() {
        List<PlanoAssinatura> planos = new ArrayList<>();
        for (String[] cols : CsvUtil.lerLinhas(ARQ)) {
            planos.add(parse(cols));
        }
        return planos;
    }

    public PlanoAssinatura buscarPorId(Long id) {
        for (String[] cols : CsvUtil.lerLinhas(ARQ)) {
            if (Long.parseLong(cols[0]) == id) return parse(cols);
        }
        return null;
    }

    private PlanoAssinatura parse(String[] cols) {
        return new PlanoAssinatura(
                Long.parseLong(cols[0]),
                cols[1],
                cols[2],
                Double.parseDouble(cols[3]),
                Integer.parseInt(cols[4]),
                Integer.parseInt(cols[5]),
                Integer.parseInt(cols[6])
        );
    }
}
