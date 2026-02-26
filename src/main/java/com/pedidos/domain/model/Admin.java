package com.pedidos.domain.model;

import com.pedidos.domain.enums.TipoUsuario;

import java.util.UUID;

public class Admin extends Usuario {

    private final TipoUsuario tipo = TipoUsuario.ADMIN;

    public Admin(UUID uuid, String nome, String email, String senhaHash) {
        super(uuid, nome, email, senhaHash);
    }

    public Admin(String nome, String email, String senhaHash) {
        super(UUID.randomUUID(), nome, email, senhaHash);
    }

    public TipoUsuario getTipo() {
        return tipo;
    }
}

