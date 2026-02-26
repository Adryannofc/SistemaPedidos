package com.pedidos.domain.model;

import com.pedidos.domain.enums.TipoUsuario;

import java.util.UUID;

public class Admin extends Usuario{

    public Admin(String nome, String email, String senhaHash) {
        super(nome, email, senhaHash, TipoUsuario.ADMIN);

    }
}