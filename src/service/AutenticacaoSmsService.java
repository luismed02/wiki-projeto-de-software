package service;

import domain.CodigoVerificacao;
import gateway.SmsGateway;
import repository.CodigoVerificacaoRepository;
import java.time.LocalDateTime;
import java.util.Random;

public class AutenticacaoSmsService {
    private final SmsGateway smsGateway;
    private final CodigoVerificacaoRepository codigoRepo;
    private final Random random = new Random();

    public AutenticacaoSmsService(SmsGateway smsGateway, CodigoVerificacaoRepository codigoRepo) {
        this.smsGateway = smsGateway;
        this.codigoRepo = codigoRepo;
    }

    public void enviarCodigoConfirmacao(String celular) {
        String codigo = String.format("%04d", random.nextInt(10000));
        smsGateway.enviarSms(celular, "Seu código de confirmação é: " + codigo);
        codigoRepo.salvar(new CodigoVerificacao(celular, codigo, LocalDateTime.now(), false, false));
    }

    public boolean validarCodigo(String celular, String codigo) {
        CodigoVerificacao c = codigoRepo.buscarCodigoValido(celular, codigo);
        if (c == null) return false;
        codigoRepo.marcarComoUtilizado(celular, codigo);
        return true;
    }
}
