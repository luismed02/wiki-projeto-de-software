package br.com.feiraassinatura.service;

import br.com.feiraassinatura.domain.PlanoAssinatura;
import br.com.feiraassinatura.domain.Produto;
import br.com.feiraassinatura.domain.TipoProduto;
import br.com.feiraassinatura.repository.CatalogoProdutoRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CatalogoSemanaService {
    private final CatalogoProdutoRepository repository;

    public CatalogoSemanaService(CatalogoProdutoRepository repository) {
        this.repository = repository;
    }

    public List<Produto> listarItensDisponiveis(TipoProduto tipo, PlanoAssinatura plano) {
        if (plano == null) {
            throw new IllegalStateException("Selecione um plano antes de montar a cesta.");
        }
        return repository.buscarProdutosDaSemana(tipo);
    }

    public Map<Produto, Integer> validarDisponibilidade(TipoProduto tipo, Map<Long, Integer> itensEscolhidos) {
        Map<Produto, Integer> itensValidados = new LinkedHashMap<>();
        for (Map.Entry<Long, Integer> entry : itensEscolhidos.entrySet()) {
            int quantidade = entry.getValue();
            if (quantidade <= 0) {
                continue;
            }
            Produto produto = repository.buscarPorId(entry.getKey())
                    .orElseThrow(() -> new IllegalArgumentException("Produto nao encontrado: " + entry.getKey()));
            if (produto.getTipo() != tipo) {
                throw new IllegalArgumentException("Produto " + produto.getNome() + " nao pertence ao tipo " + tipo + ".");
            }
            if (quantidade > produto.getQuantidadeDisponivel()) {
                throw new IllegalArgumentException("Quantidade indisponivel para " + produto.getNome() + ".");
            }
            itensValidados.put(produto, quantidade);
        }
        return itensValidados;
    }
}
