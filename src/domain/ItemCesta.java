package domain;

public class ItemCesta {
    public Long cestaId;
    public Long produtoId;
    public int quantidade;

    public ItemCesta() {}

    public ItemCesta(Long cestaId, Long produtoId, int quantidade) {
        this.cestaId = cestaId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }
}
