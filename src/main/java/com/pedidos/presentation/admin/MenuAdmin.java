package com.pedidos.presentation.admin;
import com.pedidos.application.service.AdminService;
import com.pedidos.domain.model.Admin;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;
import com.pedidos.presentation.admin.MenuCategorias;

import java.util.Scanner;

public class MenuAdmin {
    private final AdminService adminService;
    private final Scanner scanner;
    private final MenuCategorias menuCategorias;

    public MenuAdmin(AdminService adminService, Scanner scanner, MenuCategorias menuCategorias){
        this.adminService = adminService;
        this.scanner = scanner;
        this.menuCategorias = menuCategorias;


    }

    public void exibir(Admin adminLogado){

        while (true){
            TerminalUtils.limparTela();
            TerminalUtils.cabecalho(adminLogado.getNome());
            System.out.println("1 - Listar Restaurantes");
            System.out.println("2 - Aprovar Restaurante");
            System.out.println("3 - Bloquear Restaurante");
            System.out.println("4 - Remover Restaurante");
            System.out.println("5 - Gerenciar Categorias Globais");
            System.out.println("6 - Alterar Senha");
            System.out.println("7 - Visualizar Perfil");
            System.out.println("0 - Logout");
            System.out.println();
            System.out.print("Escolha uma opção: ");

            int opcao = EntradaSegura.lerOpcao(scanner,0,7);

            switch (opcao) {
                case 1:
                    try {
                        //  TODO: Listar Restaurantes
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 2:
                    try {
                        //TODO: Aprovar Restaurante
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 3:
                    try {
                        //TODO:Bloquear Restaurante
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 4:
                    try {
                        // TODO:Remover Restaurante
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 5:
                    try {
                        //TODO: Gerenciar Categorias Globais
                        menuCategorias.exibir();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 6:
                    try {
                        //TODO: Alterar Senha
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 7:
                    try {
                        //TODO: Visualizar Perfil
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    TerminalUtils.pausar();
            }
        }
    }
}
