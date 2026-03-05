package com.pedidos.presentation.menu;

import com.pedidos.application.service.*;
import com.pedidos.domain.model.*;
import com.pedidos.presentation.admin.MenuCategorias;
import com.pedidos.presentation.restaurante.*;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.Scanner;

public class MenuLogin {
    private final AutenticacaoService autenticacaoService;
    private final AdminService adminService;
    private final ClienteService clienteService;
    private final CategoriaService categoriaService;
    private final ProdutoService produtoService;
    private final RestauranteService restauranteService;
    private final AreaEntregaService areaEntregaService;
    private final HorarioService horarioService;
    private final HistoricoService historicoService;
    private final PedidoService pedidoService;
    private final EnderecoService enderecoService;
    private final CarrinhoService carrinhoService;
    private final FavoritosService favoritosService;

    private final Scanner scan = new Scanner(System.in);

    public MenuLogin(AutenticacaoService autenticacaoService,
                     AdminService adminService,
                     ClienteService clienteService,
                     CategoriaService categoriaService,
                     ProdutoService produtoService,
                     RestauranteService restauranteService,
                     AreaEntregaService areaEntregaService,
                     HorarioService horarioService,
                     HistoricoService historicoService,
                     PedidoService pedidoService,
                     EnderecoService enderecoService,
                     CarrinhoService carrinhoService,
                     FavoritosService favoritosService) {
        this.autenticacaoService = autenticacaoService;
        this.adminService = adminService;
        this.clienteService = clienteService;
        this.categoriaService = categoriaService;
        this.produtoService = produtoService;
        this.restauranteService = restauranteService;
        this.areaEntregaService = areaEntregaService;
        this.horarioService = horarioService;
        this.historicoService = historicoService;
        this.pedidoService = pedidoService;
        this.enderecoService = enderecoService;
        this.carrinhoService = carrinhoService;
        this.favoritosService = favoritosService;
    }

    public void iniciar() {
        while (true) {
            TerminalUtils.limparTela();
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                                    LOGIN                                     ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════╝");

            String email = EntradaSegura.lerString(scan, "E-MAIL: ");
            String senha = EntradaSegura.lerString(scan, "SENHA : ");

            try {
                Usuario usuario = autenticacaoService.autenticar(email, senha);
                System.out.println("Bem-vindo, " + usuario.getNome() + "!");

                switch (usuario.getTipoUsuario()) {
                    case ADMIN -> {
                        Admin adminLogado = (Admin) usuario;
                        MenuCategorias menuCategorias = new MenuCategorias(adminService, categoriaService, scan);
                        new com.pedidos.presentation.admin.MenuAdmin(adminService, scan, menuCategorias)
                                .exibir(adminLogado);
                    }
                    case RESTAURANTE -> {
                        Restaurante restauranteLogado = (Restaurante) usuario;
                        MenuProdutos menuProdutos = new MenuProdutos(produtoService, categoriaService, scan);
                        MenuCategoriasCardapio menuCats = new MenuCategoriasCardapio(categoriaService, scan);
                        MenuAreaEntrega menuArea = new MenuAreaEntrega(areaEntregaService, scan);
                        MenuHorarios menuHorarios = new MenuHorarios(horarioService, scan);
                        MenuHistoricoPedidos menuHistorico = new MenuHistoricoPedidos(historicoService, pedidoService, scan);
                        new MenuRestaurante(
                                menuProdutos, menuCats, menuArea,
                                menuHorarios, menuHistorico,
                                restauranteService, categoriaService, scan
                        ).exibir(restauranteLogado);
                    }
                    case CLIENTE -> {
                        Cliente clienteLogado = (Cliente) usuario;
                        new MenuCliente(
                                clienteLogado,
                                clienteService,
                                enderecoService,
                                historicoService,
                                pedidoService,
                                carrinhoService,
                                favoritosService,
                                produtoService,   // ← novo
                                scan
                        ).iniciar();
                    }
                }
            } catch (RuntimeException e) {
                System.out.println("[ERRO] " + e.getMessage());
                TerminalUtils.pausar();
            }
        }
    }
}