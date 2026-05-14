package br.com.feiraassinatura.domain;

public class PlanoAssinatura {
    private final long id;
    private final String nome;
    private final double preco;
    private final int limiteFrutas;
    private final int limiteLegumes;
    private final int limiteVerduras;
    private final String descricao;
    private final boolean ativo;

    public PlanoAssinatura(
            long id,
            String nome,
            double preco,
            int limiteFrutas,
            int limiteLegumes,
            int limiteVerduras,
            String descricao,
            boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.limiteFrutas = limiteFrutas;
        this.limiteLegumes = limiteLegumes;
        this.limiteVerduras = limiteVerduras;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getLimiteFrutas() {
        return limiteFrutas;
    }

    public int getLimiteLegumes() {
        return limiteLegumes;
    }

    public int getLimiteVerduras() {
        return limiteVerduras;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public int limitePara(TipoProduto tipo) {
        return switch (tipo) {
            case FRUTA -> limiteFrutas;
            case LEGUME -> limiteLegumes;
            case VERDURA -> limiteVerduras;
        };
    }
}
