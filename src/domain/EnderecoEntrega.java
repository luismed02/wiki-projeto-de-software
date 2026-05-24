package domain;

public class EnderecoEntrega {
    public Long id;
    public Long assinaturaId;
    public String cep;
    public String rua;
    public String numero;
    public String bairro;
    public String cidade;
    public String complemento;

    public EnderecoEntrega() {}

    public EnderecoEntrega(String cep, String rua, String numero, String bairro, String cidade, String complemento) {
        this.cep = cep;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.complemento = complemento;
    }
}
