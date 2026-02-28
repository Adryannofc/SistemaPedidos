package com.pedidos.presentation.admin;
import com.pedidos.application.service.AdminService;
import com.pedidos.domain.model.Admin;
import com.pedidos.presentation.util.EntradaSegura;
import com.pedidos.presentation.util.TerminalUtils;

import java.util.Scanner;

public class MenuAdmin {
    private final AdminService adminService;
    private final Scanner scanner;

    public MenuAdmin(AdminService adminService, Scanner scanner){
        this.adminService = adminService;
        this.scanner = scanner;


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
                        // Listar Restaurantes
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 2:
                    try {
                        // Aprovar Restaurante
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 3:
                    try {
                        // Bloquear Restaurante
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 4:
                    try {
                        // Remover Restaurante
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 5:
                    try {
                        // Gerenciar Categorias Globais
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 6:
                    try {
                        // Alterar Senha
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    TerminalUtils.pausar();
                    break;

                case 7:
                    try {
                        // Visualizar Perfil
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
