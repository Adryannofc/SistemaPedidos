package com.pedidos.domain.model;

import com.pedidos.application.service.AutenticacaoService;
import com.pedidos.domain.enums.TipoUsuario;
import com.pedidos.domain.repository.AdminRepository;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.UUID;

public class Admin extends Usuario {
    public Admin(String nome, String email, String senhaHash) {
        super(nome, email, senhaHash, TipoUsuario.ADMIN);

    }

    @Override
    public void exibirDetalhes() {
        TerminalUtils.cabecalho("PAINEL DO ADMINISTRADOR ");
        System.out.println("Id :" + getId());
        System.out.println("Nome  : " + getNome());
        System.out.println("E-mail: " + getEmail());
    }

    @Override
    public String toString() {
        return "Admin{nome=" + getNome() + ", email=" + getEmail() + "}";
    }
}