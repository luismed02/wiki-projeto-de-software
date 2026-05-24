package service;

import domain.PlanoAssinatura;
import domain.Produto;
import domain.TipoProduto;
import repository.CatalogoProdutoRepository;
import java.util.List;

public class CatalogoSemanaService {
    private final CatalogoProdutoRepository repo;

    public CatalogoSemanaService(CatalogoProdutoRepository repo) {
        this.repo = repo;
    }

    public List<Produto> listarItensDisponiveis(TipoProduto tipo, PlanoAssinatura plano) {
        return repo.buscarProdutosDaSemana(tipo);
    }
}
