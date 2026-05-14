package br.com.feiraassinatura.gateway;

public class ResultadoAutorizacao {
    private final boolean aprovado;
    private final String codigoTransacao;
    private final String mensagem;

    public ResultadoAutorizacao(boolean aprovado, String codigoTransacao, String mensagem) {
        this.aprovado = aprovado;
        this.codigoTransacao = codigoTransacao;
        this.mensagem = mensagem;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public String getMensagem() {
        return mensagem;
    }
}
