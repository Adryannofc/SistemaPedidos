package com.pedidos.application.service;

import com.pedidos.domain.model.Admin;
import com.pedidos.domain.model.Restaurante;
import com.pedidos.domain.model.Usuario;
import com.pedidos.domain.repository.AdminRepository;
import com.pedidos.domain.repository.RestauranteRepository;

import java.util.List;

public class AdminService {
    private final AdminRepository adminRepository;
    private final AutenticacaoService autenticacaoService;
    private final RestauranteRepository restauranteRepository;

    public AdminService(AdminRepository adminRepository, AutenticacaoService autenticacaoService, RestauranteRepository restauranteRepository) {
        this.adminRepository = adminRepository;
        this.autenticacaoService = autenticacaoService;
        this.restauranteRepository = restauranteRepository;
    }

    public void cadastrarAdmin(String nome, String email, String senha) {
        if (adminRepository.buscarPorEmail(email).isPresent()) {
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
        if (!usuario.verificarSenha(hashAtual)) {
            throw new IllegalArgumentException("Senha atual incorreta.");
        }

        if (!novaSenha.equals(confirmacaoSenha)) {
            throw new IllegalArgumentException("Nova senha e confirmação não coincidem.");
        }

        String novoHash = autenticacaoService.hashSenha(novaSenha);
        usuario.setSenhaHash(novoHash);
        adminRepository.salvar(usuario);
        System.out.println("Senha alterada com sucesso.");
    }

    // --- métodos ADM-02 — gestão de restaurantes ---

    public List<Restaurante> listarRestaurantes() {
        return restauranteRepository.listarRestaurantes();
    }

    public void aprovarRestaurante(String id) {
        Restaurante restaurante = buscarRestaurantePorId(id);
        restaurante.setStatusAtivo(true);
        restauranteRepository.salvar(restaurante);
    }

    public void bloquearRestaurante(String id) {
        Restaurante restaurante = buscarRestaurantePorId(id);
        restaurante.setStatusAtivo(false);
        restauranteRepository.salvar(restaurante);
    }

    public void removerRestaurante(String id) {
        buscarRestaurantePorId(id);
        restauranteRepository.deletar(id);
    }

    // evita repetir a busca+validação
    private Restaurante buscarRestaurantePorId(String id) {
        return restauranteRepository.listarRestaurantes().stream()
                .filter(r -> r.getId().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Restaurante não encontrado."));
    }
}