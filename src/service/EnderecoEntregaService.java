package service;

import domain.EnderecoEntrega;
import repository.EnderecoEntregaRepository;

public class EnderecoEntregaService {
    private final EnderecoEntregaRepository repo;

    public EnderecoEntregaService(EnderecoEntregaRepository repo) {
        this.repo = repo;
    }

    public boolean validarEndereco(EnderecoEntrega e) {
        return e != null
                && naoVazio(e.cep)
                && naoVazio(e.rua)
                && naoVazio(e.numero)
                && naoVazio(e.bairro)
                && naoVazio(e.cidade);
    }

    public EnderecoEntrega registrarEndereco(Long assinaturaId, EnderecoEntrega e) {
        e.assinaturaId = assinaturaId;
        return repo.salvar(e);
    }

    private boolean naoVazio(String s) {
        return s != null && !s.isBlank();
    }
}
