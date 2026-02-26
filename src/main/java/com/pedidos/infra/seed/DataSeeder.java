package com.pedidos.infra.seed;

import com.pedidos.application.service.AutenticacaoService;
import com.pedidos.domain.model.Admin;
import com.pedidos.domain.model.Cliente;
import com.pedidos.domain.model.Restaurante;
import com.pedidos.domain.repository.AdminRepository;
import com.pedidos.domain.repository.ClienteRepository;
import com.pedidos.domain.repository.RestauranteRepository;

public class DataSeeder {

    private final AdminRepository adminRepo;
    private final ClienteRepository clienteRepo;
    private final RestauranteRepository restRepo;
    private final AutenticacaoService authService;

    public DataSeeder(AdminRepository adminRepo, ClienteRepository clienteRepo,
                      RestauranteRepository restRepo, AutenticacaoService authService) {
        this.adminRepo = adminRepo;
        this.clienteRepo = clienteRepo;
        this.restRepo = restRepo;
        this.authService = authService;
    }

    public void popular() {
        // Admin
        adminRepo.salvar(new Admin(
                "Administrador",
                "admin@delivery.com",
                authService.hashSenha("admin123")
        ));

        // Restaurante
        restRepo.salvar(new Restaurante(
                "Pizza do Bairro",
                "pizza@delivery.com",
                authService.hashSenha("rest123"),
                "12345678000195"
        ));

        // Cliente
        clienteRepo.salvar(new Cliente(
                "João Silva",
                "joao@email.com",
                authService.hashSenha("cliente123"),
                "12345678901",
                "11999990001"
        ));

        System.out.println("Dados de teste carregados.");
    }
}