package com.pedidos.presentation;

import com.pedidos.application.service.AdminService;
import com.pedidos.application.service.AutenticacaoService;
import com.pedidos.application.service.CategoriaService;
import com.pedidos.infra.repository.impl.*;
import com.pedidos.infra.seed.DataSeeder;
import com.pedidos.presentation.menu.MenuLogin;


public class Main {
    public static void main(String[] args) {
        AdminRepositoryMemoria adminRepo            = new AdminRepositoryMemoria();
        RestauranteRepositoryMemoria restauranteRepo = new RestauranteRepositoryMemoria();
        ClienteRepositoryMemoria clienteRepo        = new ClienteRepositoryMemoria();
        CategoriaGlobalRepositoryMemoria categoriaGlobalRepo     = new CategoriaGlobalRepositoryMemoria();
        CategoriaCardapioRepositoryMemoria categoriaCardapioRepo = new CategoriaCardapioRepositoryMemoria();
        ProdutoRepositoryMemoria produtoRepo        = new ProdutoRepositoryMemoria();

        AutenticacaoService authService = new AutenticacaoService(adminRepo, restauranteRepo, clienteRepo);
        AdminService adminService = new AdminService(adminRepo, authService, restauranteRepo);
        CategoriaService categoriaService   = new CategoriaService(categoriaGlobalRepo, categoriaCardapioRepo, restauranteRepo, produtoRepo);

        new DataSeeder(adminRepo, clienteRepo, restauranteRepo, authService).popular();

        new MenuLogin(authService, adminService).iniciar();
    }
}