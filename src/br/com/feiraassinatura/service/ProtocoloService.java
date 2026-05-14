package br.com.feiraassinatura.service;

import br.com.feiraassinatura.domain.Assinatura;
import br.com.feiraassinatura.domain.ProtocoloAssinatura;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProtocoloService {
    public ProtocoloAssinatura gerarProtocolo(Assinatura assinatura) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String numero = "AF-" + assinatura.getId() + "-" + timestamp;
        return new ProtocoloAssinatura(numero, LocalDateTime.now());
    }
}
