package domain;

public class PlanoAssinatura {
    public Long id;
    public String nome;
    public String descricao;
    public double precoSemanal;
    public int limiteFrutas;
    public int limiteLegumes;
    public int limiteVerduras;

    public PlanoAssinatura() {}

    public PlanoAssinatura(Long id, String nome, String descricao, double precoSemanal,
                           int limiteFrutas, int limiteLegumes, int limiteVerduras) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.precoSemanal = precoSemanal;
        this.limiteFrutas = limiteFrutas;
        this.limiteLegumes = limiteLegumes;
        this.limiteVerduras = limiteVerduras;
    }

    public int limitePara(TipoProduto tipo) {
        return switch (tipo) {
            case FRUTA -> limiteFrutas;
            case LEGUME -> limiteLegumes;
            case VERDURA -> limiteVerduras;
        };
    }
}
