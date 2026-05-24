package service;

import domain.CestaSemanal;
import domain.ItemCesta;
import domain.PlanoAssinatura;
import domain.StatusCesta;
import repository.CestaSemanalRepository;
import java.time.LocalDate;
import java.util.List;

public class CestaSemanalService {
    private final CestaSemanalRepository repo;

    public CestaSemanalService(CestaSemanalRepository repo) {
        this.repo = repo;
    }

    public CestaSemanal criarCestaSemanal(PlanoAssinatura plano) {
        CestaSemanal cesta = new CestaSemanal(null, plano.id, LocalDate.now(), StatusCesta.MONTANDO);
        return repo.salvar(cesta);
    }

    public void adicionarItens(CestaSemanal cesta, List<ItemCesta> itens) {
        for (ItemCesta item : itens) {
            item.cestaId = cesta.id;
            cesta.itens.add(item);
        }
        repo.salvarItens(cesta, itens);
    }

    public void confirmarCesta(CestaSemanal cesta) {
        cesta.confirmar();
        repo.atualizarStatus(cesta, StatusCesta.CONFIRMADA);
    }

    public void aprovarCesta(CestaSemanal cesta) {
        cesta.aprovar();
        repo.atualizarStatus(cesta, StatusCesta.APROVADA);
    }
}
