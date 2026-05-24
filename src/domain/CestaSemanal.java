package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CestaSemanal {
    public Long id;
    public Long planoId;
    public LocalDate dataComposicao;
    public StatusCesta status;
    public List<ItemCesta> itens = new ArrayList<>();

    public CestaSemanal() {}

    public CestaSemanal(Long id, Long planoId, LocalDate dataComposicao, StatusCesta status) {
        this.id = id;
        this.planoId = planoId;
        this.dataComposicao = dataComposicao;
        this.status = status;
    }

    public void adicionarItem(Long produtoId, int quantidade) {
        itens.add(new ItemCesta(id, produtoId, quantidade));
    }

    public void confirmar() {
        this.status = StatusCesta.CONFIRMADA;
    }

    public void aprovar() {
        this.status = StatusCesta.APROVADA;
    }
}
