package com.pedidos.domain.model;

import java.util.UUID;

public class CategoriaGlobal {
    private final String id;
    private String nome;
    private String descricao;

    public CategoriaGlobal(String nome, String descricao) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
