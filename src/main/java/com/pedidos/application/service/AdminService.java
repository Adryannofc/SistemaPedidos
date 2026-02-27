package com.pedidos.application.service;

import com.pedidos.domain.model.Admin;
import com.pedidos.domain.model.Usuario;
import com.pedidos.domain.repository.AdminRepository;

public class AdminService {
    private final AdminRepository adminRepository;
    private final AutenticacaoService autenticacaoService;

    public AdminService(AdminRepository adminRepository, AutenticacaoService autenticacaoService) {
        this.adminRepository = adminRepository;
        this.autenticacaoService = autenticacaoService;
    }

    public void cadastrarAdmin(String nome, String email, String senha) {
        if(adminRepository.buscarPorEmail(email).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        String hash = autenticacaoService.hashSenha(senha);
        Admin admin = new Admin(nome, email, hash);
        adminRepository.salvar(admin);
        System.out.println("Administrador cadastrado com sucesso.");
    }

    public void visualizarPerfil(Usuario usuario) {
        usuario.exibirDetalhes();
    }

    public void alterarSenha(Usuario usuario, String senhaAtual, String novaSenha, String confirmacaoSenha) {
        String hashAtual = autenticacaoService.hashSenha(senhaAtual);
        if(!usuario.verificarSenha(hashAtual)) {
            throw new IllegalArgumentException("Senha atual incorreta.");
        }

        if(!novaSenha.equals(confirmacaoSenha)) {
            throw new IllegalArgumentException("Nova senha e confirmação não coincidem.");
        }

        String novoHash = autenticacaoService.hashSenha(novaSenha);
        usuario.setSenhaHash(novoHash);
        adminRepository.salvar(usuario);
        System.out.println("Senha alterada com sucesso.");
    }
}
