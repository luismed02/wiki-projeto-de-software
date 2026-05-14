package br.com.feiraassinatura.domain;

public enum TipoProduto {
    FRUTA("frutas"),
    LEGUME("legumes"),
    VERDURA("verduras");

    private final String descricaoPlural;

    TipoProduto(String descricaoPlural) {
        this.descricaoPlural = descricaoPlural;
    }

    public String getDescricaoPlural() {
        return descricaoPlural;
    }
}
