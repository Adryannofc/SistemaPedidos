package com.pedidos.domain.model;

import java.util.UUID;

public class Admin extends Usuario{

    private int nivelAcesso;

    public Admin(UUID uuid, String nome, String email, String senha, int nivelAcesso) {
        super(uuid, nome, email, senha);

        this.nivelAcesso = nivelAcesso;

    }

    public int getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(int nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
}
