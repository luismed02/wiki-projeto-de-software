package br.com.feiraassinatura.service;

import br.com.feiraassinatura.domain.CestaSemanal;
import br.com.feiraassinatura.domain.PlanoAssinatura;
import br.com.feiraassinatura.domain.Produto;
import br.com.feiraassinatura.domain.StatusCesta;
import br.com.feiraassinatura.domain.TipoProduto;
import br.com.feiraassinatura.repository.CestaSemanalRepository;
import java.time.LocalDate;
import java.util.Map;

public class CestaSemanalService {
    private final CestaSemanalRepository repository;

    public CestaSemanalService(CestaSemanalRepository repository) {
        this.repository = repository;
    }

    public CestaSemanal criarCestaSemanal(PlanoAssinatura plano) {
        CestaSemanal cesta = new CestaSemanal(repository.proximoId(), LocalDate.now(), StatusCesta.MONTANDO);
        repository.salvar(cesta, plano);
        return cesta;
    }

    public void adicionarItens(
            CestaSemanal cesta,
            TipoProduto tipo,
            Map<Produto, Integer> itensEscolhidos,
            PlanoAssinatura plano) {
        int total = itensEscolhidos.values().stream().mapToInt(Integer::intValue).sum();
        if (total <= 0) {
            throw new IllegalArgumentException("Escolha pelo menos um item de " + tipo.getDescricaoPlural() + ".");
        }
        int limite = plano.limitePara(tipo);
        if (total > limite) {
            throw new IllegalArgumentException("O plano permite ate " + limite + " item(ns) de " + tipo.getDescricaoPlural() + ".");
        }

        cesta.removerItensDoTipo(tipo);
        for (Map.Entry<Produto, Integer> entry : itensEscolhidos.entrySet()) {
            cesta.adicionarItem(entry.getKey(), entry.getValue());
        }
        repository.salvarItens(cesta, cesta.getItens());
    }

    public void confirmarCesta(CestaSemanal cesta) {
        for (TipoProduto tipo : TipoProduto.values()) {
            if (cesta.totalItensPorTipo(tipo) <= 0) {
                throw new IllegalStateException("A cesta precisa ter itens de " + tipo.getDescricaoPlural() + ".");
            }
        }
        cesta.confirmar();
        repository.atualizarStatus(cesta, cesta.getStatus());
    }

    public void aprovarCesta(CestaSemanal cesta) {
        cesta.aprovar();
        repository.atualizarStatus(cesta, cesta.getStatus());
    }
}
