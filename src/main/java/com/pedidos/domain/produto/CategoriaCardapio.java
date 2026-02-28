package com.pedidos.domain.produto;

import java.util.UUID;

public class CategoriaCardapio {
    private final String id;
    private String nome;
    private String restauranteId;


    public CategoriaCardapio(String id, String nome, String restauranteId) {
        this.id = (id != null) ? id : UUID.randomUUID().toString();
        this.nome = nome;
        this.restauranteId = restauranteId;
    }

    public String getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(String restauranteId) {
        if (restauranteId == null || restauranteId.isBlank()) {
            throw new IllegalArgumentException("O ID do restaurante é obrigatório para esta categoria.");
        }
        this.restauranteId = restauranteId;
    }

    public String getId() {
        return id;
    }

    public void getNome() {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome da categoria é obrigatório.");
        }
        this.nome = nome.trim();
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "CategoriaCardapio{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", restauranteId='" + restauranteId + '\'' +
                '}';
    }
}
