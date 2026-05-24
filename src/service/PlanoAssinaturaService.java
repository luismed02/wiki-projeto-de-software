package service;

import domain.PlanoAssinatura;
import repository.PlanoAssinaturaRepository;
import java.util.List;

public class PlanoAssinaturaService {
    private final PlanoAssinaturaRepository repo;

    public PlanoAssinaturaService(PlanoAssinaturaRepository repo) {
        this.repo = repo;
    }

    public List<PlanoAssinatura> listarPlanosDisponiveis() {
        return repo.buscarPlanosAtivos();
    }

    public PlanoAssinatura obterPlano(Long idPlano) {
        return repo.buscarPorId(idPlano);
    }

    public double calcularTotal(PlanoAssinatura plano) {
        return plano.precoSemanal;
    }
}
