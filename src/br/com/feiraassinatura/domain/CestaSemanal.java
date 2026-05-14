package br.com.feiraassinatura.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CestaSemanal {
    private final long id;
    private final LocalDate dataComposicao;
    private StatusCesta status;
    private final List<ItemCesta> itens;

    public CestaSemanal(long id, LocalDate dataComposicao, StatusCesta status) {
        this.id = id;
        this.dataComposicao = dataComposicao;
        this.status = status;
        this.itens = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public LocalDate getDataComposicao() {
        return dataComposicao;
    }

    public StatusCesta getStatus() {
        return status;
    }

    public List<ItemCesta> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public void adicionarItem(Produto produto, int quantidade) {
        itens.add(new ItemCesta(produto, quantidade));
    }

    public void removerItensDoTipo(TipoProduto tipo) {
        itens.removeIf(item -> item.getProduto().getTipo() == tipo);
    }

    public int totalItensPorTipo(TipoProduto tipo) {
        return itens.stream()
                .filter(item -> item.getProduto().getTipo() == tipo)
                .mapToInt(ItemCesta::getQuantidade)
                .sum();
    }

    public void confirmar() {
        status = StatusCesta.CONFIRMADA;
    }

    public void aprovar() {
        status = StatusCesta.APROVADA;
    }
}
