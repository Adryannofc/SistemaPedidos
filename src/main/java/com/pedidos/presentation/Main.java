package com.pedidos.presentation;

import com.pedidos.application.service.*;
import com.pedidos.domain.repository.*;
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
        AreaEntregaRepositoryMemoria areaEntregaRepo = new AreaEntregaRepositoryMemoria();
        HorarioFuncionamentoRepositoryMemoria horarioRepo = new HorarioFuncionamentoRepositoryMemoria();
        EnderecoRepositoryMemoria enderecoRepo = new EnderecoRepositoryMemoria();

        AutenticacaoService authService = new AutenticacaoService(adminRepo, restauranteRepo, clienteRepo);
        AdminService adminService = new AdminService(adminRepo, authService, restauranteRepo);
        ClienteService clienteService = new ClienteService(clienteRepo, authService, adminRepo, restauranteRepo);
        CategoriaService categoriaService   = new CategoriaService(categoriaGlobalRepo, categoriaCardapioRepo, restauranteRepo, produtoRepo);

        DataSeeder seeder = new DataSeeder(adminRepo, clienteRepo, restauranteRepo, authService, produtoRepo, categoriaGlobalRepo, categoriaCardapioRepo, enderecoRepo, horarioRepo, areaEntregaRepo);
        seeder.popular();

        new MenuLogin(authService, adminService, clienteService, categoriaService).iniciar();
    }
}