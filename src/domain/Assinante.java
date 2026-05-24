package domain;

import java.time.LocalDate;

public class Assinante {
    public Long id;
    public String nome;
    public String celular;
    public String email;
    public LocalDate dataCadastro;

    public Assinante() {}

    public Assinante(Long id, String nome, String celular, String email, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }
}
