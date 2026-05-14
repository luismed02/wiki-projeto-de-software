package br.com.feiraassinatura.service;

import br.com.feiraassinatura.domain.Assinatura;
import br.com.feiraassinatura.domain.EnderecoEntrega;
import br.com.feiraassinatura.repository.EnderecoEntregaRepository;

public class EnderecoEntregaService {
    private final EnderecoEntregaRepository repository;

    public EnderecoEntregaService(EnderecoEntregaRepository repository) {
        this.repository = repository;
    }

    public EnderecoEntrega validarEndereco(EnderecoEntrega endereco) {
        validarCampo("CEP", endereco.getCep());
        validarCampo("Rua", endereco.getRua());
        validarCampo("Numero", endereco.getNumero());
        validarCampo("Bairro", endereco.getBairro());
        validarCampo("Cidade", endereco.getCidade());
        return endereco;
    }

    public void registrarEndereco(Assinatura assinatura, EnderecoEntrega endereco) {
        repository.salvar(assinatura.getId(), endereco);
    }

    private void validarCampo(String campo, String valor) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " e obrigatorio.");
        }
    }
}
