package com.pedidos.presentation;

import com.pedidos.application.service.*;
import com.pedidos.domain.repository.*;
import com.pedidos.infra.repository.impl.*;
import com.pedidos.infra.seed.DataSeeder;
import com.pedidos.presentation.menu.MenuLogin;

public class Main {
    public static void main(String[] args) {

        // --- Repositórios ---
        AdminRepositoryMemoria adminRepo = new AdminRepositoryMemoria();
        RestauranteRepositoryMemoria restauranteRepo = new RestauranteRepositoryMemoria();
        ClienteRepositoryMemoria clienteRepo = new ClienteRepositoryMemoria();
        CategoriaGlobalRepositoryMemoria categoriaGlobalRepo = new CategoriaGlobalRepositoryMemoria();
        CategoriaCardapioRepositoryMemoria categoriaCardapioRepo = new CategoriaCardapioRepositoryMemoria();
        ProdutoRepositoryMemoria produtoRepo = new ProdutoRepositoryMemoria();
        AreaEntregaRepositoryMemoria areaEntregaRepo = new AreaEntregaRepositoryMemoria();
        HorarioFuncionamentoRepositoryMemoria horarioRepo  = new HorarioFuncionamentoRepositoryMemoria();
        EnderecoRepositoryMemoria enderecoRepo = new EnderecoRepositoryMemoria();
        PedidoRepositoryMemoria pedidoRepo = new PedidoRepositoryMemoria();

        // --- Services ---
        AutenticacaoService authService = new AutenticacaoService(adminRepo, restauranteRepo, clienteRepo);
        AdminService adminService = new AdminService(adminRepo, authService, restauranteRepo);
        ClienteService clienteService = new ClienteService(clienteRepo, authService, adminRepo, restauranteRepo);
        CategoriaService categoriaService  = new CategoriaService(categoriaGlobalRepo, categoriaCardapioRepo, restauranteRepo, produtoRepo);
        ProdutoService produtoService = new ProdutoService(produtoRepo);
        RestauranteService restauranteService = new RestauranteService(restauranteRepo, categoriaGlobalRepo, authService);
        AreaEntregaService areaEntregaService = new AreaEntregaService(areaEntregaRepo);
        HorarioService horarioService  = new HorarioService(horarioRepo);

        // --- Seed ---
        DataSeeder seeder = new DataSeeder(
                adminRepo, clienteRepo, restauranteRepo, authService,
                produtoRepo, categoriaGlobalRepo, categoriaCardapioRepo,
                enderecoRepo, horarioRepo, areaEntregaRepo
        );
        seeder.popular();

        // --- Inicia aplicação ---
        new MenuLogin(
                authService, adminService, clienteService,
                categoriaService, produtoService, restauranteService,
                areaEntregaService, horarioService
        ).iniciar();
    }
}