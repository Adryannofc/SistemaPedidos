package com.pedidos.domain.model;

import java.util.UUID;

public class CategoriaCardapio {
    private final String id;
    private String nome;
    private String descricao;
    private String restauranteId;

    public CategoriaCardapio(String nome, String descricao, String restauranteId) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.descricao = descricao;
        this.restauranteId = restauranteId;
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

    public String getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(String restauranteId) {
        this.restauranteId = restauranteId;
    }
}
