package com.pedidos.presentation.menu;

import com.pedidos.application.service.AdminService;
import com.pedidos.application.service.AutenticacaoService;
import com.pedidos.application.service.CategoriaService;
import com.pedidos.application.service.ClienteService;
import com.pedidos.domain.model.Admin;
import com.pedidos.domain.model.Usuario;
import com.pedidos.presentation.admin.MenuCategorias;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.Scanner;

public class MenuLogin {
    private final AutenticacaoService autenticacaoService;
    private final AdminService adminService;
    private final ClienteService clienteService;
    private final CategoriaService categoriaService;

    private final Scanner scan = new Scanner(System.in);

    public MenuLogin(AutenticacaoService autenticacaoService,
                     AdminService adminService,
                     ClienteService clienteService,
                     CategoriaService categoriaService) {
        this.autenticacaoService = autenticacaoService;
        this.adminService = adminService;
        this.clienteService = clienteService;
        this.categoriaService = categoriaService;
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
                        // também precisa passar categoriaService e o MenuCategorias
                        MenuCategorias menuCategorias = new MenuCategorias(adminService, categoriaService, scan);
                        new com.pedidos.presentation.admin.MenuAdmin(adminService, scan, menuCategorias).exibir(adminLogado);
                    }
                    case RESTAURANTE -> new MenuRestaurante(usuario).iniciar();
                    case CLIENTE -> new MenuCliente(usuario, clienteService).iniciar();
                }
            } catch (RuntimeException e) {
                System.out.println("[ERRO] " + e.getMessage());
            }
        }
    }
}
