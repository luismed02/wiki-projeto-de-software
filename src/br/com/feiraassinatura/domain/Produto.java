package br.com.feiraassinatura.domain;

public class Produto {
    private final long id;
    private final String nome;
    private final TipoProduto tipo;
    private final int quantidadeDisponivel;
    private final boolean disponivel;

    public Produto(long id, String nome, TipoProduto tipo, int quantidadeDisponivel, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.disponivel = disponivel;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoProduto getTipo() {
        return tipo;
    }

    public int getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
}
