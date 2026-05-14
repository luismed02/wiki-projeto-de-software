package br.com.feiraassinatura.service;

import br.com.feiraassinatura.domain.PlanoAssinatura;
import br.com.feiraassinatura.repository.PlanoAssinaturaRepository;
import java.util.List;

public class PlanoAssinaturaService {
    private final PlanoAssinaturaRepository repository;

    public PlanoAssinaturaService(PlanoAssinaturaRepository repository) {
        this.repository = repository;
    }

    public List<PlanoAssinatura> listarPlanosDisponiveis() {
        return repository.buscarPlanosAtivos();
    }

    public PlanoAssinatura obterPlano(long idPlano) {
        return repository.buscarPorId(idPlano)
                .orElseThrow(() -> new IllegalArgumentException("Plano de assinatura nao encontrado."));
    }

    public double calcularTotal(PlanoAssinatura plano) {
        return plano.getPreco();
    }
}
