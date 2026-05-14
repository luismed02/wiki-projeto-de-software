package br.com.feiraassinatura.domain;

import java.time.LocalDate;

public class Assinante {
    private final long id;
    private final String nome;
    private final String celular;
    private final String email;
    private final LocalDate dataCadastro;

    public Assinante(long id, String nome, String celular, String email, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.celular = celular;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCelular() {
        return celular;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }
}
