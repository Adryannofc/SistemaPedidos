package com.pedidos.domain.model;

import com.pedidos.domain.enums.TipoUsuario;

public class Admin extends Usuario {
    public Admin(String nome, String email, String senhaHash) {
        super(nome, email, senhaHash, TipoUsuario.ADMIN);

    }

    @Override
    public String toString() {
        return "Admin{nome=" + getNome() + ", email=" + getEmail() + "}";
    }
}