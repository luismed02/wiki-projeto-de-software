package domain;

import java.time.LocalDate;

public class Assinatura {
    public Long id;
    public Long assinanteId;
    public Long planoId;
    public Long cestaId;
    public Long enderecoId;
    public LocalDate dataInicio;
    public StatusAssinatura status;
    public LocalDate proximaEntrega;
    public String protocolo;

    public Assinatura() {}

    public Assinatura(Long assinanteId, Long planoId, Long cestaId) {
        this.assinanteId = assinanteId;
        this.planoId = planoId;
        this.cestaId = cestaId;
        this.dataInicio = LocalDate.now();
        this.proximaEntrega = LocalDate.now().plusDays(7);
        this.status = StatusAssinatura.AGUARDANDO_APROVACAO;
    }

    public void aguardarAprovacao() {
        this.status = StatusAssinatura.AGUARDANDO_APROVACAO;
    }

    public void aprovar() {
        this.status = StatusAssinatura.APROVADA;
    }
}
