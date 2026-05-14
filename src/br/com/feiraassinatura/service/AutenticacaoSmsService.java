package br.com.feiraassinatura.service;

import br.com.feiraassinatura.domain.Assinante;
import br.com.feiraassinatura.domain.CodigoVerificacao;
import br.com.feiraassinatura.gateway.SmsGateway;
import br.com.feiraassinatura.repository.AssinanteRepository;
import br.com.feiraassinatura.repository.CodigoVerificacaoRepository;
import java.time.LocalDateTime;

public class AutenticacaoSmsService {
    private static final String CODIGO_TESTE = "123456";

    private final SmsGateway smsGateway;
    private final CodigoVerificacaoRepository codigoRepository;
    private final AssinanteRepository assinanteRepository;

    public AutenticacaoSmsService(
            SmsGateway smsGateway,
            CodigoVerificacaoRepository codigoRepository,
            AssinanteRepository assinanteRepository) {
        this.smsGateway = smsGateway;
        this.codigoRepository = codigoRepository;
        this.assinanteRepository = assinanteRepository;
    }

    public void enviarCodigoConfirmacao(String celular) {
        String celularNormalizado = normalizarCelular(celular);
        CodigoVerificacao codigo = new CodigoVerificacao(
                celularNormalizado,
                CODIGO_TESTE,
                LocalDateTime.now(),
                false);
        smsGateway.enviarSms(celularNormalizado, "Codigo de confirmacao: " + CODIGO_TESTE);
        codigoRepository.salvar(codigo);
    }

    public boolean validarCodigo(String celular, String codigoInformado) {
        String celularNormalizado = normalizarCelular(celular);
        return codigoRepository.buscarCodigoValido(celularNormalizado, codigoInformado.trim())
                .map(codigo -> {
                    codigoRepository.marcarComoUtilizado(codigo);
                    return true;
                })
                .orElse(false);
    }

    public Assinante obterOuCriarAssinante(String celular) {
        return assinanteRepository.buscarOuCriarPorCelular(normalizarCelular(celular));
    }

    private String normalizarCelular(String celular) {
        String normalizado = celular == null ? "" : celular.replaceAll("\\D", "");
        if (normalizado.length() < 10) {
            throw new IllegalArgumentException("Informe um numero de celular valido com DDD.");
        }
        return normalizado;
    }
}
