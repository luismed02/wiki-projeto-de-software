package br.com.feiraassinatura.dto;

import br.com.feiraassinatura.domain.Assinatura;
import br.com.feiraassinatura.domain.EnderecoEntrega;
import br.com.feiraassinatura.domain.ProtocoloAssinatura;

public class ConfirmacaoAssinatura {
    private final Assinatura assinatura;
    private final EnderecoEntrega enderecoEntrega;
    private final ProtocoloAssinatura protocolo;

    public ConfirmacaoAssinatura(
            Assinatura assinatura,
            EnderecoEntrega enderecoEntrega,
            ProtocoloAssinatura protocolo) {
        this.assinatura = assinatura;
        this.enderecoEntrega = enderecoEntrega;
        this.protocolo = protocolo;
    }

    public Assinatura getAssinatura() {
        return assinatura;
    }

    public EnderecoEntrega getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public ProtocoloAssinatura getProtocolo() {
        return protocolo;
    }
}
