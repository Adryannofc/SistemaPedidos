package com.pedidos.presentation;

import com.pedidos.application.service.AdminService;
import com.pedidos.application.service.AutenticacaoService;
import com.pedidos.infra.repository.impl.AdminRepositoryMemoria;
import com.pedidos.infra.repository.impl.ClienteRepositoryMemoria;
import com.pedidos.infra.repository.impl.RestauranteRepositoryMemoria;
import com.pedidos.infra.seed.DataSeeder;
import com.pedidos.presentation.menu.MenuLogin;

public class Main {
    public static void main(String[] args) {
        AdminRepositoryMemoria adminRepo = new AdminRepositoryMemoria();
        RestauranteRepositoryMemoria restauranteRepo = new RestauranteRepositoryMemoria();
        ClienteRepositoryMemoria clienteRepo = new ClienteRepositoryMemoria();

        AutenticacaoService authService = new AutenticacaoService(adminRepo, restauranteRepo, clienteRepo);
        AdminService adminService = new AdminService(adminRepo, authService);

        new DataSeeder(adminRepo, clienteRepo, restauranteRepo, authService).popular();

        new MenuLogin(authService, adminService).iniciar();
    }
}