package domain;

public class Produto {
    public Long id;
    public String nome;
    public TipoProduto tipo;
    public int quantidadeDisponivel;

    public Produto() {}

    public Produto(Long id, String nome, TipoProduto tipo, int quantidadeDisponivel) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
}
