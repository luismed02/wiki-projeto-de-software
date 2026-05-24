package service;

import domain.Assinatura;
import repository.AssinaturaRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProtocoloService {
    private final AssinaturaRepository repo;

    public ProtocoloService(AssinaturaRepository repo) {
        this.repo = repo;
    }

    public String gerarProtocolo(Assinatura assinatura) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String protocolo = "PROT-" + timestamp + "-" + assinatura.id;
        repo.salvarProtocolo(assinatura, protocolo);
        return protocolo;
    }
}
