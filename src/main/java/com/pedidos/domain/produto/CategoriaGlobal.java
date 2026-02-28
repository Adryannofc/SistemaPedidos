package com.pedidos.domain.produto;

import java.util.UUID;

public class CategoriaGlobal {
    private final String id;
    private String nome;

    public CategoriaGlobal(String id, String nome) {
        this.id = (id != null) ? id : UUID.randomUUID().toString();
        this.nome = nome;
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


    @Override
    public String toString() {
        return "CategoriaGlobal{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                '}';
    }
}
